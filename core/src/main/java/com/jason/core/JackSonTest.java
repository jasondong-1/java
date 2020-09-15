package com.jason.core;

import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JackSonTest {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //3.调用mapper的writeValueAsString()方法把一个对象或集合转为json字符串
        List<String> list = new ArrayList<>(3);
        list.add("kungfu");
        list.add("taiji");
        list.add("tkd");
        CustomerBean customer = new CustomerBean(null, 100, list);
        String jsonStr = mapper.writeValueAsString(customer);
        System.out.println("===1=== " + jsonStr);
        JSONObject jo = net.sf.json.JSONObject.fromObject(jsonStr);
        System.out.println("===2=== " + jo);
        CustomerBean customer2 = mapper.readValue(jsonStr, CustomerBean.class);
        System.out.println("===3=== " + customer2);
    }

}

class CustomerBean {
    private String name;
    private int age;
    private List<String> hobby;

    public CustomerBean(String name, int age, List<String> hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public CustomerBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hobby=" + hobby +
                '}';
    }
}