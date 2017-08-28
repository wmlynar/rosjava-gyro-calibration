package com.github.wmlynar.ekf_examples;

import com.github.wmlynar.ekf.Matrix;
import com.github.wmlynar.ekf.ObservationModel;

public class Linear1dObservationModel extends ObservationModel {

	private double x;
	
	public void setPosition(double x) {
		this.x = x;
	}
	
	@Override
	public int getStateDimension() {
		return 2;
	}

	@Override
	public int getObservationDimension() {
		return 1;
	}

	@Override
	public void getObservation(Matrix observation) {
		observation.data[0][0] = x;
	}

	@Override
	public void getObservationModel(Matrix state, Matrix innovation) {
		innovation.data[0][0] = state.data[0][0];
	}

	@Override
	public void getObservationJacobian(Matrix observation_model) {
		observation_model.data[0][0] = 1;
	}

	@Override
	public void getObservationNoiseCovariance(Matrix observation_noise_covariance) {
		observation_noise_covariance.set_identity_matrix();
	}

}
