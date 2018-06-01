/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicInterface;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;    
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Emilien
 */
public class Main extends Application {
    
    private int borderSize = 25;
    private int groupBoxHeight = 500;
    private int groupBoxWidth = 300;
    private int screenHeight = (int) Screen.getPrimary().getVisualBounds().getHeight();
    private int screenWidth = (int) Screen.getPrimary().getVisualBounds().getWidth();

    @Override
    public void start(Stage applicationFrame) {
        
        applicationFrame.setTitle("Simulation de drones");                      // name of the window
        Group framePanel = new Group();                                         // creation panel
        Scene frameScene = new Scene(framePanel, Color.WHITESMOKE);             // creation scene with the group and the color
        applicationFrame.setMaximized(true);                                    // full-screen
        applicationFrame.setResizable(false);

        BorderPane layoutSettings = new BorderPane();                           // creation layout for groupBox Settings 

        /* Commentaire a faire  */
        layoutSettings.setCenter(new Button("Center"));
        layoutSettings.setRight(new Button("Right"));


        TitledPane groupBoxSettings = new TitledPane();                         // creation groupBox Settings
        groupBoxSettings.setText("Paramètres");                                 // name of the groupBox Settings
        groupBoxSettings.setContent(layoutSettings);                            // set Layout 
        groupBoxSettings.setCollapsible(false);                                 // remove dynamic top -> bottom
        groupBoxSettings.setLayoutX(borderSize);
        groupBoxSettings.setLayoutY(borderSize);                                // set position of the group box settings
        groupBoxSettings.setPrefSize(groupBoxWidth, groupBoxHeight);            // set size of the groupBox Settings        
        

        final Canvas canvas = new Canvas((screenWidth - ((borderSize * 3) + groupBoxWidth)), (screenHeight - (2 * borderSize)));
        canvas.setTranslateX((borderSize * 2) + groupBoxWidth);
        canvas.setTranslateY(borderSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
 
        gc.setFill(Color.BLUE);
        gc.fillRect(0,0,(screenWidth - ((borderSize * 3) + groupBoxWidth)), (screenHeight - (2 * borderSize)));

        framePanel.getChildren().add(canvas);

        framePanel.getChildren().add(groupBoxSettings);                         // add groupBox Settings to group
        applicationFrame.setScene(frameScene);                                  // add scene to frame
        applicationFrame.show();                                                // show the frame
        
    }

}
