package com.github.wmlynar.rosjava_gyro_calibration.ekf;

public class LinearMovementModel {
	
	int stateDimension = 2;
	Matrix initialState = new Matrix(stateDimension, 1);
	Matrix state = new Matrix(stateDimension, 1);
	Matrix jacobian = new Matrix(stateDimension, stateDimension);
	Matrix covariance = new Matrix(stateDimension, stateDimension);
	Matrix noiseCovariance = new Matrix(stateDimension, stateDimension);
	
	public int getDimension() {
		return stateDimension;
	}

	public Matrix initialState() {
		initialState.data[0][0] = 0;
		initialState.data[1][0] = 0;
		return initialState;
	}

	public Matrix initialCovariance() {
		covariance.set_identity_matrix();
		covariance.scale_matrix(1000);
		return covariance;
	}

	public Matrix getNextState(Matrix state_estimate, double dt) {
		state.data[0][0] = state_estimate.data[0][0] + state_estimate.data[1][0] * dt;
		state.data[1][0] = state_estimate.data[1][0];
		return state;
	}

	public Matrix getJacobian(Matrix state_estimate, double dt) {
		jacobian.data[0][0] = 1;
		jacobian.data[0][1] = dt;
		jacobian.data[1][0] = 0;
		jacobian.data[1][1] = 1;
		return jacobian;
	}

	public Matrix getProcessNoiseCovariance(double dt) {
		noiseCovariance.data[0][0] = dt;
		noiseCovariance.data[1][1] = dt;
		return noiseCovariance;
	}
}

