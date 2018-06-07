package simulationDrones;

public abstract class WorldObject {
	
	public Vect3 position;//center of the bounding box
	public Vect3 speed;
	//public Collider collidingBox;
	public Vect3 size;
	
	
	public WorldObject() {
		position=new Vect3();
		speed=new Vect3();
		size=new Vect3();
	}
	
	//we want a deep copy
	public WorldObject(WorldObject w) {
		position=new Vect3(w.position);
		speed=new Vect3(w.speed);
		size=new Vect3(w.size);
	}
	
	
	public abstract void collideWith(WorldObject w);
	
	public void move(double time)
	{
		position=position.getSubstracted(speed.getMultipliedBy(time));//position-=speed*time
	}
	
	public abstract WorldObject copy();

}
