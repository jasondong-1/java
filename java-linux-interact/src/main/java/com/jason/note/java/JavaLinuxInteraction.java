package com.jason.note.java;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JavaLinuxInteraction {
    public static void test() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        Map<String, String> map = builder.environment();
        map.put("JJ", "3");
        map.entrySet().iterator();
        for (Iterator<Map.Entry<String, String>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            // System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        char[] chars = new char[]{97, 52, 32};
        System.out.println(new String(chars));
        char s = 32;
        System.out.println("\"" + s + "\"");
        List<String> commands = new ArrayList<>(5);
        commands.add("/usr/local/spark/spark-2.4.3-bin-hadoop2.7/bin/spark-shell");
        //commands.add("/home/jason/aa.sh");
        builder.command(commands);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final Scanner reader = new Scanner(reader2);
        PrintWriter writer = new PrintWriter(process.getOutputStream(), true);
        writer.println("val a = 3");
        writer.println("val a = 5");
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
