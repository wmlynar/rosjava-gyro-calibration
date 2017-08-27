package com.github.wmlynar.rosjava_gyro_calibration.ekf;

public class LinearModelObservation {
	
	int stateDimension = 2;
	int observationDimension = 1;
	Matrix observationValue = new Matrix(observationDimension, 1);
	Matrix observationModel = new Matrix(observationDimension, 1);
	Matrix jacobian = new Matrix(observationDimension, stateDimension);
	Matrix covariance = new Matrix(observationDimension, observationDimension);

	Matrix temporaryObservationObservationOne = new Matrix(observationDimension, observationDimension);
	Matrix temporaryObservationObservationTwo = new Matrix(observationDimension, observationDimension);
	Matrix temporaryStateObservationOne = new Matrix(stateDimension, observationDimension);
	Matrix temporaryStateObservationTwo = new Matrix(stateDimension, observationDimension);
	
	public int getObservationDimension() {
		return 1;
	}

	public Matrix observationValue() {
		return observationValue;
	}

	public Matrix observationModel(Matrix state) {
		observationModel.data[0][0] = state.data[0][0];
		return observationModel;
	}

	public Matrix observationJacobian() {
		jacobian.data[0][0] = 1;
		return jacobian;
	}

	public Matrix observationNoiseCovariance() {
		covariance.set_identity_matrix();
		return covariance;
	}
	
	public Matrix getTemporaryMatrixObservationObservationOne() {
		return temporaryObservationObservationOne;
	}

	public Matrix getTemporaryMatrixObservationObservationTwo() {
		return temporaryObservationObservationTwo;
	}

	public Matrix getTemporaryMatrixStateObservationOne() {
		return temporaryStateObservationOne;
	}

	public Matrix getTemporaryMatrixStateObservationTwo() {
		return temporaryStateObservationTwo;
	}

	public void setPosition(double x) {
		observationValue.data[0][0] = x;
	}

}
