/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.net.InetAddress;
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
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        int tamanhoMap;
        tamanhoMap= listaMonitores.size();
        for(int i=0;i<tamanhoMap;i++){
            Pinger p = new Pinger(i);
            p.start();
        }
    }

}
