package com.jason.example;

public class ThreadGroupExample {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("groupj");
        for(int i =0;i<=10;i++){
            Thread thread = new Thread(group,new Taskx());
            thread.start();
        }
        //返回当前group中 active thread 的估计值
        System.out.println(group.activeCount());
        //统计active groups（包括子group）数量
        System.out.println(group.activeGroupCount());
        //销毁group记忆子group，前提是group中所有thread都停了
        group.destroy();
        //将当前group及其子group中的active线程复制过来
        group.enumerate(new Thread[10]);
        //interrupt group 中的所有thread
        group.interrupt();
        //给group中的thread设置最大优先级，grooup中高于当前优先级的线程不受影响
        group.setMaxPriority(Thread.NORM_PRIORITY);
    }
}
