package com.dom.notificacao.controller;

import com.dom.notificacao.model.entity.Notificacao;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.util.ArrayList;
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
    private ObservableList<Notificacao> filteredData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Tableview Iniciada...");
        tbController = this;
        filteredData.addAll(masterData);
        tbPaciente.setItems(filteredData);

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
        masterData.addListener(new ListChangeListener<Notificacao>() {
            @Override
            public void onChanged(Change<? extends Notificacao> change) {
                updateFilteredData();
            }
        });

        ctfPesquisa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                updateFilteredData();
            }
        });


    }
    public void preencherTabela(ObservableList<Notificacao> obs){
        masterData.clear();
        masterData.addAll(obs);
    }
    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    private void updateFilteredData() {
        filteredData.clear();

        for (Notificacao p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    /**
     * Returns true if the person matches the current filter. Lower/Upper case
     * is ignored.
     *
     * @param n
     * @return
     */
    private boolean matchesFilter(Notificacao n) {
        String filterString = ctfPesquisa.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (n.getPaciente().getNome().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Notificacao, ?>> sortOrder = new ArrayList<>(tbPaciente.getSortOrder());
        tbPaciente.getSortOrder().clear();
        tbPaciente.getSortOrder().addAll(sortOrder);
    }

    public TableView<Notificacao> getTbPaciente() {
        return tbPaciente;
    }

    public ObservableList<Notificacao> getMasterData() {
        return masterData;
    }
}
