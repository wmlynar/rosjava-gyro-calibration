/*
 * Copyright (C) 2014 woj.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.wmlynar.rosjava.log.writing;

import java.net.URI;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;
import org.ros.node.topic.Subscriber;

import com.github.wmlynar.rosjava.utils.CountUpLatch;
import com.github.wmlynar.rosjava.utils.RosMain;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

/**
 * A simple {@link Subscriber} {@link NodeMain}.
 */
public class RosLogRecorderNode extends AbstractNodeMain {

    private Subscriber<Odometry> odomSubscriber;
    private Subscriber<LaserScan> scanSubscriber;
    private Subscriber<Vector3Stamped> distSubscriber;

    private RosMessageTranslator rosMessageTranslator;

    private CountUpLatch messagesCount = new CountUpLatch(0);

    private int queueSize;

    public RosLogRecorderNode(String name, int queueSize) {
        rosMessageTranslator = new RosMessageTranslator(new RosMessageLogger(name));
        this.queueSize = queueSize;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ros_log_recorder");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {

        odomSubscriber = connectedNode.newSubscriber("odom", Odometry._TYPE);
        odomSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
        odomSubscriber.addMessageListener(new MessageListener<Odometry>() {
            @Override
            public void onNewMessage(Odometry odom) {
                try {
                    System.out.println("received : odom");
                    rosMessageTranslator.logOdom(odom);
                    messagesCount.countUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, queueSize);

        scanSubscriber = connectedNode.newSubscriber("base_scan", LaserScan._TYPE);
        scanSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
        scanSubscriber.addMessageListener(new MessageListener<LaserScan>() {
            @Override
            public void onNewMessage(LaserScan scan) {
                try {
                    System.out.println("received : scan");
                    rosMessageTranslator.logScan(scan);
                    messagesCount.countUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, queueSize);

        distSubscriber = connectedNode.newSubscriber("dist", Vector3Stamped._TYPE);
        distSubscriber.addSubscriberListener(RosMain.getSubscriberListener());
        distSubscriber.addMessageListener(new MessageListener<Vector3Stamped>() {
            @Override
            public void onNewMessage(Vector3Stamped dist) {
                try {
                    System.out.println("received : dist");
                    rosMessageTranslator.logDist(dist);
                    messagesCount.countUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, queueSize);

        System.out.println("initialized recorder");
    }

    public void awaitForMessages(int count) throws InterruptedException {
        messagesCount.awaitFor(count);
    }

    public static void main(String[] args) {

        URI masteruri = URI.create("http://127.0.0.1:11311");
        String host = "127.0.0.1";

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(host, masteruri);
        NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();

        RosLogRecorderNode subscriber = new RosLogRecorderNode("log.csv", 10);
        nodeMainExecutor.execute(subscriber, nodeConfiguration);

    }

}
