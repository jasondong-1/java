package com.jason.note.java;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JavaLinuxInteraction {
    public static void read(BufferedReader reader) throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void read(Scanner reader) {
        while (reader.hasNextLine()) {
            System.out.println(reader.nextLine());
        }
    }

    public static void test() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        Map<String, String> map = builder.environment();
        map.put("JAVA_HOME", "/usr/local/jdk/jdk1.8.0_131");
        map.entrySet().iterator();
        for (Iterator<Map.Entry<String, String>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            // System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        List<String> commands = new ArrayList<>(5);

        commands.add("/usr/local/spark/spark-2.4.4-bin-hadoop2.7/bin/spark-shell");
        //commands.add("/home/jason/aa.sh");
        builder.command(commands);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final Scanner reader = new Scanner(reader2);
        PrintWriter writer = new PrintWriter(process.getOutputStream(), true);

        for (int i = 0; i < 5; i++) {
            writer.println(String.format("val a = %s", i));
        }

        String line;
        int i = -1;
        /*while((line = reader.readLine())!=null){
            reader.read()
        }*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (reader.hasNextLine()) {
                    System.out.println(reader.nextLine());
                }
            }
        });
        thread.start();


        /*while ((i = reader.read()) != -1) {
            System.out.println(i);
        }*/
        System.out.println("=====================");
        Thread.sleep(10000);
        process.destroy();
        reader.close();
        writer.close();
    }

    public static void test2() throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("ls", "/home/jason");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        Scanner scanner = new Scanner(process.getInputStream());
        PrintWriter pw = new PrintWriter(process.getOutputStream());
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        pw.close();
        scanner.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        test();
    }
}
