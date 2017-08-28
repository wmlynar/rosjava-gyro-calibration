package com.github.wmlynar.ekf_examples;

import com.github.wmlynar.ekf.Matrix;
import com.github.wmlynar.ekf.ProcessModel;

public class Linear2dProcessModel extends ProcessModel {

	@Override
	public int getStateDimension() {
		return 4;
	}

	@Override
	public void initializeState(Matrix state_estimate) {
		state_estimate.data[0][0] = 0;
		state_estimate.data[1][0] = 0;
		state_estimate.data[2][0] = 0;
		state_estimate.data[3][0] = 0;
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
		predicted_state.data[2][0] = state_estimate.data[2][0] + state_estimate.data[3][0] * dt;
		predicted_state.data[3][0] = state_estimate.data[3][0];
	}

	@Override
	public void getJacobian(Matrix state_estimate, double dt, Matrix state_transition) {
		state_transition.set_identity_matrix();
		state_transition.data[0][1] = dt;
		state_transition.data[2][3] = dt;
	}

	@Override
	public void getProcessNoiseCovariance(double dt, Matrix process_noise_covariance) {
		estimate_covariance.set_identity_matrix();
		estimate_covariance.scale_matrix(dt);
	}
}
