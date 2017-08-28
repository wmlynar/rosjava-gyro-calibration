package com.github.wmlynar.ekf_examples;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.wmlynar.ekf.KalmanFilter;
import com.github.wmlynar.ekf_examples.Linear1dObservationModel;
import com.github.wmlynar.ekf_examples.Linear1dProcessModel;

public class AngleModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		AngleProcessModel model = new AngleProcessModel();
		AngleObservationModel obs = new AngleObservationModel();
		KalmanFilter filter = new KalmanFilter(model);
		
        for (int i = 0; i <= 10; ++i) {
        	obs.setPosition(i,i);
            filter.update(1,obs);
        }
        
        double x = filter.model.state_estimate.data[0][0];
        double y = filter.model.state_estimate.data[1][0];
        double v = filter.model.state_estimate.data[2][0];
        double alpha = filter.model.state_estimate.data[3][0];
        
        assertEquals(10,x,1e-3);
        assertEquals(10,y,1e-3);
        assertEquals(Math.sqrt(2),v,1e-3);
        assertEquals(Math.PI/4,alpha,1e-3);
	}

}
