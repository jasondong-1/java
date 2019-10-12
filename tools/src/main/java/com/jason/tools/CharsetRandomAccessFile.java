package com.jason.tools;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * RandomAccessFile 的readline方法如果读取中文会乱码
 */
public class CharsetRandomAccessFile extends RandomAccessFile {
    public CharsetRandomAccessFile(String name, String mode) throws FileNotFoundException {
        super(name, mode);
    }

    public CharsetRandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
    }

    /**
     * 倒着往上读
     *
     * @param charset
     * @return
     * @throws IOException
     */
    public String readliner(Charset charset) throws IOException {
        LinkedList<Byte> list = new LinkedList<>();
        boolean stop = false;
        int c = -1;
        while (!stop) {
            switch (c = read2()) {
                case '\n':
                    stop = true;
                    long point = getFilePointer();
                    if ((c = read2()) != '\r') {
                        seek(point);
                    }
                    break;
                case -1:
                case '\r':
                    stop = true;
                    break;
                default:
                    list.add(0, (byte) c);
                    break;
            }
        }

        if (c == -1 && list.size() == 0) {
            return null;
        }

        Byte[] bytes = list.toArray(new Byte[list.size()]);

        return new String(Bytes2bytes(bytes), charset);

    }

    public String readline(Charset charset) throws IOException {
        LinkedList<Byte> list = new LinkedList<>();
        int c = -1;
        boolean eol = false;

        while (!eol) {
            switch (c = read()) {
                case -1:
                case '\n':
                    eol = true;
                    break;
                case '\r':
                    eol = true;
                    long cur = getFilePointer();
                    if ((read()) != '\n') {
                        seek(cur);
                    }
                    break;
                default:
                    list.add((byte) c);
                    break;
            }
        }

        if ((c == -1) && (list.size() == 0)) {
            return null;
        }
        Byte[] bytes = list.toArray(new Byte[list.size()]);

        return new String(Bytes2bytes(bytes), charset);
    }

    private byte[] Bytes2bytes(Byte[] bytes) {
        byte[] bs = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bs[i] = bytes[i].byteValue();
        }
        return bs;
    }

    private int read2() throws IOException {
        long point = getFilePointer();
        long p2 = 0l;
        int i;
        if (0l != point) {
            seek(point - 1);
            p2 = point - 1;
            i = read();
        } else {
            i = -1;
        }
        seek(p2);
        return i;
    }

    public static void main(String[] args) throws IOException {
        try (
                RandomAccessFile raf = new RandomAccessFile("pom.xml", "r");
        ) {
            raf.seek(Long.MAX_VALUE);
        }
    }
}
