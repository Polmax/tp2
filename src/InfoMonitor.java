/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package source;

import java.net.InetAddress;

/**
 *
 * @author core
 */

public class InfoMonitor {
    InetAddress ip;
    long timestamp;
    long rttm;
    int pacotesPerdidos;
    int nLigacoes;
    Boolean disponivel;
    int carga;
    
    
    
    public InfoMonitor(InetAddress ip,long timestamp, long rttm, int pacotesPerdidos, int nLigacoes){
        this.ip=ip;
        this.timestamp=timestamp;
        this.rttm=rttm;
        this.pacotesPerdidos=pacotesPerdidos;
        this.nLigacoes=nLigacoes;
        this.disponivel=true;
        this.carga=0;
    }
    
    
    
    public int carga(){
        carga = 0;
        if(this.nLigacoes>=5){this.carga=100; return 1;}
        if(this.rttm>=300) {this.carga=100; return 1;}
        if(this.rttm>300) {this.carga=100; return 1;}
        if(this.rttm>=0 && this.rttm<=100) {
            if(!(this.carga==100)){
                this.carga+=20;
            }
        }
        if(this.rttm>100 && this.rttm <=200){
            if(!(this.carga==100)){
             this.carga+=35;
            }
        }
        if(this.rttm>200 && this.rttm <=300){
             if(!(this.carga==100)){
                this.carga+=50;
              }
        }
        if(this.nLigacoes==1){
             if(!(this.carga==100)){
                 this.carga+=5;
             }
        }
        if(this.nLigacoes==2){
            if(!(this.carga==100)){
                 this.carga+=10;
            }
        }
        if(this.nLigacoes==3){
              if(!(this.carga==100)){
            this.carga+=15;
              }
        }
        if(this.nLigacoes==4){
              if(!(this.carga==100)){
            this.carga+=30;
              }
        }
        return carga;
        
    }
    
    public Boolean estado(){
        return disponivel;
    }

    public int getCarga() {
        return carga;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getnLigacoes() {
        return nLigacoes;
    }

    public int getPacotesPerdidos() {
        return pacotesPerdidos;
    }

    public long getRttm() {
        return rttm;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }


    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setnLigacoes(int nLigacoes) {
        this.nLigacoes = nLigacoes;
    }

    public void setPacotesPerdidos(int pacotesPerdidos) {
        this.pacotesPerdidos = pacotesPerdidos;
    }

    public void setRttm(long rttm) {
        this.rttm = rttm;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
    
}
