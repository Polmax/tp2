
//package source;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
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
        
        //TCP
        String receberC;
        String enviarC;
        ServerSocket servidorTCP = new ServerSocket(6800);
        //TCP - FIN
        System.out.println("Proxy ligado e a escuta na porta 5555");
        while(true){
        
        byte[] buffer = new byte[1024];
        long timestamp = System.currentTimeMillis();
        DatagramPacket pacoteEstado = new DatagramPacket(buffer,buffer.length);
        socket.receive(pacoteEstado);
        /*
        //TCP
        Socket socketTCP= servidorTCP.accept();
        BufferedReader inputCliente = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));
        DataOutputStream outputCliente = new DataOutputStream(socketTCP.getOutputStream());
        receberC = inputCliente.readLine();
        outputCliente.writeBytes(escolheServidor().getCanonicalHostName());
       //TCP - FIN
         * 
         */
        String pacoteRecebido = new String(pacoteEstado.getData());
        if(pacoteRecebido.startsWith("pingi")){
        System.out.println("Recebido do endereço : " + pacoteEstado.getAddress() + " A mensagem : " + pacoteRecebido);
        socket.send(pacoteEstado);
        }
        else {
        String[] arrayEstado = pacoteRecebido.split("/");
        if(!listaMonitores.containsKey(pacoteEstado.getAddress())){
            System.out.println("Novo monitor");
                InfoMonitor novoMonitor = new InfoMonitor(pacoteEstado.getAddress(),timestamp,Long.parseLong(arrayEstado[0]),Integer.parseInt(arrayEstado[1]),Integer.parseInt(arrayEstado[2]));
                listaMonitores.put(pacoteEstado.getAddress(), novoMonitor);
                listaMonitores.get(pacoteEstado.getAddress()).carga();
                Pinger p = new Pinger(pacoteEstado.getAddress());
                p.start();
            }
            else{
            System.out.println("Monitor Existente");
                listaMonitores.get(pacoteEstado.getAddress()).setnLigacoes(Integer.parseInt(arrayEstado[2]));
                //listaMonitores.get(pacoteEstado.getAddress()).setPacotesPerdidos(Integer.parseInt(arrayEstado[1]));
                listaMonitores.get(pacoteEstado.getAddress()).setRttm(Long.parseLong(arrayEstado[0]));
                listaMonitores.get(pacoteEstado.getAddress()).setTimestamp(timestamp);
                listaMonitores.get(pacoteEstado.getAddress()).carga();
                System.out.println("Informações do monitor : "+pacoteEstado.getAddress());
                System.out.println("Rtt : "+listaMonitores.get(pacoteEstado.getAddress()).getRttm());
                System.out.println("Numero de Conexões : "+listaMonitores.get(pacoteEstado.getAddress()).getnLigacoes());
                //System.out.println("Numero de pacotes Perdidos : "+listaMonitores.get(pacoteEstado.getAddress()).getPacotesPerdidos());
                System.out.println("Carga actual : "+listaMonitores.get(pacoteEstado.getAddress()).getCarga()+"%");
                InetAddress melhorServidor = escolheServidor();
                System.out.println("***************************************************************************");
                System.out.println("                  SERVIDOR COM MELHOR CARGA ACTUALMENTE                    ");
                System.out.println("IP = "+melhorServidor.getCanonicalHostName()+" CARGA = "+listaMonitores.get(melhorServidor).getCarga());
                System.out.println("NUMERO DE CLIENTES A SEREM ATENDIDOS = "+listaMonitores.get(melhorServidor).getnLigacoes());
                System.out.println("DISPONIVEL = "+listaMonitores.get(melhorServidor).getDisponivel() +" RTT = "+listaMonitores.get(melhorServidor).getRttm()+"ms");
                System.out.println("****************************************************************************");
                System.out.println('\n');
            }
        /*
            for(InfoMonitor a: listaMonitores.values()){
                System.out.println("Ip: "+ a.getIp() + " Carga = " + a.getCarga() + "%");
            }
      
         */
        }
        
        //System.out.println("carga = " + (listaMonitores.get(0).carga()) + "%");
   
        }
       
        }
    
    public static InetAddress escolheServidor() throws UnknownHostException{
        InetAddress ipaux = InetAddress.getByName("localhost");
        int caux=100;
        
        for(InetAddress a : listaMonitores.keySet()){
            System.out.println(listaMonitores.get(a).getCarga());
            if (caux>=listaMonitores.get(a).getCarga()) {caux=listaMonitores.get(a).getCarga(); ipaux = a ; System.out.println("Caux era maior agora os valores sao caux , ipaux:"+caux+ipaux);}
        }
        
        return ipaux;
    }
        
        
       
    }

