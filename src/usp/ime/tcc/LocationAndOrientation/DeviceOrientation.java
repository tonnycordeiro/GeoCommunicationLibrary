package usp.ime.tcc.LocationAndOrientation;

import java.io.Serializable;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class DeviceOrientation implements SensorEventListener, Serializable{

	private static final long serialVersionUID = 5L;

	private float orientation;
	
	/*TODO: separar em classe específica para Android*/
	/*AndroidLocation al*/
	private SensorManager mySensorManager;
	private Context context;
	
	public DeviceOrientation(Context context) {
		this.context = context;
	}
	
	public void enableSensorListener() {
		mySensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
	}
	
	public void disableSensorListener() {
		mySensorManager.unregisterListener(this);
		mySensorManager = null;
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	public void onSensorChanged(SensorEvent event) {
		switch(event.sensor.getType()){
			case Sensor.TYPE_ACCELEROMETER:
				//System.out.println("Acelerometro");
				break;
			case Sensor.TYPE_GYROSCOPE:
				//System.out.println("Giroscopio");
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				//System.out.println("Magnetic_field");
				break;
			case Sensor.TYPE_ORIENTATION:
				this.orientation = event.values[0];
				//System.out.println("Orientation");
				break;
			default:
				//System.out.println("Other sensor: " + event.sensor.getType());
				break;
		}
	}
	
	public float getOrientatio(){
		return this.orientation;
	}

}
