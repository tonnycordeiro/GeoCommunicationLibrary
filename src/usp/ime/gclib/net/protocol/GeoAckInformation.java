package usp.ime.gclib.net.protocol;

import java.io.Serializable;

public class GeoAckInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private double distance;
	private double relativeAzimuth;
	private boolean isHit;
	
	public GeoAckInformation(double distance, double relativeAzimuth) {
		this.distance = distance;
		this.relativeAzimuth = relativeAzimuth;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getRelativeAzimuth() {
		return relativeAzimuth;
	}

	public void setRelativeAzimuth(double relativeAzimuth) {
		this.relativeAzimuth = relativeAzimuth;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

}
