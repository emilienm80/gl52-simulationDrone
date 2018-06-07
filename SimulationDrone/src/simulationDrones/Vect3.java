package simulationDrones;

/**
 * Class intended to represent either a 3D point or vector.
 * <br>
 * Be careful, null references are not checked for speed and laziness purposes.
 */
public class Vect3 {
	
	private static final Vect3 Default=new Vect3();
	
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
		return dist(Default);
	}
	
	/**
	 * 
	 * @return this vector with a norm of 1
	 */
	public Vect3 getNormalized()
	{
		return this.getMultipliedBy(1/this.norm());
	}
	
	
	/**
	 * Merci la surcharge d'opérateurs !
	 * @param c
	 * @return this vector/point multiplied by c.
	 */
	public Vect3 getMultipliedBy(double c)
	{
		return new Vect3(x*c, y*c, z*c);
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

}
