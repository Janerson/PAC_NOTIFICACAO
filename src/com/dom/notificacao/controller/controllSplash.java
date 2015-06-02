/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dom.notificacao.controller;


import com.dom.notificacao.config.Config;
import com.dom.notificacao.model.dao.entitydao.UserDAO;
import com.dom.notificacao.model.entity.User;
import com.dom.notificacao.model.helper.FxmlHelper;
import com.dom.notificacao.model.util.HibernateUtil;
import com.dom.notificacao.view.animation.FadeInLeftTransition;
import com.dom.notificacao.view.animation.FadeInRightTransition;
import com.dom.notificacao.view.animation.FadeInTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Herudi
 */
public class controllSplash implements Initializable {
    @FXML
    private Text lblWelcome;
    @FXML
    private Text lblRudy;
    @FXML
    private VBox vboxBottom;
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
        longStart();
        lblClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
    }   


    private void longStart() {
        Service<Session> service = new Service<Session>() {
            @Override
            protected Task<Session> createTask() {
                return new Task<Session>() {
                    @Override
                    protected Session call() throws Exception {
                        int max = HibernateUtil.getSession().getCacheMode().ordinal();
                        updateProgress(0, max);
                        for (int k = 0; k < max; k++) {
                            Thread.sleep(500);
                            updateProgress(k+1, max);
                        }
                        return HibernateUtil.getSession();
                    }
                };
            }
        };
        service.start();

        service.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                new FadeInLeftTransition(lblWelcome).play();
                new FadeInRightTransition(lblRudy).play();
                new FadeInTransition(vboxBottom).play();
            }
        });
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                if(!getUser().isEmpty()){

                    Config config = new Config();
                    config.loadStage(stage, lblClose, FxmlHelper.loadFxml("login"), "Olá Mundo", true, StageStyle.UNDECORATED, false);
                }else{
                    Config config = new Config();
                    config.loadStage(stage, lblClose, FxmlHelper.loadFxml("CadUser"), "Olá Mundo", true, StageStyle.UNDECORATED, false);
                }
            }
        });



    }

    private List<User> getUser(){
        return new UserDAO().getAll();
    }

}
