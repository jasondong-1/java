package com.jason.examples;


import com.mysql.jdbc.TimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 网上关于隔离级别的文章很多，隔离级别的概念请百度，
 * 这里测试隔离级别应该用在写数据阶段还是查询数据阶段
 */
public class IsolationLevelTest {
    private JDBCExample jdbcExample = new JDBCExample("jdbc:mysql://localhost:3306/databus?useUnicode=true&characterEncoding=utf-8", "root", "879892206");
    private CountDownLatch cdl = new CountDownLatch(1);
    private AtomicBoolean stop = new AtomicBoolean(false);
    private int writeLevel = Connection.TRANSACTION_NONE;
    private int readLevel = Connection.TRANSACTION_READ_COMMITTED;

    public void loopRead() throws InterruptedException {
        cdl.await();
        while (!stop.get()) {
            read();
            Thread.sleep(500);
        }
        Thread.sleep(3000);
        read();
    }

    public void read() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            int i = 0;
            conn = jdbcExample.getConn();
            //conn.setAutoCommit(false);
            if (readLevel != Connection.TRANSACTION_NONE) {
                conn.setTransactionIsolation(readLevel);
            }

            //conn.setTransactionIsolation(Connection.TRANSACTION_NONE);
            ps = conn.prepareStatement("select * from test");
            System.out.println("开始读取");
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("读取结果：" + rs.getString(1));
            }
            //conn.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void write() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = jdbcExample.getConn();
            conn.setAutoCommit(false);
            if (writeLevel != Connection.TRANSACTION_NONE){
                conn.setTransactionIsolation(writeLevel);
            }

            //conn.setTransactionIsolation(Connection.TRANSACTION_NONE);
            ps = conn.prepareStatement("INSERT INTO test (id) VALUES (?)");
            ps.setInt(1, 200);
            int res = ps.executeUpdate();
            System.out.println(res);
            cdl.countDown();
            System.out.println("countdown " + cdl.getCount());
            Thread.sleep(3000);
            conn.commit();
            stop.compareAndSet(false, true);
            System.out.println("commit 完成");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final IsolationLevelTest i = new IsolationLevelTest();
        Thread read = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    i.loopRead();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread write = new Thread(new Runnable() {
            @Override
            public void run() {
                i.write();
            }
        });
        write.start();
        read.start();
    }
}
