package com.dom.notificacao.controller;

import com.dom.notificacao.config.UserSingleton;
import com.dom.notificacao.model.dao.entitydao.NotificacaoDAO;
import com.dom.notificacao.model.entity.Notificacao;
import com.dom.notificacao.model.entity.Sexo;
import com.dom.notificacao.model.entity.User;
import com.dom.notificacao.model.helper.ValidationHelper;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by DOM on 23/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class FormNotifController implements Initializable{

    public static FormNotifController controller;

    @FXML
    private DatePicker dpHoje , dpNascimento;

    @FXML
    private TextField tfPaciente;
//
//    @FXML
//    private DatePicker dpNascimento;

    @FXML
    private TextField tfNotificacao;

    @FXML
    private TextField tfCID;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfResponsavel;

    @FXML
    private ComboBox comboSexo;


    private User user;
    private  ObjectProperty<Notificacao> notificacaoSimpleObjectProperty = new SimpleObjectProperty<>();





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        controller = this;
        init();
        textFieldToUpperCase();
    }

    public void notificar(){
        NotificacaoDAO dao = new NotificacaoDAO();
        Notificacao n = notificacaoSimpleObjectProperty.get();
        if(n!=null){

            n.setHoje(dpHoje.getSelectedDate());
            n.getPaciente().setNome(tfPaciente.getText());
            n.getPaciente().setDataNascimento(dpNascimento.getSelectedDate());
            n.getPaciente().setSexo(comboSexo.getValue().toString());
            n.setNotification(tfNotificacao.getText());
            n.setCid(tfCID.getText());
            n.setBairro(tfBairro.getText());
            n.getResponsavel().setNome(tfResponsavel.getText());
        }else{
            n = new Notificacao();
            n.setHoje(dpHoje.getSelectedDate());
            n.getPaciente().setNome(tfPaciente.getText());
            n.getPaciente().setDataNascimento(dpNascimento.getSelectedDate());
            n.getPaciente().setSexo(comboSexo.getValue().toString());
            n.setNotification(tfNotificacao.getText());
            n.setCid(tfCID.getText());
            n.setBairro(tfBairro.getText());
            n.getResponsavel().setNome(tfResponsavel.getText());
        }

        user.notificar(n,dao);
        refresh();
        limpar();

    }

    private void refresh(){
        NotificacaoDAO dao = new NotificacaoDAO();
        TableViewController.tbController.preencherTabela(user.listar(dao));
    }

    public void cancelar(){
        Stage s = (Stage) tfBairro.getScene().getWindow();
        s.close();
        FormMainControlller.frmController.stageEffect(null);
    }

    public void limpar(){
        Date d = new Date();
        dpHoje.setSelectedDate(d);
        tfPaciente.clear();
        dpNascimento.setSelectedDate(null);
        tfNotificacao.clear();
        tfCID.clear();
        tfBairro.clear();
        tfResponsavel.clear();
    }
    public void deletar(){
        NotificacaoDAO dao = new NotificacaoDAO();
        user.deletar(dao, notificacaoSimpleObjectProperty.get());
        notificacaoSimpleObjectProperty.setValue(null);
        refresh();

    }

    public void textFieldToUpperCase(){
        ValidationHelper.TextFieldToUpperCase(tfPaciente);
        ValidationHelper.TextFieldToUpperCase(tfNotificacao);
        ValidationHelper.TextFieldToUpperCase(tfCID);
        ValidationHelper.TextFieldToUpperCase(tfBairro);
        ValidationHelper.TextFieldToUpperCase(tfResponsavel);
    }

    void init(){

        comboSexo.getItems().addAll(Arrays.asList(Sexo.values()));
        user = UserSingleton.getInstance().getUser();
        Date hoje = new Date();
        dpHoje.setSelectedDate(hoje);

    }
    public void changeNotificacao(Notificacao n){
        System.out.println("Value alterado");
        notificacaoSimpleObjectProperty.setValue(n);
    }

}
