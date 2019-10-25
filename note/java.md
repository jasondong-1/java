### 静态类  
当在一个类中创建类时才能使用静态内部类，其他情况无法创建静态类  
静态内部类和非静态内部类的区别：  
* 1.静态内部类可以创建静态属性和方法，费静态内部类则不可以  
* 2.静态内部类只能访问外部类的静态成员，非静态内部类则可以访问外部类的静态和费静态成员、属性  
* 3.创建内部类的区别  
```
        //非静态内部类的创建
        InnerClass outter = new InnerClass();
        AA aa = outter.new AA();
        AA aa2 = outter.new AA();
        //静态内部类的创建
        BB bb = new InnerClass.BB();
        BB bb2 = new InnerClass.BB();
``` 

### log4j.properties  
[请看这里](https://blog.csdn.net/cen_s/article/details/78426682)
  
###  SerialversionUID作用  
[请看这里](https://blog.csdn.net/java_mdzy/article/details/78354959)  

### try-catch  
```
public static void test1() {
        try {
            Integer.valueOf("kk");
        } catch (Exception e) {
            throw e;
        }
        System.out.println("test1");
    }

    public static void test2() {
        try {
            Integer.valueOf("kk");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("test2");
    }
    public static void main(String[] args) {
        test1();
        test2();
    }
``` 
test1 中如果throw exception 则System.out.println("test1"); 不会执行，throw 类似return，且test2（） 也不执行  
但是如果e.printstacktrace()则就会继续向下执行  

###  类加载时会初始化静态变量吗  
静态内部类：  
 > 类加载时不会初始化静态成员变量，只有第一次访问变量时，静态成员变量才会被初始化，即延迟加载  

非绝胎内部类：  
 > 类加载的时候就会初始化静态成员变量  
 
请看示例：  
```java
package com.jason.core;

/**
 * 用来检测类中的变量何时被初始化
 */
public class ClassInit {

    private int i = 2;
    private String s;
    private static String s2;
    private static String s4 = getS();
    private String s3 = getS();

    static {
        System.out.println("static");
        s2 = "aa";
    }


    public ClassInit(String s) {
        System.out.println("hello");
        this.s = s;
    }

    private static String getS() {
        System.out.println(ClassInit.s2);
        System.out.println("hi");
        return "jj";
    }

    public static void main(String[] args) {
        //加载类
        System.out.println(ClassInit.class.hashCode());
        System.out.println("======================");
        System.out.println(Demo.class.hashCode());
        System.out.println(Demo.i);
        /*System.out.println(ClassInit.s2);
        new ClassInit("ss");*/
    }

    static class Demo {
        static int i = getI();

        private static int getI() {
            System.out.println("i");
            return 3;
        }
    }

}

```
输出结果为：  
```
null
hi
static
21685669
======================
2133927002
i
3
```

### log4j  + slf4j  
添加依赖  
```
<dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.2</version>
        </dependency>
``` 
log4j.properties 的配置请参考[这里](https://pan.baidu.com/share/link?shareid=2985093647&uk=305605848)  

### logback的使用  
1.依赖  logback-classic logback-core slf4j-api  
```
dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-core -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-core</artifactId>
        <version>1.2.3</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.7</version>
    </dependency>
```
2. 配置文件  
```xml
<configuration debug="true">

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are  by default assigned the type
             ch.qos.logback.classic.encoder.PatternLayoutEncoder -->
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```
3.<configuration debug="true">  
debug="true" 属性的设置可以打印status data  

4.<configuration debug="false" scan="true" scanPeriod="30 seconds">  自动reload  
scan="true" scanPeriod="30 seconds"，设置自动扫描为true，扫描间隔30秒，默认1分  
 
5.当抛出异常时候，打印jar 的相关信息    
```xml
<configuration packagingData="true">
  ...
</configuration>
```
  
### java.lang.NoClassDefFoundError和ClassNotFoundException  
ClassNotFoundException：在程序编译时会遇到  
java.lang.NoClassDefFoundError：在程序运行时会遇到  

原因多为在classpath中未找到相关类，或者不具备rx权限  


### arraylist foreach 遍历并删除 报错  
[看这里](https://www.cnblogs.com/huangjinyong/p/9455163.html)

