package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ObservationModel;

public class RobotAngleObservation extends ObservationModel {
	
	public double angle = 0;

	@Override
	public int observationDimension() {
		return 1;
	}

	@Override
	public int stateDimension() {
		return 8;
	}

	@Override
	public void observationMeasurement(double[][] y) {
		y[0][0] = angle;
	}

	@Override
	public void observationModel(double[][] x, double[][] h) {
		h[0][0] = x[RobotProcess.A][0];
	}

	@Override
	public void observationModelJacobian(double[][] j) {
		j[0][RobotProcess.A] = 1;
	}

	@Override
	public void observationNoiseCovariance(double[][] cov) {
		cov[0][0] = 2;
	}

}
