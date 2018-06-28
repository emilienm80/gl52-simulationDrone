package world;

import physics.collisions.colliders.Collider;
import utilities.Vect3;

//put position, speed and size in the collider only ?
public abstract class WorldObject {
	
	protected Collider collider;//colliding box (sphere or cuboid)
	protected Vect3 position;//center of the bounding box
	protected Vect3 speed;
	protected Vect3 size;//for a sphere this is the diameter	

	public WorldObject() {
		position=new Vect3();
		speed=new Vect3();
		size=new Vect3();
		collider=createSpecificCollider();
	}
	
	/**
	 * Actually useful constructor, thx Francis
	 * @param position
	 * @param speed
	 * @param size
	 */
	public WorldObject(Vect3 position, Vect3 speed, Vect3 size, Collider c) {
		this.position = position;
		this.speed = speed;
		this.size = size;
		this.collider=c;
	}
	
	//we want a deep copy
	public WorldObject(WorldObject w) {
		
		position=new Vect3(w.position);
		speed=new Vect3(w.speed);
		size=new Vect3(w.size);
		collider=w.collider.copy();
	}
	
	//
	///**
	// * returns a new collider (sphere or cuboid) specific to this class.
	// * @return
	// */
	//protected abstract Collider getSpecificCollider();
	
	/**
	 * checks if a collision occurs, and applies necessary actions if this is the case (typically change position of both worldobjects)
	 * <br><br>
	 * Caution ! This affects both the calling WorldObject and the one in parameter, in order to compute only one intersection for two objects
	 * @param w
	 * @return true if a collision occured and was processed
	 */
	public abstract boolean collideWith(WorldObject w);
	
	
	public void move(double time)
	{
		Vect3 dspeed=speed.getMultipliedBy(time);
		
		position=position.getAdded(dspeed);//position+=speed*time
		
		this.collider.setSpeed(dspeed);//set the last position variation, to be used for collision processing
	}
	
	/**for deep copy
	 * 
	 * @return
	 */
	public abstract WorldObject copy();
	
	
	//getters/setters
	public Vect3 getPosition() {
		return position;
	}
	
	//position is not meant to be used outside of this class family, position should be updated by updateSpeed() and collideWith() only
	public void setPosition(Vect3 position) {
		this.position = position;
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
	 * create a specific collider for this object (sphere or cuboid)
	 * @return
	 */
	protected abstract Collider createSpecificCollider();
	
	/**
	 * compute and returns specific collider
	 * @return
	 */
	public Collider getCollider()
	{
		updateCollider();
		return this.collider;
	}
	
	/**
	 * should be called at each frame
	 */
	protected void updateCollider()
	{
		if(this.collider!=null)
		{
			this.collider.setCenter(this.position);
		}
	}

}
