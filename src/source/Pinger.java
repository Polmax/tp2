/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author core
 */
public class Pinger extends Thread{
    int id; //id utilizado para saber qual o monitor 
    public Pinger(int id){
        this.id=id;
    }
    
    @Override
    public void run(){
        try {
            DatagramSocket socket = new DatagramSocket(5555,Proxy.listaMonitores.get(this.id).ip);
        } catch (SocketException ex) {
            System.out.println(ex);
        }
        
        String str="pingp";
        byte[] buffer = new byte[10];
        buffer = str.getBytes();
        DatagramPacket ping = new DatagramPacket(buffer, buffer.length, Proxy.listaMonitores.get(id).ip, 5555);

    }
    
    
}
