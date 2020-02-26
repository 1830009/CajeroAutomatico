package com.mycompany.app;

public class Credito {
    public String No_Cuenta,NIP,Propietario;
    public float Credito;

    public Credito(String no_Cuenta, String NIP, String propietario, String credito) {
        No_Cuenta = no_Cuenta;
        this.NIP = NIP;
        Propietario = propietario;
        Credito = Float.parseFloat(credito);
    }

    public String getNo_Cuenta() {
        return No_Cuenta;
    }

    public void setNo_Cuenta(String no_Cuenta) {
        No_Cuenta = no_Cuenta;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getPropietario() {
        return Propietario;
    }

    public void setPropietario(String propietario) {
        Propietario = propietario;
    }

    public float getCredito() {
        return Credito;
    }

    public void setCredito(float credito) {
        Credito = credito;
    }
}
