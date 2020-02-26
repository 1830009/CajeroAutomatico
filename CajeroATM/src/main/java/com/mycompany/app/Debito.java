package com.mycompany.app;

public class Debito {
    public String No_Cuenta,NIP,Propietario;
    public float Saldo;

    public Debito(String no_Cuenta, String NIP, String propietario, String saldo) {
        No_Cuenta = no_Cuenta;
        this.NIP = NIP;
        Propietario = propietario;
        Saldo = Float.parseFloat(saldo);
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

    public float getSaldo() {
        return Saldo;
    }

    public void setSaldo(float saldo) {
        Saldo = saldo;
    }
}
