package simulationDrones;

import java.util.Vector;

/**
 * 
 * 3D rectangle : Rectangular cuboid
 *
 */
public class RectCuboid {

	public Vect3 origin;
	public Vect3 size;//dimensions
	
	
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
		Vect3 p2=new Vect3(origin.x+size.x, origin.y+size.y, origin.z+size.z);//the farthest cuboid point from its origin
		return !( p.x<origin.x || p.x>p2.x || p.y<origin.y || p.y>p2.y || p.z<origin.z || p.z>p2.z);
	}
	
	public double volume()
	{
		return size.x*size.y*size.z;
	}
	
	
	private void thresholdDims()
	{
		if(size.x<0) size.x=0;
		if(size.y<0) size.y=0;
		if(size.z<0) size.z=0;
	}
	
}
