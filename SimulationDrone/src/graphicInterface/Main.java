/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicInterface;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;    
import javafx.stage.Stage;

/**
 *
 * @author Emilien
 */
public class Main extends Application {

    @Override
    public void start(Stage frameSettings) {
        
        frameSettings.setTitle("Simulation de drones");                         // name of the window
        Group root = new Group();                                               // creation group
        Scene scene = new Scene(root, Color.WHITESMOKE);                        // creation scene with the group and the color
        frameSettings.setMaximized(true);                                       // full-screen
        
        Button btn = new Button();                                              // creation button
        btn.setText("Hello World");
        
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

            }
        });
        
        root.getChildren().add(btn);                                            // add button to group
        frameSettings.setScene(scene);                                          // add scene to frame
        frameSettings.show();                                                   // show the frame
    }

}
