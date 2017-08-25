package com.github.wmlynar.rosjava_gyro_calibration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GyroModelTest {
	
	@Test
	public void testDrift() {
		// given
		GyroModel model = new GyroModel();
		model.setInitialBias(1.5);
		model.setInitialGain(1);
		model.setBiasDriftPerSecond(0.01);
		model.setGainDriftPerSecond(0.01);

		//when
		model.moveTime(1);
		
		//then
		assertEquals(1.51, model.getCurrentBias(), 1e-10);
		assertEquals(1.01, model.getCurrentGain(), 1e-10);
	}

	@Test
	public void testOutput() {
		// given
		GyroModel model = new GyroModel();
		model.setInitialBias(1.5);
		model.setInitialGain(1);
		model.setBiasDriftPerSecond(0.01);
		model.setGainDriftPerSecond(0.01);

		//when
		model.setAngularVelocityRadiansPerSecondRight(1);
		
		//then
		assertEquals(2.5, model.getMeasurement(), 1e-10);
	}

}
