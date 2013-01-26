package usp.ime.tcc.Communication;

import java.io.Serializable;

public class ProtocolGEOSMSGInformation extends ProtocolInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	private double latitude;
	private double longitude;
	private double azimuth;
	
	public ProtocolGEOSMSGInformation(AppProtocol app) {
		super(app);
		this.latitude = app.getDeviceSrc().getLatitude();
		this.longitude = app.getDeviceSrc().getLongitude();
		this.azimuth = app.getDeviceSrc().getOrientation()[0];
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

	public double getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(double azimuth) {
		this.azimuth = azimuth;
	}

}
