package com.github.wmlynar.rosjava.n.models.axlewidth;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;

public class RosPublisherNode extends AbstractNodeMain {

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ros_sim_player");
    }

	public void setOdomTopic(String string) {
	}

	public void setDistTopic(String string) {
	}
	
	public void publishOdom(double x, double y, double speed, double angle, double rotation) {
	}

	public void publishDist(double distanceLeft, double distanceRight) {
	}

	public void awaitForConnections(int i) {
	}


}
