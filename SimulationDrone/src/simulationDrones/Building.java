package simulationDrones;

public class Building extends WorldObject {

	private Station droneStation;
	
	public Building()
	{
		
	}
	
	//deep copy
	public Building(Building b)
	{
		droneStation=new Station(b.droneStation);
	}
	
	
	public boolean hasStation()
	{
		return droneStation!=null;
	}
	
	
	@Override
	public void collideWith(WorldObject w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WorldObject copy() {
		
		return new Building(this);
	}

}
