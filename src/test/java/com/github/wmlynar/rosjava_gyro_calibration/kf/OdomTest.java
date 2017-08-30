package com.github.wmlynar.rosjava_gyro_calibration.kf;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.wmlynar.ekf.KalmanFilter;

public class OdomTest {

	@Test
	public void test() {
		RobotAngleObservation obs = new RobotAngleObservation();
		RobotProcess process = new RobotProcess();
		KalmanFilter filter = new KalmanFilter(process);
		
		for(int i = 0; i<10; i++) {
			obs.angle = 0;
			filter.update(i, obs);
		}
	}

}
