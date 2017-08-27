package com.github.wmlynar.rosjava_gyro_calibration.ekf;

public class ProcessModel {
	
	public int stateDimension = 2;
	public Matrix state_estimate = new Matrix(stateDimension, 1);
	public Matrix predicted_state = new Matrix(stateDimension, 1);
	public Matrix state_transition = new Matrix(stateDimension, stateDimension);
	public Matrix estimate_covariance = new Matrix(stateDimension, stateDimension);
	public Matrix process_noise_covariance = new Matrix(stateDimension, stateDimension);
	public Matrix predicted_estimate_covariance = new Matrix(stateDimension, stateDimension);
	public Matrix big_square_scratch = new Matrix(stateDimension, stateDimension);
	
	public int getStateDimension() {
		return 2;
	}

	public void initializeState(Matrix state_estimate) {
		state_estimate.data[0][0] = 0;
		state_estimate.data[1][0] = 0;
	}

	public void initializeCovariance(Matrix estimate_covariance) {
		estimate_covariance.set_identity_matrix();
		estimate_covariance.scale_matrix(1000);
	}

	public void getNextState(Matrix state_estimate, double dt, Matrix predicted_state) {
		predicted_state.data[0][0] = state_estimate.data[0][0] + state_estimate.data[1][0] * dt;
		predicted_state.data[1][0] = state_estimate.data[1][0];
	}

	public void getJacobian(Matrix state_estimate, double dt, Matrix state_transition) {
		state_transition.data[0][0] = 1;
		state_transition.data[0][1] = dt;
		state_transition.data[1][0] = 0;
		state_transition.data[1][1] = 1;
	}

	public Matrix getProcessNoiseCovariance(double dt, Matrix process_noise_covariance) {
		process_noise_covariance.data[0][0] = dt;
		process_noise_covariance.data[1][1] = dt;
		return process_noise_covariance;
	}
}

