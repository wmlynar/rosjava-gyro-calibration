package com.github.wmlynar.rosjava.logging;

import org.junit.Test;

import com.github.wmlynar.rosjava.log.reading.RosMessageReader;

public class RosMessageReaderTest {

    @Test
    public void test() {
        RosMessageReader reader = new RosMessageReader(
                "src/main/resources/log_neato_scan_odom_dist_rotating_only_close.csv");

        String type = reader.getNextMessageType();
    }

}
