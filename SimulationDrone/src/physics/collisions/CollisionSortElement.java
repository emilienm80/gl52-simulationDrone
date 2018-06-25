package physics.collisions;

import world.WorldObject;

import java.util.ArrayList;
import java.util.Collection;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import world.WorldObject;

/**
 * used to store an association of objects and their distance for sorting according to this distance.
 * The distance is between the object referenced in this class and another object that doesn't need to be referenced
 * @author Francis
 *
 */
public class CollisionSortElement {

	public double distance;//no encapsulation needed as this is only storage
	
	public WorldObject obj;
	
	public CollisionSortElement(WorldObject w, double distance)
	{
		this.distance=distance;
		this.obj=w;
	}
	
	
	public static void sortInAscendingOrder(ArrayList<CollisionSortElement> list)
	{
		java.util.Collections.sort(list, (a, b) -> a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1);
	}
	
	public static void sortInDescendingOrder(ArrayList<CollisionSortElement> list)
	{
		java.util.Collections.sort(list, (a, b) -> a.distance < b.distance ? 1 : a.distance == b.distance ? 0 : -1);
	}
}
