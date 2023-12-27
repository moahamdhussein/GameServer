package gameserver;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameServerBase extends AnchorPane {

    protected final Text text;
    protected final CategoryAxis categoryAxis;
    protected final NumberAxis numberAxis;
    protected final BarChart charthBar;
    protected final ToggleButton btnStartStop;

    public GameServerBase() throws IOException {

        text = new Text();
        categoryAxis = new CategoryAxis();
        numberAxis = new NumberAxis(0, 20, 1);
        charthBar = new BarChart(categoryAxis, numberAxis);
        btnStartStop = new ToggleButton();

        setId("AnchorPane");
        setPrefHeight(858.0);
        setPrefWidth(1343.0);

        AnchorPane.setBottomAnchor(text, 526.9296875);
        AnchorPane.setLeftAnchor(text, 18.0);
        text.setLayoutX(18.0);
        text.setLayoutY(313.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Analysis for online players");
        text.setWrappingWidth(1307.23828125);
        text.setFont(new Font(72.0));
        text.setTextAlignment(TextAlignment.CENTER);
        categoryAxis.setSide(javafx.geometry.Side.BOTTOM);

        numberAxis.setLabel("number of player");
        numberAxis.setSide(javafx.geometry.Side.LEFT);

        charthBar.setCategoryGap(2.0);
        charthBar.setLayoutX(237.0);
        charthBar.setLayoutY(363.0);
        charthBar.setMaxHeight(Double.MAX_VALUE);
        charthBar.setPrefHeight(459.0);
        charthBar.setPrefWidth(870.0);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Online", 9));
        series.getData().add(new XYChart.Data<>("Offline", 5));

        charthBar.getData().add(series);
        series.getData().get(0).getNode().setStyle("-fx-bar-fill:GREEN");
        series.getData().get(1).getNode().setStyle("-fx-bar-fill:RED");

        btnStartStop.setLayoutX(508.0);
        btnStartStop.setLayoutY(92.0);
        btnStartStop.setMnemonicParsing(false);
        btnStartStop.setPrefHeight(96.0);
        btnStartStop.setPrefWidth(304.0);
        btnStartStop.setStyle("-fx-background-color: RED; -fx-background-radius: 50; -fx-font-size: 70; -fx-font-weight: Bold;-fx-text-fill: White");
        btnStartStop.setText("Stop");
        btnStartStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (btnStartStop.isSelected() == true) {
                    btnStartStop.setStyle("-fx-background-color: GREEN; -fx-background-radius: 50; -fx-font-size: 70; -fx-font-weight: Bold;-fx-text-fill: White");
                    System.out.println("Server Started");
                    btnStartStop.setText("Start");
                } else {
                    btnStartStop.setStyle("-fx-background-color: RED; -fx-background-radius: 50; -fx-font-size: 70; -fx-font-weight: Bold;-fx-text-fill: White");
                    System.out.println("Server Stoped");
                    btnStartStop.setText("Stop");
                }
            }
        });

        getChildren().add(text);
        getChildren().add(charthBar);
        getChildren().add(btnStartStop);

        
    }
}
