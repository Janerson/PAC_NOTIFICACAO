package com.dom.notificacao.controller;

import com.dom.notificacao.config.Config;
import com.dom.notificacao.config.UserSingleton;
import com.dom.notificacao.model.dao.entitydao.UserDAO;
import com.dom.notificacao.model.entity.User;
import com.dom.notificacao.view.fxml.FxmlHelper;
import com.dom.notificacao.model.helper.ValidationHelper;
import com.dom.notificacao.validator.BeanCheckConstraints;
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

import javax.validation.ConstraintViolation;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by DOM on 23/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class CadUserController implements Initializable {
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
    private Label lblClose;

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

        UserDAO dao = new UserDAO();
        User user = new User();
        user.setUsername(txtUsername.getText());
        user.setPassword(txtPassword.getText());
        UserSingleton.getInstance().setUser(user);
        Set<ConstraintViolation<Object>> obj = new BeanCheckConstraints<>(user).validar();
        try {
            if(ValidationHelper.validarBean(obj,user,dao)){
                Config c = new Config();
                c.LoadMain(btnLogin, FxmlHelper.loadFxml("FormMain"));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            ValidationHelper.showException(e , "Ops, há algo de errado aqui.",user);

        }

    }
}
