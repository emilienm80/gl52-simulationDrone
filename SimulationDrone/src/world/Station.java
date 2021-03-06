package world;

import physics.collisions.colliders.Collider;
import physics.collisions.colliders.RectCuboid;
import utilities.Vect3;
import world.drone.Drone;

public class Station extends WorldObject {

	//we assume for simplicity that only a drone at a time can be plugged to station, but we could easily improve that if we have time
	private Drone pluggedDrone;//plugged drone for recharging, if null, not plugged to any drone
	
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
	
	public void plugToDrone(Drone d)
	{
		this.pluggedDrone=d;
	}
	
	public void unplugFromDrone()
	{
		this.pluggedDrone=null;
	}
	
	public boolean isPluggedToDrone()
	{
		return this.pluggedDrone!=null;
	}
	
	public boolean isPluggedToDrone(Drone d)
	{
		return this.pluggedDrone==d;
	}

	public Vect3 getLandingPoint()
	{
		Vect3 respos=getPosition();
		respos.setZ(respos.getZ()+0.5*getSize().getZ());
		
		return respos;
	}

	@Override
	public boolean collideWith(WorldObject w) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Collider createSpecificCollider() {
		// TODO Auto-generated method stub
		return RectCuboid.createCentered(position, size);
	}


}
