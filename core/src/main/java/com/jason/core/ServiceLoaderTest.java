package com.jason.core;

import java.net.URL;
import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderTest {
    public ServiceLoaderTest() {
    }

    public <T> void pp(Class<T> clazz) {
        ServiceLoader<T> sl = ServiceLoader.load(clazz);
        Iterator<T> it = sl.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    /**
     * 需要创建META-INF/services
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        ServiceLoaderTest st = new ServiceLoaderTest();
        //st.pp(Driver.class);
        st.pp(ServiceLoaderTest.class);
        st.getClass().getResource("").getFile();
        Class<Driver> cls = Driver.class;
        //这句可以查找加载的类的jar包
        URL urlx = cls.getResource("/" + cls.getName().replace('.', '/') + ".class");
        System.out.println(urlx);
        URL url = cls.getResource("/");
        System.out.println(url);
        System.out.println(Class.forName("com.mysql.jdbc.Driver").getResource(""));
        System.out.println(ServiceLoaderTest.class.getClassLoader().getResource(""));
    }
}

