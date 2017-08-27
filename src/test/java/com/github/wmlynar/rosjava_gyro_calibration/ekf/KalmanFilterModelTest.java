package com.github.wmlynar.rosjava_gyro_calibration.ekf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.wmlynar.ekf.KalmanFilter;
import com.github.wmlynar.ekf.ObservationModel;
import com.github.wmlynar.ekf.ProcessModel;
import com.github.wmlynar.ekf_examples.LinearObservationModel;
import com.github.wmlynar.ekf_examples.LinearProcessModel;

public class KalmanFilterModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		LinearProcessModel model = new LinearProcessModel();
		LinearObservationModel obs = new LinearObservationModel();
		KalmanFilter filter = new KalmanFilter(model);
		
		// ruch w bok
        for (int i = 0; i <= 10; ++i) {
        	obs.setPosition(i);
            filter.update(1,obs);
        }
        
        double x = filter.model.state_estimate.data[0][0];
        double v = filter.model.state_estimate.data[1][0];
        
        assertEquals(x,10,1e-4);
        assertEquals(v,1,1e-4);
	}

}
