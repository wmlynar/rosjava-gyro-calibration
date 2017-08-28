package com.github.wmlynar.ekf_examples;

import com.github.wmlynar.ekf.Matrix;
import com.github.wmlynar.ekf.ObservationModel;

public class VectorObservationModel extends ObservationModel {

	private double x;
	private double y;
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getStateDimension() {
		return 4;
	}

	@Override
	public int getObservationDimension() {
		return 2;
	}

	@Override
	public void getObservation(Matrix observation) {
		observation.data[0][0] = x;
		observation.data[1][0] = y;
	}

	@Override
	public void getObservationModel(Matrix state, Matrix innovation) {
		innovation.data[0][0] = state.data[0][0];
		innovation.data[1][0] = state.data[1][0];
	}

	@Override
	public void getObservationJacobian(Matrix observation_model) {
		observation_model.data[0][0] = 1;
		observation_model.data[1][1] = 1;
	}

	@Override
	public void getObservationNoiseCovariance(Matrix observation_noise_covariance) {
		observation_noise_covariance.set_identity_matrix();
	}

}
