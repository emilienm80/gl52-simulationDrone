package simulationDrones;

public class Drone extends WorldObject /*implements Intelligence*/ {

	private DroneType characteristics;
	public DroneAI brain;
	public double batteryLevel;//A.h
	public double payload;//Kg
	public double motorThrottle;//%
	public double motorOutputPower() { return motorConsumption()*characteristics.motorEfficiency; } //W
	public double motorConsumption() { return motorThrottle*characteristics.motorMaxConsumption; } //W
	
	
	public DroneAI getBrain() {
		return brain;
	}
	public void setBrain(DroneAI brain) {
		this.brain = brain;
	}
	public Drone(Drone d)
	{
		super(d);
		
		characteristics=d.characteristics;
		//brain=d.brain;
		batteryLevel=d.batteryLevel;
		payload=d.payload;
		motorThrottle=d.motorThrottle;
	}
	
	
	@Override
	public void collideWith(WorldObject w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(double time) {
		// TODO Auto-generated method stub
		
	}
	
	public void dischargeBattery(double time)
	{
		batteryLevel-=motorConsumption()*time;
		if(batteryLevel<0)
		{
			batteryLevel=0;
		}
	}
	
	public void rechargeBattery(double time)
	{
		batteryLevel+=characteristics.batteryRechargingRate*time;
		if(batteryLevel>characteristics.batteryCapacity)
		{
			batteryLevel=characteristics.batteryCapacity;
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
	
}
