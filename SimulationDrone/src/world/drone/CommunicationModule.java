package world.drone;

import java.util.ArrayList;

import world.Message;

/**
 * acts as an emission/reception buffer for Drone communication
 * @author Francis
 *
 */
public class CommunicationModule {

	private ArrayList<Message> messagesBufferIN=new ArrayList<Message>();//messages waiting to be read by drone
	private ArrayList<Message> messagesBufferOUT=new ArrayList<Message>();//messages waiting to be broadcasted through environment
	
	//used by PhysicsEngine
	/**
	 * makes the module receive ambient messages
	 * @param mss
	 */
	public void receiveAmbientMessages(ArrayList<Message> mss)
	{
		this.messagesBufferIN.addAll(mss);
	}
	
	//used by PhysicsEngine
	/**
	 * makes the module send messages in environment
	 * @param mss
	 */
	public ArrayList<Message> sendPendingMessages()
	{
		ArrayList<Message> res=this.messagesBufferOUT;
		this.messagesBufferOUT=new ArrayList<Message>();
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
	 * emit a message from the drone
	 * @param m
	 */
	public void emitMessage(Message m)
	{
		messagesBufferOUT.add(m);
	}
}
