package com.github.wmlynar.rosjava.o.main;

import com.github.wmlynar.rosjava.o.nodes.csvplayer.NodeRosLogPlayer;
import com.github.wmlynar.rosjava.o.nodes.plotter.NodePlotter;
import com.github.wmlynar.rosjava.utils.RosMain;

public class MainPlayCsvPlot {
	
	public static void main(String[] args) {
        RosMain.connectToRosCoreWithoutEnvironmentVariables();

        NodeRosLogPlayer playerNode = new NodeRosLogPlayer("src/main/resources/logs/with_gyro/rotating.csv");
        NodePlotter plotterOdom = new NodePlotter("odom",10);
        RosMain.executeNode(playerNode);
        RosMain.executeNode(plotterOdom);
        playerNode.start();

		
	}

}
