package com.github.wmlynar.rosjava_gyro_calibration.ekf;

public class ObservationModel {
	
	public int stateDimension = 2;
	public int observationDimension = 1;
	
	public Matrix observation = new Matrix(observationDimension, 1);
	public Matrix innovation = new Matrix(observationDimension, 1);
	public Matrix observation_model = new Matrix(observationDimension, stateDimension);
	public Matrix observation_noise_covariance = new Matrix(observationDimension, observationDimension);

	public Matrix innovation_covariance = new Matrix(observationDimension, observationDimension);
	public Matrix inverse_innovation_covariance = new Matrix(observationDimension, observationDimension);
	public Matrix vertical_scratch = new Matrix(stateDimension, observationDimension);
	public Matrix optimal_gain = new Matrix(stateDimension, observationDimension);
	
	public int getStateDimension() {
		return 2;
	}

	public int getObservationDimension() {
		return 1;
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
