package usp.ime.gclib.net.protocol;

import java.io.Serializable;

import usp.ime.gclib.hit.ShootingRestrictions;
import usp.ime.gclib.hit.TargetRestrictions;

/**
 * This class contain basically two objects of restrictions used in library
 * to determinate the destine device of a message.
 * @see TargetRestrictions
 * @see ShootingRestrictions  
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class LibConfigurationObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private TargetRestrictions targetRestrictions;
	private ShootingRestrictions shootRestrictions;
	
	/**
	 * If these objects are null then the defaults values will be used.
	 * 
	 * @param targetRestrictions
	 * @param shootRestrictions
	 */
	public LibConfigurationObject(TargetRestrictions targetRestrictions, ShootingRestrictions shootRestrictions) {
		this.targetRestrictions = targetRestrictions;
		this.shootRestrictions = shootRestrictions;
	}

	public ShootingRestrictions getShootingRestrictions() {
		return shootRestrictions;
	}

	public void setShootingRestrictions(ShootingRestrictions shootRestrictions) {
		this.shootRestrictions = shootRestrictions;
	}

	public TargetRestrictions getTargetRestrictions() {
		return targetRestrictions;
	}

	public void setTargetRestrictions(TargetRestrictions targetRestrictions) {
		this.targetRestrictions = targetRestrictions;
	}
}
