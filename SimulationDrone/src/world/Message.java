package world;

import utilities.Vect3;

/**
 * Stores a message emitted through a wave (Emission class)
 * It is mainly used by agents to communicate their state and intentions
 * @author Francis
 *
 */
public class Message {
	
	protected String text;
	protected Vect3 position;
	protected Vect3 speed;
	protected Vect3 targetPos;
	protected Vect3 startPos;
	protected double batteryLevel;
	protected int priority;
	
	
	public Message(String text, Vect3 position, Vect3 speed, Vect3 targetPos, Vect3 startPos, double batteryLevel,
			int priority) {
		this.text = text;
		this.position = position;
		this.speed = speed;
		this.targetPos = targetPos;
		this.startPos = startPos;
		this.batteryLevel = batteryLevel;
		this.priority = priority;
	}
	
	public Message(Message m) {
		this.text = m.text;
		this.position = new Vect3(position);
		this.speed = new Vect3(speed);
		this.targetPos = new Vect3(targetPos);
		this.startPos = new Vect3(startPos);
		this.batteryLevel = batteryLevel;
		this.priority = priority;
	}
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Vect3 getPosition() {
		return position;
	}
	public void setPosition(Vect3 position) {
		this.position = position;
	}
	public Vect3 getSpeed() {
		return speed;
	}
	public void setSpeed(Vect3 speed) {
		this.speed = speed;
	}
	public Vect3 getTargetPos() {
		return targetPos;
	}
	public void setTargetPos(Vect3 targetPos) {
		this.targetPos = targetPos;
	}
	public Vect3 getStartPos() {
		return startPos;
	}
	public void setStartPos(Vect3 startPos) {
		this.startPos = startPos;
	}
	public double getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	

}
