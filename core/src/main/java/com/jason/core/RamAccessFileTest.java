package com.jason.core;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RamAccessFileTest {
    public RafInfo test1(long point) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile("java.iml", "r");
            raf.seek(point);
            String line = raf.readLine();
            if (line != null) {
                System.out.println(line);
            }
            long pos = raf.getFilePointer();
            return new RafInfo(pos, line);
        } finally {
            raf.close();
        }
    }

    public static void test2() throws IOException {
        long pos = 0;
        String line;
        do {
            RafInfo rafInfo = new RamAccessFileTest().test1(pos);
            line = rafInfo.getLine();
            pos = rafInfo.getPos();
        } while (line != null);
    }

    //对于raf#readline的验证
    public static void test3() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile("aa", "r")) {
            System.out.println(raf.readLine());
            raf.seek(0l);
            byte[] bytes = new byte[20];
            System.out.println(raf.read(bytes));
        }
    }

    public static void main(String[] args) throws IOException {
        test3();
    }

    class RafInfo {
        private long pos;
        private String line;

        public RafInfo(long pos, String line) {
            this.pos = pos;
            this.line = line;
        }

        public long getPos() {
            return pos;
        }

        public String getLine() {
            return line;
        }
    }
}
