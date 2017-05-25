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
import java.net.UnknownHostException;


/**
 *
 * @author core
 */
public class Monitor{
    static int nLigacoes;
    static int pacotesPerdidos;
    static long rttm;
    //static Boolean disponivel;
    
  
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException{
        long pingInicialProxy ;
     
        pingInicialProxy = initPing(args[0],5555);
        byte[] buffer = new byte[1024];
        String str = rttm + "/" + pacotesPerdidos + "/" + nLigacoes + "/" ;
        buffer = str.getBytes();
        byte[] bufferPedidoPinger = new byte[1024];
        InetAddress ip = InetAddress.getByName(args[0]);
        DatagramSocket socket = new DatagramSocket(5555);
        DatagramPacket enviaEstado = new DatagramPacket(buffer,buffer.length,ip,5555);
        socket.send(enviaEstado);
        System.out.println(pingInicialProxy);
        System.out.println("Numero de ligaçoes : " + nLigacoes + " Numero de pacotes perdidos : " + pacotesPerdidos + " ping : " + rttm + " ms");
        while(true){
            DatagramPacket recebePing = new DatagramPacket(bufferPedidoPinger,bufferPedidoPinger.length,ip,5555);
            socket.receive(recebePing);
            String pacoteRecebido = new String(recebePing.getData());
            System.out.println("pacote recebido pelo pinger - "+pacoteRecebido);
            String[] arrayEstado = pacoteRecebido.split("/");
            System.out.println("array 0 "+arrayEstado[0]+" array 1 "+arrayEstado[1]);
            System.out.println("tamanho da string" +arrayEstado[1].length());
            long timestamp = Long.parseLong(arrayEstado[1]);
            
            long tempoRecibo = System.currentTimeMillis();
            rttm = tempoRecibo - timestamp;
            System.out.println("Nome do pacote : "+pacoteRecebido + " do proxy de ip : " + recebePing.getAddress());
            byte[] bufferPong = new byte[1024];
            String stringAE = rttm + "/" + pacotesPerdidos + "/" + nLigacoes + "/" ;
            bufferPong = stringAE.getBytes();
            DatagramPacket enviaPong = new DatagramPacket(bufferPong,bufferPong.length,ip,5556);
            socket.send(enviaPong);
            System.out.println("Resposta ao pedido de actualização de estado enviada para : "+recebePing.getAddress());
            System.out.println("Dados enviados : rtt = "+rttm+" pacotes perdidos = "+pacotesPerdidos+" numero de ligacoes actuais com clientes = "+nLigacoes);
            
        }
        
    }
    public static long initPing(String ips,int port) throws UnknownHostException, SocketException, IOException{
        long rttinicial, tempoPartida, tempoChegada = 0;
        InetAddress ip = InetAddress.getByName(ips);
        DatagramSocket socket = new DatagramSocket();
        String str="pingi";
        byte[] buffer = new byte[10];
        buffer = str.getBytes();
        DatagramPacket ping = new DatagramPacket(buffer, buffer.length, ip, port);
        tempoPartida = System.currentTimeMillis();
        
        socket.send(ping);
        socket.setSoTimeout(3);
        try{
        socket.receive(ping);
        
        }catch(Exception e){
            pacotesPerdidos++;
            //System.out.println(e);
        }
        tempoChegada = System.currentTimeMillis();
        System.out.println(tempoChegada + " tempo partida " + tempoPartida);
        rttinicial = tempoChegada - tempoPartida ;
        rttm = rttinicial;
        nLigacoes = 0;
        //disponivel = true;
        return rttinicial;
    }
    
    
    
}
