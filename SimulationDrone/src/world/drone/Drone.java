package world.drone;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.css.Rect;

import com.sun.media.sound.InvalidDataException;

import physics.PhysicsEngine;
import physics.collisions.CollisionTools;
import physics.collisions.colliders.Collider;
import physics.collisions.colliders.Sphere;
import sun.text.resources.is.CollationData_is;
import utilities.Constantes;
import utilities.Vect3;
import world.WorldObject;

import world.*;

import utilities.Vect3;
import physics.collisions.CollisionTools;
import physics.collisions.colliders.*;
import physics.PhysicsEngine;

public class Drone extends WorldObject /*implements Intelligence*/ {

	private static int TotalNbOfDrones=0;//used to generate unique drone id
	
	private int id;
	private DroneCharacteristics characteristics;
	private DroneAI brain;
	private double batteryLevel;//W.h=Joules/3600
	private double payload;//Kg
	private double motorThrottle;//%
	private Vect3 propellerDirection;//the z axis vector in the drone local frame (pointing upwards, represent motor force) - should be updated by the AI
	private Station pluggedStation;//null if not connected to any station for recharging
	private CommunicationModule moduleCOM;
	
	private Mission mission;
	
	public int getId() {
		return id;
	}
	
	
	public CommunicationModule getModuleCOM() {
		return moduleCOM;
	}

	public void setModuleCOM(CommunicationModule moduleCOM) {
		this.moduleCOM = moduleCOM;
	}

	public DroneCharacteristics getCharacteristics() {
		return characteristics;
	}

	public double getBatteryLevel() {
		return batteryLevel;
	}
	public double getBatteryLevelRelative() {
		return batteryLevel/characteristics.getBatteryCapacity();
	}
	public double getPayload() {
		return payload;
	}
	public void setMotorThrottle(double motorThrottle) {
		
		this.motorThrottle=CollisionTools.limit(motorThrottle, 0, 1);
	}
	public double getMotorThrottle() {
		return motorThrottle;
	}
	public Vect3 getPropellerDirection() {
		return propellerDirection;
	}
	public void setPropellerDirection(Vect3 propellerDirection) {
		
		if(propellerDirection==null)
		{
			propellerDirection=new Vect3(0,0,0);
		}
		
		this.propellerDirection = propellerDirection;
	}
	

	public double getMotorOutputPower() { return getMotorConsumption()*characteristics.getMotorEfficiency(); } //W
	public double getMotorConsumption() { return batteryState()*motorThrottle*characteristics.getMotorMaxConsumption(); } //W
	public double getTotalFlyWeight() { return payload+characteristics.getDryWeight(); } //W
	
	
	public DroneAI getBrain() {
		return brain;
	}
	public void setBrain(DroneAI brain) {
		this.brain = brain;
	}
	
	/**
	 * Default constructor
	 * @param colliding
	 * @param dc
	 * @param batteryLevel
	 * @param payload
	 * @param motorThrottle
	 */
	public Drone(Vect3 position, Vect3 speed, Vect3 size, Sphere colliding, DroneCharacteristics dc, 
					double batteryLevel, double payload, double motorThrottle, Mission mission) {
		super(position, speed, size, colliding);
		
		this.id=getNewId();
		this.characteristics = dc;
		this.brain = new DroneAI();
		this.batteryLevel = CollisionTools.limit(0.01*batteryLevel,0,1)*dc.getBatteryCapacity();
		this.payload = payload;
		this.motorThrottle = motorThrottle;
		this.propellerDirection=new Vect3(0,0,1);
		this.moduleCOM=new CommunicationModule(this);
		
		this.mission = mission;
	}
	
	/**
	 * doesn't copy COM module current state
	 * @param d
	 */
	public Drone(Drone d)
	{
		super(d);
		
		id=getNewId();
		characteristics=d.characteristics;
		brain=d.brain;
		batteryLevel=d.batteryLevel;
		payload=d.payload;
		motorThrottle=d.motorThrottle;
		propellerDirection=new Vect3(d.propellerDirection);
		moduleCOM=new CommunicationModule(this);
		
		collider=createSpecificCollider();
		
		mission=new Mission(d.mission);
	}
	
	private int getNewId()
	{
		TotalNbOfDrones++;
		return TotalNbOfDrones;
	}
	
	
	//TODO put this in worldobject parent class
	@Override
	public boolean collideWith(WorldObject w) {
		//compute new position in this function
		
		Collider thiscollider=this.getCollider();
		Collider wcollider=w.getCollider();
		
		if(thiscollider.intersectWith(wcollider))//collision occurs
		{
			Vector<Vect3> newpos = Collider.getCollidersCentersAfterCollision(thiscollider, wcollider);
			
			//update objects position
			this.setPosition(newpos.firstElement());
			w.setPosition(newpos.lastElement());
			
			System.out.println(newpos.firstElement());
			System.out.println(newpos.lastElement());
			

			return true;
		}
		
		return false;
	}

	/*
	@Override
	public void move(double time) {
		// TODO Auto-generated method stub
		
	}*/
	
	
	private void adjustPropellerDirection()
	{
		Vect3 propDir=new Vect3(propellerDirection);
		if(propDir.getZ()<=0)
		{
			System.out.println("Invalid propellerDirection. Did nothing.");
			//motorThrottle=0;//we could stop motors if drone is flipped over
			
			//in this case leaning angle is exceeded and the drone can go in any unwanted direction, but physics will still work as expected
			//this can be dangerous if the control module doesn't take this into account
			//we have to link propellerDirection from output of control module to input of updateSpeed
			return;
		}
		
		Vect3 propDirNormalizedZ=propDir.getMultipliedBy(1/propDir.getZ());//normalize on z axis
		double maxHspeedNormalized=Math.sin(characteristics.getMaxLeaningAngle()*Constantes.DegToRad);
		propDirNormalizedZ.setZ(0);
		double propDirHspeedNormalized=propDirNormalizedZ.norm();
		if(propDirHspeedNormalized>maxHspeedNormalized)
		{
			//limit max leaning angle
			double decreaseRatio=maxHspeedNormalized/propDirHspeedNormalized;
			propDir.setX(propDir.getX()*decreaseRatio);
			propDir.setY(propDir.getY()*decreaseRatio);
		}
		
		propellerDirection=propDir;
	}
	
	/**
	 * call all the sub-functions needed to update the drone state
	 * @param time
	 */
	public void updateMe(double time)
	{
		
		updateSpeed(time);
		
		if(isPluggedToStation())
		{
			rechargeBattery(time);
		}
		
		dischargeBattery(time);
		
		decide(time);
	}
	
	private void updateSpeed(double time)
	{
		adjustPropellerDirection();
		
		//so dirty without operator overloading...
		Vect3 propellerAcceleration = propellerDirection.getNormalized().multiplyBy(characteristics.getPropellerLift() * getMotorOutputPower() / getTotalFlyWeight());
		Vect3 dragAcceleration = speed.getMultipliedBy(speed.norm() * characteristics.getAirDrag() / getTotalFlyWeight()); ////drag proportional to squared speed
		//Vect3 dragAcceleration = speed.getMultipliedBy(characteristics.getAirDrag() / getTotalFlyWeight()); //drag proportional to speed
		Vect3 acceleration = propellerAcceleration.getAdded(PhysicsEngine.Gravity).substract(dragAcceleration);
		
		speed.add(acceleration.getMultipliedBy(time));
	}
	
	private void dischargeBattery(double time)
	{
		batteryLevel-=getMotorConsumption()*time*Constantes.WsToWh;
		if(batteryLevel<0)
		{
			batteryLevel=0;
		}
	}
	
	public boolean batteryIsEmpty()
	{
		return batteryLevel==0;
	}
	
	//necessary as boolean arithmetic doesn't exist in java
	private double batteryState()
	{
		if(batteryLevel==0)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	//TODO low battery state
	
	private void rechargeBattery(double time)
	{
		batteryLevel+=characteristics.getBatteryRechargingRate()*time*Constantes.WsToWh;
		if(batteryLevel>characteristics.getBatteryCapacity())
		{
			batteryLevel=characteristics.getBatteryCapacity();
		}
	}
	
	public void plugToStation(Station st)
	{
		this.unplugFromStation();
		
		this.pluggedStation=st;
		
		if(!st.isPluggedToDrone(this))
		{
			st.plugToDrone(this);
		}
	}
	
	public void unplugFromStation()
	{
		if(this.pluggedStation!=null)
		{
			this.pluggedStation.unplugFromDrone();
		}
		
		this.pluggedStation=null;
	}
	
	public boolean isPluggedToStation()
	{
		return this.pluggedStation!=null;
	}
	
	/**
	 * Perform actions according to environment and drone parameters
	 * This is what makes our system multi-agent
	 */
	public void decide(double time)
	{
		
	}
	

	
	@Override
	public WorldObject copy() {
		
		return new Drone(this);
	}
	
	/**
	 * TODO
	 * @return
	 */
	public Objective getNextObjective() {
		
		ArrayList<Objective> objs=mission.getObjectives();
		if(objs.size()>0)
		{
			return objs.get(0);
		}
		
		return new Objective(0, 0);
	}

	@Override
	protected Collider createSpecificCollider() {
		// TODO Auto-generated method stub
		return new Sphere(position, characteristics.getRadius());
	}
        
    public boolean inComRangeDrone(Drone drone) {
        Vect3 vect = getPosition();
        Double dist = vect.dist(drone.getPosition());
        boolean isInRange = (characteristics.getCommunicationRange() >= dist) && (drone.characteristics.getCommunicationRange() >= dist);
        return isInRange;
    }

    public boolean inComRangeStation(Station station) {
        Vect3 vect = getPosition();
        Double dist = vect.dist(station.getPosition());
        boolean isInRange = (characteristics.getCommunicationRange() >= dist);
        return isInRange;
    }
    
}
