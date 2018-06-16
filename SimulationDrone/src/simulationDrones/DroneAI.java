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
		public static Vect3 updateSpeed(Drone drone, Vect3 goalPosition, Map map) {
			Vect3 positionDrone = drone.getPosition();
			Vect3 speed = new Vect3(positionDrone.getSubstracted(goalPosition));
			
			Vect3 nextPosition = positionDrone.getAdded(speed);
			
			Collider nextCollider = new Sphere(nextPosition, drone.getCharacteristics().getRadius());
			
			
			//TODO make drones talk.
			if(map.testCollision(nextCollider).size() > 0) {
				speed = new Vect3(0,0,0);
			}
			
			return speed.getNormalized();
		}
		
	}
}
