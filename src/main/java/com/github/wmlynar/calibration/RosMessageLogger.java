package com.github.wmlynar.calibration;

import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

public class RosMessageLogger {
	
	public void logScan(LaserScan scan) {
		long n = scan.getHeader().getStamp().totalNsecs();
		float[] ranges = scan.getRanges();
		CsvLogWriter.log("scan", n, ranges);
	}

	public void logOdom(Odometry odom) {
		long n = odom.getHeader().getStamp().totalNsecs();
		double valueX = odom.getPose().getPose().getPosition().getX();
		double valueY = odom.getPose().getPose().getPosition().getY();
		CsvLogWriter.log("odom", n, valueX, valueY);
	}

}
