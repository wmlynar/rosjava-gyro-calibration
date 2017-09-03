package com.github.wmlynar.rosjava.utils;

import org.junit.Test;

public class CountUpLatchTest {

    @Test
    public void test() throws InterruptedException {

        CountUpLatch latch = new CountUpLatch(0);
        latch.countUp();
        latch.countUp();
        latch.awaitFor(2);
        System.out.println(latch.getCount());

    }

}
