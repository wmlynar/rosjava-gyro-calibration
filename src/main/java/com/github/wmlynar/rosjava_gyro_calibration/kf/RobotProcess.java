package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.ProcessModel;

public class RobotProcess extends ProcessModel {
	
	public static int X = 0;
	public static int Y = 1;
	public static int S = 2;
	public static int A = 3;
	public static int O = 4;
	public static int B = 5;
	public static int L = 6;
	public static int R = 7;

	@Override
	public int stateDimension() {
		return 8;
	}

	@Override
	public void initialState(double[][] x) {
		x[X][0] = 0;
		x[Y][0] = 0;
		x[S][0] = 0;
		x[A][0] = 0;
		x[O][0] = 0;
		x[B][0] = 15;
		x[L][0] = 0;
		x[R][0] = 0;
	}

	@Override
	public void initialStateCovariance(double[][] cov) {
		cov[X][X] = 10;
		cov[Y][Y] = 10;
		cov[S][S] = 10;
		cov[A][A] = 10;
		cov[O][O] = 10;
		cov[B][B] = 1000;
		cov[L][L] = 10;
		cov[R][R] = 10;
	}

	@Override
	public void stateFunction(double[][] x, double[][] f) {
		f[X][0] = x[S][0] * Math.sin(x[A][0]);
		f[Y][0] = x[S][0] * Math.cos(x[A][0]);
		f[S][0] = 0;
		f[A][0] = x[R][0];
		f[O][0] = 0;
		f[B][0] = 0;
		f[L][0] = x[S][0] + x[B][0]*x[O][0];
		f[R][0] = x[S][0] - x[B][0]*x[O][0];
	}

	@Override
	public void stateFunctionJacobian(double[][] x, double[][] j) {
		j[X][S] = Math.sin(x[A][0]);
		j[X][A] = x[A][0] * Math.cos(x[A][0]);
		j[Y][S] = Math.cos(x[A][0]);
		j[Y][A] = -x[A][0] * Math.sin(x[A][0]);
		j[A][R] = 1;
		j[L][S] = 1;
		j[L][B] = x[O][0];
		j[L][O] = x[B][0];
		j[R][S] = 1;
		j[R][B] = -x[O][0];
		j[R][O] = -x[B][0];
	}

	@Override
	public void processNoiseCovariance(double[][] cov) {
		cov[X][X] = 1;
		cov[Y][Y] = 1;
		cov[S][S] = 1;
		cov[A][A] = 1;
		cov[O][O] = 1;
		cov[B][B] = 1;
		cov[L][L] = 1;
		cov[R][R] = 1;
	}

}
