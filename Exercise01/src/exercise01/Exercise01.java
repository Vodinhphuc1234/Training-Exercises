/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exercise01;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author vodinhphuc
 */
public class Exercise01 {

    public static profileHandler handler;
    public static ProfileService.Processor processor;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            handler = new profileHandler();
            processor = new ProfileService.Processor(handler);

            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    try {
                        simple(processor);
                    } catch (TTransportException ex) {
                        Logger.getLogger(Exercise01.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            new Thread(simple).start();
        } catch (Exception x) {
            System.out.println("Erorr");
        }
    }

    public static void simple(ProfileService.Processor processor) throws TTransportException {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            //TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

            // Use this for a multithreaded server
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Starting the simple server...");
            server.serve();
        } catch (TTransportException e) {
        }
    }

}
