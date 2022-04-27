/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ThriftConnection;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author vodinhphuc
 */
public class ThriftConnectionsPool {
    
    private static ThriftConnectionsPool instance;
    
    public static ThriftConnectionsPool getInstance () {
        if (instance == null)
            instance = new ThriftConnectionsPool();
        return instance;
    }
    
    private final int MAX_SIZE = 10;

    private final int EXPIRE_TIME = 30 * 60;

    private static List<ThriftConnection> availableConnections;
    private static List<ThriftConnection> usedConnections;

    public ThriftConnectionsPool() {
        availableConnections = new ArrayList<>();
       
        usedConnections = new ArrayList<>();
    }

    public ThriftConnection getConnection() throws TTransportException {
        if (availableConnections.isEmpty()) {
            if (usedConnections.size() < MAX_SIZE) {
                ThriftConnection tConn = new ThriftConnection();
                usedConnections.add(tConn);
                return tConn;
            } else {
                throw new RuntimeException("Connection pool is currently full !!!!!");
            }
        }
        
        ThriftConnection tConn = availableConnections.get(availableConnections.size() - 1);
        availableConnections.remove(tConn);
        
        if (!tConn.isUsable())
            tConn = new ThriftConnection();
        
        usedConnections.add(tConn);
        
        return tConn;
    }

    public void releaseConnection(ThriftConnection tConn) {
        availableConnections.add(tConn);
        usedConnections.remove(tConn);
    }

    public int getSize() {
        return availableConnections.size() + usedConnections.size();
    }

}
