package usp.ime.tcc.Communication;

import java.io.Serializable;

public class GeoAckInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private double distance;
	private double relativeAzimuth;
	
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

}
