package com.github.wmlynar.rosjava.filtering;

import org.ros.message.Time;

import com.github.wmlynar.ekf.utils.Plots;
import com.github.wmlynar.rosjava.log.writing.RosMessageReceiver;

public class FilterMessageReceiver implements RosMessageReceiver {

    LaserBeaconTracker beaconTracker = new LaserBeaconTracker(0.5f, 10f, 20, .25);

    @Override
    public void processScan(long n, float[] ranges) {
        beaconTracker.processScan(n, ranges);
        Plots.plotXTime("angle", "beacon", Time.fromNano(n).toSeconds(), beaconTracker.getAngle());
        Plots.plotXTime("distance", "beacon", Time.fromNano(n).toSeconds(), beaconTracker.getDistance());
    }

    @Override
    public void processOdom(long n, double valueX, double valueY, double yaw, double linear, double angular) {
        Plots.plotXy("pos", "odom", valueX, valueY);
    }

    @Override
    public void processDist(long n, double valueX, double valueY) {
    }

}
