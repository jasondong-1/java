package com.jason.core;

import org.json.JSONObject;

import java.util.*;

public class JsonObjTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(3);
        list.add("puma");
        list.add("are");
        Map<String, String> map = new HashMap<>(10, 0.8f);
        map.put("a", "b");
        map.put("c", "d");
        JSONObject jo = new JSONObject(map);
        String jsonString = jo.toString();
        System.out.println(jsonString);
        JSONObject jo2 = new JSONObject(jsonString);
        System.out.println(jo2);
        System.out.println(jo.getString("a"));
        new com.alibaba.fastjson.JSONObject();

        System.out.println("-----------------------------");

        String s = "{\"status\":true,\"code\":200,\"message\":\"dataSource\",\"data\":{\"deployCode\":\"b2682ad2-0b20-4019-b0b3-02cb74696c5e\",\"connectType\":\"ssh\",\"passwd\":\"mepo123\",\"port\":\"22\",\"ip\":\"10.4.66.96\",\"remark\":null,\"connectName\":\"meepo96\",\"userName\":\"meepo\",\"encoding\":\"\"}}";
        JSONObject jo3 = new JSONObject(s);
        System.out.println(jo3.getJSONObject("data").getString("userName"));

    }

}

