package world;

import utilities.Vect3;
import world.drone.ComInteract;
import world.drone.Priority;

/**
 * Stores a message emitted through a wave (Emission class)
 * It is mainly used by agents to communicate their state and intentions
 * @author Francis
 *
 */
public class Message {
	
	//all messages are public, but the receiver id allows an exchange to take place only between concerned agents
	
	protected String text;
	protected int senderId;
	protected int receiverId;//0 for pure broadcast
	protected Vect3 position;
	protected Vect3 speed;
	protected Vect3 targetPos;
	protected Vect3 startPos;
	protected double batteryLevel;
	protected Priority priority;
	protected ComInteract interaction;
	protected double vSpace;//usually, the space needed to move during one second
	
	
	public Message(int senderId, int receiverId, String text, Vect3 position, Vect3 speed, Vect3 targetPos, Vect3 startPos, double batteryLevel,
			Priority priority, ComInteract interaction, double vSpace) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.text = text;
		this.position = position;
		this.speed = speed;
		this.targetPos = targetPos;
		this.startPos = startPos;
		this.batteryLevel = batteryLevel;
		this.priority = priority;
		this.interaction = interaction;
		this.vSpace=vSpace;
	}
	
	/**
	 * Simplified constructor for fast use (used with emitfast in com module)
	 * @param receiverId
	 * @param text
	 * @param startPos
	 * @param priority
	 * @param interaction
	 */
	public Message(int receiverId, String text, Vect3 startPos, Priority priority, ComInteract interaction) {
		this.receiverId = receiverId;
		this.startPos = startPos;
		this.priority = priority;
		this.interaction = interaction;
	}
	

	
	public Message(Message m) {
		this.senderId = m.senderId;
		this.receiverId = m.receiverId;
		this.position = new Vect3(position);
		this.speed = new Vect3(speed);
		this.targetPos = new Vect3(targetPos);
		this.startPos = new Vect3(startPos);
		this.batteryLevel = m.batteryLevel;
		this.priority = m.priority;
		this.interaction = m.interaction;
		this.vSpace=m.vSpace;
	}
	
	
	
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
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
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public ComInteract getInteraction() {
		return interaction;
	}
	public void setInteraction(ComInteract interaction) {
		this.interaction = interaction;
	}
	public double getvSpace() {
		return vSpace;
	}
	public void setMovingSpace(double vSpace) {
		this.vSpace = vSpace;
	}
	
	
	

}
