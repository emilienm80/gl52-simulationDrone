package simulationDrones;

public class DroneAI {
	
	/**
	 * Deprecated, use updateVItesse
	 * @param m
	 * @param time
	 */
	public void decide(Map m, double time)
	{
		
	}

	
	public static class AI{
		
		/**
		 * TODO finish algo with building avoidance
		 * @param positionDrone current position of the drone
		 * @param goalPosition position of the goal the drone wants to move towards
		 * @param map the map in which the simulation is taking place
		 * @return a 3 component vector containing the direction the drone has to move in
		 */
		public static Vect3 updateSpeed(Vect3 positionDrone, Vect3 goalPosition, Map map) {
			Vect3 speed = new Vect3(positionDrone.getSubstracted(goalPosition));
			
			return speed.getNormalized();
		}
		
	}
}
