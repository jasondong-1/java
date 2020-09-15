package com.jason.core;

/**
 * 用来检测类中的变量何时被初始化
 */
public class ClassInit extends Fxx{

    private int i = 2;
    private String s;

    private static String s4 = getS();
    private String s3 = getS();
    protected String name = "ClassInit";
    public static String s2 = "bb";
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
        /*System.out.println(ClassInit.class.hashCode());
        System.out.println("======================");
        System.out.println(Demo.class.hashCode());
        System.out.println(Demo.i);
        System.out.println(ClassInit.s2);
        System.out.println("======================");*/
        ClassInit ci = new ClassInit("ss");
        System.out.println("=============" + ci.s2);
        //System.out.println(ci.getName());

    }

    static class Demo {
        static int i = getI();

        private static int getI() {
            System.out.println("i");
            return 3;
        }
    }

}
class Fxx{
    protected String name = "Fxx";
    public Fxx() {
        System.out.println("fxx");
    }

    public String getName() {
        return name;
    }
}