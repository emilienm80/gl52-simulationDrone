package simulationDrones;

import java.util.ArrayList;

public class PhysicsEngine {
	
	public static Vect3 Gravity=new Vect3(0,0,-9.81);
	
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
		
		for (WorldObject w : world) {
			w.move(time);
			checkCollisions(w, world);

			if(w instanceof Drone)
			{
				Drone d = ((Drone) w);
				Vect3 goalPosition = d.getNextObjective().getPosition();
				//w.setSpeed(DroneAI.AI.updateSpeed(d, goalPosition, map));
				d.setSpeed(new Vect3(10,10,0));
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
