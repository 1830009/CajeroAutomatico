package com.mycompany.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public PasswordField T_Pass = new PasswordField();
    public TextField T_Usuario = new TextField();
    public Button Btn_Salir;
    public Button Btn_Saldo;
    public Button Btn_NIP;
    public Button Btn_Transferir;
    public Button Btn_Retirar;
    public Button Btn_Consultar;
    public Button B_logIn;
    public Label L_Usuario = new Label();
    public Button Btn_Regresar;
    public Button Btn_ConfTrans;
    public TextField T_CuentaTrans;
    public TextField T_CantTrans;
    public Button Btn_RecargaSaldo;
    public ChoiceBox T_Compania= new ChoiceBox();
    public TextField T_Cantidad;
    public TextField T_NumTel;
    public Button Btn_CambiaNIP;
    public PasswordField T_NewNIP2;
    public PasswordField T_NewNIP;
    public TextField T_oldNIP;
    public TextField T_Otro;
    public Button Btn_100;
    public Button Btn_200;
    public Button Btn_300;
    public Button Btn_500;
    public Button Btn_mil;
    public Button Btn_Otro;
    @FXML public TextField T_No_Cuenta= new TextField("0");
    @FXML public TextField T_Saldo= new TextField("0");
    static List<Debito> L_Debito = new ArrayList<>();
    static List<Credito> L_Credito = new ArrayList<>();
    static int PosicionDebito,PosicionCredito;
    public TextField T_SaldoAct;
    static int limIntentos=0;

    @FXML
    void initialize() {
    Inic_choiceB();
    }
    private void Inic_choiceB() {
        ObservableList list = FXCollections.observableArrayList();
        list.addAll("Telcel", "Movistar", "AT&T", "Unefon");
        T_Compania.getItems().setAll(list);
    }

    public int CompararCredito(String Usuario) {
        int lim=L_Credito.size();
        for (int i = 0;i<lim ; i++){
            if (Usuario.equals(L_Credito.get(i).No_Cuenta)) {
                System.out.println("CREDITO ENCONTRAO----------------------------------------");
                return i;
            }
        }
        return -1;
    }

    public int CompararDebito(String Usuario) {
        int lim=L_Debito.size();
        for (int i = 0; i < lim; i++) {
            if (Usuario.equals(L_Debito.get(i).No_Cuenta)) {
                System.out.println("Encontrado Debito------------------------");
                return i;
            }
        }
        return -1;
    }

    /**
     * Carga la BD de las cuentas registradas
     */
    public void Ini_Listas(){
        L_Credito=Cajero.CargarCredito();
        L_Debito=Cajero.CargarDebito();
    }

    public void B_LogIn() throws IOException {
        Ini_Listas();

            String Validar = Cajero.validarCuenta(T_Usuario.getText(), T_Pass.getText());
        if(limIntentos<3) {
            System.out.println(Validar + "--------------------------------");
            if (Validar != "0" && Validar != "4") {
                PosicionCredito = CompararCredito(Validar);
                PosicionDebito = CompararDebito(Validar);
                if (PosicionCredito == -1) {
                    Validar = L_Debito.get(PosicionDebito).Propietario;
                    System.out.println("Credito Esta vacio");
                }
                if (PosicionDebito == -1) {
                    Validar = L_Credito.get(PosicionCredito).Propietario;
                    System.out.println("Debito Esta vacio");
                }
                String Validar2 = Cajero.getUserName(Validar);
                Stage stage = FXMLLoader.load(getClass().getResource("/V_Menu.fxml"));
                stage.setTitle("Bienvenido " + Validar2);
                stage.show();
                stage = (Stage) B_logIn.getScene().getWindow();
                stage.close();
            } else if (Validar == "0") {
                JOptionPane.showMessageDialog(null, "Lo sentimos, Cuenta no encontrada\nIngrese de nuevo");
            }
            limIntentos++;
        }else
            JOptionPane.showMessageDialog(null,"Ya haz superado el limite de intentos\nIntente Mañana");
    }

    public void B_Retirar() throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("/V_Retirar.fxml"));
        stage.setTitle("Retirar");
        stage.show();
        stage = (Stage) Btn_Retirar.getScene().getWindow();
        stage.close();
    }

    public void B_Transferir() throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("/V_Transferir.fxml"));
        stage.setTitle("Transferir");
        stage.show();
        stage = (Stage) Btn_Transferir.getScene().getWindow();
        stage.close();
    }

    public void B_NIP() throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("/V_NIP.fxml"));
        stage.setTitle("Cambiar NIP");
        stage.show();
        stage = (Stage) Btn_NIP.getScene().getWindow();
        stage.close();
    }

    public void B_Saldo() throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("/V_Saldo.fxml"));
        stage.setTitle("Recargar Saldo");
        stage.show();
        stage = (Stage) Btn_Saldo.getScene().getWindow();
        stage.close();
    }

    public void B_Salir() throws IOException {
        if(PosicionCredito!= -1){
            Cajero.GuardarCredito(L_Credito);
        }
        if(PosicionDebito!= -1){
            Cajero.GuardarDebito(L_Debito);
        }
        Stage stage = (Stage) Btn_Salir.getScene().getWindow();
        stage.close();

    }

    public void B_Consultar() throws IOException {

        Stage stage = FXMLLoader.load(getClass().getResource("/V_Consultar.fxml"));
        stage.setTitle("Consultar");
        stage.show();
         stage = (Stage) Btn_Consultar.getScene().getWindow();
        stage.close();

    }

    public void B_Regresar() throws IOException {
        AbrirMenu();
        Stage stage = (Stage) Btn_Regresar.getScene().getWindow();
        stage.close();
    }

    int buscarCuenta(String Cuenta,String Cantidad){
        int Y=L_Debito.size();
        int X=L_Credito.size();
        float cantidad=Float.parseFloat(Cantidad);;
        System.out.println("X vale: "+X+"Y Vale: "+Y);
        for(int i=0;i<X;i++){
            if(Cuenta.equals(L_Credito.get(i).No_Cuenta)){

                L_Credito.get(i).Credito-=cantidad;
                return i;
            }
        }
        for(int i=0;i<Y;i++){
            if(Cuenta.equals(L_Debito.get(i).No_Cuenta)){
                L_Debito.get(i).Saldo+=cantidad;
                return i;

            }
        }
        return -1;
    }
    public void B_ConfTrans() throws IOException {
        float cantidad;
        float limite= Float.parseFloat(T_CantTrans.getText());
        if(limite>=100) {
            int Posicion = buscarCuenta(T_CuentaTrans.getText(), T_CantTrans.getText());
            if (Posicion != -1) {
                if (PosicionCredito != -1) {
                    cantidad = Float.parseFloat(T_CantTrans.getText());
                    L_Credito.get(PosicionCredito).Credito += cantidad;
                    System.out.println(L_Credito.get(PosicionCredito).Credito + "--------------------");
                    JOptionPane.showMessageDialog(null, "Operación realizada con exito\n" +
                            "Credito: $" + L_Credito.get(PosicionCredito).Credito);
                    AbrirMenu();
                    Stage stage = (Stage) Btn_ConfTrans.getScene().getWindow();
                    stage.close();
                } else if (PosicionDebito != -1 && limite<=L_Debito.get(PosicionDebito).Saldo) {
                    cantidad = Float.parseFloat(T_CantTrans.getText());
                    L_Debito.get(PosicionDebito).Saldo -= cantidad;
                    System.out.println(L_Debito.get(PosicionDebito).Saldo + "---------------------------");
                    JOptionPane.showMessageDialog(null, "Operación realizada con exito\n" +
                            "Saldo Disponible: $" + L_Debito.get(PosicionDebito).Saldo);
                    AbrirMenu();
                    Stage stage = (Stage) Btn_ConfTrans.getScene().getWindow();
                    stage.close();
                }else
                    JOptionPane.showMessageDialog(null,"Lo sentimos, No puedes\nTransferir mas de lo que tienes disponible");

            }
            else
                JOptionPane.showMessageDialog(null,"Lo sentimos, Cuenta no Encontrada :(");

        }
        else{
            JOptionPane.showMessageDialog(null,"Necesita hacer una transacción\nMayor a $100.00 MXN");
        }

    }

    boolean validarSaldo(){
        if(T_Cantidad.getLength()==0|| T_NumTel.getLength()==0){
            JOptionPane.showMessageDialog(null,"Debes ingresar la cantidad\n y el Numero Telefonico");
            return false;
        }
        else{
            int Cantidad= Integer.parseInt(T_Cantidad.getText());
            if(Cantidad<50){
                JOptionPane.showMessageDialog(null,"Debes ingresar una cantidad\n minima de $50.00 MXN");
                return false;
            }else if(T_NumTel.getLength()>10){
                JOptionPane.showMessageDialog(null,"Debes ingresar un Número telefónico\n a 10 digitos");
                return false;
            }
        }
        return true;
    }
    public void B_RecargaSaldo() throws IOException {
        if(validarSaldo()==true) {
            if (PosicionCredito != -1) {
                L_Credito.get(PosicionCredito).Credito+=Float.parseFloat(T_Cantidad.getText());
                System.out.println(L_Credito.get(PosicionCredito).Credito+"--------------------");
                JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                        "El núm: "+T_NumTel.getText()+" se ha recargado $"+T_Cantidad.getText()+" con exito!"+
                        "\nCredito: $"+L_Credito.get(PosicionCredito).Credito);
            }
            if (PosicionDebito != -1) {
                L_Debito.get(PosicionDebito).Saldo-=Float.parseFloat(T_Cantidad.getText());
                System.out.println(L_Debito.get(PosicionDebito).Saldo+"--------------------");
                JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                        "El núm: "+T_NumTel.getText()+" se ha recargado $"+T_Cantidad.getText()+" con exito!"+
                        "\nSaldo en cuenta: $"+L_Debito.get(PosicionDebito).Saldo);
            }
            AbrirMenu();
            Stage stage = (Stage) Btn_RecargaSaldo.getScene().getWindow();
            stage.close();
        }
    }

    boolean validarNIP(){
        if(T_oldNIP.getLength()==0 || T_NewNIP.getLength()==0 || T_NewNIP2.getLength()==0){
            JOptionPane.showMessageDialog(null,"Debes Teclear en todos los\nCampos marcados con [*]");
            return false;
        }
        return true;
    }
    public void B_CambiaNIP() throws IOException {
        if(validarNIP()==true) {
            if (PosicionCredito != -1) {
                if (!(T_oldNIP.getText().equals(L_Credito.get(PosicionCredito).NIP))) {
                    JOptionPane.showMessageDialog(null, "El NIP actual no coincide\nIntente de nuevo");
                } else if (!(T_NewNIP.getText().equals(T_NewNIP2.getText()))) {
                    JOptionPane.showMessageDialog(null, "EL Nuevo NIP no coincide\nIntente de nuevo");
                } else {
                    L_Credito.get(PosicionCredito).NIP = T_NewNIP.getText();
                    System.out.println(L_Credito.get(PosicionCredito).NIP);
                    JOptionPane.showMessageDialog(null, "Tu NIP ha sido cambiado\n con Exito!");
                    AbrirMenu();
                    Stage stage = (Stage) Btn_CambiaNIP.getScene().getWindow();
                    stage.close();
                }

            }
            if (PosicionDebito != -1) {
                if(!(T_oldNIP.getText().equals(L_Debito.get(PosicionDebito).NIP))){
                    JOptionPane.showMessageDialog(null,"El NIP actual no coincide\nIntente de nuevo");
                }
                else if(!(T_NewNIP.getText().equals(T_NewNIP2.getText()))){
                    JOptionPane.showMessageDialog(null,"EL Nuevo NIP no coincide\nIntente de nuevo");
                }
                else{
                    L_Debito.get(PosicionDebito).NIP=T_NewNIP.getText();
                    System.out.println(L_Debito.get(PosicionDebito).NIP);
                    JOptionPane.showMessageDialog(null,"Tu NIP ha sido cambiado\n con Exito!");
                    AbrirMenu();
                    Stage stage = (Stage) Btn_CambiaNIP.getScene().getWindow();
                    stage.close();
                }

            }

        }
    }

    public void B_Otro() throws IOException {
        float otro= Float.parseFloat(T_Otro.getText());
        if(PosicionCredito!=-1 ){
            L_Credito.get(PosicionCredito).Credito+=otro;
            System.out.println(L_Credito.get(PosicionCredito).Credito+"--------------------");
        JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                                        "Credito: $"+L_Credito.get(PosicionCredito).Credito);
            AbrirMenu();
            Stage stage = (Stage) Btn_Otro.getScene().getWindow();
            stage.close();
        }
        else if(PosicionDebito!=-1 && L_Debito.get(PosicionDebito).Saldo>=otro){
            L_Debito.get(PosicionDebito).Saldo-=otro;
            System.out.println(L_Debito.get(PosicionDebito).Saldo+"---------------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Saldo Disponible: $"+L_Debito.get(PosicionDebito).Saldo);
            AbrirMenu();
            Stage stage = (Stage) Btn_Otro.getScene().getWindow();
            stage.close();
        }else
            JOptionPane.showMessageDialog(null, "Lo sentimos, No tiene suficiente\nSaldo para retirar");


    }

    public void B_mil() throws IOException {
        if(PosicionCredito!=-1 ){
            L_Credito.get(PosicionCredito).Credito+=1000.0;
            System.out.println(L_Credito.get(PosicionCredito).Credito+"--------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Credito: $"+L_Credito.get(PosicionCredito).Credito);

            AbrirMenu();
            Stage stage = (Stage) Btn_mil.getScene().getWindow();
            stage.close();
        }
        else if(PosicionDebito!=-1 && L_Debito.get(PosicionDebito).Saldo>=1000 ){
            L_Debito.get(PosicionDebito).Saldo-=1000.0;
            System.out.println(L_Debito.get(PosicionDebito).Saldo+"---------------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Saldo Disponible: $"+L_Debito.get(PosicionDebito).Saldo);

        AbrirMenu();
        Stage stage = (Stage) Btn_mil.getScene().getWindow();
        stage.close();
        }else{
            JOptionPane.showMessageDialog(null,"Lo sentimos, No tiene suficiente\nSaldo para retirar");
        }
    }

    public void B_500() throws IOException {
        if(PosicionCredito!=-1 ){
            L_Credito.get(PosicionCredito).Credito+=500.0;
            System.out.println(L_Credito.get(PosicionCredito).Credito+"--------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Credito: $"+L_Credito.get(PosicionCredito).Credito);
            AbrirMenu();
            Stage stage = (Stage) Btn_500.getScene().getWindow();
            stage.close();
        }
        else if(PosicionDebito!=-1 && L_Debito.get(PosicionDebito).Saldo>=500){
            L_Debito.get(PosicionDebito).Saldo-=500;
            System.out.println(L_Debito.get(PosicionDebito).Saldo+"---------------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Saldo Disponible: $"+L_Debito.get(PosicionDebito).Saldo);
            AbrirMenu();
            Stage stage = (Stage) Btn_500.getScene().getWindow();
            stage.close();
        }else
            JOptionPane.showMessageDialog(null,"Lo sentimos, No tiene suficiente\nSaldo para retirar");

    }

    public void B_300() throws IOException {
        if(PosicionCredito!=-1 ){
            L_Credito.get(PosicionCredito).Credito+=300.0;
            System.out.println(L_Credito.get(PosicionCredito).Credito+"--------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Credito: $"+L_Credito.get(PosicionCredito).Credito);
            AbrirMenu();
            Stage stage = (Stage) Btn_300.getScene().getWindow();
            stage.close();
        }
        else if(PosicionDebito!=-1 && L_Debito.get(PosicionDebito).Saldo>=300){
            L_Debito.get(PosicionDebito).Saldo-=300;
            System.out.println(L_Debito.get(PosicionDebito).Saldo+"---------------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Saldo Disponible: $"+L_Debito.get(PosicionDebito).Saldo);

            AbrirMenu();
            Stage stage = (Stage) Btn_300.getScene().getWindow();
            stage.close();
        }else
            JOptionPane.showMessageDialog(null,"Lo sentimos, No tiene suficiente\nSaldo para retirar");

    }

    public void B_200() throws IOException {
        if(PosicionCredito!=-1){
            L_Credito.get(PosicionCredito).Credito+=200.0;
            System.out.println(L_Credito.get(PosicionCredito).Credito+"--------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Credito: $"+L_Credito.get(PosicionCredito).Credito);
            AbrirMenu();
            Stage stage = (Stage) Btn_200.getScene().getWindow();
            stage.close();
        }
        else if(PosicionDebito!=-1 && L_Debito.get(PosicionDebito).Saldo>=200){
            L_Debito.get(PosicionDebito).Saldo-=200;
            System.out.println(L_Debito.get(PosicionDebito).Saldo+"---------------------------");
            JOptionPane.showMessageDialog(null,"Operación realizada con exito\n" +
                    "Saldo Disponible: $"+L_Debito.get(PosicionDebito).Saldo);
            AbrirMenu();
            Stage stage = (Stage) Btn_200.getScene().getWindow();
            stage.close();
        }else
            JOptionPane.showMessageDialog(null,"Lo sentimos, No tiene suficiente\nSaldo para retirar");

    }

    public void B_100() throws IOException {

            if (PosicionCredito != -1 ) {
                L_Credito.get(PosicionCredito).Credito += 100.0;
                System.out.println(L_Credito.get(PosicionCredito).Credito + "--------------------");
                JOptionPane.showMessageDialog(null, "Operación realizada con exito\n" +
                        "Credito: $" + L_Credito.get(PosicionCredito).Credito);
                AbrirMenu();
                Stage stage = (Stage) Btn_100.getScene().getWindow();
                stage.close();
            } else if (PosicionDebito != -1 && L_Debito.get(PosicionDebito).Saldo>=100) {
                L_Debito.get(PosicionDebito).Saldo -= 100;
                System.out.println(L_Debito.get(PosicionDebito).Saldo + "---------------------------");
                JOptionPane.showMessageDialog(null, "Operación realizada con exito\n" +
                        "Saldo Disponible: $" + L_Debito.get(PosicionDebito).Saldo);
                AbrirMenu();
                Stage stage = (Stage) Btn_100.getScene().getWindow();
                stage.close();
            }
        else
            JOptionPane.showMessageDialog(null,"Lo sentimos No cuentas\n Con saldo suficiente...");
    }

    void AbrirMenu() throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("/V_Menu.fxml"));
        stage.setTitle("Bienvenido!");
        stage.show();
    }

    public void B_Ver(ActionEvent actionEvent) {
        if(PosicionCredito!=-1){
            T_Saldo.setText("$ "+(L_Credito.get(PosicionCredito).Credito));
            T_No_Cuenta.setText(String.valueOf(L_Credito.get(PosicionCredito).No_Cuenta));
        }
        if(PosicionDebito!=-1){
            T_Saldo.setText("$ "+(L_Debito.get(PosicionDebito).Saldo));

            T_No_Cuenta.setText(String.valueOf(L_Debito.get(PosicionDebito).No_Cuenta));
        }
    }

    public void B_SaldoAct() {
        if(PosicionCredito!=-1){
            T_SaldoAct.setText(String.valueOf(L_Credito.get(PosicionCredito).Credito));
        }
        else if(PosicionDebito!=-1){
            T_SaldoAct.setText(String.valueOf(L_Debito.get(PosicionDebito).Saldo));
        }
    }
}
