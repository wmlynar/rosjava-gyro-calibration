package com.github.wmlynar.rosjava_gyro_calibration.kf;

import org.jfree.ui.RefineryUtilities;

import com.github.wmlynar.ekf.KalmanFilter;
import com.github.wmlynar.ekf.utils.XTimePlotter;
import com.github.wmlynar.ekf.utils.XyPlotter;

public class Simulation {
	
	public static void main(String[] args) {

		RobotAngleObservation angleObs = new RobotAngleObservation();
		RobotOdomObservation odomObs = new RobotOdomObservation();
		RobotProcess process = new RobotProcess();
		KalmanFilter filter = new KalmanFilter(process);
		filter.setMaximalTimeStep(0.01);
		
		RobotSimulator simulator = new RobotSimulator();
		simulator.setRotationNoise(10);
		simulator.setSpeed(1);
		simulator.setAccelerationNoise(0.1);
		simulator.setTimeStep(0.0001);
		
		
		XyPlotter plotXy = new XyPlotter("Robot xy");
		RefineryUtilities.centerFrameOnScreen(plotXy);
		plotXy.setVisible(true);

		final XTimePlotter plotDist = new XTimePlotter("Robot distances");
		RefineryUtilities.centerFrameOnScreen(plotDist);
		plotDist.setVisible(true);
		
		final XTimePlotter plotSpeed = new XTimePlotter("Robot speed");
		RefineryUtilities.centerFrameOnScreen(plotSpeed);
		plotSpeed.setVisible(true);
		
		final XTimePlotter plotAngle = new XTimePlotter("Robot angles");
		RefineryUtilities.centerFrameOnScreen(plotAngle);
		plotAngle.setVisible(true);
		
		int i=0;
		for(double d = 0; d<100; d+=0.1) {
			simulator.simulate(d);
			if(i%10==0) {
				plotXy.addValues("sim position", simulator.getX(), simulator.getY());
				plotDist.addValues("sim left", d, simulator.getDistanceLeft());
				plotDist.addValues("sim right", d, simulator.getDistanceRight());
				plotSpeed.addValues("sim speed", d, simulator.getSpeed());
				plotAngle.addValues("sim angle", d, simulator.getAngle());
				
//				angleObs.angle = 0;
//				filter.update(d, angleObs);
				odomObs.left = simulator.getDistanceLeft();
				odomObs.right = simulator.getDistanceRight();
				filter.update(d, odomObs);

				plotXy.addValues("filter position", process.getX(), process.getY());
				plotDist.addValues("filter left", d, process.getDistanceLeft());
				plotDist.addValues("filter right", d, process.getDistanceRight());
				plotSpeed.addValues("filter speed", d, process.getSpeed());
				plotAngle.addValues("filter angle", d, process.getAngle());
			}
			
			i++;
		}
	}

}
