package com.dom.notificacao.controller;

import com.dom.notificacao.config.Config;
import com.dom.notificacao.config.UserSingleton;
import com.dom.notificacao.model.dao.entitydao.NotificacaoDAO;
import com.dom.notificacao.model.entity.Notificacao;
import com.dom.notificacao.model.entity.User;
import com.dom.notificacao.model.helper.ValidationHelper;
import com.dom.notificacao.view.fxml.FxmlHelper;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import jfxtras.labs.dialogs.DialogFX;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by DOM on 29/05/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class TableViewController implements Initializable {

    public static TableViewController tbController;

    @FXML
    private TableView<Notificacao> tbPaciente;

    @FXML TableColumn tbAction;

    @FXML
    private TableColumn<Notificacao, String> colDataNotificacao;

    @FXML
    private TableColumn<Notificacao, String> colPaciente;

    @FXML
    private TableColumn<Notificacao, String> colSexo;

    @FXML
    private TableColumn<Notificacao, String> colDataNascimento;

    @FXML
    private TableColumn<Notificacao, String> colNotificacao;

    @FXML
    private TableColumn<Notificacao, String> colCID;

    @FXML
    private TableColumn<Notificacao, String> colBairro;

    @FXML
    private TableColumn<Notificacao, String> colResposavel;

    @FXML private TextField ctfPesquisa;

    private DatePicker dpDE = new DatePicker();
    private DatePicker dpATE = new DatePicker();
    private boolean isMenu = false;
    @FXML private AnchorPane apSlide;
    @FXML private GridPane gridPane;

    User user;

    private final SimpleDateFormat FORMART_BR = new SimpleDateFormat("dd/MM/yyyy");
    private ObservableList<Notificacao> masterData = FXCollections.observableArrayList();
    private Config c = new Config();
    private Stage st;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbController = this;
        tbPaciente.setItems(masterData);
        initTableColumn();
        initFilter();
        gridPane.add(dpDE,0,1);
        gridPane.add(dpATE,0,3);
        user = UserSingleton.getInstance().getUser();
    }
    @FXML
    public void showFilterSlide(){
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), apSlide);
        tt.setAutoReverse(true);
        if(isMenu == false){
            tt.setFromX(0);
            tt.setToX(-230);
            tt.play();
            isMenu = true;
        }else{
            tt.setFromX(-230);
            tt.setToX(0);
            tt.play();
            isMenu = false;
        }
    }

    //TODO - Validar Dados de entrada, tratar NPE.
    @FXML
    public void filterBetweenDate(){
        Date end = dpATE.getSelectedDate();
        Date start = dpDE.getSelectedDate();
        tbPaciente.setItems(user.listBetweenDate(new NotificacaoDAO() , start , end));
        dpDE.setSelectedDate(null);
        dpATE.setSelectedDate(null);
    }

    private void initTableColumn(){
        tbAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object , Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        tbAction.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                return new ButtonCell(tbPaciente);
            }
        });
        colDataNotificacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Notificacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Notificacao, String> n) {
                return new SimpleObjectProperty<>(FORMART_BR.format(n.getValue().getHoje()));
            }
        });

        colPaciente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Notificacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Notificacao, String> n) {
                return new SimpleObjectProperty<>(n.getValue().getPaciente().getNome());
            }
        });

        colSexo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Notificacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Notificacao, String> n) {
                return new SimpleObjectProperty<>(n.getValue().getPaciente().getSexo());
            }
        });

        colDataNascimento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Notificacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Notificacao, String> n) {
                return new SimpleObjectProperty<>(FORMART_BR.format(n.getValue().getPaciente().getDataNascimento()));
            }
        });

        colNotificacao.setCellValueFactory(new PropertyValueFactory<Notificacao, String>("notification"));

        colCID.setCellValueFactory(new PropertyValueFactory<Notificacao, String>("cid"));

        colBairro.setCellValueFactory(new PropertyValueFactory<Notificacao, String>("bairro"));

        colResposavel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Notificacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Notificacao, String> n) {
                return new SimpleObjectProperty<>(n.getValue().getResponsavel().getNome());
            }
        });
    }
    private void initFilter(){
        ValidationHelper.TextFieldToUpperCase(ctfPesquisa);
        ctfPesquisa.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(ctfPesquisa.textProperty().get().isEmpty()){
                    tbPaciente.setItems(masterData);
                    return;
                }

                ObservableList<Notificacao> tableItem = FXCollections.observableArrayList();
                ObservableList<TableColumn<Notificacao, ?>>cols = tbPaciente.getColumns();

                for(int i=0; i<masterData.size(); i++){
                    for(int j=0; j<cols.size(); j++){
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(masterData.get(i)).toString();
                        if(cellValue.toLowerCase().contains(ctfPesquisa.textProperty().get().toLowerCase())){
                            tableItem.add(masterData.get(i));
                            break;
                        }
                    }
                }
                tbPaciente.setItems(tableItem);
            }
        });
    }
    public void preencherTabela(ObservableList<Notificacao> obs){
        masterData.removeAll();
        //masterData.addAll(obs);
        //masterData.clear();
        masterData.setAll(obs);
        tbPaciente.getColumns().get(0).setVisible(false);
        tbPaciente.getColumns().get(0).setVisible(true);
        tbPaciente.setItems(obs);
    }

    @FXML
    public void refresh(){
        FormMainControlller.frmController.listService();
    }


    @FXML
    public void notificar(){
        c.formNotificacao( st, FxmlHelper.loadFxml("FormNotificacao") , null);
    }

    public ObservableList<Notificacao> getMasterData() {
        return masterData;
    }

    private class ButtonCell extends TableCell<Object, Boolean> {
        final Hyperlink cellButtonDelete = new Hyperlink("Deletar");
        final Hyperlink cellButtonEdit = new Hyperlink("Editar");
        final HBox hb = new HBox();

        private int showDialog(String title , String question){
            DialogFX dialog = new DialogFX(DialogFX.Type.QUESTION);
            dialog.setTitleText(title);
            dialog.setMessage(question);
            return dialog.showDialog();

            //dialog.
        }

        ButtonCell(final TableView<Notificacao> tblView){
            cellButtonDelete.setStyle("-fx-text-fill: #2365b4");
            cellButtonEdit.setStyle("-fx-text-fill: #2365b4");
            hb.getChildren().addAll(cellButtonDelete , cellButtonEdit);
            hb.setSpacing(4);

            cellButtonDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int row = getTableRow().getIndex();
                    tblView.getSelectionModel().select(row);
                    final Notificacao n = tblView.getSelectionModel().getSelectedItem();
                    if(showDialog("Confirmar Exclusão" ,"Deseja realmente excluir este item?") == 0){
                        UserSingleton.getInstance().getUser().deletar(new NotificacaoDAO() , n);
                        tblView.getItems().remove(n);
                    }
                }
            });

            cellButtonEdit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int row = getTableRow().getIndex();
                    tblView.getSelectionModel().select(row);
                    Notificacao n = tblView.getSelectionModel().getSelectedItem();
                    notificar();
                    FormNotifController.controller.changeNotificacao(n);

                }
            });

        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(hb);
            }else{
                setGraphic(null);
            }
        }
    }

}
