package com.github.wmlynar.calibration;

import org.junit.Assert;
import org.junit.Test;
import org.ros.message.Time;

public class RosMessageFactoryTest {

    @Test
    public void test() {
        RosMessageFactory factory = new RosMessageFactory();
        Time t = factory.newTimeFromNsecs(new Time(1.1).totalNsecs());
        int secs = t.secs;
        int nsecs = t.nsecs;

        Assert.assertEquals(1, secs);
        Assert.assertEquals(100000000, nsecs);
    }

}
