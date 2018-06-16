package simulationDrones;

import java.util.ArrayList;
import utilities.Constantes;

public class Map {

    private RectCuboid limitsbox;
    private ArrayList<Drone> drones = new ArrayList<Drone>();
    //building
    private ArrayList<WorldObject> environment = new ArrayList<WorldObject>(); //buildings, stations and everything
    private Constantes Const;

    public Map() {
        Const = new Constantes();
        limitsbox = new RectCuboid(new Vect3(0, 0, 0), new Vect3(Const.CANVAS_WIDTH, Const.CANVAS_HEIGHT, 500));

        //Generates dumb dumb dumb basic map

        RectCuboid rect1 = new RectCuboid(new Vect3(700, 415, 0), new Vect3(150, 150, 200));
        Building building1 = new Building(new Vect3(700, 415, 0), new Vect3(0, 0, 0), new Vect3(150, 150, 200), rect1, null, "Building 1");

        RectCuboid rect2 = new RectCuboid(new Vect3(700, 135, 0), new Vect3(150, 150, 200));
        Building building2 = new Building(new Vect3(700, 135, 0), new Vect3(0, 0, 0), new Vect3(150, 150, 200), rect2, null, "Building 2"); 
        
        RectCuboid rect3 = new RectCuboid(new Vect3(50, 350, 0), new Vect3(150, 200, 200));
        Building building3 = new Building(new Vect3(50, 350, 0), new Vect3(0, 0, 0), new Vect3(150, 200, 200), rect3, null, "Building 3"); 
        
        RectCuboid rect4 = new RectCuboid(new Vect3(50, 600, 0), new Vect3(150, 100, 200));
        Building building4 = new Building(new Vect3(50, 600, 0), new Vect3(0, 0, 0), new Vect3(150, 100, 200), rect4, null, "Building 4");    
 
        RectCuboid rect5 = new RectCuboid(new Vect3(50, 750, 0), new Vect3(150, 175, 200));
        Building building5 = new Building(new Vect3(50, 750, 0), new Vect3(0, 0, 0), new Vect3(150, 175, 200), rect5, null, "Building 5");  
    
        RectCuboid rect6 = new RectCuboid(new Vect3(1300, 50, 0), new Vect3(200, 250, 200));
        Building building6 = new Building(new Vect3(1300, 50, 0), new Vect3(0, 0, 0), new Vect3(200, 250, 200), rect6, null, "Building 6");  
   
        RectCuboid rect7 = new RectCuboid(new Vect3(1300, 350, 0), new Vect3(200, 250, 200));
        Building building7 = new Building(new Vect3(1300, 350, 0), new Vect3(0, 0, 0), new Vect3(200, 250, 200), rect7, null, "Building 7");  

        RectCuboid rect8 = new RectCuboid(new Vect3(1300, 650, 0), new Vect3(200, 300, 200));
        Building building8 = new Building(new Vect3(1300, 650, 0), new Vect3(0, 0, 0), new Vect3(200, 300, 200), rect8, null, "Building 8"); 
        
        RectCuboid rect9 = new RectCuboid(new Vect3(600, 750, 0), new Vect3(400, 200, 200));
        Building building9 = new Building(new Vect3(600, 750, 0), new Vect3(0, 0, 0), new Vect3(400, 200, 200), rect9, null, "Building 8");  
        
        environment.add(building1);
        environment.add(building2);
        environment.add(building3);
        environment.add(building4);
        environment.add(building5);
        environment.add(building6);
        environment.add(building7);
        environment.add(building8);
        environment.add(building9);
        
        
        //Vect3 positionStation = new Vect3(i * 50, i * 50, size.getZ());
        //Vect3 sizeStation = new Vect3(size.getX(), size.getY(), 0);
        //Station station = new Station(positionStation, sizeStation, speed);
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

            if (CollisionTools.interesect(c1, c2)) {
                res.add(temp);
            }

        }

        return res;
    }
}
