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

package com.github.wmlynar.rosjava.logging;

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

import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

/**
 * A simple {@link Subscriber} {@link NodeMain}.
 */
public class RosMessagesLoggerNode extends AbstractNodeMain {

	private Subscriber<nav_msgs.Odometry> odomSubscriber;
	private Subscriber<sensor_msgs.LaserScan> scanSubscriber;
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("ros_logger");
	}

	@Override
	public void onStart(ConnectedNode connectedNode) {
		
		odomSubscriber = connectedNode.newSubscriber("odom", nav_msgs.Odometry._TYPE);
		odomSubscriber.addMessageListener(new MessageListener<Odometry>() {
			@Override
			public void onNewMessage(Odometry message) {
				try {
					//onOdomMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		scanSubscriber = connectedNode.newSubscriber("base_scan", sensor_msgs.LaserScan._TYPE);
		scanSubscriber.addMessageListener(new MessageListener<LaserScan>() {
			@Override
			public void onNewMessage(LaserScan message) {
				try {
					//onScanMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		
		URI masteruri = URI.create("http://127.0.0.1:11311");
		String host = "127.0.0.1";

		NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(host, masteruri);
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
		
		RosMessagesLoggerNode subscriber = new RosMessagesLoggerNode();
		nodeMainExecutor.execute(subscriber, nodeConfiguration);

	}
}

