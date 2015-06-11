package com.dom.notificacao;

import com.dom.notificacao.view.fxml.FxmlHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Created by DOM on 21/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Image icone = new Image(getClass().getResourceAsStream("icone.png"));
        Region root = FXMLLoader.load(FxmlHelper.loadFxml("splash"));
        //Java 8
       // UndecoratorScene scene = new UndecoratorScene(primaryStage , root);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icone);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
