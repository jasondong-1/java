package com.jason.example;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;

public class PipeInOut {
    public static void main(String[] args) throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        Thread thread = new Thread(new PipeRunnable(out));
        thread.start();
        byte[] bytes = new byte[1024];
        int len = -1;
        while ((len = in.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, len, "utf-8"));
        }
        in.close();
    }
}

class PipeRunnable implements Runnable {
    private PipedOutputStream out;

    public PipeRunnable(PipedOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            String s = new String("jason" + i);
            try {
                out.write(s.getBytes(Charset.forName("utf-8")));
                out.flush();
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}