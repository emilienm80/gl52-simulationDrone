package world;

import java.util.ArrayList;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;

import physics.collisions.CollisionTools;
import physics.collisions.colliders.Collider;
import physics.collisions.colliders.RectCuboid;
import utilities.Constantes;
import utilities.UsF;
import utilities.Vect3;
import world.drone.Drone;

public class Map {

    private RectCuboid limitsbox;
    //private ArrayList<Drone> drones = new ArrayList<Drone>();
    //building
    private ArrayList<WorldObject> environment = new ArrayList<WorldObject>(); //buildings, stations and everything
    private Constantes Const;

    public Map() {
        Const = new Constantes();
        limitsbox = new RectCuboid(new Vect3(0, 0, 0), new Vect3(Const.CANVAS_WIDTH, Const.CANVAS_HEIGHT, 500));
        
        /*
        //Generates dumb dumb dumb basic map
        Station stationLeftTop = new Station(new Vect3(100, 425, 0), new Vect3(0, 0, 0), new Vect3(50, 50, 200));
        RectCuboid rectLeftTop = new RectCuboid(new Vect3(50, 350, 0), new Vect3(150, 200, 200));
        Building LeftTop = new Building(new Vect3(50, 350, 0), new Vect3(0, 0, 0), new Vect3(150, 200, 200), rectLeftTop, stationLeftTop, "Building 1"); 
        
        RectCuboid rectLeftCenter = new RectCuboid(new Vect3(50, 600, 0), new Vect3(150, 100, 200));
        Building LeftCenter= new Building(new Vect3(50, 600, 0), new Vect3(0, 0, 0), new Vect3(150, 100, 200), rectLeftCenter, null, "Building 2");    
 
        Station stationLeftBottom = new Station(new Vect3(100, 812, 0), new Vect3(0, 0, 0), new Vect3(50, 50, 200));
        RectCuboid rectLeftBottom = new RectCuboid(new Vect3(50, 750, 0), new Vect3(150, 175, 200));
        Building LeftBottom = new Building(new Vect3(50, 750, 0), new Vect3(0, 0, 0), new Vect3(150, 175, 200), rectLeftBottom, stationLeftBottom, "Building 3");  

        RectCuboid rectCenterTop = new RectCuboid(new Vect3(700, 135, 0), new Vect3(150, 150, 200));
        Building CenterTop = new Building(new Vect3(700, 135, 0), new Vect3(0, 0, 0), new Vect3(150, 150, 200), rectCenterTop, null, "Building 4"); 
       
        Station stationCenterCenter = new Station(new Vect3(750, 465, 0), new Vect3(0, 0, 0), new Vect3(50, 50, 200));
        RectCuboid rectCenterCenter  = new RectCuboid(new Vect3(700, 415, 0), new Vect3(150, 150, 200));
        Building CenterCenter = new Building(new Vect3(700, 415, 0), new Vect3(0, 0, 0), new Vect3(150, 150, 200), rectCenterCenter, stationCenterCenter, "Building 5");
       
        Station stationCenterBottom = new Station(new Vect3(775, 825, 0), new Vect3(0, 0, 0), new Vect3(50, 50, 200));
        RectCuboid rectCenterBottom = new RectCuboid(new Vect3(600, 750, 0), new Vect3(400, 200, 200));
        Building CenterBottom = new Building(new Vect3(600, 750, 0), new Vect3(0, 0, 0), new Vect3(400, 200, 200), rectCenterBottom, stationCenterBottom, "Building 6");
    
        RectCuboid rectRightTop = new RectCuboid(new Vect3(1300, 50, 0), new Vect3(200, 250, 200));
        Building RightTop = new Building(new Vect3(1300, 50, 0), new Vect3(0, 0, 0), new Vect3(200, 250, 200), rectRightTop, null, "Building 7");  
   
        Station stationRightCenter = new Station(new Vect3(1375, 450, 0), new Vect3(0, 0, 0), new Vect3(50, 50, 200));
        RectCuboid rectRightCenter = new RectCuboid(new Vect3(1300, 350, 0), new Vect3(200, 250, 200));
        Building RightCenter = new Building(new Vect3(1300, 350, 0), new Vect3(0, 0, 0), new Vect3(200, 250, 200), rectRightCenter, stationRightCenter, "Building 8");  

        Station stationRightBottom = new Station(new Vect3(1375, 775, 0), new Vect3(0, 0, 0), new Vect3(50, 50, 200));
        RectCuboid rectRightBottom = new RectCuboid(new Vect3(1300, 650, 0), new Vect3(200, 300, 200));
        Building RightBottom = new Building(new Vect3(1300, 650, 0), new Vect3(0, 0, 0), new Vect3(200, 300, 200), rectRightBottom, stationRightBottom, "Building 9"); 
        
          
        
        environment.add(LeftTop);
        environment.add(LeftCenter);
        environment.add(LeftBottom);
        environment.add(CenterTop);
        environment.add(CenterCenter);
        environment.add(CenterBottom);
        environment.add(RightTop);
        environment.add(RightCenter);
        environment.add(RightBottom);
        */
        
        environment.addAll(createBuildings(6, 1, false));
        
        
        //Vect3 positionStation = new Vect3(i * 50, i * 50, size.getZ());
        //Vect3 sizeStation = new Vect3(size.getX(), size.getY(), 0);
        //Station station = new Station(positionStation, sizeStation, speed);
    }
    
    /**
     * Randomly creates buildings with stations in the map
     * 
     * @param nbBuildings number of buildings to be generated
     * @param stationProba probability of a station spawning on each generated building
     * @param allowOverlap allow building overlapping on X,Y coordinates
     */
    private ArrayList<Building> createBuildings(int nbBuildings, double stationProba, boolean allowOverlap)
    {
    	ArrayList<Building> createdBuildings=new ArrayList<Building>();
    	
    	Vect3 center=limitsbox.getCenter();//map center
    	Vect3 size=limitsbox.getSize();//map size
    	double optsizef=Math.min(size.getX(), size.getY());
    	double minsf=0.2;//min building size relative to map size
    	double maxsf=0.1;//max building size relative to map size
    	double stf=0.3;//sizefactor of stations relative to building upper surface dimensions
    	
    	for(int i=0; i<nbBuildings;i++)
    	{
    		Building b;
    		
    		do
    		{
	    		Vect3 rndSize=new Vect3(UsF.getRandomWithin(minsf, maxsf)*optsizef, UsF.getRandomWithin(minsf, maxsf)*optsizef, UsF.getRandomWithin(minsf, maxsf)*size.getZ());
	    		Vect3 rndPos=new Vect3(center.getX() + UsF.getRandomWithin(-0.5, 0.5)*(size.getX()-rndSize.getX()), center.getY() + UsF.getRandomWithin(-0.5, 0.5)*(size.getY()-rndSize.getY()), 0.5*rndSize.getZ());//guarantees a little space between buildings and map boundaries
	    		
	            RectCuboid rc = RectCuboid.createCentered(rndPos, rndSize);
	            
	            Station st=null;
	            if(Math.random()<stationProba)
	            {
	            	st=new Station(rc.getCenter(), new Vect3(0, 0, 0), rc.getSize().getMultipliedBy(stf));
	            }
	            
	            b = new Building(rc.getCenter(), new Vect3(0, 0, 0), rc.getSize(), rc, st, "Building "+i);
	            
    		}while(!allowOverlap && overlaps(b, createdBuildings));//temporary loop solution, can be very inefficient in computing time when building size is not negligible against map size and nb buildings is high
    		
    		createdBuildings.add(b);
    	}
    	
    	return createdBuildings;
    }
    
    /**
     * true if b overlaps with at least one of the buildings in bl
     * @param b
     * @param bl
     * @return
     */
    private boolean overlaps(Building b, ArrayList<Building> bl)
    {
    	for (WorldObject bi: bl)
    	{
    		if(CollisionTools.intersect(bi.getCollider(), b.getCollider()))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    

    /**
     * Returns all buildings with stations on top
     *
     * @return
     */
    public ArrayList<Building> getBuilding() {
        ArrayList<Building> res = new ArrayList<Building>();

        for (WorldObject temp : environment) {
            if (temp instanceof Building) {
                res.add((Building) temp);
            }
        }

        return res;
    }

    /**
     * Returns all buildings with stations on top
     *
     * @return
     */
    public ArrayList<Building> getBuildingWithStation() {
        ArrayList<Building> res = new ArrayList<Building>();

        for (WorldObject temp : environment) {
            if (temp instanceof Building) {
                Station station = ((Building) temp).getStation();

                if (station != null) {
                    res.add((Building) temp);
                }
            }
        }

        return res;
    }

    public ArrayList<WorldObject> testCollision(Collider c1) {
        ArrayList<WorldObject> res = new ArrayList<WorldObject>();

        for (WorldObject temp : environment) {
            Collider c2 = temp.getCollider();

            if (CollisionTools.intersect(c1, c2)) {
                res.add(temp);
            }

        }

        return res;
    }
    
    public void addDrone(Drone d) {
    	environment.add(d);
    }
    
    public Building getBuildingByName(String name) {
    	
    	Building res = null;
    	for (WorldObject temp : environment) {
            
    		if(temp instanceof Building) {
    			Building b = (Building) temp;
    			
    			if(b.getName().equalsIgnoreCase(name)) {
    				res = b;
    			}
    		}
        }
    	
    	return res;
    }
    
    /**
     * 
     * @return all di objects in da map !
     */
    public ArrayList<WorldObject> getAllObjects() {

        return this.environment;
    }
    
    public ArrayList<Drone> getDrone() {
        ArrayList<Drone> res = new ArrayList<Drone>();

        for (WorldObject temp : environment) {
            if (temp instanceof Drone) {
            	res.add((Drone) temp);
            }
        }

        return res;
    }
}
