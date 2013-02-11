package usp.ime.gclib.sensor.orientation;

import java.io.Serializable;
/**
 * This class creates a device orientation to monitor the position of a device relative to the earth's frame of reference 
 * 
 * @author Renato Avila e Tonny Cordeiro
 * @version 1.0
 *
 */
public class DeviceOrientation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * orientation vector relative to the earth's frame of reference
	 */
	protected float[] orientation;
	/**
	 * sensor types available to generate the orientation vector 
	 */
	protected ESensorType sensors[];
	
	/**
	 * orientation vector index of azimuth angle 
	 */
	public static final int AZIMUTH_INDEX = 0;
	/**
	 * orientation vector index of yaw angle 
	 */
	public static final int YAW_INDEX = 0;
	/**
	 * orientation vector index of pitch angle 
	 */
	public static final int PITCH_INDEX = 1;
	/**
	 * orientation vector index of roll angle 
	 */
	public static final int ROLL_INDEX = 2;
	
	
	public DeviceOrientation(){
		this.orientation = new float[3];
	}
	
	public float[] getOrientation() {
		return orientation;
	}

	public void setOrientation(float[] orientation) {
		this.orientation = orientation;
	}

	public float getAzimuth(){
		return orientation[AZIMUTH_INDEX];
	}

	public void setAzimuth(float azimuth){
		this.orientation[AZIMUTH_INDEX] = azimuth;
	}

	public float getYaw(){
		return getAzimuth();
	}

	public void setYaw(float angle){
		this.orientation[YAW_INDEX] = angle;
	}

	public float getPitch(){
		return orientation[PITCH_INDEX];
	}

	public void setPitch(float angle){
		this.orientation[PITCH_INDEX] = angle;
	}

	public float getRoll(){
		return orientation[ROLL_INDEX];
	}

	public void setRoll(float angle){
		this.orientation[ROLL_INDEX] = angle;
	}

	public ESensorType[] getSensors() {
		return sensors;
	}

	public void setSensors(ESensorType sensors[]) {
		this.sensors = sensors;
	}	
	
	/**
	 * Called when sensor values have changed by the listener parameter 
	 * 
	 * @param sensorType sensor type of SensorManager in Android API 2.2
	 * @param sample SensorEvent values in Android API 2.2
	 * @param timestampSample SensorEvent timestamp in Android API 2.2
	 * @param listener
	 */
	public void sensorManager(ESensorType sensorType, float[] sample, long timestampSample, OrientationSensorListener listener) {
	}	

}

