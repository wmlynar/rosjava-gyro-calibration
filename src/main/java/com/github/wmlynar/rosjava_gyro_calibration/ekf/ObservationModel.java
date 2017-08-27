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

	public Matrix getObservationMeasurements() {
		return observation;
	}

	public Matrix getObservationModel(Matrix state) {
		innovation.data[0][0] = state.data[0][0];
		return innovation;
	}

	public Matrix getObservationJacobian() {
		observation_model.data[0][0] = 1;
		return observation_model;
	}

	public Matrix getObservationNoiseCovariance() {
		observation_noise_covariance.set_identity_matrix();
		return observation_noise_covariance;
	}
	
	public Matrix getTemporaryMatrixObservationObservationOne() {
		return innovation_covariance;
	}

	public Matrix getTemporaryMatrixObservationObservationTwo() {
		return inverse_innovation_covariance;
	}

	public Matrix getTemporaryMatrixStateObservationOne() {
		return vertical_scratch;
	}

	public Matrix getTemporaryMatrixStateObservationTwo() {
		return optimal_gain;
	}

	public void setPosition(double x) {
		observation.data[0][0] = x;
	}

}
