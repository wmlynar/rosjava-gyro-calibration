package com.github.wmlynar.calibration;

import org.junit.Assert;
import org.junit.Test;

public class CsvLogWriterReaderTest {

	@Test
	public void test() {
		String name = "src/test/resources/testfile.csv";
		CsvLogWriter.initialize(name);
		CsvLogWriter.log("key1", 0, 0.5, 0.6);

		CsvLogReader reader = new CsvLogReader(name);
		Assert.assertTrue(reader.hasNext());
		String[] strings = reader.readLine();
		Assert.assertArrayEquals(new String[] { "key1", "0", "0.5", "0.6" }, strings);
	}
}
