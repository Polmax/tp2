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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author core
 */
public class Pinger extends Thread {

    InetAddress id; //id utilizado para saber qual o monitor 

    public Pinger(InetAddress ipid) {
        this.id = ipid;
    }
    /*
    @Override
    public void run() {
    
    DatagramSocket socketMonitor= null;
    try {
    socketMonitor = new DatagramSocket();
    } catch (SocketException ex) {
    System.out.println("Nao foi possivel criar o socket UDP - "+ex);
    }
    
    DatagramPacket ping;
    DatagramPacket pong;
    // DatagramSocket socketDeActualizacao = null;
    //socketDeActualizacao = new DatagramSocket();
    
    //socketDeActualizacao.setSoTimeout(5000);
    while (true) {
    
    
    long tempoEnvio = System.currentTimeMillis();
    String str = "pingp" + "/" + tempoEnvio + "/";
    
    byte[] bufferMonitor = new byte[1024];
    //byte[] actualizaEstado = new byte[1024];
    bufferMonitor = str.getBytes();
    ping = new DatagramPacket(bufferMonitor, bufferMonitor.length, id, 5556);
    //pong = new DatagramPacket(actualizaEstado, actualizaEstado.length,id,5556);
    
    
    try {
    
    socketMonitor.send(ping);
    System.out.println("Ping enviado para " + this.id + " Mensagem " + str);
    
    } catch (IOException ex) {
    System.out.println(ex);
    }
    try {
    socketMonitor.setSoTimeout(10000);
    } catch (SocketException ex) {
    System.out.println(ex);
    
    }
    try {
    
    socketMonitor.receive(ping);
    System.out.println("recebi a actualizaçao do monitor ");
    /*
    long timestamp = System.currentTimeMillis();
    String pacoteRecebido = new String(ping.getData());
    System.out.println("recebido do monitor pelo pinger : " + pacoteRecebido);
    String[] arrayEstado = pacoteRecebido.split("/");
    Proxy.listaMonitores.get(id).setnLigacoes(Integer.parseInt(arrayEstado[2]));
    Proxy.listaMonitores.get(id).setPacotesPerdidos(Integer.parseInt(arrayEstado[1]));
    Proxy.listaMonitores.get(id).setRttm(Long.parseLong(arrayEstado[0]));
    System.out.println(Long.parseLong(arrayEstado[0]));
    Proxy.listaMonitores.get(id).setTimestamp(timestamp);
    Proxy.listaMonitores.get(id).carga();
     * 
    
    //System.out.println("Monitor com ip = " + id + " informações actuais : rtt = " + Proxy.listaMonitores.get(id).getRttm() + " e carga = " + Proxy.listaMonitores.get(id).getCarga());
    
    } catch (Exception e) {
    System.out.println(e);
    Proxy.listaMonitores.remove(id);
    break;
    }
    
    
    }
    socketMonitor.close();
    }
     * 
     */

    @Override
    public void run() {
        while (true) {
            try {

                enviaPedido(id, 5556);

            } catch (Exception ex) {
                if (ex instanceof SocketException) {
                  
                    System.out.println("exception dentro do run "+ex);
                    
                }
                if (ex instanceof IOException) {
                    System.out.println(ex);
                }
            }
            try {
                Pinger.sleep(10000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }

    public void enviaPedido(InetAddress ip, int port) throws SocketException, IOException {
        DatagramSocket pinger2monitor = new DatagramSocket();
        Calendar calendario = Calendar.getInstance();
           
        long tempoEnvio = calendario.getTimeInMillis();
        String str = "pingp" + "/" + tempoEnvio + "/";
        byte[] buffer = new byte[128];
        buffer = str.getBytes();
        DatagramPacket ping = new DatagramPacket(buffer, buffer.length, ip, port);
        pinger2monitor.send(ping);
        
        try {
            pinger2monitor.setSoTimeout(3000);
            pinger2monitor.receive(ping);
        } catch (SocketException ex) {
           
             System.out.println("exception dentro do envia "+ex);
            
        }


    }
}
