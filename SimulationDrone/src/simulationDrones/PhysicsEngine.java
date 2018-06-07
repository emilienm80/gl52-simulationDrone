package simulationDrones;

import java.util.ArrayList;

public class PhysicsEngine {
	
	private Map map;
	
	public void updateWorld(ArrayList<WorldObject> world, double time)
	{
		ArrayList<WorldObject> newWorld=new ArrayList<>();
		for (WorldObject w : world) {
			newWorld.add(w.copy());
		}
		
		for (WorldObject w : newWorld) {
			w.move(time);
			checkCollisions(w, world);

			if(w.getClass()==Drone.class)
			{
				((Drone)w).brain.decide(map, time);
			}
		}
	}
	
	private void checkCollisions(WorldObject w, ArrayList<WorldObject> world)
	{
		
	}
	
	private void collide(WorldObject w1, WorldObject w2)
	{
		
	}

}
