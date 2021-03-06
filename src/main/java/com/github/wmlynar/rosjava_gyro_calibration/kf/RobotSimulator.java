package com.github.wmlynar.rosjava_gyro_calibration.kf;

import java.util.Random;

public class RobotSimulator {

    double time = 0;
    double maximalTimeStep = 1;
    double x = 0;
    double y = 0;
    double speed = 0;
    double angle = 0;
    double rotation = 0;
    double rotationNoise = 0;
    double accelerationNoise = 0;
    double distanceLeft = 0;
    double distanceRight = 0;
    double width = 0.2;

    double beaconX = -1;
    double beaconY = 0;

    double bias = 1;
    double biasDrift = 0;
    double invgain = 1;
    double gainDrift = 0;

    double laserPosition = -1;

    MovingAverageFilter gyroFilter = new MovingAverageFilter(10);

    Random random = new Random(0);

    public void setRotationNoise(double n) {
        rotationNoise = n;
    }

    public void setSpeed(double s) {
        speed = s;
    }

    public void setAccelerationNoise(double n) {
        accelerationNoise = n;
    }

    public void setTimeStep(double d) {
        maximalTimeStep = d;
    }

    public void simulate(double t) {
        do {
            double dt = t - time;
            if (dt > maximalTimeStep) {
                dt = maximalTimeStep;
            }
            simulateInterval(dt);
            time += dt;
        } while (time < t);
    }

    public void simulateInterval(double dt) {
        double acceleration = accelerationNoise * (random.nextDouble() - 0.5);
        double drotation = rotationNoise * (random.nextDouble() - 0.5);
        speed += acceleration * dt;
        angle += rotation * dt;
        rotation += drotation * dt;
        x += speed * Math.sin(angle) * dt;
        y += speed * Math.cos(angle) * dt;
        distanceLeft += speed * dt + width * rotation * dt;
        distanceRight += speed * dt - width * rotation * dt;
        bias += biasDrift * (random.nextDouble() - 0.5) * dt;
        invgain += gainDrift * (random.nextDouble() - 0.5) * dt;

        gyroFilter.add(rotation);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistanceLeft() {
        return distanceLeft;
    }

    public double getDistanceRight() {
        return distanceRight;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }

    public double getBeaconAngle() {
        double dx = beaconX - (x + laserPosition * Math.sin(angle));
        double dy = beaconY - (y + laserPosition * Math.cos(angle));

        return Math.atan2(dx, dy) + angle;
    }

    public double getBeaconDistance() {
        double dx = beaconX - (x + laserPosition * Math.sin(angle));
        double dy = beaconY - (y + laserPosition * Math.cos(angle));

        return Math.sqrt(dx * dx + dy * dy);
    }

    public void setBiasDrift(double biasDrift) {
        this.biasDrift = biasDrift;
    }

    public void setGainDrift(double gainDrift) {
        this.gainDrift = gainDrift;
    }

    public double getGyroMeasurement() {
        return (gyroFilter.getAverage() + bias) * invgain;
    }

    public double getBias() {
        return bias;
    }

    public double getGain() {
        return invgain;
    }

    public boolean hasRotation() {
        return gyroFilter.hasAverage();
    }

    public double getRotation() {
        return gyroFilter.getAverage();
    }

}
