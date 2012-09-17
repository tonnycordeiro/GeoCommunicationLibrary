package com.example.tcclibrary;

/**
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class CommunicationSocket {
	
	private static final int PORT = 57843;
	private Device sourceDevice;
	private String message;
	
	/**
	 * Send a default message. Just a empty String.
	 * 
	 * @param sourceSevice Indicates the source device that will be sent the default message.
	 */
	public CommunicationSocket(Device sourceSevice) {
		this.sourceDevice = sourceSevice;
	}
	
	/**
	 * Send a message from the sourceDevice.
	 *  
	 * @param sourceDevice Indicates the source device that will be sent the message
	 * @param message The message that will be send.
	 */
	public CommunicationSocket(Device sourceDevice, String message) {
		this.sourceDevice = sourceDevice;
		this.message = message;
	}
	
	/**
	 * This method will be sent the message that is specified in message attribute.
	 * 
	 * @return A integer that indicates the state of the sending.
	 * The possibles values are:
	 * <li>0: Successful sending.</li>
	 * <li>1: Sending Error.</li>
	 */
	public int sendMessage(){
		//TODO - finish this method
		
		return 0;
	}
	
	/**
	 * This method is used to receive a message from another device.
	 * It wait until the message arrives.
	 * 
	 * @return The message that was received.
	 */
	public String receiveMessage(){
		//TODO - finish this method
		
		return null;
	}

}
