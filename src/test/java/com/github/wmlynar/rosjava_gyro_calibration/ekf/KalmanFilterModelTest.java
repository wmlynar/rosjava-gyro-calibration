package com.github.wmlynar.rosjava_gyro_calibration.ekf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class KalmanFilterModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		LinearMovementModel model = new LinearMovementModel();
		LinearModelObservation obs = new LinearModelObservation();
		KalmanFilter filter = new KalmanFilter(model);
		
		// ruch w bok
        for (int i = 0; i <= 10; ++i) {
        	obs.setPosition(i);
            filter.update(1,obs);
        }
        
        double x = filter.state_estimate.data[0][0];
        double v = filter.state_estimate.data[1][0];
        
        assertEquals(x,10,1e-4);
        assertEquals(v,1,1e-4);
	}

}
