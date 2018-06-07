package simulationDrones;

public class Drone extends WorldObject /*implements Intelligence*/ {

	private DroneCharacteristics characteristics;
	private DroneAI brain;
	private double batteryLevel;//A.h
	private double payload;//Kg
	private double motorThrottle;//%
	
	public DroneCharacteristics getCharacteristics() {
		return characteristics;
	}
	public DroneAI getBrain() {
		return brain;
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
	
}
