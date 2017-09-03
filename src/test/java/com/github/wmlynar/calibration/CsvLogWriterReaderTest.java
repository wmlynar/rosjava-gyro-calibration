package com.github.wmlynar.calibration;

import org.junit.Assert;
import org.junit.Test;

import com.github.wmlynar.rosjava.log.reading.CsvLogReader;
import com.github.wmlynar.rosjava.log.writing.CsvLogWriter;

public class CsvLogWriterReaderTest {

    @Test
    public void test() {
        String name = "src/test/resources/testfile2.csv";
        CsvLogWriter.log(name, "key1", 0, 0.5, 0.6);

        CsvLogReader reader = new CsvLogReader(name);
        Assert.assertTrue(reader.hasNext());
        String[] strings = reader.readLine();
        Assert.assertArrayEquals(new String[] { "key1", "0", "0.5", "0.6" }, strings);
    }

    @Test
    public void test2() {
        CsvLogReader reader = new CsvLogReader("src/test/resources/logging/all_messages_source.csv");
        Assert.assertTrue(reader.hasNext());
        String[] strings = reader.readLine();
        Assert.assertEquals("scan", strings[0]);
        Assert.assertTrue(reader.hasNext());
        strings = reader.readLine();
        Assert.assertEquals("dist", strings[0]);
    }
}
