package com.example.communicationbyorientation;

import javax.vecmath.Matrix3f;

import android.util.FloatMath;

public class OrientatonManager {
	/*TODO: USAR getRotationMatrixFromVector from the SensoManager*/
	public static float[] getRotationMatrixFromOrientation(float[] o) {
	    float[] xM = new float[9];
	    float[] yM = new float[9];
	    float[] zM = new float[9];
	 
	    float sinX = (float)FloatMath.sin(o[1]);
	    float cosX = (float)FloatMath.cos(o[1]);
	    float sinY = (float)FloatMath.sin(o[2]);
	    float cosY = (float)FloatMath.cos(o[2]);
	    float sinZ = (float)FloatMath.sin(o[0]);
	    float cosZ = (float)FloatMath.cos(o[0]);
	
	    // rotation about x-axis (pitch)
	    xM[0] = 1.0f; xM[1] = 0.0f; xM[2] = 0.0f;
	    xM[3] = 0.0f; xM[4] = cosX; xM[5] = sinX;
	    xM[6] = 0.0f; xM[7] = -sinX; xM[8] = cosX;
	 
	    // rotation about y-axis (roll)
	    yM[0] = cosY; yM[1] = 0.0f; yM[2] = sinY;//-sinY em sensor fusion
	    yM[3] = 0.0f; yM[4] = 1.0f; yM[5] = 0.0f;
	    yM[6] = -sinY; yM[7] = 0.0f; yM[8] = cosY;//yM[6] = sinY; yM[7] = 0; yM[8] = cosY;
	 
	    // rotation about z-axis (azimuth)
	    zM[0] = cosZ; zM[1] = sinZ; zM[2] = 0.0f;
	    zM[3] = -sinZ; zM[4] = cosZ; zM[5] = 0.0f;
	    zM[6] = 0.0f; zM[7] = 0.0f; zM[8] = 1.0f;
	 
	    // rotation order is y, x, z (roll, pitch, azimuth)
	    float[] resultMatrix = matrixMultiplication(xM, yM);
	    resultMatrix = matrixMultiplication(zM, resultMatrix);
	    return resultMatrix;
	}	
	
	public static float[] matrixMultiplication(float[] A, float[] B) {
		
		Matrix3f matrixA = new Matrix3f(A);
		Matrix3f matrixB = new Matrix3f(B);
		Matrix3f matrixResult = new Matrix3f();
		matrixResult.mul(matrixA, matrixB);
		
		float[] result = new float[9];
		result[0] = matrixResult.m00;
		result[1] = matrixResult.m01;
		result[2] = matrixResult.m02;
		result[3] = matrixResult.m10;
		result[4] = matrixResult.m11;
		result[5] = matrixResult.m12;
		result[6] = matrixResult.m20;
		result[7] = matrixResult.m21;
		result[8] = matrixResult.m22;
	
	    return result;
	}

	public float[] integrationOfVectors(float[] currentVector, float[] newVector, float alpha){
		for(int i =0; i< newVector.length; i++){
			currentVector[i] = currentVector[i]*alpha + (1 - alpha)*newVector[i];
		}
		return currentVector;
	}
	
}
