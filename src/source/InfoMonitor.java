/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.net.InetAddress;

/**
 *
 * @author core
 */
public class InfoMonitor {
    int id;
    InetAddress ip;
    long timestamp;
    long rttm;
    int pacotesPerdidos;
    int nLigacoes;
    Boolean disponivel;
    int carga;
    
    
    
    public InfoMonitor(int id,InetAddress ip,long timestamp, long rttm, int pacotesPerdidos, int nLigacoes){
        this.id=id;
        this.ip=ip;
        this.timestamp=timestamp;
        this.rttm=rttm;
        this.pacotesPerdidos=pacotesPerdidos;
        this.nLigacoes=nLigacoes;
        this.disponivel=true;
        this.carga=0;
    }
    
    
    
    public int carga(){
        if(this.nLigacoes>=5){this.carga=100; return 1;}
        if(this.rttm>=300) {this.carga=100; return 1;}
        if(this.rttm>300) {this.carga=100; return 1;}
        if(this.rttm>=0 && this.rttm<=100) this.carga+=20;
        if(this.rttm>100 && this.rttm <=200) this.carga+=35;
        if(this.rttm>200 && this.rttm <=300) this.carga+=50;
        if(this.nLigacoes==1) this.carga+=5;
        if(this.nLigacoes==2) this.carga+=10;
        if(this.nLigacoes==3) this.carga+=15;
        if(this.nLigacoes==4) this.carga+=30;
        return carga;
        
    }
    
    public Boolean estado(){
        return disponivel;
    }
    
}
