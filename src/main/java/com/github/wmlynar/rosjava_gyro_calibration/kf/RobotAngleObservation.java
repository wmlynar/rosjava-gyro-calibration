package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ObservationModel;

public class RobotAngleObservation extends ObservationModel {

    public double angle = 0;

    @Override
    public int observationDimension() {
        return 1;
    }

    @Override
    public int stateDimension() {
        return 13;
    }

    @Override
    public void observationMeasurement(double[][] y) {
        y[0][0] = angle;
    }

    @Override
    public void observationModel(double[][] x, double[][] h) {
        h[0][0] = x[RobotModel.A][0];
    }

    @Override
    public void observationModelJacobian(double[][] x, double[][] j) {
        j[0][RobotModel.A] = 1;
    }

    @Override
    public void observationNoiseCovariance(double[][] cov) {
        cov[0][0] = 1e-6;
    }

    @Override
    public void normalizeInnovation(double[][] i) {
        while (i[0][0] < -Math.PI) {
            i[0][0] += 2 * Math.PI;
        }
        while (i[0][0] > Math.PI) {
            i[0][0] -= 2 * Math.PI;
        }
    }

}
