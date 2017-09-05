package com.github.wmlynar.rosjava.filtering;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import com.github.wmlynar.rosjava.log.reading.RosLogPlayerNodeNode;
import com.github.wmlynar.rosjava.log.writing.RosMessageTranslator;
import com.github.wmlynar.rosjava.logging.TimeSequencer;
import com.github.wmlynar.rosjava.utils.CountUpLatch;
import com.github.wmlynar.rosjava.utils.RosMain;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.Imu;
import sensor_msgs.LaserScan;

public class NeatoFilterNode extends AbstractNodeMain {

    private CountUpLatch latch = new CountUpLatch(0);

    private Subscriber<Odometry> odomSubscriber;
    private Subscriber<Object> scanSubscriber;
    private Subscriber<Object> distSubscriber;
    private Subscriber<Imu> imuSubscriber;

    private RosMessageTranslator translator = new RosMessageTranslator(new FilterMessageReceiver());

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("node");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {

        TimeSequencer filter = new TimeSequencer(0.5, 0.1, connectedNode);

        odomSubscriber = connectedNode.newSubscriber("odom", Odometry._TYPE);
        odomSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
        filter.addSubscriber(odomSubscriber, new MessageListener<Odometry>() {
            @Override
            public void onNewMessage(Odometry m) {
                // System.out.println("odom time: " + m.getHeader().getStamp());
                translator.logOdom(m);
                latch.countUp();
            }
        }, 10);

        scanSubscriber = connectedNode.newSubscriber("scan", LaserScan._TYPE);
        scanSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
        filter.addSubscriber(scanSubscriber, new MessageListener<LaserScan>() {
            @Override
            public void onNewMessage(LaserScan m) {
                // System.out.println("laser time: " +
                // m.getHeader().getStamp());
                translator.logScan(m);
                latch.countUp();
            }
        }, 10);

        distSubscriber = connectedNode.newSubscriber("dist", Vector3Stamped._TYPE);
        distSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
        filter.addSubscriber(distSubscriber, new MessageListener<Vector3Stamped>() {
            @Override
            public void onNewMessage(Vector3Stamped m) {
                // System.out.println("dist time: " + m.getHeader().getStamp());
                translator.logDist(m);
                latch.countUp();
            }
        }, 10);
        
        imuSubscriber = connectedNode.newSubscriber("data", Imu._TYPE);
        imuSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
        imuSubscriber.addMessageListener(new MessageListener<Imu>() {
            @Override
            public void onNewMessage(Imu imu) {
                try {
                	translator.logImu(imu);
                	latch.countUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 10);
    }

    public static void main(String[] args) {
        //RosMain.startAndConnectToRosCoreWithoutEnvironmentVariables();
        RosMain.connectToRosCoreWithoutEnvironmentVariables();

        RosLogPlayerNodeNode playerNode = new RosLogPlayerNodeNode(
                "src/main/resources/log_neato_scan_odom_dist_rotating_only_close.csv");

        NeatoFilterNode filterNode = new NeatoFilterNode();

//        RosMain.executeNode(playerNode);
        RosMain.executeNode(filterNode);

        try {
            RosMain.awaitForConnections(6);
            playerNode.start();
            filterNode.latch.awaitFor(playerNode.getNumberOfMessages());
        } catch (InterruptedException e) {
        }

    }

}
