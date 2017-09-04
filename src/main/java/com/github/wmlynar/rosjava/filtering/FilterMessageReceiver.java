package com.github.wmlynar.rosjava.filtering;

import com.github.wmlynar.rosjava.log.writing.RosMessageReceiver;

public class FilterMessageReceiver implements RosMessageReceiver {

    LaserBeaconTracker beaconTracker = new LaserBeaconTracker(0.5f, 10f, 5);

    @Override
    public void processScan(long n, float[] ranges) {
    }

    @Override
    public void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular) {
    }

    @Override
    public void processDist(long n, double valueX, double valueY) {
    }

}
