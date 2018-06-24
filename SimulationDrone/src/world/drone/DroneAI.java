package world.drone;

import world.Map;
import utilities.Vect3;
import physics.collisions.colliders.*;

public class DroneAI {
	
	//TODO create a module dedicated to drone stabilization, which wraps movement according to target position and hides drone real displacement technique
	//it will thus hide motorThrottle and maxLeaningAngle, which are quite tricky to handle for the user
	
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
			Vect3 res;
			Vect3 positionDrone = drone.getPosition();
			Vect3 speed = new Vect3(positionDrone, goalPosition);
			
			Vect3 nextPosition = positionDrone.getAdded(speed);
			
			Collider nextCollider = new Sphere(nextPosition, drone.getCharacteristics().getRadius());
			
			/*
			//TODO make drones talk.
			if(map.testCollision(nextCollider).size() > 0) {
				speed = new Vect3(0,0,0);
			}
			*/
			
			Vect3 currentSpeed = drone.getSpeed();			
			double distanceToObjective = speed.norm();		
			
			//TODO set propeller angle and motorthrottle
			
			drone.setMotorThrottle(1);
			
			
			
			if(distanceToObjective > 250) {
				//Normal behavior, head towards the objective
				drone.setMotorThrottle(1);
				res = speed.getNormalized();
			}else {
				
				if(currentSpeed.norm() > 10) {
					//If going fast and close to the objective
					
					double angle = currentSpeed.getAngle(speed);
					Vect3 temp;
					
					if(angle > ( Math.PI/2 ) ) {
						//If the drone is going the wrong way
						drone.setMotorThrottle(1);
						
						temp = speed;
					}else {
						//Slowing down phase on approach
						drone.setMotorThrottle(1);
						
						temp = new Vect3(-1*speed.getX(), -1*speed.getY(), speed.getZ());
					}
					
					
					res = temp.getNormalized();
				}else {
					//If not going too fast, we go towards the objective
					drone.setMotorThrottle(1);
					res = speed.getNormalized().getMultipliedBy(0.5);
				}
				
				
			}
			
			return res;
			
		}
		
	}
}
