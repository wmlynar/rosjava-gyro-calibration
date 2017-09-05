package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ProcessModel;

public class RobotModel extends ProcessModel {

    public static int X = 0;
    public static int Y = 1;
    public static int S = 2;
    public static int A = 3;
    public static int ROT = 4;
    public static int WIDTH = 5;
    public static int L = 6;
    public static int R = 7;
    public static int X1 = 8;
    public static int Y1 = 9;
    public static int BIAS = 10;
    public static int INVGAIN = 11;
    public static int LASER = 12;

    @Override
    public int stateDimension() {
        return 13;
    }

    @Override
    public void initialState(double[][] x) {
        x[RobotModel.X][0] = 0;
        x[RobotModel.Y][0] = 0;
        x[RobotModel.S][0] = 1;
        x[RobotModel.A][0] = 0;
        x[RobotModel.ROT][0] = 0;
        x[RobotModel.WIDTH][0] = 0.3; // initial value with error
        x[RobotModel.L][0] = 0;
        x[RobotModel.R][0] = 0;
        x[RobotModel.X1][0] = -100;
        x[RobotModel.Y1][0] = 0;
        x[RobotModel.BIAS][0] = 1;
        x[RobotModel.INVGAIN][0] = 1;
        x[RobotModel.LASER][0] = -1;
    }

    @Override
    public void initialStateCovariance(double[][] cov) {
        cov[RobotModel.X][RobotModel.X] = 1e-4;
        cov[RobotModel.Y][RobotModel.Y] = 1e-4;
        cov[RobotModel.S][RobotModel.S] = 1;
        cov[RobotModel.A][RobotModel.A] = 1;
        cov[RobotModel.ROT][RobotModel.ROT] = 1;
        cov[RobotModel.WIDTH][RobotModel.WIDTH] = 100;
        cov[RobotModel.L][RobotModel.L] = 1e-4;
        cov[RobotModel.R][RobotModel.R] = 1e-4;
        cov[RobotModel.X1][RobotModel.X1] = 1000;
        cov[RobotModel.Y1][RobotModel.Y1] = 1000;
        cov[RobotModel.BIAS][RobotModel.BIAS] = 1e-4;
        cov[RobotModel.INVGAIN][RobotModel.INVGAIN] = 1e-9;
        cov[RobotModel.LASER][RobotModel.LASER] = 1e-2;
    }

    @Override
    public void stateFunction(double[][] x, double[][] f) {
        f[RobotModel.X][0] = x[RobotModel.S][0] * Math.sin(x[RobotModel.A][0]);
        f[RobotModel.Y][0] = x[RobotModel.S][0] * Math.cos(x[RobotModel.A][0]);
        f[RobotModel.S][0] = 0;
        f[RobotModel.A][0] = x[RobotModel.ROT][0];
        f[RobotModel.ROT][0] = 0;
        f[RobotModel.WIDTH][0] = 0;
        f[RobotModel.L][0] = x[RobotModel.S][0] + x[RobotModel.WIDTH][0] * x[RobotModel.ROT][0];
        f[RobotModel.R][0] = x[RobotModel.S][0] - x[RobotModel.WIDTH][0] * x[RobotModel.ROT][0];
        f[RobotModel.X1][0] = 0;
        f[RobotModel.Y1][0] = 0;
        f[RobotModel.BIAS][0] = 0;
        f[RobotModel.INVGAIN][0] = 0;
        f[RobotModel.LASER][0] = 0;
    }

    @Override
    public void stateFunctionJacobian(double[][] x, double[][] j) {
        j[RobotModel.X][RobotModel.S] = Math.sin(x[RobotModel.A][0]);
        j[RobotModel.X][RobotModel.A] = x[RobotModel.S][0] * Math.cos(x[RobotModel.A][0]);
        j[RobotModel.Y][RobotModel.S] = Math.cos(x[RobotModel.A][0]);
        j[RobotModel.Y][RobotModel.A] = -x[RobotModel.S][0] * Math.sin(x[RobotModel.A][0]);
        j[RobotModel.A][RobotModel.ROT] = 1;
        j[RobotModel.L][RobotModel.S] = 1;
        j[RobotModel.L][RobotModel.WIDTH] = x[RobotModel.ROT][0];
        j[RobotModel.L][RobotModel.ROT] = x[RobotModel.WIDTH][0];
        j[RobotModel.R][RobotModel.S] = 1;
        j[RobotModel.R][RobotModel.WIDTH] = -x[RobotModel.ROT][0];
        j[RobotModel.R][RobotModel.ROT] = -x[RobotModel.WIDTH][0];
    }

    @Override
    public void processNoiseCovariance(double[][] cov) {
        cov[RobotModel.X][RobotModel.X] = 1e-6;
        cov[RobotModel.Y][RobotModel.Y] = 1e-6;
        cov[RobotModel.S][RobotModel.S] = 1e-0;
        cov[RobotModel.A][RobotModel.A] = 1e-6;
        cov[RobotModel.ROT][RobotModel.ROT] = 1e-0;
        cov[RobotModel.WIDTH][RobotModel.WIDTH] = 1e-7;
        cov[RobotModel.L][RobotModel.L] = 1e-6;
        cov[RobotModel.R][RobotModel.R] = 1e-6;
        cov[RobotModel.X1][RobotModel.X1] = 1e-6;
        cov[RobotModel.Y1][RobotModel.Y1] = 1e-6;
        cov[RobotModel.BIAS][RobotModel.BIAS] = 1e-4;
        cov[RobotModel.INVGAIN][RobotModel.INVGAIN] = 1e-9;
        cov[RobotModel.LASER][RobotModel.LASER] = 1e-9;
    }

    @Override
    public void normalizeState(double[][] x) {
        while (x[RobotModel.A][0] < 0) {
            x[RobotModel.A][0] += 2 * Math.PI;
        }
        while (x[RobotModel.A][0] > 2 * Math.PI) {
            x[RobotModel.A][0] -= 2 * Math.PI;
        }
    }

    public double getX() {
        return getState()[RobotModel.X][0];
    }

    public double getY() {
        return getState()[RobotModel.Y][0];
    }

    public double getDistanceLeft() {
        return getState()[RobotModel.L][0];
    }

    public double getDistanceRight() {
        return getState()[RobotModel.R][0];
    }

    public double getSpeed() {
        return getState()[RobotModel.S][0];
    }

    public double getAngle() {
        return getState()[RobotModel.A][0];
    }

    public double getWidth() {
        return getState()[RobotModel.WIDTH][0];
    }

    public double getBias() {
        return getState()[RobotModel.BIAS][0];
    }

    public double getGain() {
        return getState()[RobotModel.INVGAIN][0];
    }

    public double getRotation() {
        return getState()[RobotModel.ROT][0]; // ;(getState()[RobotModel.ROT][0]
                                              // +
                                              // getState()[RobotModel.BIAS][0])
                                              // *
                                              // getState()[RobotModel.INVGAIN][0];
    }

    public double getLaserDistance() {
        return getState()[RobotModel.LASER][0];
    }

}
