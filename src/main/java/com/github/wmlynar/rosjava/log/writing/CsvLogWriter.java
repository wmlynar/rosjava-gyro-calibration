package com.github.wmlynar.rosjava.log.writing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.opencsv.CSVWriter;

public class CsvLogWriter {

    private static final String DEFAULT_NAME = "log.csv";
    private static HashMap<String, FileWriter> fileWriterMap = new HashMap<String, FileWriter>();
    private static HashMap<String, CSVWriter> csvWriterMap = new HashMap<String, CSVWriter>();

    public static synchronized void log(String logName, String key, long time, int... values) {
        String strings[] = new String[values.length + 2];
        strings[0] = key;
        strings[1] = Long.toString(time);
        for (int i = 0; i < values.length; i++) {
            strings[i + 2] = Integer.toString(values[i]);
        }
        write(logName, strings);
    }

    public static synchronized void log(String logName, String key, long time, float... values) {
        String strings[] = new String[values.length + 2];
        strings[0] = key;
        strings[1] = Long.toString(time);
        for (int i = 0; i < values.length; i++) {
            strings[i + 2] = Float.toString(values[i]);
        }
        write(logName, strings);
    }

    public static synchronized void log(String logName, String key, long time, double... values) {
        String strings[] = new String[values.length + 2];
        strings[0] = key;
        strings[1] = Long.toString(time);
        for (int i = 0; i < values.length; i++) {
            strings[i + 2] = Double.toString(values[i]);
        }
        write(logName, strings);
    }

    private static synchronized void write(String logName, String strings[]) {
        CSVWriter writer = getCsvWriter(logName);
        writer.writeNext(strings);
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static FileWriter getFileWriter(String name) throws IOException {
        FileWriter w = fileWriterMap.get(name);
        if (w == null) {
            w = new FileWriter(name);
            fileWriterMap.put(name, w);
        }
        return w;
    }

    private static CSVWriter getCsvWriter(String name) {
        CSVWriter w = csvWriterMap.get(name);
        if (w == null) {
            try {
                w = new CSVWriter(getFileWriter(name));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            csvWriterMap.put(name, w);
        }
        return w;
    }
}
