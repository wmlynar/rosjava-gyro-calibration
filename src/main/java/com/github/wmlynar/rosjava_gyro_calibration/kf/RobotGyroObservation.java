package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ObservationModel;

public class RobotGyroObservation extends ObservationModel {
	
	public double gyroMeasurement = 0;

	@Override
	public int observationDimension() {
		return 1;
	}

	@Override
	public int stateDimension() {
		return 12;
	}

	@Override
	public void observationMeasurement(double[][] y) {
		y[0][0] = gyroMeasurement;
	}

	@Override
	public void observationModel(double[][] x, double[][] h) {
		h[0][0] = (x[RobotModel.ROT][0] + x[RobotModel.BIAS][0]) * x[RobotModel.INVGAIN][0];
	}

	@Override
	public void observationModelJacobian(double[][] x, double[][] j) {
		j[0][RobotModel.ROT] = x[RobotModel.INVGAIN][0];
		j[0][RobotModel.BIAS] = x[RobotModel.INVGAIN][0];
		j[0][RobotModel.INVGAIN] = x[RobotModel.ROT][0] + x[RobotModel.BIAS][0];
	}

	@Override
	public void observationNoiseCovariance(double[][] cov) {
		cov[0][0] = 1e-6;
	}

}
