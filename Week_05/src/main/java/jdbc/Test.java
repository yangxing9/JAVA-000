package jdbc;

import java.sql.*;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 21:18
 */
public class Test {


    static{
        try {
            //加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = getConn();
        String sql = "select * from test";
//            PreparedStatement statement = getPStmt(conn,sql);
        Statement statement = getStmt(conn);
        ResultSet resultSet = executeQuery(statement,sql);

        while(resultSet.next()){
            String id = resultSet.getString(1) ;
            String code = resultSet.getString("code");
            String name = resultSet.getString(3);
            System.out.print("id: " + id + "  ");
            System.out.print("name: " + name + "  ");
            System.out.println("code: " + code);
        }
        closeRs(resultSet);
        closeStmt(statement);
        closeConn(conn);
    }

    public static Connection getConn(){
        Connection conn = null;
        try {
            //连接数据库
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root","959130");
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

    public static Statement getStmt(Connection conn){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static ResultSet executeQuery(Statement stmt, String sql){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void closeRs(ResultSet rs){
        try {
            if(rs != null ){
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
