package simulationDrones;

import java.util.ArrayList;

public class PhysicsEngine {
	
	public static final Vect3 Gravity=new Vect3(0,0,-9.81);
	
	private Map map;
	
	public PhysicsEngine(Map m) {
		this.map=m;
	}
	
	
	public void updateMap(double time)
	{
		if(map!=null)
		{
			updateWorld(map.getAllObjects(), time);
		}
	}
	
	public void updateWorld(ArrayList<WorldObject> world, double time)
	{
		ArrayList<WorldObject> oldWorld=new ArrayList<>();
		for (WorldObject w : world) {
			oldWorld.add(w.copy());
		}
		
		for(int i=0;i<oldWorld.size();i++)
		{
			//TODO
			WorldObject wold=oldWorld.get(i);
			WorldObject wnew=world.get(i);
			
			checkCollisions(wold, oldWorld);
		}
		
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
	
	//TODO collisions
	private void checkCollisions(WorldObject w, ArrayList<WorldObject> world)
	{
		
	}
	
	private void collide(WorldObject w1, WorldObject w2)
	{
		
	}

}
