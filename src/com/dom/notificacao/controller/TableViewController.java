package com.dom.notificacao.controller;

import com.dom.notificacao.model.entity.Notificacao;
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
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
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
    private final SimpleDateFormat FORMART_BR = new SimpleDateFormat("dd/MM/yyyy");
    private ObservableList<Notificacao> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tbController = this;
        tbPaciente.setItems(masterData);
        initTableColumn();
        initFilter();

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
        masterData.clear();
        masterData.addAll(obs);
    }

    public ObservableList<Notificacao> getMasterData() {
        return masterData;
    }

    private class ButtonCell extends TableCell<Object, Boolean> {
        final Hyperlink cellButtonDelete = new Hyperlink("Deletar");
        final Hyperlink cellButtonEdit = new Hyperlink("Editar");
        final HBox hb = new HBox();

        ButtonCell(final TableView<Notificacao> tblView){
            cellButtonDelete.setStyle("-fx-text-fill: #2365b4");
            cellButtonEdit.setStyle("-fx-text-fill: #2365b4");
            hb.getChildren().addAll(cellButtonDelete , cellButtonEdit);
            hb.setSpacing(4);

            //TODO - Tratar Action
            cellButtonDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int row = getTableRow().getIndex();
                    tblView.getSelectionModel().select(row);
                    Notificacao n = tblView.getSelectionModel().getSelectedItem();

                    System.out.println("Nome: "+n.getPaciente().getNome());

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
