package com.github.wmlynar.ekf;

public abstract class ProcessModel {
	
	public Matrix state_estimate = new Matrix(getStateDimension(), 1);
	public Matrix predicted_state = new Matrix(getStateDimension(), 1);
	public Matrix state_transition = new Matrix(getStateDimension(), getStateDimension());
	public Matrix estimate_covariance = new Matrix(getStateDimension(), getStateDimension());
	public Matrix process_noise_covariance = new Matrix(getStateDimension(), getStateDimension());
	public Matrix predicted_estimate_covariance = new Matrix(getStateDimension(), getStateDimension());
	public Matrix big_square_scratch = new Matrix(getStateDimension(), getStateDimension());
	
	public abstract int getStateDimension();
	
	public abstract void initializeState(Matrix state_estimate);

	public abstract void initializeCovariance(Matrix estimate_covariance);

	public abstract void getNextState(Matrix state_estimate, double dt, Matrix predicted_state);

	public abstract void getJacobian(Matrix state_estimate, double dt, Matrix state_transition);

	public abstract void getProcessNoiseCovariance(double dt, Matrix process_noise_covariance);
}

