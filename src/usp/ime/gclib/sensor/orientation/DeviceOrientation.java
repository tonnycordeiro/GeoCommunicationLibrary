package usp.ime.gclib.sensor.orientation;

import usp.ime.gclib.hit.ESensorType;
import usp.ime.gclib.hit.OrientationSensorListener;
import usp.ime.gclib.hit.example.Device;
import android.content.Context;


public class DeviceOrientation {
	
	private float[] orientation;
	private OrientationSensorListener listener;
	protected Device device;
	
	public static final int AZIMUTH_INDEX = 0;
	public static final int YAW_INDEX = 0;
	public static final int PITCH_INDEX = 1;
	public static final int ROLL_INDEX = 2;
	
	
	public DeviceOrientation(Device device){
		this.device = device;
		this.orientation = new float[3];
		this.listener = OrientationSensorListener.getInstance(this);
	}
	
	public float[] getOrientation() {
		return orientation;
	}

	public void setOrientation(float[] orientation) {
		this.orientation = orientation;
		device.setOritentation(this.orientation);
	}

	public float getAzimuth(){
		return orientation[AZIMUTH_INDEX];
	}

	public void setAzimuth(float azimuth){
		this.orientation[AZIMUTH_INDEX] = azimuth;
		device.setOritentation(this.orientation);
	}

	public float getYaw(){
		return getAzimuth();
	}

	public void setYaw(float angle){
		this.orientation[YAW_INDEX] = angle;
		device.setOritentation(this.orientation);
	}

	public float getPitch(){
		return orientation[PITCH_INDEX];
	}

	public void setPitch(float angle){
		this.orientation[PITCH_INDEX] = angle;
		device.setOritentation(this.orientation);
	}

	public float getRoll(){
		return orientation[ROLL_INDEX];
	}

	public void setRoll(float angle){
		this.orientation[ROLL_INDEX] = angle;
		device.setOritentation(this.orientation);
	}
	
	public OrientationSensorListener getListener() {
		return listener;
	}

	public void setListener(OrientationSensorListener listener) {
		this.listener = listener;
	}

	/*TODO: providenciar auto-generate*/
	public void sensorManager(ESensorType sensorType, float[] sample, long timestampSample) {
	}	
	
	public void enableSensorListener(Context context){
		this.listener.enableSensorService(context);
	}	
	
}

