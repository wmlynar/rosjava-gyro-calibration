package com.github.wmlynar.rosjava.filtering;

import org.ros.message.Time;

import com.github.wmlynar.ekf.KalmanFilter;
import com.github.wmlynar.ekf.utils.Plots;
import com.github.wmlynar.rosjava.log.writing.RosMessageReceiver;
import com.github.wmlynar.rosjava_gyro_calibration.kf.BeaconObservation;
import com.github.wmlynar.rosjava_gyro_calibration.kf.RobotAngleObservation;
import com.github.wmlynar.rosjava_gyro_calibration.kf.RobotGyroObservation;
import com.github.wmlynar.rosjava_gyro_calibration.kf.RobotModel;
import com.github.wmlynar.rosjava_gyro_calibration.kf.RobotOdomObservation;

public class FilterMessageReceiver implements RosMessageReceiver {

    private LaserBeaconTracker beaconTracker = new LaserBeaconTracker(0.5f, 10f, 20, .25);
    private RobotAngleObservation angleObs;
    private RobotOdomObservation odomObs;
    private BeaconObservation beaconObs;
    private RobotGyroObservation gyroObs;
    private RobotModel process;
    private KalmanFilter filter;
    private long time = -1;

    public FilterMessageReceiver() {
        angleObs = new RobotAngleObservation();
        odomObs = new RobotOdomObservation();
        beaconObs = new BeaconObservation();
        gyroObs = new RobotGyroObservation();
        process = new RobotModel();
        filter = new KalmanFilter(process);
        filter.setMaximalTimeStep(0.1);

        double[][] x = process.getState();

        x[RobotModel.X][0] = 0;
        x[RobotModel.Y][0] = 0;
        x[RobotModel.S][0] = 0;
        x[RobotModel.A][0] = 0;
        x[RobotModel.ROT][0] = 0;
        x[RobotModel.WIDTH][0] = 0.1235; // initial value with error
        x[RobotModel.L][0] = 0;
        x[RobotModel.R][0] = 0;
        x[RobotModel.X1][0] = -2;
        x[RobotModel.Y1][0] = 0;
        x[RobotModel.BIAS][0] = 1;
        x[RobotModel.INVGAIN][0] = 1;
        x[RobotModel.LASER][0] = -0.2;

        double[][] cov = process.getCovariance();
        cov[RobotModel.X][RobotModel.X] = 1e-4;
        cov[RobotModel.Y][RobotModel.Y] = 1e-4;
        cov[RobotModel.S][RobotModel.S] = 1;
        cov[RobotModel.A][RobotModel.A] = 1;
        cov[RobotModel.ROT][RobotModel.ROT] = 1;
        cov[RobotModel.WIDTH][RobotModel.WIDTH] = 1e-9;
        cov[RobotModel.L][RobotModel.L] = 1e-4;
        cov[RobotModel.R][RobotModel.R] = 1e-4;
        cov[RobotModel.X1][RobotModel.X1] = 1000;
        cov[RobotModel.Y1][RobotModel.Y1] = 1000;
        cov[RobotModel.BIAS][RobotModel.BIAS] = 1e-4;
        cov[RobotModel.INVGAIN][RobotModel.INVGAIN] = 1e-9;
        cov[RobotModel.LASER][RobotModel.LASER] = 1e-2;

    }

    @Override
    public void processScan(long n, float[] ranges) {
        beaconTracker.processScan(n, ranges);
        Plots.plotXTime("angle", "beacon", Time.fromNano(n).toSeconds(), beaconTracker.getAngle());
        Plots.plotXTime("distance", "beacon", Time.fromNano(n).toSeconds(), beaconTracker.getDistance());

        beaconObs.beaconAngle = beaconTracker.getAngle();
        beaconObs.beaconDistance = beaconTracker.getDistance();
        // filter.update(getTime(n), beaconObs);

        angleObs.angle = beaconTracker.getAngle() * Math.PI / 180;
        filter.update(getTime(n), angleObs);
    }

    private double getTime(long n) {
        if (time == -1) {
            time = n;
        }
        return 1.0 * (n - time) / 1000000000;
    }

    @Override
    public void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular) {
        Plots.plotXy("pos", "odom", valueX, valueY);
        Plots.plotXTime("angle", "odom", Time.fromNano(n).toSeconds(), yaw);
    }

    @Override
    public void processDist(long n, double valueX, double valueY) {

        odomObs.left = valueX;
        odomObs.right = valueY;

        filter.update(getTime(n), odomObs);
        Plots.plotXTime("angle", "filter", Time.fromNano(n).toSeconds(), process.getAngle() * 180 / Math.PI);
        Plots.plotXTime("width", "filter", Time.fromNano(n).toSeconds(), process.getWidth());
        Plots.plotXy("pos", "filter", process.getX(), process.getY());
    }

	@Override
	public void processImu(long n, double angularYaw) {
	}

}
