package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ObservationModel;

public class RobotOdomObservation extends ObservationModel {
	
	public double left = 0;
	public double right = 0;

	@Override
	public int observationDimension() {
		return 2;
	}

	@Override
	public int stateDimension() {
		return 12;
	}

	@Override
	public void observationMeasurement(double[][] y) {
		y[0][0] = left;
		y[1][0] = right;
	}

	@Override
	public void observationModel(double[][] x, double[][] h) {
		h[0][0] = x[RobotModel.L][0];
		h[1][0] = x[RobotModel.R][0];
	}

	@Override
	public void observationModelJacobian(double[][] x, double[][] j) {
		j[0][RobotModel.L] = 1;
		j[1][RobotModel.R] = 1;
	}

	@Override
	public void observationNoiseCovariance(double[][] cov) {
		cov[0][0] = 1e-4;
		cov[1][1] = 1e-4;
	}

}
