package com.jason.examples;

import java.sql.*;

public class JDBCExample {
    private String driver = "com.mysql.jdbc.Driver";
    private String url;
    private String userName;
    private String passwd;

    public JDBCExample(String url, String userName, String passwd) {
        this.url = url;
        this.userName = userName;
        this.passwd = passwd;
    }

    private Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, userName, passwd);
        return conn;
    }

    public void createTalbe(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = getConn();
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sql);
                int i = pst.executeUpdate();
                System.out.println("=============== " + i);
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }

        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void query(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = getConn();

            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                ResultSetMetaData meta = rs.getMetaData();
                int cols = meta.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= cols; i++) {
                        System.out.println(rs.getString(i));
                    }
                }
            } finally {

            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //JDBCExample je = new JDBCExample("jdbc:mysql://localhost:3306/tai?useUnicode=true&characterEncoding=utf-8","root","123456");
        JDBCExample je = new JDBCExample("jdbc:mysql://localhost:3306", "root", "879892206");
        //String sql = "create table if not exists tai.error(id char(10) not null,content varchar(200),index(id))";
        //je.createTalbe(sql);
        je.query("select * from databus.system_dwdm limit 10");
    }

}
