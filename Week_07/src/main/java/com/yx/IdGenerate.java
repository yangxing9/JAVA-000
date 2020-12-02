package com.yx;

import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/1 0001 17:46
 */
public class IdGenerate {

    private static AtomicLong id = new AtomicLong(0);
    private static AtomicLong localMaxId = new AtomicLong(0);

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String HOST = "localhost";

    public static void main(String[] args) throws SQLException {
        for (int i = 0; i < 4000; i++) {
            System.out.println(getId());
        }
    }

    private static long getId() throws SQLException {
        if (id.longValue() < localMaxId.longValue()){
            return id.getAndIncrement();
        }else {
            getSegment();
            return getId();
        }
    }

    private static void getSegment() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3307/mall?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true", USER_NAME,PASSWORD);
            conn.setAutoCommit(false);
            String querySql = "select * from id_generator where id = 1";
            pstmt = conn.prepareStatement(querySql);
            resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                int maxId = resultSet.getInt(2);
                int step = resultSet.getInt(3);
                int version = resultSet.getInt(4);
                int globalMax = maxId + step;
                String sql = "update id_generator set max_id =" + globalMax + ", version = version + 1 where version = " + version;
                pstmt = conn.prepareStatement(sql);
                int res = pstmt.executeUpdate();
                conn.commit();
                if (res > 0){
                    id.set(maxId);
                    localMaxId.set(globalMax);
                }else {
                    getSegment();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (pstmt != null){
                pstmt.close();
            }
            if (conn != null){
                conn.close();
            }
        }
    }


}
