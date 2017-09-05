package com.github.wmlynar.rosjava.n.models.axlewidth;

import com.github.wmlynar.rosjava.n.kf.Observation;
import com.github.wmlynar.rosjava_gyro_calibration.kf.RobotModel;

public class AxleWidthVelocityLeftObservation extends Observation {
	
	public double velocityLeft;

	@Override
	public int observationDimension() {
		return 1;
	}

	@Override
	public int stateDimension() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public void observationMeasurement(double[][] y) {
		y[0][0] = velocityLeft;
	}

	@Override
	public void observationModel(double[][] x, double[][] h) {
		h[0][0] = x[AxleWidthModel.ROT][0] * x[AxleWidthModel.WIDTH][0];
	}

	@Override
	public void observationModelJacobian(double[][] x, double[][] j) {
        j[0][RobotModel.ROT] = x[AxleWidthModel.WIDTH][0];
        j[0][RobotModel.WIDTH] = x[AxleWidthModel.ROT][0];
	}
}
