package usp.ime.tcc.Communication;

import java.io.Serializable;

import com.example.communicationbyorientation.ShootRestrictions;
import com.example.communicationbyorientation.TargetRestrictions;

public class LibConfigurationObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private TargetRestrictions targetRestrictions;
	private ShootRestrictions shootRestrictions;
	
	public LibConfigurationObject(TargetRestrictions targetRestrictions, ShootRestrictions shootRestrictions) {
		this.targetRestrictions = targetRestrictions;
		this.shootRestrictions = shootRestrictions;
	}

	public ShootRestrictions getShootRestrictions() {
		return shootRestrictions;
	}

	public void setShootRestrictions(ShootRestrictions shootRestrictions) {
		this.shootRestrictions = shootRestrictions;
	}

	public TargetRestrictions getTargetRestrictions() {
		return targetRestrictions;
	}

	public void setTargetRestrictions(TargetRestrictions targetRestrictions) {
		this.targetRestrictions = targetRestrictions;
	}
}
