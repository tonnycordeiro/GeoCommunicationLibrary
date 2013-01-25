package usp.ime.tcc.Communication;

import java.io.Serializable;

public class ProtocolGEOACKInformation extends ProtocolInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private double latitude;
	private double longitude;
	private double distance;
	
	public ProtocolGEOACKInformation(AppProtocol app, double distance) {
		super(app);
		this.latitude = app.getDeviceSrc().getLatitude();
		this.longitude = app.getDeviceSrc().getLongitude();
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
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

}
