package com.github.wmlynar.ekf;

public class ObservationModel {
	
	public int stateDimension = 2;
	public int observationDimension = 1;
	
	public Matrix observation = new Matrix(getObservationDimension(), 1);
	public Matrix innovation = new Matrix(getObservationDimension(), 1);
	public Matrix observation_model = new Matrix(getObservationDimension(), getStateDimension());
	public Matrix observation_noise_covariance = new Matrix(getObservationDimension(), getObservationDimension());

	public Matrix innovation_covariance = new Matrix(getObservationDimension(), getObservationDimension());
	public Matrix inverse_innovation_covariance = new Matrix(getObservationDimension(), getObservationDimension());
	public Matrix vertical_scratch = new Matrix(getStateDimension(), getObservationDimension());
	public Matrix optimal_gain = new Matrix(getStateDimension(), getObservationDimension());
	
	public int getStateDimension() {
		return 2;
	}

	public int getObservationDimension() {
		return 1;
	}

	public void getObservation(Matrix observation) {
	}

	public void getObservationModel(Matrix state, Matrix innovation) {
		innovation.data[0][0] = state.data[0][0];
	}

	public void getObservationJacobian(Matrix observation_model) {
		observation_model.data[0][0] = 1;
	}

	public void getObservationNoiseCovariance(Matrix observation_noise_covariance) {
		observation_noise_covariance.set_identity_matrix();
	}

	public void setPosition(double x) {
		observation.data[0][0] = x;
	}

}
