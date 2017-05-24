
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
    static volatile Map<Integer,InfoMonitor> listaMonitores;
    
    
    public static void main(String[] args) throws SocketException, IOException {
        // TODO code application logic here
        listaMonitores = new HashMap<Integer,InfoMonitor>();
        DatagramSocket socket = new DatagramSocket(5555);
        while(2==2){
        
        byte[] buffer = new byte[1024];
        DatagramPacket pacoteEstado = new DatagramPacket(buffer,buffer.length);
        socket.receive(pacoteEstado);
        String pacoteRecebido = new String(pacoteEstado.getData());
        System.out.println("Recebido do endereço : " + pacoteEstado.getAddress() + " A mensagem : " + pacoteRecebido);
        }
        /*
        int tamanhoMap;
        tamanhoMap= listaMonitores.size();
        for(int i=0;i<tamanhoMap;i++){
            Pinger p = new Pinger(i);
            p.start();
        }
         * 
         */
    }

}
