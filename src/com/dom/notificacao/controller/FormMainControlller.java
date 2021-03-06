package com.dom.notificacao.controller;

import com.dom.notificacao.Services.TableService;
import com.dom.notificacao.config.Config;
import com.dom.notificacao.config.UserSingleton;
import com.dom.notificacao.model.dao.entitydao.NotificacaoDAO;
import com.dom.notificacao.model.entity.User;
import com.dom.notificacao.view.fxml.FxmlHelper;
import com.dom.notificacao.view.LoadResource;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by DOM on 22/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class FormMainControlller extends AboutController implements Initializable {

    public static FormMainControlller frmController;
    private Stage st;
    private boolean maximized;
    private BoundingBox savedBounds;

    @FXML private Region veil;
    @FXML private ProgressBar progressBar;
    @FXML private Text txtStatus;
    @FXML private ListView<Label> listMenu;
    @FXML private BorderPane borderPane;

    private NotificacaoDAO dao;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new NotificacaoDAO();
        frmController = this;
        initList();
        initTable();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                st = (Stage) veil.getScene().getWindow();
                user = UserSingleton.getInstance().getUser();
                listService();
            }
        });
    }

    /**
     * Inicializa a lista de menu.
     */
    private void initList(){
        Label home = new Label("  INICÍO" , new ImageView(LoadResource.HOME));
        Label about = new Label("  SOBRE" , new ImageView(LoadResource.ABOUT));
        Label chart = new Label("  GRÁFICOS" , new ImageView(LoadResource.CHART));
        home.setStyle("-fx-text-fill: white");
        about.setStyle("-fx-text-fill: white");
        chart.setStyle("-fx-text-fill: white");

        listMenu.getItems().addAll(home,chart , about);
    }
    /**
     * Inicializa a tabela
     */
    private void initTable(){
        borderPane.setCenter(Config.loadNode(FxmlHelper.loadFxml("TableView")));

    }

    public void listService(){
        final TableService service = new TableService(user , new NotificacaoDAO());
        progressBar.progressProperty().bind(service.progressProperty());
        progressBar.visibleProperty().bind(service.runningProperty());
        txtStatus.visibleProperty().bind(service.runningProperty());
        veil.visibleProperty().bind(service.runningProperty());

        service.start();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                TableViewController.tbController.getMasterData().clear();
                //TableViewController.tbController.getMasterData().addAll(service.getValue());
                TableViewController.tbController.preencherTabela(service.getValue());
            }
        });

    }

    public void listMouseClick(MouseEvent me){
        Config c = new Config();
        switch (listMenu.getSelectionModel().getSelectedIndex()){
            case 0:
                borderPane.setCenter(Config.loadNode(FxmlHelper.loadFxml("TableView")));
                listService();
                break;
            case 1:
                borderPane.setCenter(Config.loadNode(FxmlHelper.loadFxml("Chart")));
                break;
            case 2:
                c.formAbout(st , FxmlHelper.loadFxml("about"), new BoxBlur(3 , 3 ,3));
                break;
        }
    }


    public void minimize() {
        st.setIconified(true);
    }
    /**
     * Maximizar ou Restaurar
     */
    public void maximizeOrRestore() {


        if (maximized) {
            restoreSavedBounds(st);
            savedBounds = null;
            maximized = false;
        } else {
            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(st.getX(), st.getY(), st.getWidth(), st.getHeight());
            Screen screen = screensForRectangle.get(0);
            Rectangle2D visualBounds = screen.getVisualBounds();

            savedBounds = new BoundingBox(st.getX(), st.getY(), st.getWidth(), st.getHeight());

            st.setX(visualBounds.getMinX());
            st.setY(visualBounds.getMinY());
            st.setWidth(visualBounds.getWidth());
            st.setHeight(visualBounds.getHeight());
            maximized = true;
        }
    }
    public void restoreSavedBounds(Stage stage) {
        stage.setX(savedBounds.getMinX());
        stage.setY(savedBounds.getMinY());
        stage.setWidth(savedBounds.getWidth());
        stage.setHeight(savedBounds.getHeight());
        savedBounds = null;
    }
    /**
     * Fecha o Stage
     */
    public void stageClose() {
        System.exit(0);
    }
    /**
     * Altera para Fullscreen
     */
    public void setFullscreen() {
        if (st.isFullScreen()) {
            st.setFullScreen(false);
        } else {
            st.setFullScreen(true);
        }
    }
    public void stageEffect(Effect e){
        st.getScene().getRoot().setEffect(e);
    }
}
