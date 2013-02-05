package usp.ime.gclib.sensor.orientation;

import android.hardware.SensorManager;

public class DeviceCompassOrientation extends DeviceOrientation{
	
	private static final long serialVersionUID = 1L;

	// magnetic field vector
	private float[] magnet;
	
	// accelerometer vector
	private float[] accel;
	
	// orientation angles from accel and magnet
	private float[] orientation;
	
	// accelerometer and magnetometer based rotation matrix
	private float[] rotationMatrix;
	
	private boolean isMagnetDefined;

	private boolean isCompassOrientationDefined;

	public DeviceCompassOrientation() {
		super(); 
		
		// magnetic field vector
		magnet = new float[3];
		
		// accelerometer vector
		accel = new float[3];
		
		// orientation angles from accel and magnet
		orientation = new float[3];
		
		// accelerometer and magnetometer based rotation matrix
		rotationMatrix = new float[9];
		
		sensors = new ESensorType[3];
		sensors[0] = ESensorType.ACCELEROMETER;
		sensors[1] = ESensorType.GYROSCOPE;
		sensors[2] = ESensorType.MAGNETIC_FIELD;

		prepareForOrientationObtaining();		
	}
	
	@Override
	public float[] getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(float[] accMagOrientation) {
		this.orientation = accMagOrientation;
	}
	
	public boolean isMagnetDefined() {
		return isMagnetDefined;
	}

	public void setMagnetDefined(boolean isMagnetDefined) {
		this.isMagnetDefined = isMagnetDefined;
	}

	public boolean isCompassOrientationDefined() {
		return isCompassOrientationDefined;
	}

	public void setCompassOrientationDefined(boolean isAccMagOrientationDefined) {
		this.isCompassOrientationDefined = isAccMagOrientationDefined;
	}

	public void prepareForOrientationObtaining(){
		isCompassOrientationDefined = false;
		isMagnetDefined = false;
	}
	
	@Override
	public void sensorManager(ESensorType sensorType, float[] sample, long timestampSample, OrientationSensorListener listener) {
		switch(sensorType) {
		    case ACCELEROMETER:
		        // copy new accelerometer data into accel array
		        // then calculate new orientation
	    		System.arraycopy(sample, 0, accel, 0, 3);
		    	calculateAccMagOrientation();
		    	isCompassOrientationDefined = true;
		        break;
		 
		    case MAGNETIC_FIELD:
		        // copy new magnetometer data into magnet array
	    		System.arraycopy(sample, 0, magnet, 0, 3);
	    		isMagnetDefined = true;
		        break;
		        
		    default:
		    	break;
	    }
	}
		
	private void calculateAccMagOrientation() {
		if (!isMagnetDefined)
			return;
	    if(SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnet)) {
	        SensorManager.getOrientation(rotationMatrix, orientation);
	    }
	}		

	
}

