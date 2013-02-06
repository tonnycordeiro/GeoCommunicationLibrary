package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.device.Device;

public class ProtocolGEOACKInformation extends ProtocolInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private GeoAckInformation geoAckInfo;
	
	public ProtocolGEOACKInformation(Device deviceSrc, byte[] message, AppProtocol app, GeoAckInformation geaoAckInfo) {
		super(deviceSrc, message, app);
		this.geoAckInfo = geaoAckInfo;
	}
	
	public ProtocolGEOACKInformation(Device deviceSrc, Object object, AppProtocol app, GeoAckInformation geaoAckInfo) {
		super(deviceSrc, object, app);
		this.geoAckInfo = geaoAckInfo;
	}

	public GeoAckInformation getGeoAckInfo() {
		return geoAckInfo;
	}

	public void setGeoAckInfo(GeoAckInformation geoAckInfo) {
		this.geoAckInfo = geoAckInfo;
	}

}
