package simulationDrones;

public class DroneType {
	
	public double maxSpeed;//m/s
	public Vect3 maneuverability;//m/s²
	public double radius;//m
	public double motorEfficiency;//No unit
	public double motorMaxConsumption;//W
	public double propellerLift;//Kg/m²/s
	public double maxPayload;//Kg
	public double dryWeight;//Kg
	public double batteryCapacity;//A.h
	public double batteryRechargingRate;//W
	
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

}
