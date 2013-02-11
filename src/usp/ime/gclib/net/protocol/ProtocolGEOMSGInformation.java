package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.Device;
import usp.ime.gclib.hit.ShootingRestrictions;

/**
 * This class extends ProtocolInformation and use a ShootingRestricions object for additional informations.
 * @see ProtocolInformation
 * @see ShootingRestrictions
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class ProtocolGEOMSGInformation extends ProtocolInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private ShootingRestrictions shootRestrictions;
	
	public ProtocolGEOMSGInformation(Device deviceSrc, byte[] message, AppProtocol app, ShootingRestrictions shootRestrictions) {
		super(deviceSrc, message, app);
		this.shootRestrictions = shootRestrictions;
	}
	/**
	 * The object parameter must be Serializable, otherwise it will be throws a IllegalArgumentException.
	 * 
	 * @param deviceSrc
	 * @param object
	 * @param app
	 * @param shootRestrictions
	 */
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
