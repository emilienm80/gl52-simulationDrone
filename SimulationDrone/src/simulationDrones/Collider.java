package simulationDrones;

public abstract class Collider {
	
	public abstract double volume();
	
	public abstract boolean containsPoint(Vect3 p);

}
