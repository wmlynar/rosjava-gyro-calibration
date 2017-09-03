package com.github.wmlynar.rosjava.log.reading;

import java.util.ArrayList;

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

    public RosMessageReader(String name) {
        reader = new CsvLogReader(name);
        readLog();
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
        return RosMessageFactory.newOdomMessage(line);
    }

    public LaserScan getNextScanMessage() {
        return RosMessageFactory.newScanMessage(line);
    }

    public Vector3Stamped getNextDistMessage() {
        return RosMessageFactory.newDistMessage(line);
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
}
