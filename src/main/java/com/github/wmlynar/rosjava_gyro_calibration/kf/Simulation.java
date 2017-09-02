package com.github.wmlynar.rosjava_gyro_calibration.kf;

import com.github.wmlynar.ekf.KalmanFilter;
import com.github.wmlynar.ekf.utils.Plots;

public class Simulation {

    public static void main(String[] args) {

        RobotSimulator simulator = new RobotSimulator();
        simulator.setRotationNoise(1);
        simulator.setSpeed(1);
        simulator.setAccelerationNoise(1);
        simulator.setBiasDrift(0.1);
        simulator.setTimeStep(0.001);

        RobotAngleObservation angleObs = new RobotAngleObservation();
        RobotOdomObservation odomObs = new RobotOdomObservation();
        BeaconObservation beaconObs = new BeaconObservation();
        RobotGyroObservation gyroObs = new RobotGyroObservation();
        RobotModel process = new RobotModel();
        KalmanFilter filter = new KalmanFilter(process);
        filter.setMaximalTimeStep(0.1);

        int i = 0;
        for (double d = 0; d < 2000; d += 0.1) {
            simulator.simulate(d);
            if (i % 10 == 0) {

                gyroObs.gyroMeasurement = simulator.getGyroMeasurement();
                filter.update(d, gyroObs);

                odomObs.left = simulator.getDistanceLeft();
                odomObs.right = simulator.getDistanceRight();
                filter.update(d, odomObs);

                beaconObs.beaconAngle = simulator.getBeaconAngle();
                beaconObs.beaconDistance = simulator.getBeaconDistance();
                filter.update(d, beaconObs);

                Plots.plotXy("position", "sim", simulator.getX(), simulator.getY());
                Plots.plotXy("position", "filter", process.getX(), process.getY());

                Plots.plotXTime("distances", "sim left", d, simulator.getDistanceLeft());
                Plots.plotXTime("distances", "sim right", d, simulator.getDistanceRight());
                Plots.plotXTime("distances", "filter left", d, process.getDistanceLeft());
                Plots.plotXTime("distances", "filter right", d, process.getDistanceRight());

                Plots.plotXTime("speed", "sim", d, simulator.getSpeed());
                Plots.plotXTime("speed", "filter", d, process.getSpeed());

                Plots.plotXTime("angle", "sim", d, simulator.getAngle());
                Plots.plotXTime("angle", "filter", d, process.getAngle());

                Plots.plotXTime("width", "filtered", d, process.getWidth());

                Plots.plotXTime("gyro bias", "sim", d, simulator.getBias());
                Plots.plotXTime("gyro bias", "filter", d, process.getBias());

                Plots.plotXTime("rotation", "sim", d, simulator.getRotation());
                Plots.plotXTime("rotation", "filter", d, process.getRotation());
            }

            i++;
        }
    }

}
