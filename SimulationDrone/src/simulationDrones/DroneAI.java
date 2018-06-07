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
		 * TODO finish algo
		 * @param positionDrone current position of the drone
		 * @param goaPosition position of the goal the drone wants to move towards
		 * @param map the map in which the simulation is taking place
		 * @return a 3 component vector containing the direction the drone has to move in
		 */
		public Vect3 updateVitesse(Vect3 positionDrone, Vect3 goaPosition, Map map) {
			Vect3 vitesse = new Vect3();
			
			return vitesse;
		}
		
	}
}
