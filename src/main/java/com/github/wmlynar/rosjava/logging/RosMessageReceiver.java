package com.github.wmlynar.rosjava.logging;

public interface RosMessageReceiver {

    void processScan(long n, float[] ranges);

    void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular);

    void processDist(long n, double valueX, double valueY);

}