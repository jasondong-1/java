package com.jason.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LineNumberReaderTest {
    public static void main(String[] args) throws IOException {
        try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream("pom.xml")))) {
            String line;

            while ((line = reader.readLine()) != null) {
                reader.skip(Long.MAX_VALUE);
                System.out.println(reader.getLineNumber() + "  " + line);
            }
        }
    }
}
