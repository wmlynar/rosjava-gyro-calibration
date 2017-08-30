package com.github.wmlynar.rosjava_gyro_calibration.kf;

import static org.junit.Assert.*;

import org.jfree.ui.RefineryUtilities;
import org.junit.Test;

import com.github.wmlynar.ekf.KalmanFilter;
import com.github.wmlynar.ekf.utils.XTimePlotter;
import com.github.wmlynar.ekf.utils.XyPlotter;

public class OdomTest {

	@Test
	public void test() {
		RobotAngleObservation obs = new RobotAngleObservation();
		RobotProcess process = new RobotProcess();
		KalmanFilter filter = new KalmanFilter(process);
		
		RobotSimulator simulator = new RobotSimulator();
		simulator.setRotationNoise(10);
		simulator.setSpeed(1);
		simulator.setAccelerationNoise(10);
		simulator.setTimeStep(0.0001);
		
		
		XyPlotter plotter = new XyPlotter("Robot position");
		RefineryUtilities.centerFrameOnScreen(plotter);
		plotter.setVisible(true);
		
		for(int i = 0; i<10; i++) {
//			obs.angle = 0;
//			filter.update(i, obs);
			simulator.simulate(i);
			plotter.addValues("robot", simulator.getX(), simulator.getY());
		}
	}

}
