package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.device.Device;
import usp.ime.gclib.hit.ShootingRestrictions;


public class ProtocolGEOMSGInformation extends ProtocolInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private ShootingRestrictions shootRestrictions;
	
	public ProtocolGEOMSGInformation(Device deviceSrc, byte[] message, AppProtocol app, ShootingRestrictions shootRestrictions) {
		super(deviceSrc, message, app);
		this.shootRestrictions = shootRestrictions;
	}
	
	public ProtocolGEOMSGInformation(Device deviceSrc, Object object, AppProtocol app, ShootingRestrictions shootRestrictions) {
		super(deviceSrc, object, app);
		this.shootRestrictions = shootRestrictions;
	}

	public ShootingRestrictions getShootRestrictions() {
		return shootRestrictions;
	}

	public void setShootRestrictions(ShootingRestrictions shootRestrictions) {
		this.shootRestrictions = shootRestrictions;
	}
}
