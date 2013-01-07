package usp.ime.tcc.Communication;

import java.io.Serializable;

public class ProtocolInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nick;
	private String ipSrc;
	private EProtocolMessages typeMsg;
	private byte[] message;
	
	public ProtocolInformation(AppProtocol app) {
		this.nick = app.getDeviceSrc().getNick();
		this.ipSrc = app.getDeviceSrc().getIp();
		this.message = app.getMessage();
		this.setTypeMsg(app.getTypeMsg());
	}
	
	public String getNick() {
		return nick;
	}
	public byte[] getMessage() {
		return message;
	}
	public String getIpSrc() {
		return ipSrc;
	}

	public EProtocolMessages getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(EProtocolMessages typeMsg) {
		this.typeMsg = typeMsg;
	}

}
