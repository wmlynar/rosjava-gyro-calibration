package com.github.wmlynar.calibration;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class TestOpenCsv {

	public static void main(String[] args) {
		String name = "src/test/resources/file.csv";
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(name);
			CSVWriter writer = new CSVWriter(fileWriter, ',', '"');
			writer.writeNext(new String[] { "laser", Double.toString(10.232) });
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
