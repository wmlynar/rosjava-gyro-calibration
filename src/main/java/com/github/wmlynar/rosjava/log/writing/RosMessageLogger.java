package com.github.wmlynar.rosjava.log.writing;

public class RosMessageLogger implements RosMessageReceiver {

    @Override
    public void processScan(long n, float[] ranges) {
        CsvLogWriter.log("scan", n, ranges);
    }

    @Override
    public void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular) {
        CsvLogWriter.log("odom", n, valueX, valueY, yaw, linear, angular);
    }

    @Override
    public void processDist(long n, double valueX, double valueY) {
        CsvLogWriter.log("dist", n, valueX, valueY);
    }

}
