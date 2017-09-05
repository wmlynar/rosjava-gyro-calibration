package com.github.wmlynar.rosjava.log.writing;

public class RosMessageLogger implements RosMessageReceiver {

    private String logName;

    public RosMessageLogger(String name) {
        this.logName = name;
    }

    @Override
    public void processScan(long n, float[] ranges) {
        CsvLogWriter.log(logName, "scan", n, ranges);
    }

    @Override
    public void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular) {
        CsvLogWriter.log(logName, "odom", n, valueX, valueY, yaw, linear, angular);
    }

    @Override
    public void processDist(long n, double valueX, double valueY) {
        CsvLogWriter.log(logName, "dist", n, valueX, valueY);
    }

	@Override
	public void processImu(long n, double angularYaw) {
        CsvLogWriter.log(logName, "gyro_yaw", n, angularYaw);
	}

}
