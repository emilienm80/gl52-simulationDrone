package physics;

import java.util.ArrayList;

import physics.collisions.CollisionSortElement;
import utilities.Vect3;
import world.Map;
import world.WorldObject;
import world.drone.Drone;
import world.drone.DroneAI;

public class PhysicsEngine {
	
	public static final Vect3 Gravity=new Vect3(0,0,-9.81);
	
	private Map map;
	
	public PhysicsEngine(Map m) {
		this.map=m;
	}
	
	//manage only drones collisions at first
	
	public void updateMap(double time)
	{
		if(map!=null)
		{
			updateWorld(map.getAllObjects(), time);
		}
	}
	
	public void updateWorld(ArrayList<WorldObject> world, double time)
	{
		processCollisions(world);
		
		for (WorldObject w : world) {
			
			w.move(time);

			if(w instanceof Drone)
			{
				Drone d = ((Drone) w);
				d.updateMe(time);
				//Vect3 goalPosition = d.getNextObjective().getPosition(); -> bug !
				//d.setTargetDirection(DroneAI.AI.updateSpeed(d, goalPosition, map));
				//d.setPropellerDirection(new Vect3(1,0.5,1));//for testing only. the drone should be further controlled with a command module		
				
				
				Vect3 goalPosition = d.getNextObjective().getPosition();
				d.setPropellerDirection(DroneAI.AI.updateSpeed(d, goalPosition, map));
			}
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @param world
	 * @return a new object, located at the position it should have after the collision
	 */
	private void processCollisions(WorldObject obj, ArrayList<WorldObject> world)
	{
		double maxCollDist=100000;//dist between objects centers above which collision is not tested (object considered too far from each other)
		
		//this list will hold couple of (worldobject, distance) to allow further sorting
		ArrayList<CollisionSortElement> cDistList=new ArrayList<CollisionSortElement>();
		
		//select only close enough objects to take in account a possible collision
		for(WorldObject w : world)
		{
			double distBetweenObjs=w.getPosition().squaredDist(obj.getPosition());//squared dist is faster to compute
			
			if(distBetweenObjs<=maxCollDist && w!=obj)
			{
				cDistList.add(new CollisionSortElement(w, distBetweenObjs));
			}
		}
		
		//the sort is necessary to process nearest objects collisions first
		CollisionSortElement.sortInDescendingOrder(cDistList);
		
		//process collisions, starting from nearest object to farthest
		for(CollisionSortElement cse : cDistList)
		{
			WorldObject w=cse.obj;
			
			obj.collideWith(w);//affect objects only if they collide
			//if obj has been collided and its position changed, this new position will be taken in account for the next iterations with remaining objects
			
			//usually, processing objects from nearest to farthest prevents most of collision disruptions
		}
	}
	
	//we could improve this by using a copy of the world, which would be used to test collisions, and would not be modified. we could then find matching colliding objects and apply necessary actions
	private void processCollisions(ArrayList<WorldObject> world)
	{
		for(WorldObject w : world)
		{
			processCollisions(w, world);
		}
	}
	
	private void collide(WorldObject w1, WorldObject w2)
	{
		
	}

}
