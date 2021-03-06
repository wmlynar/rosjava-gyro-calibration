package com.github.wmlynar.rosjava.log.writing;

import com.github.wmlynar.ekf.utils.Plots;

public class RosMessagePlotter implements RosMessageReceiver {

    private ScanMessageInterpreter scanInterpreter = new ScanMessageInterpreter();

    @Override
    public void processScan(long n, float[] ranges) {
        double value = scanInterpreter.processScanToAngle(ranges);
        Plots.plotXTime("angle", "scan", n, value);
        value = scanInterpreter.processScanToDistance(ranges);
        Plots.plotXTime("x", "scan", n, value);
    }

    @Override
    public void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular) {
        Plots.plotXTime("angle", "odom", n, yaw);
        Plots.plotXTime("angular", "odom", n, angular);
        Plots.plotXTime("x", "odom", n, valueX);
    }

    @Override
    public void processDist(long n, double valueX, double valueY) {
    }

	@Override
	public void processImu(long n, double angularYaw) {
        Plots.plotXTime("angular", "imu", n, angularYaw);
	}

}
