package server;

import java.sql.*;

public class Connect {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=chat", "text", "123456");
        String a = "'one'";
        String sql = "select * from users where userName=" + a;
        Statement st = null;
        ResultSet rs = null;
        st = con.createStatement();
        rs = st.executeQuery(sql);
        String aa = null;

        while(rs.next()) {
            aa = rs.getString(2);
            System.out.println(aa);
        }

    }
}
