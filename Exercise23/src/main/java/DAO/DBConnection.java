/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vodinhphuc
 */
public class DBConnection {
    Connection conn;
    public DBConnection(){
        conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3006/test", "root", "Dinhphuc2009.");
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // -1 fail connect
    }
    
    public ResultSet query(String sql, Object ... params) throws SQLException{
        PreparedStatement ps = conn.prepareStatement(sql);
        
        int i=1;
        for (Object param: params){
            ps.setObject(i, param);
            i++;
        }
        
        ResultSet rs = ps.executeQuery();
        
        return rs;
    }
    
    public void update(String sql, Object ... params) throws SQLException{
        PreparedStatement ps = conn.prepareStatement(sql);
        
        int i=1;
        for (Object param: params){
            ps.setObject(i, param);
            i++;
        }
        ps.execute();
    }
}
