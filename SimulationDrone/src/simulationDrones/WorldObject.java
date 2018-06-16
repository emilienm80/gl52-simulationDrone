package simulationDrones;

public abstract class WorldObject {
	
	protected Vect3 position;//center of the bounding box
	protected Vect3 speed;
	protected Vect3 size;//for a sphere this is the diameter	

	public WorldObject() {
		position=new Vect3();
		speed=new Vect3();
		size=new Vect3();
	}
	
	/**
	 * Actually useful constructor, thx Francis
	 * @param position
	 * @param speed
	 * @param size
	 */
	public WorldObject(Vect3 position, Vect3 speed, Vect3 size) {
		this.position = position;
		this.speed = speed;
		this.size = size;
	}
	
	//we want a deep copy
	public WorldObject(WorldObject w) {
		position=new Vect3(w.position);
		speed=new Vect3(w.speed);
		size=new Vect3(w.size);
	}
	
	//
	///**
	// * returns a new collider (sphere or cuboid) specific to this class.
	// * @return
	// */
	//protected abstract Collider getSpecificCollider();
	
	public abstract boolean collideWith(WorldObject w);
	
	//public abstract void applyCollision(WorldObject w);
	
	public void move(double time)
	{
		position=position.getAdded(speed.getMultipliedBy(time));//position+=speed*time
	}
	
	public abstract WorldObject copy();
	
	
	//getters/setters
	public Vect3 getPosition() {
		return position;
	}

	public Vect3 getSpeed() {
		return speed;
	}
	
	public void setSpeed(Vect3 speed) {
		this.speed = speed;
	}

	public Vect3 getSize() {
		return size;
	}
	
	/**
	 * compute and returns specific collider
	 * @return
	 */
	public abstract Collider getCollider();

}
