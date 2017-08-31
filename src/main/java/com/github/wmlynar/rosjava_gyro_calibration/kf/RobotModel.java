package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ProcessModel;

public class RobotModel extends ProcessModel {
	
	public static int X = 0;
	public static int Y = 1;
	public static int S = 2;
	public static int A = 3;
	public static int ROT = 4;
	public static int B = 5;
	public static int L = 6;
	public static int R = 7;
	public static int X1 = 8;
	public static int Y1 = 9;

	@Override
	public int stateDimension() {
		return 10;
	}

	@Override
	public void initialState(double[][] x) {
		x[X][0] = 0;
		x[Y][0] = 0;
		x[S][0] = 1;
		x[A][0] = 0;
		x[ROT][0] = 0;
		x[B][0] = 12; // initial value with error
		x[L][0] = 0;
		x[R][0] = 0;
		x[X1][0] = -100;
		x[Y1][0] = 0;
	}

	@Override
	public void initialStateCovariance(double[][] cov) {
		cov[X][X] = 0.001;
		cov[Y][Y] = 0.001;
		cov[S][S] = 1;
		cov[A][A] = 1;
		cov[ROT][ROT] = 1;
		cov[B][B] = 10;
		cov[L][L] = 0.001;
		cov[R][R] = 0.001;
		cov[X1][X1] = 1000;
		cov[Y1][Y1] = 1000;
	}

	@Override
	public void stateFunction(double[][] x, double[][] f) {
		f[X][0] = x[S][0] * Math.sin(x[A][0]);
		f[Y][0] = x[S][0] * Math.cos(x[A][0]);
		f[S][0] = 0;
		f[A][0] = x[ROT][0];
		f[ROT][0] = 0;
		f[B][0] = 0;
		f[L][0] = x[S][0] + x[B][0]*x[ROT][0];
		f[R][0] = x[S][0] - x[B][0]*x[ROT][0];
		f[X1][0] = 0;
		f[Y1][0] = 0;
	}

	@Override
	public void stateFunctionJacobian(double[][] x, double[][] j) {
		j[X][S] = Math.sin(x[A][0]);
		j[X][A] = x[S][0] * Math.cos(x[A][0]);
		j[Y][S] = Math.cos(x[A][0]);
		j[Y][A] = -x[S][0] * Math.sin(x[A][0]);
		j[A][ROT] = 1;
		j[L][S] = 1;
		j[L][B] = x[ROT][0];
		j[L][ROT] = x[B][0];
		j[R][S] = 1;
		j[R][B] = -x[ROT][0];
		j[R][ROT] = -x[B][0];
		j[X1][0] = 0;
		j[Y1][0] = 0;
	}

	@Override
	public void processNoiseCovariance(double[][] cov) {
		cov[X][X] = 1e-6;
		cov[Y][Y] = 1e-6;
		cov[S][S] = 1e-1;
		cov[A][A] = 1e-6;
		cov[ROT][ROT] = 1e-1;
		cov[B][B] = 1e-4;
		cov[L][L] = 1e-6;
		cov[R][R] = 1e-6;
		cov[X1][X1] = 1e-6;
		cov[Y1][Y1] = 1e-6;
	}
	
	public double getX() {
		return getState()[X][0];
	}

	public double getY() {
		return getState()[Y][0];
	}

	public double getDistanceLeft() {
		return getState()[L][0];
	}

	public double getDistanceRight() {
		return getState()[R][0];
	}

	public double getSpeed() {
		return getState()[S][0];
	}

	public double getAngle() {
		return getState()[A][0];
	}

	public double getWidth() {
		return getState()[B][0];
	}

}
