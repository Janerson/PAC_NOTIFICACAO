/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dom.notificacao.config;

import com.dom.notificacao.controller.FormNotifController;
import com.dom.notificacao.model.entity.Notificacao;
import insidefx.undecorator.UndecoratorScene;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.net.URL;

/**
 *
 * @author Herudi
 */
public class Config {


    private double xOffset = 0;
    private double yOffset = 0;

    public Config() {
    }

    private final Image icone = new Image(getClass().getResourceAsStream("icone.png"));
    
      public void loadStage(Stage stage, Label lb, URL load, String judul, boolean resize, StageStyle style, boolean maximized){
        try {
            Stage st = new Stage();
            stage = (Stage) lb.getScene().getWindow();
            Region root = FXMLLoader.load(load);
            Scene scene = new Scene(root);

            st.initStyle(style);
            //st.setResizable(resize);
            // st.setMaximized(maximized);
            st.setTitle(judul);
            st.setScene(scene);
            st.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void LoadMain(Button lb, URL load){
        try {

            final Stage st = new Stage();
            Stage stage = (Stage) lb.getScene().getWindow();
            Parent root = FXMLLoader.load(load);
            st.initStyle(StageStyle.UNDECORATED);


            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    st.setX(e.getScreenX() - xOffset);
                    st.setY(e.getScreenY() - yOffset);
                }
            });
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    xOffset = e.getSceneX();
                    yOffset = e.getSceneY();
                }
            });

            st.getIcons().add(icone);
            st.setScene(new Scene(root));
            st.show();
            stage.close();
        } catch (Exception e) {
           e.printStackTrace();
        } 
    }

    public void formNotificacao(Stage owner, URL load, Notificacao n){
        try {


            Stage st = new Stage();
            Region root = FXMLLoader.load(load);
            st.initModality(Modality.APPLICATION_MODAL);
            st.initOwner(owner);
            st.initStyle(StageStyle.UNDECORATED);
            UndecoratorScene scene = new UndecoratorScene(st , root);
            st.setResizable(false);

            st.setScene(scene);
            st.show();

            if(n!= null){
                FormNotifController.controller.changeNotificacao(n);
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void formAbout(final Stage owner, URL load, Effect e) {
        try {


            Stage st = new Stage();
            Region root = FXMLLoader.load(load);
            owner.getScene().getRoot().setEffect(e);
            st.initModality(Modality.APPLICATION_MODAL);
            st.initOwner(owner);
            st.initStyle(StageStyle.UNDECORATED);
            UndecoratorScene scene = new UndecoratorScene(st, root);
            st.setResizable(false);

            st.setScene(scene);
            st.show();

            st.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    owner.getScene().getRoot().setEffect(null);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static AnchorPane loadNode(URL fxml){
        AnchorPane no = null;
        try {
            no = FXMLLoader.load(fxml);
        }catch (Exception e){
            e.printStackTrace();
        }
        return no;
    }


}
