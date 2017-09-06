package com.github.wmlynar.rosjava.n.models.axlewidth;

import com.github.wmlynar.rosjava.utils.RosMain;

public class MainSimPlotterNode {
	
	public static void main(String[] args) {

        RosSubscriberNode subscriberNode = new RosSubscriberNode(10);
        PlotFilter plotFilter = new PlotFilter(); 
        
        subscriberNode.setOdomTopic("odom");
        subscriberNode.setDistTopic("dist");
        
        subscriberNode.addOdomMessageListener(plotFilter.getOdomMessageListener());
        subscriberNode.addDistMessageListener(plotFilter.getDistMessageListener());
        
        RosMain.connectToRosCoreWithoutEnvironmentVariables();
        RosMain.executeNode(subscriberNode);
        
        
//      subscriberNode.addOdomMessageListener(new MessageListener<Odometry>() {
//      @Override
//      public void onNewMessage(Odometry m) {
//      	
//      }
//  });


        
	}

}
