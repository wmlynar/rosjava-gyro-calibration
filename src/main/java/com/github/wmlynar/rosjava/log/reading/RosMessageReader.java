package com.github.wmlynar.rosjava.log.reading;

import java.util.ArrayList;

import org.ros.message.Duration;
import org.ros.message.Time;
import org.ros.node.ConnectedNode;

import geometry_msgs.Vector3Stamped;
import nav_msgs.Odometry;
import sensor_msgs.LaserScan;

public class RosMessageReader {

    public static final String[] END = new String[] { "end" };

    private CsvLogReader reader;
    private ArrayList<String[]> messages = new ArrayList<>();
    private long startTime = -1;
    private int messagesIndex = 0;
    private String[] line;

    private Duration shift;

    private ConnectedNode node;

    public RosMessageReader(String name, ConnectedNode node) {
        reader = new CsvLogReader(name);
        readLog();
        this.node = node;
    }

    public String getNextMessageType() {
        // if (line != null) {
        // return line[0];
        // }
        if (messagesIndex < messages.size()) {
            line = messages.get(messagesIndex++);
            if (startTime == -1) {
                startTime = Long.parseLong(line[1]);
            }
        } else {
            line = END;
        }
        return line[0];
    }

    public Odometry getNextOdomMessage() {
        Odometry m = RosMessageFactory.newOdomMessage(line);
        initializeTime(m.getHeader().getStamp());
        m.getHeader().setStamp(m.getHeader().getStamp().add(shift));
        return m;
    }

    public LaserScan getNextScanMessage() {
        LaserScan m = RosMessageFactory.newScanMessage(line);
        initializeTime(m.getHeader().getStamp());
        m.getHeader().setStamp(m.getHeader().getStamp().add(shift));
        return m;
    }

    public Vector3Stamped getNextDistMessage() {
        Vector3Stamped m = RosMessageFactory.newDistMessage(line);
        initializeTime(m.getHeader().getStamp());
        m.getHeader().setStamp(m.getHeader().getStamp().add(shift));
        return m;
    }

    private void readLog() {
        while (reader.hasNext()) {
            String[] line = reader.readLine();
            if (line == null) {
                return;
            }
            messages.add(line);
        }
    }

    public int getNumberOfMessages() {
        return messages.size();
    }

    // private void addLine(String[] line) {
    // ArrayList<String[]> lineList = messages.get(line[0]);
    // if (lineList == null) {
    // lineList = new ArrayList<>();
    // messages.put(line[0], lineList);
    // }
    // lineList.add(line);
    // }

    public void initializeTime(Time time) {
        if (this.shift != null) {
            return;
        }
        this.shift = node.getCurrentTime().subtract(time);
    }
}
