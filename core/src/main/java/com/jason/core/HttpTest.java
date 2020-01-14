package com.jason.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class HttpTest {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("需要传入deployCode");
            System.exit(1);
        }
        //b2682ad2-0b20-4019-b0b3-02cb74696c5e
        String deployCode = args[0];
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://10.4.71.75:9020/pdss/data_source/get_source_by_deploy_code";
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("clientId", "aa");
        jsonObject.put("clientSecret", "bb");
        jsonObject.put("deployCode", deployCode);
        String requestJson = jsonObject.toJSONString();
        System.out.println(requestJson);
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        String result = restTemplate.postForObject(url, entity, String.class);
        System.out.println(result);
        PropertyConfigurator.configure(System.getProperty("user.dir") + "/conf/log4j.properties");
    }
}
