package com.jason.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.*;

public class JsonObjTest{

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<String> list = new ArrayList<String>(3);
        list.add("puma");
        list.add("are");
        Map<String, String> map = new HashMap<>(10, 0.8f);
        map.put("a", "b");
        map.put("c", "d");
        JSONObject jo = new JSONObject(map);
        jo.put("jj", map);
        String jsonString = jo.toString();
        System.out.println(jsonString);
        JSONObject jo2 = new JSONObject(jsonString);
        System.out.println(jo2);
        System.out.println(jo.getString("a"));
        new com.alibaba.fastjson.JSONObject();

        System.out.println("-----------------------------");

        String s = "{\"status\":true,\"dd\":{},\"code\":200,\"message\":\"dataSource\",\"data\":{\"deployCode\":\"b2682ad2-0b20-4019-b0b3-02cb74696c5e\",\"connectType\":\"ssh\",\"passwd\":\"mepo123\",\"port\":\"22\",\"ip\":\"10.4.66.96\",\"remark\":null,\"connectName\":\"meepo96\",\"userName\":\"meepo\",\"encoding\":\"\"}}";
        JSONObject jo3 = new JSONObject(s);
        System.out.println(jo3.getJSONObject("dd").isEmpty());
        System.out.println(jo3.getJSONObject("data"));
        System.out.println(System.currentTimeMillis());

        String s2 = "[{\"storageType\":\"mysql\",\"url\":\"jdbc:mysql://localhost:3306\",\"user\":\"root\",\"passwd\":\"879892206\",\"canvaName\":\"tst22222\",\"batchId\":\"12345\",\"db\":\"databus\",\"tbl\":\"app_aa\"}]";
        JSONArray ja = new JSONArray(s2);
        System.out.println(s2);
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jox = ja.getJSONObject(i);
            System.out.println(jox.toMap());
        }

        String lib = System.getProperty("java.library.path");
        lib = "mylibDir;" + lib;
        System.setProperty("java.library.path", lib);
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
        System.loadLibrary("mylib");
    }

}


