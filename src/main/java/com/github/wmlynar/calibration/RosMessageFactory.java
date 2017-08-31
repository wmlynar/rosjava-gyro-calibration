package com.github.wmlynar.calibration;

import org.ros.internal.message.DefaultMessageFactory;
import org.ros.internal.message.definition.MessageDefinitionReflectionProvider;
import org.ros.message.MessageDefinitionProvider;
import org.ros.message.MessageFactory;
import org.ros.message.Time;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

public class RosMessageFactory {

    private static long NANO = 1000000000;

    private MessageDefinitionProvider messageDefinitionProvider = new MessageDefinitionReflectionProvider();
    private MessageFactory messageFactory = new DefaultMessageFactory(messageDefinitionProvider);

    public Time newTimeFromNsecs(long n) {
        return Time.fromNano(n);
    }

    public LaserScan newLaserScanMessage() {
        return messageFactory.newFromType(sensor_msgs.LaserScan._TYPE);
    }

    public Odometry newOdometryMessage() {
        return messageFactory.newFromType(nav_msgs.Odometry._TYPE);
    }

    public Vector3Stamped newDistancesMessage() {
        return messageFactory.newFromType(geometry_msgs.Vector3Stamped._TYPE);
    }

    public Vector3Stamped newDistancesMessage(long n, double x, double y) {
        Vector3Stamped vector = messageFactory.newFromType(geometry_msgs.Vector3Stamped._TYPE);
        vector.getHeader().setStamp(Time.fromNano(n));
        vector.getVector().setX(x);
        vector.getVector().setY(y);
        return vector;
    }
}
