package com.github.wmlynar.rosjava.log.reading;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

public class RosMessagePublisherNode extends AbstractNodeMain {

    RosMessageReader reader = new RosMessageReader(
            "src/main/resources/log_neato_scan_odom_dist_rotating_only_close.csv");

    private Publisher<Odometry> odomPublisher;
    private Publisher<LaserScan> scanPublisher;
    private Publisher<Vector3Stamped> distPublisher;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ros_player");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        odomPublisher = connectedNode.newPublisher("odom", Odometry._TYPE);
        scanPublisher = connectedNode.newPublisher("base_scan", LaserScan._TYPE);
        distPublisher = connectedNode.newPublisher("dist", Vector3Stamped._TYPE);

        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void setup() {
            }

            @Override
            protected void loop() throws InterruptedException {
                String type = reader.getNextMessageType();
                switch (type) {
                case "end":
                    return;
                case "odom":
                    odomPublisher.publish(reader.getNextOdomMessage());
                    break;
                case "scan":
                    scanPublisher.publish(reader.getNextScanMessage());
                    return;
                case "dist":
                    distPublisher.publish(reader.getNextDistMessage());
                    return;
                default:
                    throw new RuntimeException("Unknown type: " + type);
                }
            }
        });
    }
}
