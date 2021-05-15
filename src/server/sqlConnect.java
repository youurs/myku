package server;

import javax.swing.*;
import java.sql.*;

class sqlConnect {

    public boolean start(String userName, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=chat", "text", "123456");
        String a = "'" + userName + "'";
        String sql = "select * from users where userName=" + a;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) {
            System.out.println("用户名正确");
            if (password.equals(rs.getString(2))) {
                System.out.println("登录成功");
                con.close();
                return true;
            } else {
                System.out.println("密码错误");
                con.close();
                return false;
            }
        } else {
            System.out.println("用户名错误");
            con.close();
            return false;
        }
    }
    public boolean register(String userName, String password)throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=chat", "text", "123456");
        String a = "'" + userName + "'";
        String b = "'" + password + "'";
        String sql1="select * from users where userName=" + a;
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(sql1);
        if(rs.next()){
            System.out.println("用户名已存在");
            return false;
        }else {
            String sql = "insert into users values(" + a+"," + b + ")";
            Statement st = con.createStatement();
            st.execute(sql);//插入用execute
            sql="create table "+userName+"(date datetime primary key,\n" +
                    "message varchar(100))";
            st.execute(sql);
            con.close();
            return true;
        }
    }

    public void keepMsg(String name,String msg)throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=chat", "text", "123456");
        String a ="'"+msg+"'";
        String sql="insert into "+name+" values(getdate(),"+a+")";
        Statement st = con.createStatement();
        st.execute(sql);
        con.close();
    }
    public void historyMsg1(String name)throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=chat", "text", "123456");
        String sql="select * from "+name;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()) {
            System.out.println(rs.getString(1)+"\n\t"+rs.getString(2)+"\n");
        }
        con.close();
    }
    public void historyMsg(String name, JTextArea read)throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=chat", "text", "123456");
        String sql="select * from "+name;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()) {
            String a=rs.getString(1)+"\n\t"+rs.getString(2)+"\n";
            read.append(a);
        }
        con.close();
    }
    public String find(String userName) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=chat", "text", "123456");
        String name = "'"+userName+"'";
        String sql="select * from users where userName="+name;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String password=null;
        while(rs.next()) {
            password=rs.getString(2);
        }
        con.close();
        return password;
    }

}
