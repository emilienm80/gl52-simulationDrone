package simulationDrones;

import java.util.Vector;

/**
 * 
 * 3D rectangle : Rectangular cuboid
 *
 */
public class RectCuboid {

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
		Vect3 p2=new Vect3(origin.getX()+size.getX(), origin.getY()+size.getY(), origin.getZ()+size.getZ());//the farthest cuboid point from its origin
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
	
}
