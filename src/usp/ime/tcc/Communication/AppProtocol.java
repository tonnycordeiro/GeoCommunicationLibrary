package usp.ime.tcc.Communication;

import java.io.Serializable;

import usp.ime.tcc.Auxiliaries.Device;
import usp.ime.tcc.Auxiliaries.IP;

public class AppProtocol implements Serializable{
	private static final long serialVersionUID = 1L;
	final static String CRLF = "\r\n";
	final static String dividerHeader = " ";
	
	private Device device;
	private String ipDst;
	private byte[] message;
	private ProtocolMessages typeMsg;
	
	public AppProtocol(Device device, byte[] message, ProtocolMessages typeMsg) {
		this.device = device;
		this.message = message;
		this.typeMsg = typeMsg;
		this.ipDst = IP.getBroadcastAddress();
	}
	
	public AppProtocol(ProtocolInformation appInfo) {
		this.device = new Device(appInfo.getNick(), null);
		this.ipDst = appInfo.getIp();
		this.message = appInfo.getMessage();
		this.typeMsg = appInfo.getTypeMsg();
	}

	protected void setIpDst(String ipDst) {
		this.ipDst = ipDst;
	}

	public String getIpDst() {
		return this.ipDst;
	}
	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public ProtocolMessages getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(ProtocolMessages typeMsg) {
		this.typeMsg = typeMsg;
	}
	
	
}
