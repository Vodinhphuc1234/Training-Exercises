/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import ThriftConnection.ThriftConnection;
import ThriftConnection.ThriftConnectionsPool;
import ThriftConnection.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author vodinhphuc
 */
public class UserService {

    public void UserService() {
    }

    public User findUser(String username, String password) {
        try {
            ThriftConnectionsPool thriftConnectionsPool = ThriftConnectionsPool.getInstance();
            ThriftConnection tConn = thriftConnectionsPool.getConnection();

            User user = tConn.getConnection().findUser(username, password);

            return user;
        } catch (TTransportException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean register(User user) {
        try {
            ThriftConnectionsPool thriftConnectionsPool = ThriftConnectionsPool.getInstance();
            ThriftConnection tConn = thriftConnectionsPool.getConnection();
            if (tConn.getConnection().existedUser(user.getUsername())) {
                return false;
            }
            return tConn.getConnection().create(user);
        } catch (TException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean  updateUser(User user) throws TTransportException, TException {
        ThriftConnectionsPool thriftConnectionsPool = ThriftConnectionsPool.getInstance();
        ThriftConnection tConn = thriftConnectionsPool.getConnection();
        
        return tConn.getConnection().update(user.id, user);
    }
}
