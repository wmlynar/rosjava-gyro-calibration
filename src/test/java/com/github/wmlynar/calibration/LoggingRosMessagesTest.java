package com.github.wmlynar.calibration;

import org.junit.Before;
import org.junit.Test;
import org.ros.internal.message.DefaultMessageFactory;
import org.ros.internal.message.definition.MessageDefinitionReflectionProvider;
import org.ros.message.MessageDefinitionProvider;
import org.ros.message.MessageFactory;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeMainExecutor;

public class LoggingRosMessagesTest {

	private MessageDefinitionProvider messageDefinitionProvider;
	private MessageFactory messageFactory;

	@Before
	public void before() {
		messageDefinitionProvider = new MessageDefinitionReflectionProvider();
		messageFactory = new DefaultMessageFactory(messageDefinitionProvider);
	}

	@Test
	public void test() {
		Time time = new Time();
		long t = time.totalNsecs();

//		sensor_msgs.LaserScan scan = messageFactory.newFromType(sensor_msgs.LaserScan._TYPE);
//		nav_msgs.Odometry odom = messageFactory.newFromType(nav_msgs.Odometry._TYPE);
//		geometry_msgs.Vector3Stamped distances = messageFactory.newFromType(geometry_msgs.Vector3Stamped._TYPE);
//		long ttt = scan.getHeader().getStamp().totalNsecs();
		
		RosMessageFactory factory = new RosMessageFactory();
		factory.newDistancesMessage();
		
		
		
		int i=0;
		
//		RosCore.newPrivate().
		
//		sensor_msgs.LaserScan scan = new 
//		logger.
	}

}
