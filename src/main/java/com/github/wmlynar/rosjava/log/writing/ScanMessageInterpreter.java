package com.github.wmlynar.rosjava.log.writing;

public class ScanMessageInterpreter {

    public double processScan(float[] ranges) {
        return 359.0 - Utils.getIndexOfNearest(ranges, 0.3f, 10, -1);
    }

}