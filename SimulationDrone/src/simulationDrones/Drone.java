package simulationDrones;

import org.w3c.dom.css.Rect;

public class Drone extends WorldObject /*implements Intelligence*/ {

	private Sphere collidingBox;
	private DroneCharacteristics characteristics;
	private DroneAI brain;
	private double batteryLevel;//W.h=Joules/3600
	private double payload;//Kg
	private double motorThrottle;//%
	
	private Mission mission;
	
	public DroneCharacteristics getCharacteristics() {
		return characteristics;
	}

	public double getBatteryLevel() {
		return batteryLevel;
	}
	public double getPayload() {
		return payload;
	}
	public double getMotorThrottle() {
		return motorThrottle;
	}
	
	public double getMotorOutputPower() { return getMotorConsumption()*characteristics.getMotorEfficiency(); } //W
	public double getMotorConsumption() { return motorThrottle*characteristics.getMotorMaxConsumption(); } //W
	
	
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
		this.batteryLevel = batteryLevel;
		this.payload = payload;
		this.motorThrottle = motorThrottle;
		
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

	@Override
	public void move(double time) {
		// TODO Auto-generated method stub
		
	}
	
	public void dischargeBattery(double time)
	{
		batteryLevel-=getMotorConsumption()*time;
		if(batteryLevel<0)
		{
			batteryLevel=0;
		}
	}
	
	public void rechargeBattery(double time)
	{
		batteryLevel+=characteristics.getBatteryRechargingRate()*time;
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
