package com.github.wmlynar.rosjava.n.models.axlewidth;

import com.github.wmlynar.rosjava.n.kf.Model;
import com.github.wmlynar.rosjava_gyro_calibration.kf.RobotModel;

public class AxleWidthModel extends Model {

    public static int X = 0;
    public static int Y = 1;
    public static int S = 2;
    public static int A = 3;
    public static int ROT = 4;
    public static int WIDTH = 5;
    
	@Override
	public int stateDimension() {
		return 6;
	}

	@Override
	public void stateFunction(double[][] x, double[][] f) {
        f[RobotModel.X][0] = x[RobotModel.S][0] * Math.cos(x[RobotModel.A][0]);
        f[RobotModel.Y][0] = x[RobotModel.S][0] * Math.sin(x[RobotModel.A][0]);
        f[RobotModel.A][0] = x[RobotModel.ROT][0];
	}

	@Override
	public void stateFunctionJacobian(double[][] x, double[][] j) {
        j[RobotModel.X][RobotModel.S] = Math.cos(x[RobotModel.A][0]);
        j[RobotModel.X][RobotModel.A] = -x[RobotModel.S][0] * Math.sin(x[RobotModel.A][0]);
        j[RobotModel.Y][RobotModel.S] = Math.sin(x[RobotModel.A][0]);
        j[RobotModel.Y][RobotModel.A] = x[RobotModel.S][0] * Math.cos(x[RobotModel.A][0]);
        j[RobotModel.A][RobotModel.ROT] = 1;
	}

    public double getRotation() {
        return getState()[RobotModel.ROT][0];
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

}
