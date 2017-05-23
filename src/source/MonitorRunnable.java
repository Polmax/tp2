/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author core
 */
public class MonitorRunnable implements Runnable {
     DatagramSocket socketPingReciever;
     DatagramPacket pingReciever;
     byte[] buffer;
     
     
     public MonitorRunnable(InetAddress ip, int port) throws SocketException{
         socketPingReciever = new DatagramSocket(port,ip);
         pingReciever = new DatagramPacket(buffer,buffer.length);
         
         
     }
    
    @Override
    public void run(){
        try {
            socketPingReciever.receive(pingReciever);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    
    
}
    
}
