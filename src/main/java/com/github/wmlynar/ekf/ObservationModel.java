package com.github.wmlynar.ekf;

public abstract class ObservationModel {
	
	public Matrix observation = new Matrix(getObservationDimension(), 1);
	public Matrix innovation = new Matrix(getObservationDimension(), 1);
	public Matrix observation_model = new Matrix(getObservationDimension(), getStateDimension());
	public Matrix observation_noise_covariance = new Matrix(getObservationDimension(), getObservationDimension());

	public Matrix innovation_covariance = new Matrix(getObservationDimension(), getObservationDimension());
	public Matrix inverse_innovation_covariance = new Matrix(getObservationDimension(), getObservationDimension());
	public Matrix vertical_scratch = new Matrix(getStateDimension(), getObservationDimension());
	public Matrix optimal_gain = new Matrix(getStateDimension(), getObservationDimension());
	
	public abstract int getStateDimension();

	public abstract int getObservationDimension();

	public abstract void getObservation(Matrix observation);
	
	public abstract void getObservationModel(Matrix state, Matrix innovation);

	public abstract void getObservationJacobian(Matrix observation_model);

	public abstract void getObservationNoiseCovariance(Matrix observation_noise_covariance);
}
