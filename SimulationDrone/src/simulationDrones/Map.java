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
