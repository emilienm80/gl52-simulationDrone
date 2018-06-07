package simulationDrones;

/**
 * 
 * Useful static collision functions
 *
 */
public class CollisionTools {

	/**
	 * 
	 * @param rc
	 * @param s
	 * @return true if the cuboid and sphere intersect
	 */
	public static boolean intersect(RectCuboid rc, Sphere s)
	{
		//closest point to the sphere belonging to the cuboid
		Vect3 closestCuboidPoint=new Vect3(
				limit(s.getCenter().getX(), rc.getOrigin().getX(), rc.getOrigin().getX()+rc.getSize().getX()), 
				limit(s.getCenter().getY(), rc.getOrigin().getY(), rc.getOrigin().getY()+rc.getSize().getY()), 
				limit(s.getCenter().getZ(), rc.getOrigin().getZ(), rc.getOrigin().getZ()+rc.getSize().getZ()));
		
		return closestCuboidPoint.squaredDist(s.getCenter())<s.getRadius()*s.getRadius();
	}
	
	/**
	 * 
	 * @param s1
	 * @param s2
	 * @return true if the two spheres intersect
	 */
	public static boolean intersect(Sphere s1, Sphere s2)
	{
		//if the sum of the two sphere radiuses is more than the distance between sphere centers.
		return s1.getCenter().squaredDist(s2.getCenter())<(s1.getRadius()+s2.getRadius())*(s1.getRadius()+s2.getRadius());
	}
	/*
	public static double getCenterBeforeIntersection(Sphere s1, Sphere s2)
	{
		return s1.getCenter().squaredDist(s2.getCenter())<(s1.getRadius()+s2.getRadius())*(s1.getRadius()+s2.getRadius());
	}
	*/
	
	public static double limit(double v, double min, double max)
	{
		return Math.min(Math.max(min, v), max);
	}
}
