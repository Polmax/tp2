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
import java.net.UnknownHostException;


/**
 *
 * @author core
 */
public class Monitor{
    int nLigacoes;
    int pacotesPerdidos;
    long rttm;
    Boolean disponivel;
    
    public static void init(String ip,int port){
        
        
    }
    public static void main(String[] args){
        
    }
    public long initPing(String ips,int port) throws UnknownHostException, SocketException, IOException{
        long rttinicial, tempoPartida=0, tempoChegada=0;
        InetAddress ip = InetAddress.getByName(ips);
        
        DatagramSocket socket = new DatagramSocket(port,ip);
        String str="pingi";
        byte[] buffer = new byte[10];
        buffer = str.getBytes();
        DatagramPacket ping = new DatagramPacket(buffer, buffer.length, ip, port);
        tempoPartida = System.currentTimeMillis();
        socket.send(ping);
        try{
        socket.receive(ping);
        tempoChegada = System.currentTimeMillis();
        }catch(Exception e){
            System.out.println(e);
        }
        rttinicial = tempoChegada - tempoPartida ;
        return rttinicial;
    }
    
    
    
}
