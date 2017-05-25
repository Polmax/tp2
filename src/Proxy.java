
//package source;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author core
 */
public class Proxy {
    /**
     * @param args the command line arguments
     */
    static volatile Map<InetAddress,InfoMonitor> listaMonitores;
    
    
    public static void main(String[] args) throws SocketException, IOException, InterruptedException {
        // TODO code application logic here
        listaMonitores = new HashMap<InetAddress,InfoMonitor>();
        DatagramSocket socket = new DatagramSocket(5555);
        while(2==2){
        
        byte[] buffer = new byte[1024];
        DatagramPacket pacoteEstado = new DatagramPacket(buffer,buffer.length);
        socket.receive(pacoteEstado);
        long timestamp = System.currentTimeMillis();
       
        String pacoteRecebido = new String(pacoteEstado.getData());
        if(pacoteRecebido.startsWith("pingi")){
        System.out.println("Recebido do endereço : " + pacoteEstado.getAddress() + " A mensagem : " + pacoteRecebido);
        socket.send(pacoteEstado);
        }
        else {
        String[] arrayEstado = pacoteRecebido.split("/");
        if(!listaMonitores.containsKey(pacoteEstado.getAddress())){
            System.out.println("n existe");
                InfoMonitor novoMonitor = new InfoMonitor(pacoteEstado.getAddress(),timestamp,Long.parseLong(arrayEstado[0]),Integer.parseInt(arrayEstado[1]),Integer.parseInt(arrayEstado[2]));
                listaMonitores.put(pacoteEstado.getAddress(), novoMonitor);
                listaMonitores.get(pacoteEstado.getAddress()).carga();
                Pinger p = new Pinger(pacoteEstado.getAddress());
                p.start();
            }
            else{
            System.out.println("Ja existe");
                listaMonitores.get(pacoteEstado.getAddress()).setnLigacoes(Integer.parseInt(arrayEstado[2]));
                listaMonitores.get(pacoteEstado.getAddress()).setPacotesPerdidos(Integer.parseInt(arrayEstado[1]));
                listaMonitores.get(pacoteEstado.getAddress()).setRttm(Long.parseLong(arrayEstado[0]));
                listaMonitores.get(pacoteEstado.getAddress()).setTimestamp(timestamp);
                listaMonitores.get(pacoteEstado.getAddress()).carga();
            }
            
            for(InfoMonitor a: listaMonitores.values()){
                System.out.println("Ip: "+ a.getIp() + " Carga = " + a.getCarga() + "%");
            }
        }
        
        //System.out.println("carga = " + (listaMonitores.get(0).carga()) + "%");
   
        }
       
        }
        
        
       
    }

