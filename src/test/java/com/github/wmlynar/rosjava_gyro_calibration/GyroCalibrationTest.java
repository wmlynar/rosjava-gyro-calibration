package com.github.wmlynar.rosjava_gyro_calibration;

import org.junit.Before;
import org.junit.Test;

public class GyroCalibrationTest {

	private GyroModel model;
	private GyroCalibration calibration;

	@Before
	public void setUp() throws Exception {
		model = new GyroModel();
		model.setInitialBias(1.5);
		model.setInitialGain(1);
		model.setBiasDriftPerSecond(0.01);
		model.setGainDriftPerSecond(0.01);
	}

	@Test
	public void test() {
		// given
		calibration = new GyroCalibration();

		calibration.setInitialPosition(0, 0);
		calibration.setInitialRotation(0);
		calibration.setInitialBias(1.5);
		calibration.setInitialGain(1.5);

		// when
		model.moveTime(1);
		calibration.moveTime(1);
		calibration.setPosition(0, 1);
		calibration.setMeasurement(model.getMeasurement());

		// then
	}

}
