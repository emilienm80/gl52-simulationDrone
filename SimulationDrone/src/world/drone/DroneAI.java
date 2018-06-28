package world.drone;

import world.Map;
import utilities.Vect3;
import javafx.geometry.Rectangle2D;
import physics.collisions.CollisionTools;
import physics.collisions.colliders.*;
import physics.collisions.colliders.Collider;
import physics.collisions.colliders.Sphere;
import utilities.Vect3;


public class DroneAI {
	
	//TODO create a module dedicated to drone stabilization, which wraps movement according to target position and hides drone real displacement technique
	//it will thus hide motorThrottle and maxLeaningAngle, which are quite tricky to handle for the user

	private Vect3 targetPos;
	private ComInteract intention;
	private Drone attachedDrone;
	private Collider lastCollider;
	
	public DroneAI(Drone d)
	{
		targetPos=new Vect3();
		intention=ComInteract.None;
		attachedDrone=d;
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
			if(distanceToObjective > 350) {
				//Normal behavior, head towards the objective
				drone.setMotorThrottle(1);
				res = speed.getNormalized();
				
			}else if(distanceToObjective < 25 && currentSpeed.norm() < 25){
				//Case where drone on final approach
				drone.setMotorThrottle(0);
				res = new Vect3(0,0,0);
			}else {
				//Case where drone is getting ready for final approach
				
				if(currentSpeed.norm() > 25) {
					//If going fast and close to the objective
					
					//Calculate the angle between the current speed and desired speed 
					double angle = currentSpeed.getAngle(speed);
					Vect3 temp;
					
					if(angle > ( Math.PI/2 ) ) {
						//If the drone is going the wrong way
						drone.setMotorThrottle(1);
						temp = speed.getMultipliedBy(0.5);
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
	
	public void goAwayFrom(Vect3 pos)
	{
		Vect3 cpos=attachedDrone.getPosition();
		setTargetPos(cpos.getAdded(new Vect3(pos, cpos)));
	}
	
	/**
	 * update propdir and motor throttle
	 */
	private void updatePropellerMotion()
	{
		Vect3 currentSpeed=attachedDrone.getSpeed();
		Vect3 currentPos=attachedDrone.getPosition();
		double radius=attachedDrone.getCharacteristics().getRadius();
		Vect3 dir=new Vect3(currentPos, targetPos);
		Vect3 normdir=dir.getNormalized();
		
		Vect3 dirXY=new Vect3(dir);
		dirXY.setZ(0);
		
		double hoverSafetyDist=3;
		double safetyDist=0.2;
		double speedOnDirScaled=currentSpeed.dotProduct(dir)/dir.squaredNorm();
		
		if(dir.getZ()>0)
		{
			attachedDrone.setPropellerDirection(new Vect3(0,0,1));
			attachedDrone.setMotorThrottle(1);
			return;
		}
		
		if(lastCollider!=null && lastCollider instanceof RectCuboid)
		{
			RectCuboid lcol=(RectCuboid)lastCollider;
			Vect3 cp=lcol.closestCuboidPoint(currentPos);
			double closestColPointDist=cp.dist(currentPos)+radius;
			if(closestColPointDist<safetyDist)
			{
				//go away from collision point
				attachedDrone.setPropellerDirection(new Vect3(cp, currentPos).add(new Vect3(0,0,3)));
				attachedDrone.setMotorThrottle(1);
				return;
			}
		}
		
		
		if(dir.getZ()<0)
		{
			dir.setZ(0.1);
			attachedDrone.setPropellerDirection(dir);
			attachedDrone.setMotorThrottle(0.7);
			return;
		}
		else
		{
			
			if(dir.norm()<1 && dir.dotProduct(currentSpeed)>0)
			{
				attachedDrone.setPropellerDirection(dir.getNormalized().reverse().add(new Vect3(0,0,2)));
				attachedDrone.setMotorThrottle(0.9);
			}
			else
			{
				attachedDrone.setPropellerDirection(dir);
				attachedDrone.setMotorThrottle(1);
			}
			
		}
		
		
	}
	
	public void decide()
	{
		setTargetPos(attachedDrone.getNextObjective().getPosition());
		updatePropellerMotion();
	}

	
	public void setTargetPos(Vect3 targetPos)
	{
		this.targetPos=new Vect3(targetPos);
	}
	

	public Vect3 getTargetPos() {
		return new Vect3(targetPos);
	}


	public ComInteract getIntention() {
		return intention;
	}


	public void setIntention(ComInteract intention) {
		this.intention = intention;
	}

	public Collider getLastCollider() {
		return lastCollider;
	}

	public void setLastCollider(Collider lastCollider) {
		this.lastCollider = lastCollider;
	}

	
	
	
	
}
