/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationDrones;

import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.Constantes;

/**
 *
 * @author Emilien
 */
public class SimulationDrone extends Application {

    private Constantes Const;
    private int compt;
    private Group framePanel;
    private AnimationTimer timer;
    private Timeline timeline;
    private Map map;

    private void Initializer() {
        Const = new Constantes();
        map = new Map();
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        timeline = new Timeline();
        framePanel = new Group();
    }

    @Override
    public void start(Stage applicationFrame) {
        Initializer();

        applicationFrame.setTitle("Simulation de drones");                      // name of the window
        Scene frameScene = new Scene(framePanel, Color.WHITESMOKE);             // creation scene with the group and the color
        applicationFrame.setMaximized(true);                                    // full-screen
        applicationFrame.setResizable(false);

        BorderPane layoutSettings = new BorderPane();                           // creation layout for groupBox Settings 

        Button start = new Button("Start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                createCanvas();
            }
        });

        Button stop = new Button("Stop");
        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                timer.stop();
                timeline.stop();
            }
        });
        
        ComboBox<DroneType> typeDrone = new ComboBox<>();
        typeDrone.getItems().setAll(DroneType.values());
        typeDrone.getSelectionModel().select(DroneType.Mini);
               
        ComboBox<String> from = new ComboBox<>();
        
        ComboBox<String> to = new ComboBox<>();
        
        ArrayList<Building> buildings = map.getBuildingWithStation();
        
        for(Building building : buildings){
            from.getItems().add(building.getName());
            to.getItems().add(building.getName());
        }

        from.getSelectionModel().select(0);
        to.getSelectionModel().select(1);
                
        ComboBox<Priority> priority = new ComboBox<>();
        priority.getItems().setAll(Priority.values());
        priority.getSelectionModel().select(Priority.Low);
        
        ComboBox<MissionType> typeMission = new ComboBox<>();
        typeMission.getItems().setAll(MissionType.values());
        typeMission.getSelectionModel().select(MissionType.Move);
        
        Button create = new Button("Create");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // create drone
            }
        });
        
        start.setPrefWidth(Const.FORM_SIZE);
        stop.setPrefWidth(Const.FORM_SIZE);
        typeDrone.setPrefWidth(Const.FORM_SIZE);
        priority.setPrefWidth(Const.FORM_SIZE);
        typeMission.setPrefWidth(Const.FORM_SIZE);
        from.setPrefWidth(Const.FORM_SIZE);
        to.setPrefWidth(Const.FORM_SIZE);
        create.setPrefWidth(Const.FORM_SIZE);

        VBox containerButton = new VBox();

        containerButton.setAlignment(Pos.TOP_CENTER);

        containerButton.setPadding(new Insets(Const.BORDER_FRAME, 0, 0, 0));
        containerButton.setSpacing(Const.BORDER_GROUPBOX);

        containerButton.getChildren().add(start);
        containerButton.getChildren().add(stop);
        containerButton.getChildren().add(new Separator());
        containerButton.getChildren().add(new Label("Drone Type"));
        containerButton.getChildren().add(typeDrone);
        containerButton.getChildren().add(new Label("Priority"));
        containerButton.getChildren().add(priority);
        containerButton.getChildren().add(new Label("Mission Type"));
        containerButton.getChildren().add(typeMission);
        containerButton.getChildren().add(new Label("From"));
        containerButton.getChildren().add(from);
        containerButton.getChildren().add(new Label("To"));
        containerButton.getChildren().add(to);
        containerButton.getChildren().add(create);
        
        layoutSettings.setCenter(containerButton);

        TitledPane groupBoxSettings = new TitledPane();                         // creation groupBox Settings

        groupBoxSettings.setText("Paramètres");                                 // name of the groupBox Settings
        groupBoxSettings.setContent(layoutSettings);                            // set Layout 

        groupBoxSettings.setCollapsible(false);                                 // remove dynamic top -> bottom
        groupBoxSettings.setLayoutX(Const.BORDER_FRAME);

        groupBoxSettings.setLayoutY(Const.BORDER_FRAME);                        // set position of the group box settings

        groupBoxSettings.setPrefSize(Const.GROUPBOX_WIDTH, Const.GROUPBOX_HEIGHT);            // set size of the groupBox Settings        

        framePanel.getChildren().add(groupBoxSettings);                         // add groupBox Settings to group

        createCanvas();

        applicationFrame.setScene(frameScene);                                  // add scene to frame

        applicationFrame.show();                                                // show the frame
    }

    public void createCanvas() {
        timer.stop();
        timeline.stop();
        DoubleProperty x = new SimpleDoubleProperty();
        DoubleProperty y = new SimpleDoubleProperty();

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(x, 0),
                        new KeyValue(y, 0)
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(x, (Const.SCREEN_WIDTH - ((Const.BORDER_FRAME * 3) + Const.GROUPBOX_WIDTH))),
                        new KeyValue(y, (Const.SCREEN_HEIGHT - (2 * Const.BORDER_FRAME)))
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);

        compt = 100;

        final Canvas canvas = new Canvas((Const.SCREEN_WIDTH - ((Const.BORDER_FRAME * 3) + Const.GROUPBOX_WIDTH)), (Const.SCREEN_HEIGHT - (2 * Const.BORDER_FRAME)));
        canvas.setTranslateX((Const.BORDER_FRAME * 2) + Const.GROUPBOX_WIDTH);
        canvas.setTranslateY(Const.BORDER_FRAME);
        Draw(canvas.getGraphicsContext2D());

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Draw(canvas.getGraphicsContext2D());
                if (compt < 500) {
                    ++compt;
                }
            }
        };
        framePanel.getChildren().add(canvas);

        timer.start();
        timeline.play();
    }

    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0, 0, (Const.SCREEN_WIDTH - ((Const.BORDER_FRAME * 3) + Const.GROUPBOX_WIDTH)), (Const.SCREEN_HEIGHT - (2 * Const.BORDER_FRAME)));
        gc.setFill(Color.BLUE);
        gc.fillRect(100, 100, Const.BORDER_FRAME, Const.BORDER_FRAME);
        gc.setFill(Color.RED);
        gc.fillRect(500, 500, Const.BORDER_FRAME, Const.BORDER_FRAME);
        gc.setFill(Color.GREY);
        gc.fillRoundRect(compt, compt, Const.BORDER_FRAME, Const.BORDER_FRAME, Const.BORDER_FRAME, Const.BORDER_FRAME);
    }

}
