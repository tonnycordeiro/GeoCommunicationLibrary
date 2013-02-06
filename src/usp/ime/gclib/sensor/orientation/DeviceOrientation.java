package usp.ime.gclib.sensor.orientation;

import java.io.Serializable;

public class DeviceOrientation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected float[] orientation;
	protected ESensorType sensors[];

	public static final int AZIMUTH_INDEX = 0;
	public static final int YAW_INDEX = 0;
	public static final int PITCH_INDEX = 1;
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
	
	/*TODO: providenciar auto-generate*/
	public void sensorManager(ESensorType sensorType, float[] sample, long timestampSample, OrientationSensorListener listener) {
	}	

}

