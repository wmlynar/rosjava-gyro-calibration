package com.github.wmlynar.rosjava_gyro_calibration.kf;

public class MovingAverageFilter {

    private double[] values;
    private int position = 0;
    private boolean hasAverage = false;

    public MovingAverageFilter(int numSamples) {
        values = new double[numSamples];
    }

    public void add(double d) {
        values[position++] = d;
        if (position >= values.length) {
            position = 0;
            hasAverage = true;
        }
    }

    public boolean hasAverage() {
        return hasAverage;
    }

    public double getAverage() {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum / values.length;
    }

}
