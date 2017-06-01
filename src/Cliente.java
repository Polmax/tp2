
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author core
 */
public class Cliente {
    
    
    public static void main(String[] args) throws UnknownHostException, IOException{
        String stringEnvio;
        String stringRecibo;
        BufferedReader inputCliente = new BufferedReader(new InputStreamReader(System.in));
        
        Socket sc = new Socket(args[0],2000);
        DataOutputStream enviarProxy = new DataOutputStream (sc.getOutputStream());
        BufferedReader inputServidor = new BufferedReader( new InputStreamReader (sc.getInputStream()));
        stringEnvio = inputCliente.readLine();
        enviarProxy.writeBytes(stringEnvio+'\n');
        stringRecibo = inputServidor.readLine();
        System.out.println(stringRecibo);
        InetAddress ipSBE = InetAddress.getByName(stringRecibo);
        Socket socketBE = new Socket(ipSBE,80);
    }
    
}
