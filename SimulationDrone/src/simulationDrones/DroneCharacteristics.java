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
	private double batteryCapacity;//W.h=Joules/3600
	private double batteryRechargingRate;//W
	private double communicationRange;//m
	//maxheight=communication range or map height bound
	
	public DroneCharacteristics()
	{
		this(DroneType.standard);
	}

	public DroneCharacteristics(DroneType dt)
	{
		setCharacteristics(dt);
	}

	public void setCharacteristics(DroneType dt)
	{
		switch (dt) {
	        case mini://based on Parrot Mambo
	        	maxSpeed=4.2;
	        	maneuverability=new Vect3(1,1,1);
	        	radius=0.09;
	        	motorEfficiency=0.85;
	        	motorMaxConsumption=25;//for 4 motors
	        	//stationary = 12W
	        	propellerLift=1;
	        	maxPayload=0.005;
	        	dryWeight=0.065;
	        	batteryCapacity=2.2;//around 5 min autonomy at max throttle
	        	batteryRechargingRate=4.4;//30 min charging time
	        	communicationRange=20;
	            break;
	        case standard://based on DJI Spark
	            maxSpeed=12;
	        	maneuverability=new Vect3(1,1,1);
	        	radius=0.11;
	        	motorEfficiency=0.85;
	        	motorMaxConsumption=100;//around 10 min autonomy at max throttle
	        	//stationary = 68W
	        	propellerLift=1;
	        	maxPayload=0.03;
	        	dryWeight=0.3;
	        	batteryCapacity=17;
	        	batteryRechargingRate=29;
	        	communicationRange=100;
	        	break;
	        /*case pro://based on YUNEEC H520
	        	maxHSpeed=;
	        	maxUpSpeed=;
	            maxDownSpeed=;
	        	maneuverability=new Vect3(1,1,1);
	        	radius=;
	        	motorEfficiency=;
	        	motorMaxConsumption=;
	        	propellerLift=;
	        	maxPayload=;
	        	dryWeight=;
	        	batteryCapacity=;
	        	batteryRechargingRate=;
	        	communicationRange=;
	            break;*/
	        case transporter://based on DJI S900
	            maxSpeed=16;
	        	maneuverability=new Vect3(1,1,1);
	        	radius=0.5;
	        	motorEfficiency=0.9;
	        	motorMaxConsumption=3000;//for 6 motors
	        	//stationary = 1000W @ 6.8Kg
	        	propellerLift=1;
	        	maxPayload=4;
	        	dryWeight=3.5;
	        	batteryCapacity=300;
	        	batteryRechargingRate=300;
	        	communicationRange=1000;
	        	break;
		}
	}
	
	
	/*
	 * We assume a linear charge/discharge model, and a potentially infinite instantaneous power delivered from the accumulators.
	 * 
	 * Consumption takes only motors into account, and not electronic or communication components.
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
	
	public double getCommunicationRange() {
		return communicationRange;
	}
	
	

}
