package com.github.wmlynar.ekf_examples;

import com.github.wmlynar.ekf.Matrix;
import com.github.wmlynar.ekf.ProcessModel;

public class LinearProcesModel extends ProcessModel {

	@Override
	public int getStateDimension() {
		return 2;
	}

	@Override
	public void initializeState(Matrix state_estimate) {
		state_estimate.data[0][0] = 0;
		state_estimate.data[1][0] = 0;
	}

	@Override
	public void initializeCovariance(Matrix estimate_covariance) {
		estimate_covariance.set_identity_matrix();
		estimate_covariance.scale_matrix(1000);
	}

	@Override
	public void getNextState(Matrix state_estimate, double dt, Matrix predicted_state) {
		predicted_state.data[0][0] = state_estimate.data[0][0] + state_estimate.data[1][0] * dt;
		predicted_state.data[1][0] = state_estimate.data[1][0];
	}

	@Override
	public void getJacobian(Matrix state_estimate, double dt, Matrix state_transition) {
		state_transition.data[0][0] = 1;
		state_transition.data[0][1] = dt;
		state_transition.data[1][0] = 0;
		state_transition.data[1][1] = 1;
	}

	@Override
	public Matrix getProcessNoiseCovariance(double dt, Matrix process_noise_covariance) {
		process_noise_covariance.data[0][0] = dt;
		process_noise_covariance.data[1][1] = dt;
		return process_noise_covariance;
	}
}
