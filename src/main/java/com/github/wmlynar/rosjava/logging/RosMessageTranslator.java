package com.github.wmlynar.rosjava.logging;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

public class RosMessageTranslator {

    private RosMessageReceiver receiver;

    public RosMessageTranslator(RosMessageReceiver receiver) {
        this.receiver = receiver;
    }

    public void logScan(LaserScan scan) {
        long n = scan.getHeader().getStamp().totalNsecs();
        float[] ranges = scan.getRanges();
        receiver.processScan(n, ranges);
    }

    public void logOdom(Odometry odom) {
        long n = odom.getHeader().getStamp().totalNsecs();
        double valueX = odom.getPose().getPose().getPosition().getX();
        double valueY = odom.getPose().getPose().getPosition().getY();
        double yaw = 180 / Math.PI * Utils.fromQuaternionToYaw(odom.getPose().getPose().getOrientation());
        double linear = odom.getTwist().getTwist().getLinear().getX();
        double angular = odom.getTwist().getTwist().getAngular().getZ();
        receiver.processOdom(n, valueX, valueY, yaw, linear, angular);
    }

    public void logDist(Vector3Stamped dist) {
        long n = dist.getHeader().getStamp().totalNsecs();
        double valueX = dist.getVector().getX();
        double valueY = dist.getVector().getY();
        receiver.processDist(n, valueX, valueY);
    }

}
