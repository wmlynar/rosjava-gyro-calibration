package com.github.wmlynar.rosjava.logging;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class CsvLogWriter {

	private static final String DEFAULT_NAME = "log.csv";
	private static FileWriter fileWriter = null;
	private static CSVWriter writer = null;

	public static synchronized void initialize(String name) {
		try {
			CsvLogWriter.fileWriter = new FileWriter(name);
			CsvLogWriter.writer = new CSVWriter(CsvLogWriter.fileWriter, ',');
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized void log(String key, long time, int... values) {
		CsvLogWriter.checkInitialized();
		String strings[] = new String[values.length + 2];
		strings[0] = key;
		strings[1] = Long.toString(time);
		for (int i = 0; i < values.length; i++) {
			strings[i + 2] = Integer.toString(values[i]);
		}
		CsvLogWriter.write(strings);
	}

	public static synchronized void log(String key, long time, float... values) {
		CsvLogWriter.checkInitialized();
		String strings[] = new String[values.length + 2];
		strings[0] = key;
		strings[1] = Long.toString(time);
		for (int i = 0; i < values.length; i++) {
			strings[i + 2] = Float.toString(values[i]);
		}
		CsvLogWriter.write(strings);
	}

	public static synchronized void log(String key, long time, double... values) {
		CsvLogWriter.checkInitialized();
		String strings[] = new String[values.length + 2];
		strings[0] = key;
		strings[1] = Long.toString(time);
		for (int i = 0; i < values.length; i++) {
			strings[i + 2] = Double.toString(values[i]);
		}
		CsvLogWriter.write(strings);
	}

	private static synchronized void write(String strings[]) {
		CsvLogWriter.writer.writeNext(strings);
		try {
			CsvLogWriter.writer.flush();
			CsvLogWriter.fileWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void checkInitialized() {
		if (CsvLogWriter.fileWriter == null) {
			CsvLogWriter.initialize(CsvLogWriter.DEFAULT_NAME);
		}
	}

}
