package com.github.wmlynar.rosjava_gyro_calibration.ekf;

/* Refer to http://en.wikipedia.org/wiki/Kalman_filter for
 mathematical details. The naming scheme is that variables get names
 that make sense, and are commented with their analog in
 the Wikipedia mathematical notation.
 This Kalman filter implementation does not support controlled
 input.
 (Like knowing which way the steering wheel in a car is turned and
 using that to inform the model.)
 Vectors are handled as n-by-1 matrices.
 TODO: comment on the dimension of the matrices */

public class KalmanFilter {

	/* These parameters define the size of the matrices. */
	int state_dimension;

	/* This group of matrices must be specified by the user. */
	/* F_k */
	Matrix state_transition;
	/* H_k */
	Matrix observation_model;
	/* Q_k */
	Matrix process_noise_covariance;
	/* R_k */
	Matrix observation_noise_covariance;

	/* The observation is modified by the user beforekalmangpsevery time step. */
	/* z_k */
	Matrix observation;

	/* This group of matrices are updated every time step by the filter. */
	/* x-hat_k|k-1 */
	Matrix predicted_state;
	/* P_k|k-1 */
	Matrix predicted_estimate_covariance;
	/* y-tilde_k */
	Matrix innovation;
	/* S_k */
//	Matrix innovation_covariance;
	/* S_k^-1 */
//	Matrix inverse_innovation_covariance;
	/* K_k */
//	Matrix optimal_gain;
	/* x-hat_k|k */
	Matrix state_estimate;
	/* P_k|k */
	Matrix estimate_covariance;

	/* This group is used for meaningless intermediate calculations */
//	Matrix vertical_scratch;
//	Matrix small_square_scratch;
	Matrix big_square_scratch;

	private LinearMovementModel model;

	public KalmanFilter(LinearMovementModel model) {
		this.model = model;
		state_dimension = model.getDimension();
		state_estimate = model.initialState();
		estimate_covariance = model.initialCovariance();

		predicted_estimate_covariance = new Matrix(state_dimension,	state_dimension);
//		innovation_covariance = new Matrix(observation_dimension, observation_dimension);
//		inverse_innovation_covariance = new Matrix(observation_dimension, observation_dimension);
//		optimal_gain = new Matrix(state_dimension, observation_dimension);

//		vertical_scratch = new Matrix(state_dimension, observation_dimension);
//		small_square_scratch = new Matrix(observation_dimension,observation_dimension);
		big_square_scratch = new Matrix(state_dimension, state_dimension);
	}

	/*
	 * Runs one timestep of prediction + estimation.
	 *
	 * Before each time step of running this, set f.observation to be the next
	 * time step's observation.
	 *
	 * Before the first step, define the model by setting: f.state_transition
	 * f.observation_model f.process_noise_covariance
	 * f.observation_noise_covariance
	 *
	 * It is also advisable to initialize with reasonable guesses for
	 * f.state_estimate f.estimate_covariance
	 */
	void update(double dt, LinearModelObservation obs) {
		predict(dt);
		estimate(dt, obs);
	}

	/* Just the prediction phase of update. */
	void predict(double dt) {
		/* Predict the state */
		predicted_state = model.getNextState(state_estimate, dt);
		
		/* Predict the state estimate covariance */
		state_transition = model.getJacobian(state_estimate, dt);
		process_noise_covariance = model.getProcessNoiseCovariance(dt);
		Matrix.multiply_matrix(state_transition, estimate_covariance, big_square_scratch);
		Matrix.multiply_by_transpose_matrix(big_square_scratch, state_transition, predicted_estimate_covariance);
		Matrix.add_matrix(predicted_estimate_covariance, process_noise_covariance, predicted_estimate_covariance);
	}

	/* Just the estimation phase of update. */
	void estimate(double dt, LinearModelObservation obs) {
		// get temporary matrices
		Matrix vertical_scratch = obs.getTemporaryMatrixStateObservationOne();
		Matrix optimal_gain = obs.getTemporaryMatrixStateObservationTwo();
		Matrix innovation_covariance = obs.getTemporaryMatrixObservationObservationOne();
		Matrix inverse_innovation_covariance = obs.getTemporaryMatrixObservationObservationTwo();
		
		/* Calculate innovation */
		observation = obs.observationValue();
		innovation = obs.observationModel(predicted_state);
		Matrix.subtract_matrix(observation, innovation, innovation);

		/* Calculate innovation covariance */
		observation_model = obs.observationJacobian();
		observation_noise_covariance = obs.observationNoiseCovariance();
		Matrix.multiply_by_transpose_matrix(predicted_estimate_covariance, observation_model, vertical_scratch);
		Matrix.multiply_matrix(observation_model, vertical_scratch, innovation_covariance);
		Matrix.add_matrix(innovation_covariance, observation_noise_covariance, innovation_covariance);

		/*
		 * Invert the innovation covariance. Note: this destroys the innovation
		 * covariance. TODO: handle inversion failure intelligently.
		 */
		Matrix.destructive_invert_matrix(innovation_covariance, inverse_innovation_covariance);

		/*
		 * Calculate the optimal Kalman gain. Note we still have a useful
		 * partial product in vertical scratch from the innovation covariance.
		 */
		Matrix.multiply_matrix(vertical_scratch, inverse_innovation_covariance, optimal_gain);

		/* Estimate the state */
		Matrix.multiply_matrix(optimal_gain, innovation, state_estimate);
		Matrix.add_matrix(state_estimate, predicted_state, state_estimate);

		/* Estimate the state covariance */
		Matrix.multiply_matrix(optimal_gain, observation_model, big_square_scratch);
		Matrix.subtract_from_identity_matrix(big_square_scratch);
		Matrix.multiply_matrix(big_square_scratch, predicted_estimate_covariance, estimate_covariance);
	}
}
