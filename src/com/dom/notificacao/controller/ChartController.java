package com.dom.notificacao.controller;

import com.dom.notificacao.model.entity.DateAxis;
import com.dom.notificacao.model.entity.Notificacao;
import com.dom.notificacao.model.helper.ValidationHelper;
import com.dom.notificacao.model.util.GraficoUtil;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DOM on 05/06/2015.
 * Email: douglas.janerson@gmail.com
 * Project: PAC_NOTIFICACAO.
 */
public class ChartController implements Initializable {

    @FXML
    private BorderPane bdSexo;
    @FXML
    private AnchorPane chartPane;
    @FXML
    private TabPane tabChart;

    private static BarChart barChart = new BarChart(new CategoryAxis() , new NumberAxis());
    private static LineChart lineChart = new LineChart<>(new DateAxis(), new NumberAxis());
    private static PieChart pieChart = new PieChart();
    private  ObservableList<Notificacao> listaNotificacao = FXCollections.observableArrayList();

    private SimpleBooleanProperty isOpen = new SimpleBooleanProperty(false);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaNotificacao = TableViewController.tbController.getMasterData();
    }

    @FXML
    public void pierChart(){
        int index = tabChart.getSelectionModel().getSelectedIndex();
        switch (index){
            case 0:
                setPierChart(pieChart,bdSexo,GraficoUtil.notificacaoSexo(listaNotificacao),"Notificação por Sexo");
                break;

        }

        showTypeOfChart();
    }
    @FXML
    public void lineChart(){
        int index = tabChart.getSelectionModel().getSelectedIndex();
        switch (index){
            case 0:
                setLineChart(lineChart, bdSexo,  "Notificação por Sexo" ,GraficoUtil.getXYSeries(listaNotificacao));
                break;

        }

        showTypeOfChart();
    }


    @FXML
    public void saveAsImage(){
        AnchorPane pane = (AnchorPane) tabChart.getSelectionModel().getSelectedItem().getContent();
        WritableImage image = pane.snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("IMAGE files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        File images = new File(file.getAbsoluteFile()+".png");

        try {
            if (file != null) {

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", images);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showTypeOfChart(){
        final TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), chartPane);

        if (!isOpen.get()) {
            tt.setByX(0);
            tt.setToX(-210);
            tt.play();
            isOpen.set(true);
        } else {
            tt.setByX(-210);
            tt.setToX(0);
            tt.play();
            isOpen.set(false);
        }
    }
    private void changeChart(Node chart , BorderPane borderPane ){
        try {
                borderPane.setCenter(chart);
        }catch (Exception e){
           e.printStackTrace();
        }
    }
    private void setPierChart(PieChart p , BorderPane bd  , ObservableList<PieChart.Data> data , String chartTitle){
        try {
            if(!GraficoUtil.pierChartIsEmpty(data)){
                p.setClockwise(true);
                p.setStartAngle(45);
                p.setTitle(chartTitle);
                changeChart(p , bd);
                p.setData(data);
                showTypeOfChart();
                GraficoUtil.pierChartCSS(p);
                GraficoUtil.pierChartCSSLegendItem(p);
                showTypeOfChart();
            }else{
                ValidationHelper.showInformation("Sem dados para serem exibidos" , "Sem dados");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void setLineChart(LineChart l , BorderPane p , String title , ObservableList<XYChart.Series>serie){
        l.getData().clear();
        try {

                l.setTitle(title);
                changeChart(l , p);
                showTypeOfChart();
                l.getData().addAll(serie);
                GraficoUtil.lineChartCSS(l);
                showTypeOfChart();

        }catch (Exception e){
       e.printStackTrace();
        }
    }
}