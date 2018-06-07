package simulationDrones;

public class Sphere {
	
	public Vect3 center;
	public double radius;
	
	
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

}
