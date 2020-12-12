package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","1314406791");

return con;
    }
}
