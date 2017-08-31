package com.github.wmlynar.rosjava_gyro_calibration.kf;

import org.junit.Assert;
import org.junit.Test;

public class MovingAverageFilterTest {

	@Test
	public void test() {
		MovingAverageFilter maFilter = new MovingAverageFilter(10);
		for (int i = 0; i < 20; i++) {
			maFilter.add(i);
		}

		double sum = 0;
		for (int i = 10; i < 20; i++) {
			sum += i;
		}

		Assert.assertTrue(maFilter.hasAverage());
		double average = maFilter.getAverage();
		Assert.assertEquals(sum, average, 1e-6);
	}

}
