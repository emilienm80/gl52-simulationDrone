/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicInterface;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        Button btn = new Button("test");
        layoutSettings.setCenter(btn);
        layoutSettings.setRight(new Button("Right"));
        layoutSettings.setLeft(new Button("Left"));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //TODO
            }
        });

        TitledPane groupBoxSettings = new TitledPane();                         // creation groupBox Settings
        groupBoxSettings.setText("Paramètres");                                 // name of the groupBox Settings
        groupBoxSettings.setContent(layoutSettings);                            // set Layout 
        groupBoxSettings.setCollapsible(false);                                 // remove dynamic top -> bottom
        groupBoxSettings.setLayoutX(borderSize);
        groupBoxSettings.setLayoutY(borderSize);                                // set position of the group box settings
        groupBoxSettings.setPrefSize(groupBoxWidth, groupBoxHeight);            // set size of the groupBox Settings        



        /*final Rectangle rectPath = new Rectangle(0, 0, 40, 40);
        rectPath.setArcHeight(10);
        rectPath.setArcWidth(10);
        rectPath.setFill(Color.ORANGE);
        Path path = new Path();
        path.getElements().add(new MoveTo(20, 20));
        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(rectPath);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
        groupBoxSimulation.setContent(rectPath);*/



        
        
        
        final Canvas canvas = new Canvas((screenWidth - ((borderSize * 3) + groupBoxWidth)), (screenHeight - (2 * borderSize)));
        canvas.setTranslateX((borderSize * 2) + groupBoxWidth);
        canvas.setTranslateY(borderSize);
        GraphicsContext gc = canvas.getGraphicsContext2D(); 
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,(screenWidth - ((borderSize * 3) + groupBoxWidth)), (screenHeight - (2 * borderSize)));
        framePanel.getChildren().add(canvas);
         
        
        
        framePanel.getChildren().add(groupBoxSettings);                         // add groupBox Settings to group
        applicationFrame.setScene(frameScene);                                  // add scene to frame
        applicationFrame.show();                                                // show the frame

    }
}
