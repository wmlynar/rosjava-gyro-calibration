package com.github.wmlynar.rosjava.n.models.axlewidth;

import org.ros.message.MessageListener;

import com.github.wmlynar.ekf.utils.Plots;
import com.github.wmlynar.rosjava.utils.RosMain;

import nav_msgs.Odometry;

public class MainSimPlayerNode {
	
	public static void main(String[] args) {
        SequencerFilter sequencerFilter = new SequencerFilter(); 
        PlotFilter plotFilter = new PlotFilter(); 
        CsVLoggingFilter csvLoggingFilter = new CsVLoggingFilter(); 
        
        RosSubscriberNode subscriberNode = new RosSubscriberNode(10);
        subscriberNode.setOdomTopic("odom");
        subscriberNode.setDistTopic("dist");
//        subscriberNode.addOdomMessageListener(
//        		sequencerFilter.getMessageListaner(
//        				plotFilter.getOdomMessageListener()));
//        subscriberNode.addDistMessageListener(
//        		sequencerFilter.getMessageListaner(
//        				plotFilter.getDistMessageListener()));

        
        
        RosPublisherNode publisherNode = new RosPublisherNode();
        publisherNode.setOdomTopic("odom");
        publisherNode.setDistTopic("dist");

        RosMain.connectToRosCoreWithoutEnvironmentVariables();
        RosMain.executeNode(publisherNode);
        RosMain.executeNode(subscriberNode);
        
        publisherNode.awaitForConnections(2);
        subscriberNode.awaitForConnections(2);
        
		AxleWidthSimulator simulator = new AxleWidthSimulator();
		simulator.setAxleWidth(0.25);
        simulator.setRotationNoise(.1);
        simulator.setSpeed(1);
        simulator.setAccelerationNoise(1);
        simulator.setTimeStep(0.1);
        
        for (double d = 0; d < 100; d += 1) {
            simulator.simulate(d);
            
            Plots.plotXy("position", "sim", simulator.getX(), simulator.getY());
            
            publisherNode.publishOdom(simulator.getX(), simulator.getY(), simulator.getSpeed(), simulator.getAngle(), simulator.getRotation());
            publisherNode.publishDist(simulator.getDistanceLeft(), simulator.getDistanceRight());
        }
	}

}
