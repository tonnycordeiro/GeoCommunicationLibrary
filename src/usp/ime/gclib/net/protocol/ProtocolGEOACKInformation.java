package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.device.Device;

public class ProtocolGEOACKInformation extends ProtocolInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private GeoAckInformation geoAckInfo;
	
	public ProtocolGEOACKInformation(Device deviceSrc, AppProtocol app, GeoAckInformation geaoAckInfo) {
		super(deviceSrc, app);
		this.geoAckInfo = geaoAckInfo;
	}

	public GeoAckInformation getGeoAckInfo() {
		return geoAckInfo;
	}

	public void setGeoAckInfo(GeoAckInformation geoAckInfo) {
		this.geoAckInfo = geoAckInfo;
	}

}
