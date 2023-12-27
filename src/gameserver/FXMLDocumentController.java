/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;


public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private BarChart<?, ?> charthBar;
    @FXML
    private ToggleButton btnStartStop;
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
