package com.github.wmlynar.rosjava.n.models.axlewidth;

import org.ros.internal.node.CountDownRegistrantListener;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import com.github.wmlynar.rosjava.logging.TimeSequencer;
import com.github.wmlynar.rosjava.utils.CountUpSubscriberListener;
import com.github.wmlynar.rosjava.utils.CountUpLatch;
import com.github.wmlynar.rosjava.utils.RosMain;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.Imu;
import sensor_msgs.LaserScan;

public class RosSubscriberNode extends AbstractNodeMain {

	private int queueSize;
	
	private String odomTopicName;
    private Subscriber<Odometry> odomSubscriber;
	private MessageListener<Odometry> odomMessageListaner;
	
	private String distTopicName;
    private Subscriber<Vector3Stamped> distSubscriber;
	private MessageListener<Vector3Stamped> distMessageListaner;
    
	private CountUpLatch registeredCount = new CountUpLatch(0);
	private CountUpLatch connectedCount = new CountUpLatch(0);
	private CountUpSubscriberListener subscriberListener = new CountUpSubscriberListener(registeredCount, connectedCount);
	
    public RosSubscriberNode(int queueSize) {
    	this.queueSize = queueSize;
	}

	@Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ros_sim_plotter");
    }
    
	public void setOdomTopic(String name) {
		this.odomTopicName = name;
	}
	
	public void addOdomMessageListener(MessageListener<Odometry> messageListener) {
		this.odomMessageListaner = messageListener;
	}

	public void setDistTopic(String name) {
		this.distTopicName = name;
	}

	public void addDistMessageListener(MessageListener<Vector3Stamped> messageListener) {
		this.distMessageListaner = messageListener;
	}

    @Override
    public void onStart(ConnectedNode connectedNode) {
    	if(odomMessageListaner!=null) {
            odomSubscriber = connectedNode.newSubscriber(odomTopicName, Odometry._TYPE);
            odomSubscriber.addSubscriberListener(subscriberListener);
            odomSubscriber.addMessageListener(odomMessageListaner, queueSize);
    	}

        distSubscriber = connectedNode.newSubscriber(distTopicName, Vector3Stamped._TYPE);
        distSubscriber.addSubscriberListener(subscriberListener);
        distSubscriber.addMessageListener(distMessageListaner, queueSize);
    }


	public void awaitForConnections(int number) {
		try {
			connectedCount.awaitFor(number);
		} catch (InterruptedException e) {
		}
	}


}
