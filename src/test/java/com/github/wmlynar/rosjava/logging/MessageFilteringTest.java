package com.github.wmlynar.rosjava.logging;

import org.junit.Test;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import com.github.wmlynar.rosjava.log.reading.RosLogPlayerNodeNode;
import com.github.wmlynar.rosjava.utils.CountUpLatch;
import com.github.wmlynar.rosjava.utils.RosMain;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

public class MessageFilteringTest {

    // @Test
    // public void testSimpleFiltering() {
    // RosMain.startAndConnectToRosCoreWithoutEnvironmentVariables();
    // RosLogPlayerNodeNode player = new
    // RosLogPlayerNodeNode("src/test/resources/logging/all_messages_source.csv");
    //
    // SimpleFilter filter = new SimpleFilter(player);
    // }
    //
    @Test
    public void testTimeSequencing() {
        RosMain.startAndConnectToRosCoreWithoutEnvironmentVariables();
        RosLogPlayerNodeNode playerNode = new RosLogPlayerNodeNode(
                "src/test/resources/logging/all_messages_source.csv");

        CountUpLatch latch = new CountUpLatch(0);

        AbstractNodeMain filteredNode = new AbstractNodeMain() {

            private Subscriber<Object> odomSubscriber;
            protected Subscriber<Object> scanSubscriber;
            private Subscriber<Object> distSubscriber;

            @Override
            public GraphName getDefaultNodeName() {
                // TODO Auto-generated method stub
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
                        System.out.println("odom time: " + m.getHeader().getStamp());
                        latch.countUp();
                    }
                }, 10);

                scanSubscriber = connectedNode.newSubscriber("base_scan", LaserScan._TYPE);
                scanSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
                filter.addSubscriber(scanSubscriber, new MessageListener<LaserScan>() {
                    @Override
                    public void onNewMessage(LaserScan m) {
                        System.out.println("laser time: " + m.getHeader().getStamp());
                        latch.countUp();
                    }
                }, 10);

                distSubscriber = connectedNode.newSubscriber("dist", Vector3Stamped._TYPE);
                distSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
                filter.addSubscriber(distSubscriber, new MessageListener<Vector3Stamped>() {
                    @Override
                    public void onNewMessage(Vector3Stamped m) {
                        System.out.println("dist time: " + m.getHeader().getStamp());
                        latch.countUp();
                    }
                }, 10);
            }
        };

        RosMain.executeNode(playerNode);
        RosMain.executeNode(filteredNode);

        try {
            RosMain.awaitForConnections(6);
            playerNode.start();
            latch.awaitFor(playerNode.getNumberOfMessages());
        } catch (InterruptedException e) {
        }

    }
}
