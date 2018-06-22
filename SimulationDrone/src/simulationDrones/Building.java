package simulationDrones;

public class Building extends WorldObject {

    private Station droneStation;

    private String name;

    public Building() {
        super();
        collider = createSpecificCollider();
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
        super(position, speed, size, collidingBox);
        this.droneStation = droneStation;
        this.name = name;
    }

    /**
     * Less redondant constructor.
     * @param position the vect3 representation of the position of the building
     * @param size the vect3 representation of the size of the building
     * @param droneStation the drone station installed on top of the building
     */
    public Building(Vect3 position, Vect3 size, Station droneStation, String name) {
        super(position, new Vect3(0,0,0), size, null);
        this.droneStation = droneStation;
        this.name = name;
        this.collider=createSpecificCollider();
    }
    
    
    //deep copy
    public Building(Building b) {
        super(b);
        if(b.droneStation!=null)
        {
        	droneStation = new Station(b.droneStation);
        }
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

    public Station getStation() {
        return this.droneStation;
    }

	@Override
	protected Collider createSpecificCollider() {
		// TODO Auto-generated method stub
		return RectCuboid.createCentered(position, size);
	}

}
