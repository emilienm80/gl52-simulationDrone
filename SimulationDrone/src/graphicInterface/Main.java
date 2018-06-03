/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicInterface;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Emilien
 */
public class Main extends Application {

    private int BORDER_FRAME = 25;
    private int BORDER_GROUPBOX = 16;
    private int GROUPBOX_HEIGHT = 500;
    private int GROUPBOX_WIDTH = 300;
    private int SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();
    private int SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    private int compt;
    @Override
    public void start(Stage applicationFrame) {

        applicationFrame.setTitle("Simulation de drones");                      // name of the window
        Group framePanel = new Group();                                         // creation panel
        Scene frameScene = new Scene(framePanel, Color.WHITESMOKE);             // creation scene with the group and the color
        applicationFrame.setMaximized(true);                                    // full-screen
        applicationFrame.setResizable(false);

        BorderPane layoutSettings = new BorderPane();                           // creation layout for groupBox Settings 

        Button btn = new Button("test");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //TODO
            }
        });
        btn.setPrefWidth(200);
        VBox containerButton = new VBox();
        containerButton.setAlignment(Pos.TOP_CENTER);
        containerButton.setPadding(new Insets(BORDER_FRAME, 0, 0, 0));
        containerButton.setSpacing(BORDER_GROUPBOX);
        containerButton.getChildren().add(new Button("1"));
        containerButton.getChildren().add(new Button("2"));
        containerButton.getChildren().add(btn);
        layoutSettings.setCenter(containerButton);

        TitledPane groupBoxSettings = new TitledPane();                         // creation groupBox Settings
        groupBoxSettings.setText("Paramètres");                                 // name of the groupBox Settings
        groupBoxSettings.setContent(layoutSettings);                            // set Layout 
        groupBoxSettings.setCollapsible(false);                                 // remove dynamic top -> bottom
        groupBoxSettings.setLayoutX(BORDER_FRAME);
        groupBoxSettings.setLayoutY(BORDER_FRAME);                                // set position of the group box settings
        groupBoxSettings.setPrefSize(GROUPBOX_WIDTH, GROUPBOX_HEIGHT);            // set size of the groupBox Settings        
        framePanel.getChildren().add(groupBoxSettings);                         // add groupBox Settings to group

        /*TitledPane groupBoxSimulation = new TitledPane();
        groupBoxSimulation.setText("Simulation");
        groupBoxSimulation.setCollapsible(false);
        groupBoxSimulation.setLayoutX((borderSize * 2) + groupBoxWidth);
        groupBoxSimulation.setLayoutY(borderSize);
        groupBoxSimulation.setPrefSize((screenWidth - ((borderSize * 3) + groupBoxWidth)), (screenHeight - (2 * borderSize)));
        Group frameSimulation = new Group();
        //Scene sceneSimulation = new Scene(frameSimulation, Color.WHITESMOKE);
        Rectangle rect = new Rectangle(0, 0, 100, 100);
        rect.setFill(Color.RED);
        Rectangle rect1 = new Rectangle(100, 0, 1000, 100);
        rect1.setFill(Color.BLUE);
        frameSimulation.getChildren().add(rect);
        frameSimulation.getChildren().add(rect1);
        Scene sceneSimulation = new Scene(frameSimulation, Color.YELLOW); 
        frameSimulation.setS
        groupBoxSimulation.setContent(frameSimulation);
        framePanel.getChildren().add(groupBoxSimulation);*/

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
        
        

        
        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                    new KeyValue(x, 0),
                    new KeyValue(y, 0)
            ),
            new KeyFrame(Duration.seconds(3),
                    new KeyValue(x, (SCREEN_WIDTH - ((BORDER_FRAME * 3) + GROUPBOX_WIDTH))),
                    new KeyValue(y, (SCREEN_HEIGHT - (2 * BORDER_FRAME)))
            )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
                final Canvas canvas = new Canvas((SCREEN_WIDTH - ((BORDER_FRAME * 3) + GROUPBOX_WIDTH)), (SCREEN_HEIGHT - (2 * BORDER_FRAME)));
        canvas.setTranslateX((BORDER_FRAME * 2) + GROUPBOX_WIDTH);
        canvas.setTranslateY(BORDER_FRAME);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(100,100, BORDER_FRAME, BORDER_FRAME);
        gc.setFill(Color.RED);
        gc.fillRect(500, 500, BORDER_FRAME, BORDER_FRAME);
        compt = 100;
        /*gc.setFill(Color.YELLOW);
        gc.fillRoundRect(compt, compt, BORDER_FRAME, BORDER_FRAME, BORDER_FRAME, BORDER_FRAME);*/
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.YELLOW);
                gc.fillRoundRect(compt, compt, BORDER_FRAME, BORDER_FRAME, BORDER_FRAME, BORDER_FRAME);
                ++compt;
            }
        };
        /*final Timeline timeline = new Timeline();
        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000),
          new KeyValue (gc.getCanvas(), 25)));
        timeline.play();
         */

        //gc.fillRect(0,0,(screenWidth - ((borderSize * 3) + groupBoxWidth)), (screenHeight - (2 * borderSize)));*/
        framePanel.getChildren().add(canvas);

        applicationFrame.setScene(frameScene);                                  // add scene to frame
        applicationFrame.show();                                                // show the frame
        
                timer.start();
        timeline.play();

    }
}
