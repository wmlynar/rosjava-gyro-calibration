package com.github.wmlynar.rosjava.filtering;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LaserBeaconTrackerTest {

    @Test
    public void test() {
        LaserBeaconTracker t = new LaserBeaconTracker(0f, 10f, 5, 10);
        t.processScan(0, new float[] { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        assertEquals(0, t.getAngle());
        assertEquals(2, t.getDistance(), 1e-6);
        t.processScan(0, new float[] { 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        assertEquals(0, t.getAngle());
        assertEquals(2, t.getDistance(), 1e-6);
        t.processScan(0, new float[] { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
        assertEquals(19, t.getAngle());
        assertEquals(2, t.getDistance(), 1e-6);
        t.processScan(0, new float[] { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
        assertEquals(4, t.getAngle());
        assertEquals(1, t.getDistance(), 1e-6);
    }

}
