package com.github.wmlynar.ekf.examples;

import org.jfree.ui.RefineryUtilities;
import org.junit.Assert;
import org.junit.Test;

import com.github.wmlynar.ekf.KalmanFilter;
import com.github.wmlynar.ekf.utils.XTimePlotter;

public class Linear1dModelTest {

	@Test
	public void test() {
		Linear1dProcessModel model = new Linear1dProcessModel();
		Linear1dObservationModel obs = new Linear1dObservationModel();
		KalmanFilter filter = new KalmanFilter(model);
		filter.setMaximalTimeStep(0.001);

		XTimePlotter plotter = new XTimePlotter("EKF Test");
		RefineryUtilities.centerFrameOnScreen(plotter);
		plotter.setVisible(true);

		for (int i = 0; i <= 10; ++i) {
			double time = i;
			obs.setPosition(i);
			filter.update(time, obs);
			plotter.addValues("x", time, model.getState()[0][0]);
			plotter.addValues("v", time, model.getState()[1][0]);
			plotter.addValues("cx", time, model.getCovariance()[0][0]);
			plotter.addValues("cv", time, model.getCovariance()[1][0]);
		}

		double x = model.getState()[0][0];
		double v = model.getState()[1][0];

		Assert.assertEquals(10, x, 1e-3);
		Assert.assertEquals(1, v, 1e-3);
	}
}
