package simulationDrones;

import java.util.ArrayList;

/**
 * Class intended to represent either a 3D point or vector.
 * <br>
 * Be careful, null references are not checked for speed and laziness purposes.
 */
public class Vect3 {
	
	private static final Vect3 ZeroVect3=new Vect3(0,0,0);
	
	private double x,y,z;
	
	public Vect3()
	{
		this(0,0,0);
	}
	
	

	public Vect3(double x, double y, double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Vect3(Vect3 v)
	{
		this(v.x,v.y,v.z);
	}
	
	/**
	 * Creates the vector AB from two points A and B.
	 * @param a
	 * @param b
	 */
	public Vect3(Vect3 a, Vect3 b)
	{
		x=b.x-a.x;
		y=b.y-a.y;
		z=b.z-a.z;
	}
	
	/**
	 * Dope !
	 * 
	 * @param v Triplet of coordinates assumed to be a 3D point.
	 * @return Squared euclidian dist between this and v, considered as 3D points.
	 * 	 */
	public double squaredDist(Vect3 v)
	{
		return (x-v.x)*(x-v.x)+(y-v.y)*(y-v.y)+(z-v.z)*(z-v.z);
	}
	
	/**
	 * 
	 * @param v Triplet of coordinates assumed to be a 3D point.
	 * @return Euclidian Dist between this and v, considered as 3D points.
	 */
	public double dist(Vect3 v)
	{
		return Math.sqrt(squaredDist(v));
	}
	
	public double dotProduct(Vect3 v)
	{
		return x*v.x+y*v.y+z*v.z;
	}
	
	/**
	 * 
	 * @return Norm of this vector.
	 */
	public double norm()
	{
		return dist(ZeroVect3);
	}
	
	/**
	 * 
	 * @return this vector with a norm of 1
	 */
	public Vect3 getNormalized()
	{
		double norm=this.norm();
		
		if(norm==0)
		{
			return new Vect3(0,0,1);//we don't throw an exception, but orient it towards the z axis by convention
		}
		
		return this.getMultipliedBy(1/norm);
	}
	
	/**
	 * Normalize this vector (norm=1)
	 * @return itself
	 */
	public Vect3 Normalize()
	{
		double norm=this.norm();
		
		if(norm==0)
		{
			return this;//we don't throw an exception
		}
		
		return this.multiplyBy(1/norm);
	}
	
	public Vect3 reverse()
	{
		x=-x;
		y=-y;
		z=-z;
		
		return this;
	}
	
	public Vect3 getReversed()
	{
		return new Vect3(-x,-y,-z);
	}
	
	
	/**
	 * Merci la surcharge d'operateurs !
	 * @param c
	 * @return this vector/point multiplied by c.
	 */
	public Vect3 getMultipliedBy(double c)
	{
		return new Vect3(x*c, y*c, z*c);
	}
	
	/**
	 * Multiply pairs of coordinates (x*x,y*y,z*z)
	 * @param v
	 * @return new multiplied vector
	 */
	public Vect3 getMultipliedBy(Vect3 v)
	{
		return new Vect3(x*v.x, y*v.y, z*v.z);
	}
	
	/**
	 * 
	 * @param a
	 * @return this+a
	 */
	public Vect3 getAdded(Vect3 a)
	{
		return new Vect3(x+a.x, y+a.y, z+a.z);
	}
	
	/**
	 * 
	 * @param a
	 * @return this-a
	 */
	public Vect3 getSubstracted(Vect3 a)
	{
		return new Vect3(x-a.x, y-a.y, z-a.z);
	}
	
	/**
	 * Multiply this vector by scalar c.
	 * @param c
	 * @return this
	 */
	public Vect3 multiplyBy(double c)
	{
		x*=c;
		y*=c;
		z*=c;
		
		return this;
	}
	
	/**
	 * Multiply pairs of coordinates (x*x,y*y,z*z) = combined product
	 * @param v
	 * @return this
	 */
	public Vect3 multiplyBy(Vect3 v)
	{
		x*=v.x;
		y*=v.y;
		z*=v.z;
		
		return this;
	}
	
	/**
	 * Add a to this vector.
	 * @param a
	 * @return this
	 */
	public Vect3 add(Vect3 a)
	{
		x+=a.x;
		y+=a.y;
		z+=a.z;
		
		return this;
	}
	
	/**
	 * Substract a to this vector.
	 * @param a
	 * @return this
	 */
	public Vect3 substract(Vect3 a)
	{
		x-=a.x;
		y-=a.y;
		z-=a.z;
		
		return this;
	}
	
	/**
	 * 
	 * @param p
	 * @param lp
	 * @return from the list of points lp, returns the closest point to this one.
	 * Returns null if arraylist is empty.
	 */
	public Vect3 getClosestPoint(ArrayList<Vect3> lp)
	{
		double mindist=Double.MAX_VALUE;
		Vect3 res=null;
		for(Vect3 p : lp)
		{
			double d=this.squaredDist(p);
			if(d<mindist)
			{
				mindist=d;
				res=p;
			}
		}
		
		return res;
	}
	
	public boolean isNullVector()
	{
		return this.isEqualTo(ZeroVect3);
	}
	
	
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}



	@Override
	public String toString() {
		return "Vect3 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	/**
	 * return a fixed length representation
	 * @param len
	 * @return
	 */
	public String toStringLen(int len, int decimals) {
		String res="[x=" + CollisionTools.round(x, decimals) + ", y=" + CollisionTools.round(y, decimals) + ", z=" + CollisionTools.round(z, decimals) + "]";
		int diff=len-res.length();

		for(int i=0;i<diff;i++)
		{
			res+=" ";
		}

		return res=res.substring(0, len);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vect3 other = (Vect3) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
	/**
	 * Faster but less strict method than "equals". Does practically the same, but only with a Vect3.
	 * @param v
	 * @return
	 */
	public boolean isEqualTo(Vect3 v)
	{
		return (v!=null) && v.x==x && v.y==y && v.z==z; 
	}
	
	//deep copy
	public Vect3 copy()
	{
		return new Vect3(this);
	}


}
