/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercise01;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author vodinhphuc
 */
public class profileHandler implements ProfileService.Iface {

    DatabaseConnection DBConn;

    public profileHandler() throws ClassNotFoundException {
        this.DBConn = DatabaseConnection.getInstance();
    }

    @Override
    public User get(int id) throws TException {
        Cache cache = Cache.getInstance();

        User storedUser = cache.getUser(id);

        if (storedUser != null) {
            return storedUser;
        }

        try {

            String sql = "select * from users where id=?";

            ResultSet rs = DBConn.query(sql, 1);

            User user = null;

            while (rs.next()) {
                int _id = rs.getInt("id");
                String _name = rs.getString("name");
                String _username = rs.getString("username");
                String _password = rs.getString("password");
                boolean _isActive = rs.getBoolean("isActive");

                user = new User(_id, _name, _username, _password, _isActive);
            }

            if (user != null) {
                cache.addUser(user);
            }

            return user;
        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<User> multiGet(List<Integer> ids) throws TException {
        List<User> results = new ArrayList<>();
        for (int id : ids) {
            User user = get(id);
            if (user != null) {
                results.add(user);
            }
        }
        return results;
    }

    @Override
    public boolean update(int id, User updatedUser) throws TException {
        if (Cache.getInstance().checkUser(id)) {
            Cache.getInstance().updateUser(updatedUser);
        }

        try {

            String sql = "update users set name=?, username=?, password=?, isActive=? where id=?";

            DBConn.update(sql, updatedUser.getName(), updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.isIsActive(), id);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean remove(int id) throws TException {
        try {

            String sql = "delete from users where id=?";

            boolean updated = DBConn.update(sql, id);

            if (updated) {
                Cache.getInstance().removeUser(id);
            }

            return updated;
        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<User> getAll() throws TException {
        try {
            List<User> results = new ArrayList<>();

            String sql = "select * from users";

            ResultSet rs = DBConn.query(sql);

            while (rs.next()) {
                int _id = rs.getInt("id");
                String _name = rs.getString("name");
                String _username = rs.getString("username");
                String _password = rs.getString("password");
                boolean _isActive = rs.getBoolean("isActive");

                User user = new User(_id, _name, _username, _password, _isActive);
                results.add(user);
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean create(User user) throws TException {

        try {
            String sql = "insert into users(name, username, password, isActive)"
                    + "values(?, ?, ?, ?)";

            user.password = BCrypt.hashpw(user.password, BCrypt.gensalt(12));

            DBConn.update(sql, user.getName(), user.getUsername(),
                    user.getPassword(), user.isIsActive());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean existedUser(String username) throws TException {
        try {
            String sql = "select * from users where username=?";
            ResultSet rs = DBConn.query(sql, username);

            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public User findUser(String username, String password) throws TException {
        User result = null;
        try {
            String sql = "select * from users where username=?";
            ResultSet rs = DBConn.query(sql, username);
            while (rs.next()) {
                int _id = rs.getInt("id");
                String _name = rs.getString("name");
                String _username = rs.getString("username");
                String _password = rs.getString("password");
                boolean _isActive = rs.getBoolean("isActive");

                if (BCrypt.checkpw(password, _password)) {
                    result = new User(_id, _name, _username, _password, true);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(profileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
