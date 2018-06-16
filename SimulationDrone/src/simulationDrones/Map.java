package simulationDrones;

import java.util.ArrayList;

public class Map {

	private RectCuboid limitsbox;
	private ArrayList<Drone> drones=new ArrayList<Drone>();
	//building
	private ArrayList<WorldObject> environment=new ArrayList<WorldObject>(); //buildings, stations and everything
	
	
	public Map() {
		limitsbox = new RectCuboid( new Vect3(0,0,0), new Vect3(500,500,500) );
	}
	
}
