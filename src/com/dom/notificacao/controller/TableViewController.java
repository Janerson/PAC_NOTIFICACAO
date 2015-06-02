package com.dom.notificacao.controller;

import com.dom.notificacao.model.entity.Notificacao;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
                return new SimpleObjectProperty<String>(n.getValue().getResponsavel().getNome());
            }
        });
        initFilter();

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
}
