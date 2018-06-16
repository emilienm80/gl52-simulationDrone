package simulationDrones;

public class Station extends WorldObject {

	public Station() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Station(Vect3 position, Vect3 speed, Vect3 size) {
		super(position, speed, size);
	}
	
	//deep copy
	public Station(Station s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public WorldObject copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collider getCollider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean collideWith(WorldObject w) {
		// TODO Auto-generated method stub
		return false;
	}


}
