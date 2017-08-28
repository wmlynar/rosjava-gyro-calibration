package com.github.wmlynar.ekf;

public abstract class ObservationModel {
	
	public Matrix observation = new Matrix(observationDimension(), 1);
	public Matrix innovation = new Matrix(observationDimension(), 1);
	public Matrix observation_model = new Matrix(observationDimension(), stateDimension());
	public Matrix observation_noise_covariance = new Matrix(observationDimension(), observationDimension());

	public Matrix innovation_covariance = new Matrix(observationDimension(), observationDimension());
	public Matrix inverse_innovation_covariance = new Matrix(observationDimension(), observationDimension());
	public Matrix vertical_scratch = new Matrix(stateDimension(), observationDimension());
	public Matrix optimal_gain = new Matrix(stateDimension(), observationDimension());
	
	public abstract int stateDimension();

	public abstract int observationDimension();

	public abstract void observationMeasurement(Matrix observation_measured);
	
	public abstract void observationModel(Matrix state, Matrix observation_predicted);

	public abstract void observationModelJacobian(Matrix observation_jacobian);

	public abstract void observationNoiseCovariance(Matrix observation_noise_covariance);
}
