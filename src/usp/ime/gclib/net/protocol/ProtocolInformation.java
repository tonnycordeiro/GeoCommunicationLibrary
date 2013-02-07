package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.Device;

public class ProtocolInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Device deviceSrc;
	private EProtocolMessages typeMsg;
	private EProtocolTranspLayer protocol;
	private byte[] message;
	private Object object;
	
	public ProtocolInformation(Device deviceSrc, byte[] message, AppProtocol app) {
		this.deviceSrc = deviceSrc;
		this.message = message;
		this.typeMsg = app.getTypeMsg();
		this.protocol = app.getProtocol();
	}
	
	public ProtocolInformation(Device deviceSrc, Object object, AppProtocol app) {
		this.deviceSrc = deviceSrc;
		this.object = object;
		this.typeMsg = app.getTypeMsg();
		this.protocol = app.getProtocol();
	}
	
	public Device getDeviceSrc() {
		return deviceSrc;
	}

	public void setDeviceSrc(Device deviceSrc) {
		this.deviceSrc = deviceSrc;
	}
	
	public void setMessage(byte[] message) {
		this.message = message;
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
