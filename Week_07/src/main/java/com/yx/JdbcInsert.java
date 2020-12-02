package com.yx;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/1 0001 09:49
 */
public class JdbcInsert {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String HOST = "localhost";

    public static void main(String[] args) throws SQLException, InterruptedException, ExecutionException {
//        testPreparedForeach();
//        testPreparedForeachWithTransaction();
//        testPreparedSql();
//        testPreparedBatch2();
        testPreparedBatchThread();
    }

    /**
     * 普通单挑插入，1000 数据
     * 耗时： 131489 ms
     */
    private static void testPreparedForeach() {
        long start = System.currentTimeMillis();
        Connection conn = getConn();
        PreparedStatement statement = null;
        try {
            String sql = "insert into product(product_name,product_code,price,descript,spu_id,product_img,inventory) values(?,?,?,?,?,?,?)";
            statement = getPStmt(conn,sql);
            for (int i = 0; i < 1000; i++) {
                statement.setString(1,"小米10");
                statement.setString(2,"qweasdawrqw");
                statement.setBigDecimal(3,new BigDecimal(5000.52));
                statement.setString(4,"得屌丝者得天下");
                statement.setInt(5,i+1);
                statement.setString(6,"/phone/xiaomi/dasdqwrfasd.png");
                statement.setInt(7,1000);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStmt(statement);
            closeConn(conn);
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时： " + (end - start) + " ms");
    }

    /**
     * 拼sql，100万 数据
     * 耗时： 255875 ms
     */
    private static void testPreparedSql() {
        long start = System.currentTimeMillis();
        Connection conn = getConn();
        PreparedStatement statement = null;
        try {
            String sql = "insert into product(product_name,product_code,price,descript,spu_id,product_img,inventory) values";
            StringBuilder stringBuilder = new StringBuilder(sql);
            for (int j = 0; j < 100000; j++) {
                if (j == 100000-1){
                    stringBuilder.append(" ('小米10','qweasdawrqw','500.52','得屌丝者得天下',10,'/phone/xiaomi/dasdqwrfasd.png',2000) ");
                }else{
                    stringBuilder.append(" ('小米10','qweasdawrqw','500.52','得屌丝者得天下',10,'/phone/xiaomi/dasdqwrfasd.png',2000), ");
                }
            }

            for (int i = 0; i < 10; i++) {
                statement = getPStmt(conn,stringBuilder.toString());
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStmt(statement);
            closeConn(conn);
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时： " + (end - start) + " ms");
    }

    /**
     * 普通单挑插入带事物，100万 数据
     * 耗时： 304 s
     */
    private static void testPreparedForeachWithTransaction() {
        long start = System.currentTimeMillis();
        Connection conn = getConn();
        PreparedStatement statement = null;
        try {
            conn.setAutoCommit(false);
            String sql = "insert into product(product_name,product_code,price,descript,spu_id,product_img,inventory) values(?,?,?,?,?,?,?)";
            statement = getPStmt(conn,sql);
            for (int i = 0; i < 1000000; i++) {
                statement.setString(1,"小米10");
                statement.setString(2,"qweasdawrqw");
                statement.setBigDecimal(3,new BigDecimal(5000.52));
                statement.setString(4,"得屌丝者得天下");
                statement.setInt(5,i+1);
                statement.setString(6,"/phone/xiaomi/dasdqwrfasd.png");
                statement.setInt(7,1000);
                statement.execute();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStmt(statement);
            closeConn(conn);
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时： " + (end - start) + " ms");
    }

    /**
     * 批量操作
     * mysql 8.0.21
     * 100万数据，耗时：186 s
     */
    private static void testPreparedBatch() {
        long start = System.currentTimeMillis();
        Connection conn = getConn();
        PreparedStatement statement = null;
        try {
            String sql = "insert into product(product_name,product_code,price,descript,spu_id,product_img,inventory) values(?,?,?,?,?,?,?)";
            statement = getPStmt(conn,sql);
            for (int i = 0; i < 1000000; i++) {
                statement.setString(1,"小米10");
                statement.setString(2,"qweasdawrqw");
                statement.setBigDecimal(3,new BigDecimal(5000.52));
                statement.setString(4,"得屌丝者得天下");
                statement.setInt(5,i+1);
                statement.setString(6,"/phone/xiaomi/dasdqwrfasd.png");
                statement.setInt(7,1000);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStmt(statement);
            closeConn(conn);
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时： " + (end - start) + " ms");
    }

    /**
     * 批量操作 + 多线程
     * mysql 8.0.21
     * 100万数据，耗时：264 ms
     */
    private static void testPreparedBatchThread() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable task = () -> {
            Connection conn = getConn();
            PreparedStatement statement = null;
            try {
                String sql = "insert into product(product_name,product_code,price,descript,spu_id,product_img,inventory) values(?,?,?,?,?,?,?)";
                statement = getPStmt(conn,sql);
                for (int i = 0; i < 500000; i++) {
                    statement.setString(1,"小米10");
                    statement.setString(2,"qweasdawrqw");
                    statement.setBigDecimal(3,new BigDecimal(5000.52));
                    statement.setString(4,"得屌丝者得天下");
                    statement.setInt(5,i+1);
                    statement.setString(6,"/phone/xiaomi/dasdqwrfasd.png");
                    statement.setInt(7,1000);
                    statement.addBatch();
                }
                statement.executeBatch();
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeStmt(statement);
                closeConn(conn);
            }
        };
        for (int i = 0; i < 2; i++) {
            service.submit(task);
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("共耗时： " + (end - start) + " ms");
        service.shutdown();
    }

    /**
     * 表结构简单的 两个字段表
     * 100完数据，24 s
     */
    private static void testPreparedBatch2() {
        long start = System.currentTimeMillis();
        Connection conn = getConn();
        PreparedStatement statement = null;
        try {
            String sql = "insert into test values(default,?)";
            statement = getPStmt(conn,sql);
            for (int i = 0; i < 1000000; i++) {
                statement.setString(1,"小米10");
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStmt(statement);
            closeConn(conn);
        }
        long end = System.currentTimeMillis();
        System.out.println("共耗时： " + (end - start) + " ms");
    }

    private static String getProductName() {
        Random random = new Random();
        return "小米" + (random.nextInt(15) + 1);
    }

    public static Connection getConn(){
        Connection conn = null;
        try {
            //连接数据库
            conn = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3307/mall?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true", USER_NAME,PASSWORD);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static PreparedStatement getPStmt(Connection conn,String sql){
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    //关闭执行方法
    public static void closeStmt(Statement stmt){
        try {
            if(stmt != null ){
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //关闭连接
    public static void closeConn(Connection conn){
        try {
            if(conn != null ){
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
