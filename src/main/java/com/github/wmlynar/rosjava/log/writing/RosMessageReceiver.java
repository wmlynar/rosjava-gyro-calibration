package com.github.wmlynar.rosjava.log.writing;

public interface RosMessageReceiver {

    void processScan(long n, float[] ranges);

    void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular);

    void processDist(long n, double valueX, double valueY);

	void processImu(long n, double angularYaw);

}