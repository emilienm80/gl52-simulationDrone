package world.drone;

import java.util.ArrayList;

import utilities.Vect3;
import world.Emission;
import world.Message;

/**
 * acts as an emission/reception buffer for Drone communication
 * @author Francis
 *
 */
public class CommunicationModule {

	private ArrayList<Message> messagesBufferIN=new ArrayList<Message>();//messages waiting to be read by drone
	private ArrayList<Message> messagesBufferOUT=new ArrayList<Message>();//messages waiting to be broadcasted through environment
	private Drone attachedDrone;
	
	public CommunicationModule(Drone d)
	{
		attachedDrone=d;
	}
	
	//TODO implement copy constructor -> deep copies lists

	//used by PhysicsEngine
	/**
	 * makes the module receive ambient messages
	 * @param mss
	 */
	public void receiveAmbientCommunications(ArrayList<Emission> ems)
	{
		for (Emission em : ems) {
			if (em.getCenter().dist(attachedDrone.getPosition()) <= em.getRadius())// if within range
			{
				this.messagesBufferIN.add(em.getMessage());
			}
		}
	}
	
	//used by PhysicsEngine
	/**
	 * makes the module send messages in environment
	 * @param mss
	 */
	public ArrayList<Emission> sendPendingMessages()
	{
		ArrayList<Emission> res=new ArrayList<Emission>();
		
		for (Message m : messagesBufferOUT) {
			res.add(new Emission(new Vect3(attachedDrone.getPosition()), attachedDrone.getCharacteristics().getCommunicationRange(), m));
		}

		messagesBufferOUT.clear();
		
		return res;
	}
	
	//used by Drone
	/**
	 * read a message from this module
	 * @return
	 */
	public Message readNextMessage()
	{
		if(messagesBufferIN.size()<=0) return null;

		return messagesBufferIN.remove(0);
	}
	
	//used by Drone
	/**
	 * read all messages from this module
	 * @return
	 */
	public ArrayList<Message> readAllMessages()
	{
		ArrayList<Message> res=new ArrayList<Message>();
			
		while(!messagesBufferIN.isEmpty())
		{
			res.add(readNextMessage());
		}
		
		return res;
	}
	
	//used by Drone
	/**
	 * read all messages from this module targeted to this drone, while deleting others
	 * @return
	 */
	public ArrayList<Message> readAllPersonalMessages()
	{
		ArrayList<Message> res=new ArrayList<Message>();
			
		while(!messagesBufferIN.isEmpty())
		{
			Message m=readNextMessage();
			if(m.getReceiverId()==attachedDrone.getId())
			{
				res.add(m);
			}
		}
		
		return res;
	}
	
	//used by Drone
	/**
	 * emit a message from the drone
	 * @param m
	 */
	public void emitMessage(Message m)
	{
		messagesBufferOUT.add(m);
	}

	//used by Drone
	/**
	 * emit a message from the drone, easier to use, parameters autocompleting
	 * @param m
	 */
	public void emitMessageFast(Message m)
	{
		m.setBatteryLevel(attachedDrone.getBatteryLevelRelative());
		m.setMovingSpace(attachedDrone.getCharacteristics().getRadius());
		m.setPosition(attachedDrone.getPosition());
		m.setSenderId(attachedDrone.getId());
		m.setSpeed(attachedDrone.getSpeed());
		m.setTargetPos(attachedDrone.getBrain().getTargetPos());
		
		messagesBufferOUT.add(m);
	}
	

	
	public boolean receptionBufferEmpty()
	{
		return messagesBufferIN.isEmpty();
	}
}
