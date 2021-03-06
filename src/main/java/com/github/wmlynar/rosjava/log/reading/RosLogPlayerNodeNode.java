package com.github.wmlynar.rosjava.log.reading;

import java.util.concurrent.CountDownLatch;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import com.github.wmlynar.rosjava.filtering.NeatoFilterNode;
import com.github.wmlynar.rosjava.log.writing.RosLogRecorderNode;
import com.github.wmlynar.rosjava.utils.RosMain;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.Imu;
import sensor_msgs.LaserScan;

public class RosLogPlayerNodeNode extends AbstractNodeMain {

    private RosMessageReader reader;

    private Publisher<Odometry> odomPublisher;
    private Publisher<LaserScan> scanPublisher;
    private Publisher<Vector3Stamped> distPublisher;
    private Publisher<Imu> imuPublisher;

    private ConnectedNode connectedNode;

    private CountDownLatch initializedLatch = new CountDownLatch(1);
    private CountDownLatch finishedLatch = new CountDownLatch(1);

    private String name;

    public RosLogPlayerNodeNode(String name) {
        this.name = name;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ros_log_player");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        reader = new RosMessageReader(name, connectedNode);

        odomPublisher = connectedNode.newPublisher("odom", Odometry._TYPE);
        odomPublisher.addListener(RosMain.getPublisherListener());
        scanPublisher = connectedNode.newPublisher("scan", LaserScan._TYPE);
        scanPublisher.addListener(RosMain.getPublisherListener());
        distPublisher = connectedNode.newPublisher("dist", Vector3Stamped._TYPE);
        distPublisher.addListener(RosMain.getPublisherListener());
        imuPublisher = connectedNode.newPublisher("data", Imu._TYPE);
        imuPublisher.addListener(RosMain.getPublisherListener());

        this.connectedNode = connectedNode;

        initializedLatch.countDown();
    }

    public void start() {
        try {
            initializedLatch.await();
        } catch (InterruptedException e1) {
        }
        finishedLatch = new CountDownLatch(1);
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void setup() {
            }

            @Override
            protected void loop() throws InterruptedException {
                String type = reader.getNextMessageType();
                switch (type) {
                case "end":
                    finishedLatch.countDown();
                    throw new InterruptedException();
                case "odom":
                    odomPublisher.publish(reader.getNextOdomMessage());
                    break;
                case "scan":
                    scanPublisher.publish(reader.getNextScanMessage());
                    break;
                case "dist":
                    distPublisher.publish(reader.getNextDistMessage());
                    break;
                case "gyro_yaw":
                    imuPublisher.publish(reader.getNextGyroYawMessage());
                    break;
                default:
                    throw new RuntimeException("Unknown type: " + type);
                }
            }
        });
    }

    public void awaitFinished() throws InterruptedException {
        finishedLatch.await();
    }

    public int getNumberOfMessages() {
        return reader.getNumberOfMessages();
    }

    public static void main(String[] args) {
        //RosMain.startAndConnectToRosCoreWithoutEnvironmentVariables();
        RosMain.connectToRosCoreWithoutEnvironmentVariables();

        RosLogPlayerNodeNode playerNode = new RosLogPlayerNodeNode("src/main/resources/logs/with_gyro/moving.csv");
        RosLogRecorderNode plotterNode = new RosLogRecorderNode("log.csv", 10);

        RosMain.executeNode(playerNode);
        RosMain.executeNode(plotterNode);

        try {
            RosMain.awaitForConnections(8);
            playerNode.start();
            plotterNode.awaitForMessages(playerNode.getNumberOfMessages());
        } catch (InterruptedException e) {
        }

    }
}
