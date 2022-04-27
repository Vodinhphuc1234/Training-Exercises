/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package exercise01;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vodinhphuc
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    public static DatabaseConnection getInstance() throws ClassNotFoundException{
        if (instance == null)
            instance=new DatabaseConnection();
        return instance;
    }
    
    private DatabaseConnection() throws ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Dinhphuc2009.");
            System.out.println("Connected !!!");
        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet query(String sql, Object... objects) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i=0; i<objects.length; i++){
            ps.setObject(i+1, objects[i]);
        }
        
        ResultSet rs = ps.executeQuery();
        
        return rs;
    }
    
    public boolean update(String sql, Object... objects) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i=0; i<objects.length; i++){
            ps.setObject(i+1, objects[i]);
        }
        boolean executed = ps.execute();
        
        return executed;
    }
}
