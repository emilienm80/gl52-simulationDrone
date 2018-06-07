package simulationDrones;

public class DroneCharacteristics {
	
	private double maxSpeed;//m/s
	private Vect3 maneuverability;//m/s²
	private double radius;//m
	private double motorEfficiency;//No unit
	private double motorMaxConsumption;//W
	private double propellerLift;//Kg/m²/s
	private double maxPayload;//Kg
	private double dryWeight;//Kg
	private double batteryCapacity;//A.h
	private double batteryRechargingRate;//W
	
	public DroneCharacteristics()
	{
		
	}

	public DroneCharacteristics()
	{
		
	}

	
	/*
	public DroneType(DroneType dt)
	{
		maxSpeed=dt.maxSpeed;
		maneuverability=new Vect3(dt.maneuverability);
		size=new Vect3(dt.size);
		motorEfficiency=dt.motorEfficiency;
		motorMaxConsumption=dt.motorMaxConsumption;
		propellerLift=dt.propellerLift;
		maxPayload=dt.maxPayload;
		dryWeight=dt.dryWeight;
		batteryCapacity=dt.batteryCapacity;
		batteryRechargingRate=dt.batteryRechargingRate;
	}*/
	
	/*
	 * We assume a linear charge/discharge model, and a potentially infinite instantaneous power delivered from the accumulators.
	 * 
	 * 
	 */
	
	
	public double getMaxSpeed() {
		return maxSpeed;
	}

	public Vect3 getManeuverability() {
		return maneuverability;
	}

	public double getRadius() {
		return radius;
	}

	public double getMotorEfficiency() {
		return motorEfficiency;
	}

	public double getMotorMaxConsumption() {
		return motorMaxConsumption;
	}

	public double getPropellerLift() {
		return propellerLift;
	}

	public double getMaxPayload() {
		return maxPayload;
	}

	public double getDryWeight() {
		return dryWeight;
	}

	public double getBatteryCapacity() {
		return batteryCapacity;
	}

	public double getBatteryRechargingRate() {
		return batteryRechargingRate;
	}
	
	

}
