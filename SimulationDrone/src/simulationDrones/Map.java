package simulationDrones;

import java.util.ArrayList;

public class Map {

	private RectCuboid limitsbox;
	private ArrayList<Drone> drones=new ArrayList<Drone>();
	//building
	private ArrayList<WorldObject> environment=new ArrayList<WorldObject>(); //buildings, stations and everything
	
	
	public Map() {
		limitsbox = new RectCuboid( new Vect3(0,0,0), new Vect3(500,500,500) );
		
		for(int i = 0; i < 5; i++) {
			
			//position, speed, size, colidingbox, station;//
			Vect3 position = new Vect3(i*10,i*10,i*10);
			Vect3 speed = new Vect3(0,0,0);
			Vect3 size = new Vect3(5,5,5);
			
			RectCuboid cb = new RectCuboid(position, size);
			Station station = null;
			
			String name = "Building "+String.valueOf(i);
			
			Building b = new Building(position, speed, size, cb, station, name);
			
			environment.add(b);
		}
		
	}
	
	/**
	 * Returns all buildings with stations on top
	 * @return
	 */
	ArrayList<Building> getBuildingWithStation(){
		ArrayList<Building> res = new ArrayList<Building>();
		
		for(WorldObject temp : environment) {
			if(temp instanceof Building) {
				Station station = ((Building) temp).getStation();
				
				if(station != null) {
					res.add( (Building) temp);
				}
			}
		}
		
		return res;
	}
	
}
