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

import nav_msgs.Odometry;

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
                        System.out.println(m.getHeader().getStamp());
                        latch.countUp();
                    }
                }, 10);
            }
        };

        RosMain.executeNode(playerNode);
        RosMain.executeNode(filteredNode);

        try {
            RosMain.awaitForConnections(2);
            playerNode.start();
            // recorder.awaitForMessages(player.getNumberOfMessages());
            latch.awaitFor(2);
        } catch (InterruptedException e) {
        }

    }
}
