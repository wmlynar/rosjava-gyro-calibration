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
        if (angle == -1) {
            angle = -1;
            distance = Float.MAX_EXPONENT;
            processRange(ranges, 0, ranges.length - 1);
            return;
        }
        int angleMin = angle - numSamples;
        int angleMax = angle + numSamples;
        angle = -1;
        distance = Float.MAX_EXPONENT;
        if (angleMin < 0) {
            processRange(ranges, ranges.length - 1 + angleMin, ranges.length - 1);
            angleMin = 0;
        }
        if (angleMax >= ranges.length) {
            processRange(ranges, 0, angleMax - ranges.length + 1);
            angleMax = ranges.length - 1;
        }
        processRange(ranges, angleMin, angleMax);
    }

    public int getAngle() {
        return angle;
    }

    public double getDistance() {
        return distance;
    }

    private void processRange(float[] ranges, int start, int end) {
        for (int i = start; i <= end; i++) {
            float f = ranges[i];
            if (f < distance && f > min && f < max && f > distance - distanceRange && f < distance + distanceRange) {
                distance = f;
                angle = i;
            }
        }
    }

}
