package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ObservationModel;

public class BeaconObservation extends ObservationModel {

	public double beaconDistance = 0;
	public double beaconAngle = 0;

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
		y[0][0] = beaconDistance;
		y[1][0] = beaconAngle;
	}

	@Override
	public void observationModel(double[][] x, double[][] h) {
		double dx = x[RobotModel.X1][0] - x[RobotModel.X][0];
		double dy = x[RobotModel.Y1][0] - x[RobotModel.Y][0];

		h[0][0] = Math.sqrt(dx * dx + dy * dy);
		h[1][0] = Math.atan2(dx, dy) + x[RobotModel.A][0];
	}

	@Override
	public void observationModelJacobian(double[][] x, double[][] j) {
		double dx = x[RobotModel.X1][0] - x[RobotModel.X][0];
		double dy = x[RobotModel.Y1][0] - x[RobotModel.Y][0];
		double dist2 = dx * dx + dy * dy;
		double dist = Math.sqrt(dist2);

		j[0][RobotModel.X] = -dx / dist;
		j[0][RobotModel.Y] = -dy / dist;
		j[0][RobotModel.X1] = dx / dist;
		j[0][RobotModel.Y1] = dy / dist;
		j[1][RobotModel.X] = -dy / dist2;
		j[1][RobotModel.Y] = dx / dist2;
		j[1][RobotModel.A] = 1;
		j[1][RobotModel.X1] = dy / dist2;
		j[1][RobotModel.Y1] = -dx / dist2;
	}

	@Override
	public void observationNoiseCovariance(double[][] cov) {
		cov[0][0] = 1e-1;
		cov[1][1] = 1e-1;
	}

}
