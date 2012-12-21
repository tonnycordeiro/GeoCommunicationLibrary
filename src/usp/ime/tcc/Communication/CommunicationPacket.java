package usp.ime.tcc.Communication;

import usp.ime.tcc.Auxiliaries.Device;


public class CommunicationPacket {

	private Device srcDevice;
	private byte[] message;
	private String srcIP;
	
	/**
	 * Create a constructor of {@link CommunicationPacket} class.
	 * 
	 * @param device Define the source device.
	 * @param senderType See {@link SenderType} to understand the sender types. 
	 * @param message It is obtained and used by application.
	 */
	public CommunicationPacket(Device device, byte[] message) {
		this.srcDevice = device;
		this.message = message;
	}
	
	/**
	 * This constructor is used to receive message.
	 * 
	 * @param message It's used to store the data receives.
	 */
	public CommunicationPacket(byte[] message){
		this.message = message;
	}
	
	
	public byte[] getData() {
		return this.message;
	}
	
	public void setData(byte[] message) {
		this.message = message;
	}
	
	public Device getSrcDevice() {
		return this.srcDevice;
	}
	
	public String getSrcIP(){
		return this.srcIP;
	}
	
	public void setSrcIP(String srcIP){
		this.srcIP = srcIP;
	}
		
}
