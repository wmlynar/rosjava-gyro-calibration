package com.github.wmlynar.rosjava_gyro_calibration;

public class GyroModel {

    private double bias = 0;
    private double gain = 0;
    private double biasDriftPerSecond = 0;
    private double gainDriftPerSecond = 0;
    private double time = 0;
    private double angularVelocityRadiansPerSecondRight = 0;

    public void setInitialBias(double d) {
        bias = d;
    }

    public void setInitialGain(double d) {
        gain = d;
    }

    public void setBiasDriftPerSecond(double d) {
        biasDriftPerSecond = d;
    }

    public void setGainDriftPerSecond(double d) {
        gainDriftPerSecond = d;
    }

    public void moveTime(double dt) {
        bias += dt * biasDriftPerSecond;
        gain += dt * gainDriftPerSecond;
    }

    public double getCurrentBias() {
        return bias;
    }

    public double getCurrentGain() {
        return gain;
    }

    public void setAngularVelocityRadiansPerSecondRight(int d) {
        this.angularVelocityRadiansPerSecondRight = d;
    }

    public double getMeasurement() {
        return bias + angularVelocityRadiansPerSecondRight * gain;
    }

}
