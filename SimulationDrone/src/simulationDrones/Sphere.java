package simulationDrones;

public class Sphere extends Collider {
	
	private double radius;
	
	
	public Sphere()
	{
		super();
		radius=0;
	}
	
	public Sphere(Vect3 center, double radius)
	{
		this.speed=new Vect3(0,0,0);
		this.center=new Vect3(center);
		this.radius=radius;
		thresholdRadius();
	}
	
	public Sphere(Sphere s)
	{
		super(s);
		this.radius=s.radius;
		thresholdRadius();
	}
	
	
	public boolean containsPoint(Vect3 p)
	{
		return center.squaredDist(p)<=radius*radius;
	}
	
	public double volume()
	{
		return (4/3)*Math.PI*radius*radius*radius;
	}
	
	
	private void thresholdRadius()
	{
		if(radius<0) radius=0;
	}


	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public Collider copy() {
		// TODO Auto-generated method stub
		return new Sphere(this);
	}

	@Override
	public String toString() {
		return "Sphere [center="+center+" radius=" + radius + "]";
	}

	
	
}
