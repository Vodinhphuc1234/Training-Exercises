/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ThriftConnection;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author vodinhphuc
 */
public class ThriftConnection {

    private TTransport transport;
    private ProfileService.Client connection;

    public ThriftConnection() throws TTransportException {
        transport = new TSocket("localhost", 9090);
        
        //((TSocket)transport).setTimeout(60*30);
        
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        connection = new ProfileService.Client(protocol);
    }
    
    public ProfileService.Client getConnection(){
        return connection;
    }
    
    public void closeConnection(){
        transport.close();
    }
    
    public boolean isUsable (){
        return (transport.isOpen()) ;
    }

}
