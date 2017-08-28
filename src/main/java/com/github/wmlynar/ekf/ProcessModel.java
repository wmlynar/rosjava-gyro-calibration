package com.github.wmlynar.ekf;

public abstract class ProcessModel {
	
	public Matrix state_estimate = new Matrix(stateDimension(), 1);
	public Matrix predicted_state = new Matrix(stateDimension(), 1);
	public Matrix state_transition = new Matrix(stateDimension(), stateDimension());
	public Matrix estimate_covariance = new Matrix(stateDimension(), stateDimension());
	public Matrix process_noise_covariance = new Matrix(stateDimension(), stateDimension());
	public Matrix predicted_estimate_covariance = new Matrix(stateDimension(), stateDimension());
	public Matrix big_square_scratch = new Matrix(stateDimension(), stateDimension());
	
	public abstract int stateDimension();
	
	public abstract void initialState(Matrix initial_state);

	public abstract void initialStateCovariance(Matrix initial_covariance);

	public abstract void predictionModel(Matrix state, double dt, Matrix predicted_state);

	public abstract void predictionModelJacobian(Matrix state, double dt, Matrix predicted_state_jacobian);

	public abstract void processNoiseCovariance(double dt, Matrix process_noise_covariance);
}

