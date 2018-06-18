package simulationDrones;

import org.w3c.dom.css.Rect;

import com.sun.media.sound.InvalidDataException;

import sun.text.resources.is.CollationData_is;

public class Drone extends WorldObject /*implements Intelligence*/ {

	private Sphere collidingBox;
	private DroneCharacteristics characteristics;
	private DroneAI brain;
	private double batteryLevel;//W.h=Joules/3600
	private double payload;//Kg
	private double motorThrottle;//%
	private Vect3 propellerDirection;//the z axis vector in the drone local frame (pointing upwards, represent motor force) - should be updated by the AI
	
	
	private Mission mission;
	
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
		super(position, speed, size);
		
		this.collidingBox = colliding;
		this.characteristics = dc;
		this.brain = null;
		this.batteryLevel = CollisionTools.limit(0.01*batteryLevel,0,1)*dc.getBatteryCapacity();
		this.payload = payload;
		this.motorThrottle = motorThrottle;
		this.propellerDirection=new Vect3(0,0,1);
		
		this.mission = mission;
	}
	
	public Drone(Drone d)
	{
		super(d);
		
		characteristics=d.characteristics;
		//brain=d.brain;TODO
		batteryLevel=d.batteryLevel;
		payload=d.payload;
		motorThrottle=d.motorThrottle;
		propellerDirection=new Vect3(d.propellerDirection);
		
		collidingBox=new Sphere(position, characteristics.getRadius());
	}
	
	
	
	@Override
	public boolean collideWith(WorldObject w) {
		// TODO compute new position in this function
		
		Collider wc=w.getCollider();
		
		if(wc.getClass()==Sphere.class)
		{
			return CollisionTools.intersect((Sphere)this.getCollider(), (Sphere)wc);
		}
		else if(wc.getClass()==RectCuboid.class)
		{
			return CollisionTools.intersect((RectCuboid)wc, (Sphere)this.getCollider());
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
			return;
		}
		
		Vect3 propDirNormalizedZ=propDir.getMultipliedBy(1/propDir.getZ());//normalize on z axis
		double maxHspeedNormalized=Math.sin(characteristics.getMaxLeaningAngle()*CollisionTools.DegToRad);
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
		
		//if connected to station, recharge batteries
		
		dischargeBattery(time);
		
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
		batteryLevel-=getMotorConsumption()*time*CollisionTools.WsToWh;
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
		batteryLevel+=characteristics.getBatteryRechargingRate()*time*CollisionTools.WsToWh;
		if(batteryLevel>characteristics.getBatteryCapacity())
		{
			batteryLevel=characteristics.getBatteryCapacity();
		}
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
		return new Objective(1,2);
	}

	@Override
	public Collider getCollider() {
		collidingBox.setCenter(position);
		return collidingBox;
	}

	
}
