package com.github.wmlynar.rosjava_gyro_calibration.kf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class KalmanFilterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSingleDimensionForward() {
		// going one direction, test velocity
		KalmanFilter filter = new KalmanFilter(2, 1);

		// macierz stanu i obserwacji
		filter.state_transition.set_matrix(1.0, 1.0, 0.0, 1.0);
		filter.observation_model.set_matrix(1.0, 0.0);
		
		// szumy
		filter.process_noise_covariance.set_identity_matrix();
		filter.observation_noise_covariance.set_identity_matrix();
		
		// stan poczatkowy
		filter.state_estimate.set_matrix(0,0);
		filter.estimate_covariance.set_identity_matrix();
		filter.estimate_covariance.scale_matrix(1000);
		
		// ruch w bok
        for (int i = 0; i <= 10; ++i) {
            filter.observation.set_matrix(i);
            filter.update();
        }
        
        double x = filter.state_estimate.data[0][0];
        double v = filter.state_estimate.data[1][0];
        
        assertEquals(x,10,1e-4);
        assertEquals(v,1,1e-4);
	}
}
