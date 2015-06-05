package com.dom.notificacao.model.util;

import com.dom.notificacao.model.entity.Notificacao;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by DOM on 19/01/2015.
 */
public class GraficoUtil {


    public static boolean pierChartIsEmpty(ObservableList<PieChart.Data> l){
        double value = 0;

        for(PieChart.Data d : l){
            value += d.getPieValue();
        }
        if(value > 0){
            return false;
        }
        return true;
    }

    /*Lista de Sexo*/
    public static ObservableList notificacaoSexo(List<Notificacao> lista){
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        double m = 0 , f =0;
        for(Notificacao n : lista){

            if(n.getPaciente().getSexo().equalsIgnoreCase("MASCULINO")){
                m+=1;
            }else{
                f+=1;
            }
        }
        data.add(new PieChart.Data("MASCULINO",m));
        data.add(new PieChart.Data("FEMININO",f));
        return data;
    }
    //TODO - Tratar filtro Sexo por data
 /*   public static ObservableList getXYSeries(List<Notificacao> listNotas){


    }*/

    /**
     * Calcula a % de uma <b>fração</> do valor <b>total</b>.
     * @param total
     * @param fracao
     * @return
     */
    private static double percentual(int total , double fracao){
        String str = new DecimalFormat("0.00").format((fracao/total)*100);
        return Double.parseDouble(str.replace(",","."));
    }

    /**
     * Tab Faltas
     * @param listNotas
     * @return ObservableList<PieChart.Data>
     */
    public static ObservableList populatePierchartFaltas( List<Notificacao> listNotas){
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        /*listNotas.forEach(n ->{
            data.add(new PieChart.Data(n.getDisciplinas().getDisciplina(), n.getTotalFaltas()));
        });*/
        return data;
    }

    /*BarChart CSS*/
    public static void barChartCSS(BarChart barChart){
        /**
         * Set Series color
         */
        for (int i = 0; i < barChart.getData().size(); i++)
            for (Node node : barChart.lookupAll(".series" + i)) {
                node.getStyleClass().remove("default-color"+(i));
                node.getStyleClass().add("default-color" + (i));
            }

        /**
         * Set Legend items color
         */
        int i = 0;
        for (Node node : barChart.lookupAll(".chart-legend-item")) {
            if (node instanceof Label && ((Label) node).getGraphic() != null) {
                ((Label) node).getGraphic().getStyleClass().remove("default-color" + i);
                ((Label) node).getGraphic().getStyleClass().add("default-color" +i);
            }
            i++;
        }
    }
    /*LineChart CSS*/
    public static void lineChartCSS(LineChart lineChart){
        /**
         * Set Series color
         */
        for (int i = 0; i < lineChart.getData().size(); i++)
            for (Node node : lineChart.lookupAll(".series" + i)) {
                node.getStyleClass().remove("default-color"+(i));
                node.getStyleClass().add("default-color" + (i));
            }


    }
    /*PierChart CSS*/
    public static void pierChartCSS(PieChart pieChart){
        int i = 0;
        for (PieChart.Data data : pieChart.getData()) {
            data.setName(data.getName()+" : "+data.getPieValue());
            data.getNode().getStyleClass().remove("default-color" + i);
            data.getNode().getStyleClass().add("default-color" + (i));
            i++;
        }
    }
    public static void pierChartCSSLegendItem(PieChart pieChart){
        int i = 0;
        for (Node node : pieChart.lookupAll(".chart-legend-item")) {
            if (node instanceof Label && ((Label) node).getGraphic() != null) {
                ((Label) node).getGraphic().getStyleClass().remove("default-color" + i);
                ((Label) node).getGraphic().getStyleClass().add("default-color" +i);
            }
            i++;
        }
    }
    /*Animation PieChart*/
    static class MouseHoverAnimation implements EventHandler<MouseEvent> {
        static final Duration ANIMATION_DURATION = new Duration(500);
        static final double ANIMATION_DISTANCE = 0.15;
        private double cos;
        private double sin;
        private PieChart chart;

        public MouseHoverAnimation(PieChart.Data d, PieChart chart) {
            this.chart = chart;
            double start = 0;
            double angle = calcAngle(d);
            for( PieChart.Data tmp : chart.getData() ) {
                if( tmp == d ) {
                    break;
                }
                start += calcAngle(tmp);
            }

            cos = Math.cos(Math.toRadians(0 - start - angle / 2));
            sin = Math.sin(Math.toRadians(0 - start - angle / 2));
        }

        @Override
        public void handle(MouseEvent arg0) {
            Node n = (Node) arg0.getSource();

            double minX = Double.MAX_VALUE;
            double maxX = Double.MAX_VALUE * -1;

            for( PieChart.Data d : chart.getData() ) {
                minX = Math.min(minX, d.getNode().getBoundsInParent().getMinX());
                maxX = Math.max(maxX, d.getNode().getBoundsInParent().getMaxX());
            }

            double radius = maxX - minX;

            TranslateTransition tt = new TranslateTransition(ANIMATION_DURATION,n);
            tt.setToX((radius *  ANIMATION_DISTANCE) * cos);
            tt.setToY((radius *  ANIMATION_DISTANCE) * sin);
            tt.play();
        }

        private static double calcAngle(PieChart.Data d) {
            double total = 0;
            for( PieChart.Data tmp : d.getChart().getData() ) {
                total += tmp.getPieValue();
            }

            return 360 * (d.getPieValue() / total);
        }
    }
    static class MouseExitAnimation implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {

            TranslateTransition tt = new TranslateTransition(new Duration(500),(Node) event.getSource());
            tt.setToX(0);
            tt.setToY(0);
            tt.play();
        }
    }
}
