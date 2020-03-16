package aclc_lms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Db {
    
    public Connection dbConn;
    public Statement statement;
    public ResultSet resultSet;
    public PreparedStatement preparedStatement;
    
    private final Helper helper;

    public Db() {
        this.helper = new Helper(Db.class.getName());
        openConnection();
    }

    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConn=DriverManager.getConnection(Config.dbHostUrl, Config.dbUsername, Config.dbPassword);
            statement = dbConn.createStatement();
        }
        catch(ClassNotFoundException | SQLException e){
            helper.logException(e.toString(), "ERROR: Failed to connect to database");
        }
    }
    
    // Fetch data from mysql database
    public ResultSet fetchData(String sql) {
        try { 
            preparedStatement = dbConn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            return resultSet;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void executeSql(String sql) {
        try { 
            preparedStatement = dbConn.prepareStatement(sql);
            preparedStatement.execute(sql);
        } catch (SQLException ex) {
            helper.logException(ex.toString(), "Class Db: Failed to execute query");
        }
    }
   
}
