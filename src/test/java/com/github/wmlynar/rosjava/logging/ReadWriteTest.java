package com.github.wmlynar.rosjava.logging;

import org.junit.Test;

import com.github.wmlynar.rosjava.log.reading.RosLogPlayerNodeNode;
import com.github.wmlynar.rosjava.log.writing.RosLogRecorderNode;
import com.github.wmlynar.rosjava.utils.RosMain;

public class ReadWriteTest {

    @Test
    public void test() {
        // read csv file
        // publish ros messages
        // receive ros messages
        // write csv file
        // compare

        RosMain.startAndConnectToRosCoreWithoutEnvironmentVariables();
        RosLogPlayerNodeNode player = new RosLogPlayerNodeNode("src/test/resources/logging/all_messages_source.csv");
        RosLogRecorderNode recorder = new RosLogRecorderNode("src/test/resources/logging/all_messages_destination.csv",
                10);

        RosMain.executeNode(player);
        RosMain.executeNode(recorder);

        try {
            RosMain.awaitForConnections(6);
            player.start();
            recorder.awaitForMessages(player.getNumberOfMessages());
        } catch (InterruptedException e) {
        }

    }

}
