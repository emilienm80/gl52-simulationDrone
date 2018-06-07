package simulationDrones;

public class Sphere {
	
	private Vect3 center;
	private double radius;
	
	
	public Sphere()
	{
		center=new Vect3(0,0,0);
		radius=0;
	}
	
	public Sphere(Vect3 center, double radius)
	{
		this.center=new Vect3(center);
		this.radius=radius;
		thresholdRadius();
	}
	
	public Sphere(Sphere s)
	{
		this(s.center, s.radius);
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

	public Vect3 getCenter() {
		return center;
	}

	public void setCenter(Vect3 center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	
	
}
