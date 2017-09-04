package com.github.wmlynar.rosjava.filtering;

public class LaserBeaconTracker {

    private int angle = -1;
    private float distance;
    private float min;
    private float max;
    private int numSamples;
    private double distanceRange;

    public LaserBeaconTracker(float min, float max, int numSamples, double distanceRange) {
        this.min = min;
        this.max = max;
        this.numSamples = numSamples;
        this.distanceRange = distanceRange;
    }

    public void processScan(long n, float[] ranges) {
        double prevDistance = distance;
        if (angle == -1) {
            angle = -1;
            distance = 9999999;
            processRange(ranges, 0, ranges.length - 1, prevDistance);
            return;
        }
        int angleMin = angle - numSamples;
        int angleMax = angle + numSamples;
        angle = -1;
        distance = 9999999;
        if (angleMin < 0) {
            processRange(ranges, ranges.length - 1 + angleMin, ranges.length - 1, prevDistance);
            angleMin = 0;
        }
        if (angleMax >= ranges.length) {
            processRange(ranges, 0, angleMax - ranges.length + 1, prevDistance);
            angleMax = ranges.length - 1;
        }
        processRange(ranges, angleMin, angleMax, prevDistance);
    }

    public int getAngle() {
        return angle;
    }

    public double getDistance() {
        return distance;
    }

    private void processRange(float[] ranges, int start, int end, double prevDistance) {
        for (int i = start; i <= end; i++) {
            float f = ranges[i];
            if (f < distance && f > min && f < max
                    && (angle == -1 || (f > (prevDistance - distanceRange) && f < (prevDistance + distanceRange)))) {
                distance = f;
                angle = i;
            }
        }
    }

}
