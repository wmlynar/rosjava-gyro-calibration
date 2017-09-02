package com.github.wmlynar.rosjava.log.writing;

import com.github.wmlynar.ekf.utils.Plots;

public class RosMessagePlotter implements RosMessageReceiver {

    private ScanMessageInterpreter scanInterpreter = new ScanMessageInterpreter();

    @Override
    public void processScan(long n, float[] ranges) {
        double value = scanInterpreter.processScan(ranges);
        Plots.plotXTime("angle", "scan", n, value);
    }

    @Override
    public void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular) {
        Plots.plotXTime("angle", "yaw", n, yaw);
    }

    @Override
    public void processDist(long n, double valueX, double valueY) {
    }

}
