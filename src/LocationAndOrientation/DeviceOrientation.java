package LocationAndOrientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class DeviceOrientation implements SensorEventListener {

	private float orientation;
	private SensorManager mySensorManager;
	private Context context;
	private int[] sensorsType;
	private int sensorType;
	
	public DeviceOrientation(Context context, int sensorType) {
		this.context = context;
		this.sensorType = sensorType;
	}
	
	public DeviceOrientation(Context context, int[] sensorsType) {
		this.context = context;
		this.sensorsType = sensorsType;
	}
	
	public void enableSensorListener() {
		mySensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		if(sensorsType != null){
			for (int type : sensorsType) {
				mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(type), SensorManager.SENSOR_DELAY_UI);
			}
		}
		else
			mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_UI);
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
				System.out.println("Acelerometro");
				break;
			case Sensor.TYPE_GYROSCOPE:
				System.out.println("Giroscopio");
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				System.out.println("Magnetic_field");
				break;
			case Sensor.TYPE_ORIENTATION:
				this.orientation = event.values[0];
				System.out.println("Orientation");
				break;
			default:
				System.out.println("Other sensor: " + event.sensor.getType());
				break;
		}
	}
	
	public float getOrientatio(){
		return this.orientation;
	}

}
