package com.jason.datahandle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataHandleUtil {
    /**
     * 获取招股价下限 限制
     *
     * @param s
     * @param index
     * @return
     */
    public static String getPrice(String s, int index) {
        if (s == null || s.trim().isEmpty()) {
            //throw  new IllegalArgumentException("不接受为null 或 为空的参数："+s);
            return null;
        }
        String[] arr = s.trim().split("-", -1);
        if (arr.length != 2) {
            //throw new IllegalArgumentException("参数不符合规定，无妨 按照 - 切成两个 ： "+ s);
            return null;
        }
        return arr[index];
    }

    public static void aastock() throws FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("/home/jason/Documents/港股打新/全年统计/aastock")));
             PrintWriter out = new PrintWriter("/home/jason/Documents/港股打新/全年统计/aastock分析")) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split("\t", -1);
                arr[2] = arr[2].replaceAll("\\.HK", "");
                List<String> list = new ArrayList<>(Arrays.asList(arr));
                List<String> resList = new ArrayList<>(10);
                list.remove(0);
                //股票代码
                resList.add(list.get(1));
                //股票名称
                resList.add(list.get(0));
                //招股价下限
                resList.add(getPrice(list.get(3), 0));
                //照顾家上线
                resList.add(getPrice(list.get(3), 1));
                //定价
                resList.add(list.get(12));
                //照顾股数
                resList.add(list.get(7));
                //一手股数
                resList.add(list.get(2));
                out.println(String.join("\t", resList));
                list = null;
                resList = null;
            }

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void livermore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("/home/jason/Documents/港股打新/全年统计/livermore")));
             PrintWriter out = new PrintWriter("/home/jason/Documents/港股打新/全年统计/livermore分析");
        ) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split("\t", -1);
                List<String> list = new ArrayList<>(10);
                //股票代码
                list.add(arr[1]);
                //股票名称
                list.add(arr[0]);
                //板块
                list.add(arr[7]);
                // 所属行业
                list.add(arr[6]);
                //上市时间
                list.add(arr[5]);
                //超狗倍数
                list.add(arr[11]);
                //一手中签率
                list.add(arr[4]);
                out.println(String.join("\t", list));
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //aastock();
        livermore();
    }
}
