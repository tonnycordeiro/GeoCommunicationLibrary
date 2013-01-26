package usp.ime.tcc.Communication;

import java.io.Serializable;

import usp.ime.tcc.Auxiliaries.Device;

import com.example.communicationbyorientation.ShootRestrictions;

public class ProtocolGEOMSGInformation extends ProtocolInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	private ShootRestrictions shootRestrictions;
	
	public ProtocolGEOMSGInformation(Device deviceSrc, AppProtocol app, ShootRestrictions shootRestrictions) {
		super(deviceSrc, app);
		this.shootRestrictions = shootRestrictions;
	}

	public ShootRestrictions getShootRestrictions() {
		return shootRestrictions;
	}

	public void setShootRestrictions(ShootRestrictions shootRestrictions) {
		this.shootRestrictions = shootRestrictions;
	}
}
