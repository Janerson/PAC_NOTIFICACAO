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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

  public static ObservableList getXYSeries(List<Notificacao> notif){
      ObservableList<XYChart.Series> series = FXCollections.observableArrayList();
      Set<Date> distinctSet = new HashSet<>();
      XYChart.Series<Date , Number> masc = new XYChart.Series();
      masc.setName("MASCULINO");
      XYChart.Series<Date , Number> fem = new XYChart.Series();
      fem.setName("FEMININO");

      //Pegando as Datas das notificações
      for (Notificacao n : notif){
          distinctSet.add(n.getHoje());
      }

      //Iterando sobre as datas
      for(Date d : distinctSet){
          int contM=0 , contF=0;
          for (Notificacao n : notif){
              //comparando a data da iteração com a data de noficação
            if(n.getHoje().compareTo(d)==0){
                //separando as notificações por sexo
                if(n.getPaciente().getSexo().equalsIgnoreCase("MASCULINO")){
                    contM++;
                }else contF++;
            }
          }
          masc.getData().add(new XYChart.Data<Date, Number>(d,contM));
          fem.getData().add(new XYChart.Data<Date, Number>(d,contF));
      }
      series.addAll(masc,fem);
        return series;
    }
    public static ObservableList getXYSeriesBarChart(List<Notificacao> notif){
     final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        ObservableList<XYChart.Series> series = FXCollections.observableArrayList();

        Set<Date> distinctSet = new TreeSet<>();
        XYChart.Series<String , Number> masc = new XYChart.Series();
        masc.setName("MASCULINO");
        XYChart.Series<String , Number> fem = new XYChart.Series();
        fem.setName("FEMININO");

        //Pegando as Datas das notificações
        for (Notificacao n : notif){
            distinctSet.add(n.getHoje());
        }

        //Iterando sobre as datas
        for(Date d : distinctSet){
            int contM=0 , contF=0;
            for (Notificacao n : notif){
                //comparando a data da iteração com a data de noficação
                if(n.getHoje().compareTo(d)==0){
                    //separando as notificações por sexo
                    if(n.getPaciente().getSexo().equalsIgnoreCase("MASCULINO")){
                        contM++;
                    }else contF++;
                }
            }
            masc.getData().add(new XYChart.Data<String, Number>(sdf.format(d),contM));
            fem.getData().add(new XYChart.Data<String, Number>(sdf.format(d),contF));
        }
        series.addAll(masc,fem);
        return series;
    }

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
    public static void showPieValue(PieChart chart) {
//       for (Node node : chart.lookupAll("Text.chart-pie-label")){
//           if(node instanceof Text){
//               for (PieChart.Data data : chart.getData()){
//                   if (data.getName().equalsIgnoreCase(((Text) node).getText())){
//                       ((Text) node).setText(String.format("%,.0f", data.getPieValue()));
//                       System.out.println("Data: "+((Text) node).getText());
//                   }
//               }
//           }
//       }
        for (Node node : chart.lookupAll("Text.chart-pie-label")) {
            if(node instanceof Text) {
              for(PieChart.Data data : chart.getData()) {
                 if(data.getName().equals(((Text) node).getText())) ;
                   ((Text) node).setText(String.format("%,.0f", data.getPieValue()));
              }
            }
        }
    }

    public static void pierChartCSSLegendItem(PieChart pieChart){
        int i = 0;
        ObservableList<PieChart.Data> data = pieChart.getData();
        for (Node node : pieChart.lookupAll(".chart-legend-item")) {
            if (node instanceof Label && ((Label) node).getGraphic() != null) {
                ((Label) node).setText(data.get(i).getName()+" - "+data.get(i).getPieValue());
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
