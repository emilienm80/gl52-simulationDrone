package world;

import utilities.Vect3;

/**
 * represents an electromagnetic wave carrying a message with a specific range around the emission point
 * @author Francis
 *
 */
public class Emission {
	
	private Vect3 center;//origin of emission
	private double radius;//range
	private Message message;//information broadcasted in the wave (its reading time is supposed instantaneous to simplify implementation)
	
	
	public Emission(Vect3 center, double radius, Message message) {
		this.center = center;
		this.radius = radius;
		this.message = message;
	}
	
	public Emission(Emission em)
	{
		this.center = new Vect3(center);
		this.radius = radius;
		this.message = new Message(message);
	}
	
	public Message getMessage() {
		return new Message(message);
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Vect3 getCenter() {
		return center;
	}
	public double getRadius() {
		return radius;
	}
	
}
