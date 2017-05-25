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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        DatagramPacket pong;
        DatagramSocket socketDeActualizacao;
        try {
            socket = new DatagramSocket();
            socketDeActualizacao = new DatagramSocket(5556);
            long tempoEnvio = System.currentTimeMillis();
            String str="pingp"+"/"+tempoEnvio+"/";
           
            byte[] buffer = new byte[1024];
            byte[] actualizaEstado = new byte[1024];
            buffer = str.getBytes();
            ping = new DatagramPacket(buffer, buffer.length, id, 5555);
            pong = new DatagramPacket(actualizaEstado, actualizaEstado.length, id, 5556);
            socketDeActualizacao.setSoTimeout(5000);
            while(true){
            try {
                        Pinger.sleep(3000);
                    } catch (InterruptedException ex) {
                        System.out.println("thread falhou");
                    }
            
            try {
                
                socket.send(ping);
                System.out.println("Ping enviado para "+this.id + " Mensagem "+str);
                    
            } catch (IOException ex) {
                System.out.println(ex);
                }
            try{
                socketDeActualizacao.receive(pong);
                System.out.println("recebi a actualizaçao do monitor ");
                long timestamp = System.currentTimeMillis();
                String pacoteRecebido = new String(pong.getData());
                System.out.println("recebido do monitor pelo pinger : "+pacoteRecebido);
                String[] arrayEstado = pacoteRecebido.split("/");
                Proxy.listaMonitores.get(id).setnLigacoes(Integer.parseInt(arrayEstado[2]));
                Proxy.listaMonitores.get(id).setPacotesPerdidos(Integer.parseInt(arrayEstado[1]));
                Proxy.listaMonitores.get(id).setRttm(Long.parseLong(arrayEstado[0]));
                Proxy.listaMonitores.get(id).setTimestamp(timestamp);
                Proxy.listaMonitores.get(id).carga();
                System.out.println("Monitor com ip = " + id + " informações actuais : rtt = " + Proxy.listaMonitores.get(id).getRttm() + " e carga = " + Proxy.listaMonitores.get(id).getCarga());
            }catch(Exception e){
                System.out.println(e);
                }
            }
        } catch (SocketException ex) {
            System.out.println(ex);
        }
        
    }
    
    
}
