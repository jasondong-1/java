package com.jason.core;

public class InnerClass {
    private int outa;
    private static int outb;

    /**
     * 1.不可以创建静态成员
     * 2.可以访问外部类的静态和非静态成员
     */

    class AA {
        private int ina;

        public void funcAA() {
            System.out.println(outa);
            System.out.println(outb);
        }
    }

    /**
     * 1.可以创建静态成员
     * 2.只能访问外部类的静态成员
     */
    static class BB {
        private int inb;
        private static int inb2;

        public void funcB1() {
            System.out.println(outb);
        }
    }

    public static void main(String[] args) {
        //两种内部类的创建方式差异
        InnerClass outter = new InnerClass();
        AA aa = outter.new AA();
        AA aa2 = outter.new AA();
        BB bb = new InnerClass.BB();
        BB bb2 = new InnerClass.BB();


        System.out.println(aa == aa2);
        System.out.println(bb == bb2);
        System.out.println(BB.inb2);
    }
}
