package world.drone;

public class DroneCharacteristics {
	
	//maxspeed is now a function of drag, propeller lift and motor power
	private double maxLeaningAngle;//degrees, should be positive
	private double radius;//m
	private double motorEfficiency;//No unit
	private double motorMaxConsumption;//W
	private double propellerLift;//s/m
	private double maxPayload;//Kg
	private double dryWeight;//Kg
	private double batteryCapacity;//W.h=Joules/3600
	private double batteryRechargingRate;//W
	private double communicationRange;//m
	private double airDrag;//Kg/m
	//maxheight=communication range or map height bound

	
	public DroneCharacteristics()
	{
		this(DroneType.Standard);
	}

	public DroneCharacteristics(DroneType dt)
	{
		setCharacteristics(dt);
	}

	public void setCharacteristics(DroneType dt)
	{
		//The max propeller force (=motormaxconso*motorefficiency*propellerlift) should at least be equal to 9.81*dryWeight, otherwise your drone won't even takeoff.
		//If you want to carry a payload of mass m, the recommended max propeller force is roughly 20*(dryweight+m)
		//The maxLeaningAngle should ideally be such that when the drone is leaning at that angle with max throttle, its vertical speed is zero.
		//Set a smaller angle if you want safety, and a larger if you want fast horizontal speeds
		propellerLift=0.04;
		airDrag=0.004;//airdrag will limit the maxspeed
		
		switch (dt) {
	        case Mini://based on Parrot Mambo
	        	propellerLift=0.035;
	    		airDrag=0.005;
	    		maxLeaningAngle=30;
	        	radius=0.09;
	        	motorEfficiency=0.85;
	        	motorMaxConsumption=25;//for 4 motors
	        	maxPayload=0.005;
	        	dryWeight=0.065;
	        	batteryCapacity=2.2;//around 5 min autonomy at max throttle
	        	batteryRechargingRate=4.4;//30 min charging time
	        	communicationRange=20;
	            break;
	        case Standard://based on DJI Spark
	        	propellerLift=0.035;
	    		airDrag=0.012;
	        	maxLeaningAngle=25;
	        	radius=0.11;
	        	motorEfficiency=0.85;
	        	motorMaxConsumption=100;//around 10 min autonomy at max throttle
	        	maxPayload=0.03;
	        	dryWeight=0.3;
	        	batteryCapacity=17;
	        	batteryRechargingRate=29;
	        	communicationRange=100;
	        	break;
	        /*case Pro://based on YUNEEC H520
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
	        case Transporter://based on DJI S900
	        	propellerLift=0.04;
	    		airDrag=0.02;
	        	maxLeaningAngle=20;
	        	radius=0.5;
	        	motorEfficiency=0.9;
	        	motorMaxConsumption=3000;//for 6 motors
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
	

	public double getMaxLeaningAngle() {
		return maxLeaningAngle;
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
	
	public double getAirDrag() {
		return airDrag;
	}
	
	

}
