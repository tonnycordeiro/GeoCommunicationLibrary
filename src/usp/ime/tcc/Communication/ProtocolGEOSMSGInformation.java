package usp.ime.tcc.Communication;

import java.io.Serializable;

import usp.ime.tcc.Auxiliaries.Device;

public class ProtocolGEOSMSGInformation extends ProtocolInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	private double latitude;
	private double longitude;
	private float[] orientation;
	
	public ProtocolGEOSMSGInformation(Device deviceSrc, AppProtocol app) {
		super(deviceSrc, app);
		this.latitude = deviceSrc.getLatitude();
		this.longitude = deviceSrc.getLongitude();
		this.orientation = deviceSrc.getOrientation();
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float[] getOrientation() {
		return orientation;
	}

	public void setOrientation(float[] orientation) {
		this.orientation = orientation;
	}

}
