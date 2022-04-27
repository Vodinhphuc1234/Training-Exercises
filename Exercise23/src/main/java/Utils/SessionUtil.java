/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DAO.DBConnection;
import ThriftConnection.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vodinhphuc
 */
public class SessionUtil {

    private DBConnection DBConnection;

    private static SessionUtil instance;

    public static SessionUtil getInstance() {
        if (instance == null) {
            instance = new SessionUtil();
        }
        return instance;
    }

    private SessionUtil() {
        DBConnection = new DBConnection();
    }

    public void putUserToDB(String sessionID, String key, String value) throws SQLException {
        String sql = "insert into Session (session_id, session_key, session_value) values (?,?,?)";

        DBConnection.update(sql, sessionID, key, value);
    }

    public User getUserFromDB(String sessionID) throws SQLException {
        String sql = "select * from LoginSession where session_id=?";

        ResultSet rs = DBConnection.query(sql, sessionID);

        User user=null;
        
        while (rs.next()) {

            String username = rs.getString("username");
            String password = rs.getString("password");
            String name = rs.getString("name");
            
            user = new User (name, username, password, true);
        }

        return user;
    }
    
    public void removeUserFromDB (String sessionID) throws SQLException{
        String sql = "delete from LoginSession where session_id=?";

        DBConnection.update(sql, sessionID);
    }

    public void putValue(HttpServletRequest httpRequest, String key, Object value) {
        httpRequest.getSession().setAttribute(key, value);
    }

    public Object getValue(HttpServletRequest httpRequest, String key) {
        return httpRequest.getSession().getAttribute(key);
    }

    public void removeValue(HttpServletRequest httpRequest, String key) {
        httpRequest.getSession().removeAttribute(key);
    }

    public void putUserToDB(String USessionID, User loggedUser) throws SQLException {
        String sql = "insert into LoginSession (session_id, username, password, name) values (?,?,?, ?)";

        DBConnection.update(sql, USessionID, loggedUser.getUsername(), loggedUser.getPassword(), loggedUser.getName());
    }
}
