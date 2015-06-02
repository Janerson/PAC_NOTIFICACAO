/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dom.notificacao.controller;

import com.dom.notificacao.config.Config;
import com.dom.notificacao.config.UserSingleton;
import com.dom.notificacao.model.dao.entitydao.UserDAO;
import com.dom.notificacao.model.entity.User;
import com.dom.notificacao.model.helper.FxmlHelper;
import com.dom.notificacao.model.helper.ValidationHelper;
import com.dom.notificacao.view.animation.FadeInLeftTransition;
import com.dom.notificacao.view.animation.FadeInLeftTransition1;
import com.dom.notificacao.view.animation.FadeInRightTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Herudi
 */
public class controllLogin implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Text lblWelcome;
    @FXML
    private Text lblUserLogin;
    @FXML
    private Text lblUsername;
    @FXML
    private Text lblPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Text lblRudyCom;
    @FXML 
    private Label lblClose;        
    Stage stage;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new FadeInRightTransition(lblUserLogin).play();
                new FadeInLeftTransition(lblWelcome).play();
                new FadeInLeftTransition1(lblPassword).play();
                new FadeInLeftTransition1(lblUsername).play();
                new FadeInLeftTransition1(txtUsername).play();
                new FadeInLeftTransition1(txtPassword).play();
                new FadeInRightTransition(btnLogin).play();
                lblClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Platform.exit();
                        System.exit(0);
                    }
                });
            }
        });

    }    


    @FXML
    private void aksiLogin(ActionEvent event) {
        User user = login(txtUsername.getText() , txtPassword.getText() , new UserDAO());

        if (user!= null) {
            UserSingleton.getInstance().setUser(user);
            Config c = new Config();
            c.LoadMain(btnLogin, FxmlHelper.loadFxml("FormMain"));
        }else{
       ValidationHelper.showWarning("Usuário ou senha incorreta", "Usuário", "Ops");
        }
    }

    private User login(String user , String pass , UserDAO dao){
        return  dao.getLogin(user , pass);
    }

}
