package simulationDrones;

public class Building extends WorldObject {

	private RectCuboid collidingBox;
	private Station droneStation;
	
	public Building()
	{
		super();
		collidingBox=new RectCuboid(position, size);
	}
	
	//deep copy
	public Building(Building b)
	{
		super(b);
		droneStation=new Station(b.droneStation);
		collidingBox=new RectCuboid(position, size);
	}
	
	
	public boolean hasStation()
	{
		return droneStation!=null;
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


}
