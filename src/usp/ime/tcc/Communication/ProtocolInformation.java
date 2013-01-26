package usp.ime.tcc.Communication;

import java.io.Serializable;

import usp.ime.tcc.Auxiliaries.Device;

public class ProtocolInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Device deviceSrc;
	private EProtocolMessages typeMsg;
	private EProtocolTranspLayer protocol;
	private byte[] message;
	
	public ProtocolInformation(Device deviceSrc, AppProtocol app) {
		this.deviceSrc = deviceSrc;
		this.message = app.getMessage();
		this.typeMsg = app.getTypeMsg();
		this.protocol = app.getProtocol();
	}
	
	public Device getDeviceSrc() {
		return deviceSrc;
	}

	public void setDeviceSrc(Device deviceSrc) {
		this.deviceSrc = deviceSrc;
	}

	public byte[] getMessage() {
		return message;
	}

	public EProtocolMessages getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(EProtocolMessages typeMsg) {
		this.typeMsg = typeMsg;
	}

	public EProtocolTranspLayer getProtocol() {
		return protocol;
	}

	public void setProtocol(EProtocolTranspLayer protocol) {
		this.protocol = protocol;
	}

}
