package usp.ime.tcc.Communication;

import java.io.Serializable;

public class ProtocolInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nick;
	private String ip;
	private double latitude;
	private double longitude;
	private double azimuth;
	private ProtocolMessages typeMsg;
	private byte[] message;
	
	
	public ProtocolInformation(AppProtocol app) {
		this.nick = app.getDevice().getNick();
		this.ip = app.getIpDst();
		this.latitude = app.getDevice().getLatitude();
		this.longitude = app.getDevice().getLongitude();
		this.azimuth = app.getDevice().getOrientation();
		this.typeMsg = app.getTypeMsg();
		this.message = app.getMessage();
	}
	
	
	public String getNick() {
		return nick;
	}
	public String getIp() {
		return ip;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public double getAzimuth() {
		return azimuth;
	}
	public ProtocolMessages getTypeMsg() {
		return typeMsg;
	}
	public byte[] getMessage() {
		return message;
	}

}
