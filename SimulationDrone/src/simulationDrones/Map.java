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
        /*Vect3 position = new Vect3(i * 50, i * 50, 0);
        Vect3 speed = new Vect3(0, 0, 0);
        Vect3 size = new Vect3(10, 10, 10);

        RectCuboid cb = new RectCuboid(position, size);

        Vect3 positionStation = new Vect3(i * 50, i * 50, size.getZ());
        Vect3 sizeStation = new Vect3(size.getX(), size.getY(), 0);
        Station station = new Station(positionStation, sizeStation, speed);

        String name = "Building " + String.valueOf(i);

        Building b = new Building(position, speed, size, cb, station, name);

        environment.add(b);*/

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
