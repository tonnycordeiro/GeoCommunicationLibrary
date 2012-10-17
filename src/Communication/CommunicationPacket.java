package Communication;

import java.util.List;

import Auxiliaries.Device;
import Auxiliaries.SenderType;

public class CommunicationPacket {

	private Device device;
	private SenderType senderType;
	private byte[] message;
	private String dstIP;
	private List<String> dstIPs;
	private String srcIP;
	
	/**
	 * Create a constructor of {@link CommunicationPacket} class.
	 * 
	 * @param device Define the source device.
	 * @param senderType See {@link SenderType} to understand the sender types. 
	 * @param message It is obtained and used by application.
	 */
	public CommunicationPacket(Device device, SenderType senderType, byte[] message) {
		this.device = device;
		this.senderType = senderType;
		this.message = message;
	}
	
	/**
	 * This constructor is used to send message in the Unicast mode specifying the destine IP.
	 * 
	 * @param device
	 * @param senderType
	 * @param message
	 * @param dstIP
	 */
	public CommunicationPacket(Device device, SenderType senderType, byte[] message, String dstIP) {
		this.device = device;
		this.senderType = senderType;
		this.message = message;
		this.dstIP = dstIP;
	}
	
	/**
	 * This constructor is used to send message in the Unicast mode to a List of destine IPs.
	 * It's usually used when UDP has a low reliability.
	 * 
	 * @param device
	 * @param senderType
	 * @param message
	 * @param dstIPs
	 */
	public CommunicationPacket(Device device, SenderType senderType, byte[] message, List<String> dstIPs) {
		this.device = device;
		this.senderType = senderType;
		this.message = message;
		this.dstIPs = dstIPs;
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
		return this.device;
	}
	
	public SenderType getSenderType() {
		return this.senderType;
	}
	
	protected String getDstIP(){
		return this.dstIP;
	}
	
	public void setDstIP(String dstIP){
		this.dstIP = dstIP;
	}
	
	public String getSrcIP(){
		return this.srcIP;
	}
	
	public void setSrcIP(String srcIP){
		this.srcIP = srcIP;
	}
	
	protected List<String> getDstIps() {
		return this.dstIPs;
	}
	
}
