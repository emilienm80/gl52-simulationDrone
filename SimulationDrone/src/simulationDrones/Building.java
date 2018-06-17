package simulationDrones;

public class Building extends WorldObject {

    private RectCuboid collidingBox;
    private Station droneStation;

    private String name;

    public Building() {
        super();
        collidingBox = new RectCuboid(position, size);
    }

    public String getName() {
        return name;
    }

    /**
     * Actually useful constructor, thx francis
     *
     * @param position the vect3 representation of the position of the building
     * @param speed the vect3 representation of the speed of the building
     * (hopefully 0 0 0)
     * @param size the vect3 representation of the size of the building
     * @param collidingBox the colliding box that surrounds the building
     * @param droneStation the drone station installed on top of the building
     */
    public Building(Vect3 position, Vect3 speed, Vect3 size, RectCuboid collidingBox, Station droneStation, String name) {
        super(position, speed, size);
        this.collidingBox = collidingBox;
        this.droneStation = droneStation;
        this.name = name;
    }

    //deep copy
    public Building(Building b) {
        super(b);
        if(b.droneStation!=null)
        {
        	droneStation = new Station(b.droneStation);
        }
        collidingBox = new RectCuboid(position, size);
    }

    public boolean hasStation() {
        return droneStation != null;
    }

    @Override
    public boolean collideWith(WorldObject w) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public WorldObject copy() {

        return new Building(this);
    }

    @Override
    public Collider getCollider() {
        collidingBox.setOrigin(position);
        return collidingBox;
    }

    public Station getStation() {
        return this.droneStation;
    }

}
