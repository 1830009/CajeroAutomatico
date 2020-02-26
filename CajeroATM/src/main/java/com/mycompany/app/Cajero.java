package com.mycompany.app;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cajero {
    static int fDebito,fCredito;
        static String rutaDeb= "/home/neto/Documentos/UPV/CajeroATM/src/main/resources/Debido.txt";
        static String rutaCredito="/home/neto/Documentos/UPV/CajeroATM/src/main/resources/Credito.txt";

    public static List<Debito> CargarDebito() {
        List<Debito> Lista = new ArrayList<>();
        FileReader fr = null;
        try {
            fr = new FileReader(rutaDeb);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            String campos[];
            fDebito=0;
            while ((linea = br.readLine()) != null) {
                campos = linea.split(",");
                Lista.add(new Debito(campos[0],campos[1],campos[2],campos[3]));
                System.out.println(Arrays.toString(campos));
            fDebito++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir archivo");
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return Lista;
    }

    public static List<Credito> CargarCredito() {
        List<Credito> Lista = new ArrayList<>();
        FileReader fr = null;
        try {
            fr = new FileReader(rutaCredito);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            String campos[];
            fCredito=0;
            while ((linea = br.readLine()) != null) {
                campos = linea.split(",");
                Lista.add(new Credito(campos[0],campos[1],campos[2],campos[3]));
            fCredito++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir archivo");
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return Lista;
    }
    public static String validarCuenta(String No_Cuenta,String NIP){
        if(No_Cuenta.length()!=16 || NIP.length()!=4) {
            JOptionPane.showMessageDialog(null, "Debes ingresar 16 Caracteres en tu No. de cuenta\n" +
                    "y 4 caracteres en el NIP");
            return "4";
        }
        List<Debito> LDebito=CargarDebito();
        List<Credito> LCredito=CargarCredito();
            //Debito
        int i;
            for(i=0;i<fDebito;i++){
                if (LDebito.get(i).NIP.equals(NIP) && LDebito.get(i).No_Cuenta.equals(No_Cuenta)) {
                    JOptionPane.showMessageDialog(null, "Cuenta de Debito\n  Bienvenido!\n" + LDebito.get(i).Propietario);
                    return LDebito.get(i).No_Cuenta;
                }
            }
            //Credito
        for(i=0;i<fCredito;i++){
            if (LCredito.get(i).NIP.equals(NIP) && LCredito.get(i).No_Cuenta.equals(No_Cuenta)) {
                JOptionPane.showMessageDialog(null, "Cuenta de Credito\n  Bienvenido!\n" + LCredito.get(i).Propietario);
                return LCredito.get(i).No_Cuenta;
            }
        }
        return "0";
    }
    static String getUserName(String Usuario){
        String nombre[];
            nombre= Usuario.split(" ");
        System.out.println(Arrays.toString(nombre));
        String nombre1=nombre[0];
        return nombre1;
    }

    public static void GuardarCredito(List<Credito> L) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(rutaCredito));
        bw.write("");
        bw.close();
        FileWriter fw = new FileWriter(rutaCredito,true);
        bw = new BufferedWriter(fw);
        String Fila;
        int lim=L.size();
        for(int i=0;i<lim;i++){
            Fila=(L.get(i).No_Cuenta+","+L.get(i).NIP+","+L.get(i).Propietario+","+L.get(i).Credito);
            bw.write(Fila);
        }
        bw.close();
    }

    public static void GuardarDebito(List<Debito> L) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(rutaDeb));
        bw.write("");
        bw.close();
        FileWriter fw = new FileWriter(rutaDeb,true);
        bw = new BufferedWriter(fw);
        String Fila;
        int lim=L.size();
        for(int i=0;i<lim;i++){
            Fila=(L.get(i).No_Cuenta+","+L.get(i).NIP+","+L.get(i).Propietario+","+L.get(i).Saldo+"\n");
            bw.write(Fila);
        }
        bw.close();
    }
}
