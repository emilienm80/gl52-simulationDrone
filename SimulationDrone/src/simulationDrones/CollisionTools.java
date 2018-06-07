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
				limit(s.center.getX(), rc.origin.getX(), rc.origin.getX()+rc.size.getX()), 
				limit(s.center.getY(), rc.origin.getY(), rc.origin.getY()+rc.size.getY()), 
				limit(s.center.getZ(), rc.origin.getZ(), rc.origin.getZ()+rc.size.getZ()));
		
		return closestCuboidPoint.squaredDist(s.center)<s.radius*s.radius;
	}
	
	/**
	 * 
	 * @param s1
	 * @param s2
	 * @return true if the two spheres intersect
	 */
	public static boolean intersect(Sphere s1, Sphere s2)
	{
		return s1.center.squaredDist(s2.center)<(s1.radius+s2.radius)*(s1.radius+s2.radius);
	}
	/*
	public static double getCenterBeforeIntersection(Sphere s1, Sphere s2)
	{
		return s1.center.squaredDist(s2.center)<(s1.radius+s2.radius)*(s1.radius+s2.radius);
	}
	*/
	
	public static double limit(double v, double min, double max)
	{
		return Math.min(Math.max(min, v), max);
	}
}
