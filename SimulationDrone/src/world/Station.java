package world;

import physics.collisions.colliders.Collider;
import physics.collisions.colliders.RectCuboid;
import utilities.Vect3;

public class Station extends WorldObject {

	public Station() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * TEMPORARY ! For short term compatibility purpose only.
	 * @param position
	 * @param speed
	 * @param size
	 * @param c
	 */
	public Station(Vect3 position, Vect3 speed, Vect3 size) {
		super(position, speed, size, null);
		collider=createSpecificCollider();
	}
	
	public Station(Vect3 position, Vect3 speed, Vect3 size, Collider c) {
		super(position, speed, size, c);
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
	public boolean collideWith(WorldObject w) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Collider createSpecificCollider() {
		// TODO Auto-generated method stub
		return new RectCuboid(position, size);
	}


}
