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
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author core
 */
public class Monitor {

    static int nLigacoes=0;
    static int pacotesPerdidos;
    static int pacotesEnviados;
    static long rttm;
    //static Boolean disponivel;

    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        long pingInicialProxy;
        for(int i=0;i<10;i++){
        rttm+=initPing(args[0], 5555);
        
        }
        rttm = rttm/10;
        if (pacotesPerdidos<=10){
        actualizaEstado(args[0], 5555);

        pingPong(args[0],5556);
        /*
        while(true){
        rttm=0;
        DatagramPacket recebePing = new DatagramPacket(bufferPedidoPinger,bufferPedidoPinger.length);
        socketPinger.receive(recebePing);
        long tempoRecibo = System.currentTimeMillis();
        String pacoteRecebido = new String(recebePing.getData());
        System.out.println("pacote recebido pelo pinger - "+pacoteRecebido);
        String[] arrayEstado = pacoteRecebido.split("/");
        System.out.println("array 0 "+arrayEstado[0]+" array 1 "+arrayEstado[1]);
        System.out.println("tamanho da string" +arrayEstado[1].length());
        long timestamp = Long.parseLong(arrayEstado[1]);
        
        
        
        rttm = tempoRecibo - timestamp;
        System.out.println("Nome do pacote : "+pacoteRecebido + " do proxy de ip : " + recebePing.getAddress());
        byte[] bufferPong = new byte[1024];
        String stringAE = rttm + "/" + pacotesPerdidos + "/" + nLigacoes + "/" ;
        bufferPong = stringAE.getBytes();
        DatagramPacket enviaPong = new DatagramPacket(bufferPong,bufferPong.length,ip,5556);
        socketPinger.send(enviaPong);
        System.out.println("Resposta ao pedido de actualização de estado enviada para : "+recebePing.getAddress());
        System.out.println("Dados enviados : rtt = "+rttm+" pacotes perdidos = "+pacotesPerdidos+" numero de ligacoes actuais com clientes = "+nLigacoes);
        
        }
         * 
         */
        }

    }

    public static long initPing(String ips, int port) throws UnknownHostException, SocketException, IOException {
        long rttinicial, tempoPartida, tempoChegada = 0;
        InetAddress ip = InetAddress.getByName(ips);
        DatagramSocket socket = new DatagramSocket();
        Calendar calendario = Calendar.getInstance();
        tempoPartida = calendario.getTimeInMillis();
        String str = "pingi";
        byte[] buffer = new byte[10];
        buffer = str.getBytes();
        DatagramPacket ping = new DatagramPacket(buffer, buffer.length, ip, port);
        socket.send(ping);
        
        try {
            socket.setSoTimeout(10000);
            socket.receive(ping);
            calendario = Calendar.getInstance();
            tempoChegada= calendario.getTimeInMillis();
            rttinicial = tempoChegada - tempoPartida;
            socket.close();
            return rttinicial;
      

        } catch (Exception e) {
            pacotesPerdidos++;
            calendario = Calendar.getInstance();
            tempoChegada= calendario.getTimeInMillis();
            rttinicial = tempoChegada - tempoPartida; 
            System.out.println(e);
            socket.close();
            return rttinicial;
        }
     
    }

    public static void actualizaEstado(String ips, int port) throws SocketException, UnknownHostException, IOException {
        InetAddress ip = InetAddress.getByName(ips);
        DatagramSocket socket = new DatagramSocket();
        byte[] buffer = new byte[1024];
        String str = rttm + "/" + pacotesPerdidos + "/" + nLigacoes + "/";
        buffer = str.getBytes();
        DatagramPacket enviaEstado = new DatagramPacket(buffer, buffer.length, ip, 5555);
        socket.send(enviaEstado);
        socket.close();
        System.out.println("Numero de ligaçoes : " + nLigacoes + " Numero de pacotes perdidos : " + pacotesPerdidos + " ping : " + rttm + " ms");
    }

    public static void pingPong(String ips,int port) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket(port);
        while(true){
           
        byte[] buffer = new byte[1024];
        DatagramPacket recebePing = new DatagramPacket(buffer, buffer.length);
        socket.receive(recebePing);
         Calendar calendario = Calendar.getInstance();
         long tempoChegada = calendario.getTimeInMillis();
         String pacoteRecebido = new String(recebePing.getData());
        
        //calcula metricas
        
        String[] arrayEstado = pacoteRecebido.split("/");
        
        long timestamp = Long.parseLong(arrayEstado[1]);



        rttm = tempoChegada - timestamp;
        //calcula metricas - fin
      
        
        //envia pong
        
        //byte[] bufferPong = new byte[1024];
        //String stringAE = rttm + "/" + pacotesPerdidos + "/" + nLigacoes + "/" ;
        //bufferPong = stringAE.getBytes();
        //DatagramPacket enviaPong = new DatagramPacket(bufferPong,bufferPong.length);

        socket.send(recebePing);
        actualizaEstado(ips,5555);
        }
        
        //envia pong - fin
        

    }
}
