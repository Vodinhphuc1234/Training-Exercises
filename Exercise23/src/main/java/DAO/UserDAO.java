/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ThriftConnection.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author vodinhphuc
 */
public class UserDAO {

    private Connection connection;

    public void UserDAO() {
        connection = getConnection();
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Dinhphuc2009.");
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public boolean existedUser(String username, String password) throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        String sql = "select * from users where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            if (BCrypt.checkpw(password, rs.getString("password"))) {
                return true;
            }
        }
        return false;
    }

    public boolean existedUser(String username) throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        String sql = "select * from users where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return true;
        }
        return false;
    }

    public boolean register(User user) throws SQLException {
        Connection conn = getConnection();

        String username = user.getUsername();
        String name = user.getName();
        String password = user.getPassword();
        String hashPass = BCrypt.hashpw(password, BCrypt.gensalt(12));
        boolean active = true;

        String sql = "insert into users (name, username, password, isActive) values (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.setString(2, username);
        ps.setString(3, hashPass);
        ps.setBoolean(4, active);

        ps.execute();

        return true;
    }

    public User findUser(String username, String password) throws SQLException {
        User user = null;

        Connection conn = getConnection();

        String sql = "select * from users where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            if (BCrypt.checkpw(password, rs.getString("password"))) {

                int _id = rs.getInt("id");
                String _name = rs.getString("name");
                String _username = rs.getString("username");
                String _password = rs.getString("password");
                
                user = new User (_id, _name, _username, _password, true);
            }
        }

        return user;
    }
}
