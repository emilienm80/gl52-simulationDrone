package simulationDrones;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 
 * 3D rectangle : Rectangular cuboid
 *
 */
public class RectCuboid extends Collider {

	private Vect3 origin;
	private Vect3 size;//dimensions
	
	
	public RectCuboid()
	{
		origin=new Vect3(0,0,0);
		size=new Vect3(0,0,0);
	}
	
	public RectCuboid(Vect3 origin, Vect3 size)
	{
		this.origin=new Vect3(origin);
		this.size=new Vect3(size);
		thresholdDims();
	}
	
	public RectCuboid(RectCuboid rc)
	{
		this(rc.origin, rc.size);
	}
	
	
	public boolean containsPoint(Vect3 p)
	{
		Vect3 p2=this.getTop();
		return !( 	p.getX()<origin.getX() || p.getX()>p2.getX() || 
					p.getY()<origin.getY() || p.getY()>p2.getY() || 
					p.getZ()<origin.getZ() || p.getZ()>p2.getZ());
	}
	
	public double volume()
	{
		return size.getX()*size.getY()*size.getZ();
	}
	
	
	private void thresholdDims()
	{
		if(size.getX()<0) size.setX(0);
		if(size.getY()<0) size.setY(0);
		if(size.getZ()<0) size.setZ(0);
	}
	
	
	
	public Vect3 getOrigin() {
		return origin;
	}

	public void setOrigin(Vect3 origin) {
		this.origin = origin;
	}

	public Vect3 getSize() {
		return size;
	}

	public void setSize(Vect3 size) {
		this.size = size;
	}
	
	/**
	 * 
	 * @return Farthest point from origin belonging to this cuboid.
	 */
	public Vect3 getTop()
	{
		return origin.getAdded(size);
	}
	
	public Vect3 getCenter()
	{
		return origin.getAdded(size.getMultipliedBy(0.5));
	}
	
	/*
	public boolean pointIsInsideCuboid(Vect3 p)
	{
		return 	p.getX()>this.origin.getX() && p.getX()<this.origin.getX()+this.size.getX() &&
				p.getY()>this.origin.getY() && p.getY()<this.origin.getY()+this.size.getY() &&
				p.getZ()>this.origin.getZ() && p.getZ()<this.origin.getZ()+this.size.getZ();
	}*/
	
	/**
	 * Find the 6 points belonging to the cuboid and each having at least 2 coordinates equal to those of p. 
	 * @param p
	 * @return A list of 6 points
	 */
	public ArrayList<Vect3> get6DirectionsIntersections(Vect3 p)
	{
		ArrayList<Vect3> res=new ArrayList<>();
		
		res.add(new Vect3(origin.getX(), p.getY(), p.getZ()));
		res.add(new Vect3(origin.getX() + size.getX(), p.getY(), p.getZ()));
		res.add(new Vect3(p.getX(), origin.getY(), p.getZ()));
		res.add(new Vect3(p.getX(), origin.getY() + size.getY(), p.getZ()));
		res.add(new Vect3(p.getX(), p.getY(), origin.getZ()));
		res.add(new Vect3(p.getX(), p.getY(), origin.getZ() + size.getZ()));
		
		return res;
	}
	
}
