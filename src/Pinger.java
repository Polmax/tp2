/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package source;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
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
        DatagramSocket socket ;
        DatagramPacket ping ;
        try {
            socket = new DatagramSocket(5555,Proxy.listaMonitores.get(this.id).ip);
            String str="pingp";
            byte[] buffer = new byte[64];
            buffer = str.getBytes();
            ping = new DatagramPacket(buffer, buffer.length, Proxy.listaMonitores.get(id).ip, 5555);
            try {
                socket.send(ping);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try{
                socket.receive(ping);
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println(buffer.toString());
        } catch (SocketException ex) {
            System.out.println(ex);
        }
        
    }
    
    
}
