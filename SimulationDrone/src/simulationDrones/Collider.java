package simulationDrones;

import java.util.Vector;

public abstract class Collider {
	
	protected Vect3 center;//position of this collider's center
	protected Vect3 speed;
	
	public Collider()
	{
		center=new Vect3(0,0,0);
		speed=new Vect3(0,0,0);
	}
	
	public Collider(Collider c)
	{
		center=new Vect3(c.center);
		speed=new Vect3(c.speed);
	}
	
	public Vect3 getCenter() {
		return new Vect3(center);
	}

	public void setCenter(Vect3 center) {
		this.center = new Vect3(center);
	}
	
	public Vect3 getSpeed() {
		return new Vect3(speed);
	}

	public void setSpeed(Vect3 speed) {
		this.speed = new Vect3(speed);
	}

	public abstract double volume();
	
	public abstract boolean containsPoint(Vect3 p);
	
	
	/**
	 * General intersect
	 * @param c1 first collider
	 * @param c2 second collider
	 * @return true if collision
	 */
	public boolean intersectWith(Collider c) {

		return CollisionTools.intersect(this, c);
	}
	
	
	//! some movement vectors are left unused in the current implementation
	public static Vector<Vect3> getCollidersCentersAfterCollision(Collider c1, Collider c2)
	{				
		Vect3 c1MovementVector=c1.getSpeed();
		Vect3 c2MovementVector=c2.getSpeed();
		
		Vector<Vect3> res=new Vector<>();
		
		if(c1 instanceof Sphere) {
			if(c2 instanceof Sphere) {
				res = getCollidersCentersAfterCollision((Sphere) c1, (Sphere) c2, c1MovementVector, c2MovementVector);
			}else if(c2 instanceof RectCuboid) {
				res = getCollidersCentersAfterCollision((Sphere) c1, (RectCuboid) c2, c2MovementVector, c1MovementVector);
			}
		}else if(c1 instanceof RectCuboid) {
			if(c2 instanceof Sphere) {
				res = getCollidersCentersAfterCollision((RectCuboid) c1, (Sphere) c2, c1MovementVector, c2MovementVector);
			}else if(c2 instanceof RectCuboid) {
				res = getCollidersCentersAfterCollision((RectCuboid) c1, (RectCuboid) c2, c1MovementVector, c2MovementVector);
			}
		}
		
		return res;
	}
	
	
	//TODO adapt this for a moving cuboid
	private static Vector<Vect3> getCollidersCentersAfterCollision(RectCuboid rc, Sphere s, Vect3 cuboidMovementVector, Vect3 sphereMovementVector)
	{
		Vector<Vect3> res=new Vector<>();
		Vect3 crc=rc.getCenter();
		Vect3 cs=CollisionTools.getSphereCenterAfterCollision(rc, s, sphereMovementVector);
		
		res.add(crc);
		res.add(cs);
		
		return res;
	}
	
	//TODO adapt this for a moving cuboid
	private static Vector<Vect3> getCollidersCentersAfterCollision(Sphere s, RectCuboid rc, Vect3 sphereMovementVector, Vect3 cuboidMovementVector)
	{
		Vector<Vect3> res=new Vector<>();
		Vect3 crc=rc.getCenter();
		Vect3 cs=CollisionTools.getSphereCenterAfterCollision(rc, s, sphereMovementVector);
		
		res.add(cs);
		res.add(crc);
		
		return res;
	}
	
	private static Vector<Vect3> getCollidersCentersAfterCollision(Sphere s1, Sphere s2, Vect3 sphere1MovementVector, Vect3 sphere2MovementVector)
	{
		return CollisionTools.getSpheresCentersAtCollision(s1, s2, sphere1MovementVector, sphere2MovementVector);
	}
	
	//TODO adapt this for two moving cuboids
	private static Vector<Vect3> getCollidersCentersAfterCollision(RectCuboid rc1, RectCuboid rc2, Vect3 rc1MovementVector, Vect3 rc2MovementVector)
	{
		Vector<Vect3> res=new Vector<>();
		Vect3 crc1=CollisionTools.getCuboidCenterAfterCollision(rc1, rc2, rc1MovementVector);
		Vect3 crc2=rc2.getCenter();
		
		res.add(crc1);
		res.add(crc2);
		
		return res;
	}
	
	/**for deep copy
	 * 
	 * @return
	 */
	public abstract Collider copy();

}
