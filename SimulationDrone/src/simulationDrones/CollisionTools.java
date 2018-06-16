package simulationDrones;

import java.util.ArrayList;
import java.util.Vector;

//TODO decide if position should be already updated with the movement vector when entering collision functions

/**
 * 
 * Useful static collision functions
 * <br><br>
 * The only types of collider box used are Sphere and Cuboid. There are 3 different cases :<br>
 * - sphere collides with sphere<br>
 * - cuboid collides with cuboid<br>
 * - sphere collides with cuboid<br>
 * <br>
 * Thus only 3 collision functions are necessary to find out if objects collided, and 3 more to find the the position of objects after they collided.
 */
public class CollisionTools {

	/**
	 * 
	 * @param rc1
	 * @param rc2
	 * @return true if the two cuboids intersect
	 */
	public static boolean intersect(RectCuboid rc1, RectCuboid rc2)
	{
		Vect3 rc1Top=rc1.getTop();
		Vect3 rc2Top=rc2.getTop();
		Vect3 rc1Origin=rc1.getOrigin();
		Vect3 rc2Origin=rc2.getOrigin();
		
		return ! (	rc1Origin.getX()>rc2Top.getX() || //left
					rc1Top.getX()<rc2Origin.getX() || //right
					rc1Origin.getY()>rc2Top.getY() || //up
					rc1Top.getY()<rc2Origin.getY() || //down
					rc1Origin.getZ()>rc2Top.getZ() || //front
					rc1Top.getZ()<rc2Origin.getZ() ); //back
	}
	
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
		//return whether the sum of the two sphere radiuses is more than the distance between sphere centers.
		return s1.getCenter().squaredDist(s2.getCenter())<(s1.getRadius()+s2.getRadius())*(s1.getRadius()+s2.getRadius());
	}
	
	/**
	 * General instersect
	 * @param c1 first collider
	 * @param c2 second collider
	 * @return true if collision
	 */
	public static boolean interesect(Collider c1, Collider c2) {
		boolean res = false;
		
		if(c1 instanceof Sphere) {
			if(c2 instanceof Sphere) {
				res = intersect((Sphere) c1, (Sphere) c2);
			}else if(c2 instanceof RectCuboid) {
				res = intersect((RectCuboid) c1, (Sphere) c2);
			}
		}else if(c1 instanceof RectCuboid) {
			if(c2 instanceof Sphere) {
				res = intersect((RectCuboid) c1, (Sphere) c2);
			}else if(c2 instanceof RectCuboid) {
				res = intersect((RectCuboid) c1, (RectCuboid) c2);
			}
		}
		return res;
	}
	
	/**
	 * For collision between a moving sphere and a stationary cuboid.
	 * Should be used only if the collision has been successfully tested beforehand. Otherwise, behavior is UNDEFINED (Oh shit !), which means that the resulting sphere center could be anywhere but where you'd expect it.
	 * @param s1
	 * @param s2
	 * @return The center of the sphere after collision with the cuboid. Assuming that the sphere "slips" alongside cuboid.
	 */
	public static Vect3 getSphereCenterAfterCollision(RectCuboid rc, Sphere s, Vect3 sphereMovementVector)
	{
		//sphereMovementVector.substract(cuboidMovementVector);//simplifies the problem, assuming that only the sphere moves relatively to the cuboid
		Vect3 sphereCenter=s.getCenter();
		boolean centerOutsideCuboid=!rc.containsPoint(sphereCenter);//is the center of the sphere inside or outside the cuboid ?
		Vect3 previousSphereCenter=sphereCenter.getSubstracted(sphereMovementVector);
		ArrayList<Vect3> lp=rc.get6DirectionsIntersections(sphereCenter);
		
		double mindist=Double.MAX_VALUE;
		Vect3 collisionPoint=null;//center of the sphere belonging to the cuboid edge
		for(Vect3 p : lp)
		{
			double d=sphereCenter.squaredDist(p)+p.squaredDist(previousSphereCenter);//the minimum occurence of this distance determines which side of the cuboid has been touched first by the sphere
			if(d<mindist)
			{
				mindist=d;
				collisionPoint=p;
			}
		}
		
		//we have to move "collisionPoint" away from the cuboid so that the sphere does not intersect with it anymore
		Vect3 shift=(new Vect3(sphereCenter, collisionPoint)).Normalize().multiplyBy(s.getRadius());
		if(centerOutsideCuboid)//sphere collides with cuboid but sphere center is outside cuboid
		{
			shift.reverse();
		}
		//return collisionPoint.add(shift).add(cuboidMovementVector);
		return collisionPoint.add(shift);
	}
	
	/**
	 * Compute the exact positions of the spheres at the moment of collision. If there is no collision, returns the sphere centers moved by their movement vector.
	 * @param s1
	 * @param s2
	 * @param sphere1MovementVector
	 * @param sphere2MovementVector
	 * @return The two spheres centers at the moment of collision (order :[s1,s2])
	 */
	public static Vector<Vect3> getSpheresCentersAtCollision(Sphere s1, Sphere s2, Vect3 sphere1MovementVector, Vect3 sphere2MovementVector)
	{
		Vector<Vect3> res=new Vector<>();
		
		//simplify the problem
		Sphere s=new Sphere(new Vect3(0,0,0), s1.getRadius()+s2.getRadius());//assume we have a sphere of radius r1+r2 centered on origin and a point
		Vect3 p=s2.getCenter().getSubstracted(s1.getCenter());//the point
		Vect3 pv=sphere2MovementVector.getSubstracted(sphere1MovementVector);//assume that only the point is moving, this is the movement vector of p
		
		//then the movement vector of the point defines a line, whose intersection with the sphere happens at time t. We will find t.
		
		//solve intersection equation Math.pow(at, 2)+bt+c=0
		double a=(pv.getX()*pv.getX()+pv.getY()*pv.getY()+pv.getZ()*pv.getZ());
		double b=2*(pv.getX()*p.getX()+pv.getY()*p.getY()+pv.getZ()*p.getZ());
		double c=(p.getX()*p.getX()+p.getY()*p.getY()+p.getZ()*p.getZ()-s.getRadius()*s.getRadius());
		double discrim=b*b-4*a*c;//compute the discriminant to solve the 2nd order equation of the collision
		
		if(discrim<0)//no collision
		{
			res.add(s1.getCenter().getAdded(sphere1MovementVector));
			res.add(s2.getCenter().getAdded(sphere2MovementVector));
			return res;
		}
		else
		{
			double sqd=Math.sqrt(discrim);
			double t1=0.5*(-b-sqd)/a;
			double t2=0.5*(-b+sqd)/a;
			double t=Math.min(t1, t2);//keep earliest collision time
			
			//as we have t, we can compute spheres centers at collision time, with original spheres data
			Vect3 cs1=(new Vect3(t,t,t)).multiplyBy(sphere1MovementVector).add(s1.getCenter());
			Vect3 cs2=(new Vect3(t,t,t)).multiplyBy(sphere2MovementVector).add(s2.getCenter());
			
			res.add(cs1);
			res.add(cs2);
			return res;
		}
		
	}
	
	/**
	 * For collision between a moving cuboid and a stationary cuboid.
	 * Should be used only if the collision has been successfully tested beforehand. Otherwise, behavior is UNDEFINED (Oh shit !), which means that the resulting cuboid center could be anywhere but where you'd expect it.
	 * @param rc1
	 * @param rc2
	 * @param cuboid1MovementVector
	 * @return rc1 center after collision
	 */
	public static Vect3 getCuboidCenterAfterCollision(RectCuboid rc1, RectCuboid rc2, Vect3 rc1MovementVector)
	{
		Vect3 rc1Center=rc1.getCenter();
		boolean rc1CenterOutsideRc2=!rc2.containsPoint(rc1Center);
		
		Vect3 previousRc1Center=rc1Center.getSubstracted(rc1MovementVector);
		ArrayList<Vect3> lp=rc2.get6DirectionsIntersections(rc1Center);
		
		double mindist=Double.MAX_VALUE;
		Vect3 collisionPoint=null;
		for(Vect3 p : lp)
		{
			double d=rc1Center.squaredDist(p)+p.squaredDist(previousRc1Center);
			if(d<mindist)
			{
				mindist=d;
				collisionPoint=p;
			}
		}
		
		Vect3 shift=(new Vect3(rc1Center, collisionPoint)).Normalize();
		double rc1Dim=0.5*shift.dotProduct(rc1.getSize());//dimension of rc1 to take in account for the shift (x,y or z?) (=radius)
		shift.multiplyBy(rc1Dim);
		if(rc1CenterOutsideRc2)//sphere collides with cuboid but sphere center is outside cuboid
		{
			shift.reverse();
		}
		return collisionPoint.add(shift);
	}
	
	
	public static double limit(double v, double min, double max)
	{
		return Math.min(Math.max(min, v), max);
	}
	
}
