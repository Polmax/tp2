/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package source;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/**
 *
 * @author core
 */
public class Pinger extends Thread{
    InetAddress id; //id utilizado para saber qual o monitor 
    public Pinger(InetAddress ipid){
        this.id=ipid;
    }
    
    @Override
    public void run(){
        DatagramSocket socket ;
        DatagramPacket ping ;
        try {
            socket = new DatagramSocket();
            String str="pingp";
            byte[] buffer = new byte[64];
            buffer = str.getBytes();
            socket.setSoTimeout(1000);
            while(true){
            ping = new DatagramPacket(buffer, buffer.length, Proxy.listaMonitores.get(id).getIp(), 5555);
            try {
                socket.send(ping);
                System.out.println("Ping enviado para "+this.id + "Mensagem "+str);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try{
                socket.receive(ping);
            }catch(Exception e){
                System.out.println(e);
            }
            System.out.println(buffer.toString());
            }
        } catch (SocketException ex) {
            System.out.println(ex);
        }
        
    }
    
    
}
