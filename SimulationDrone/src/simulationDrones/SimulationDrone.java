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
    private PhysicsEngine physicsEngine;
    private ArrayList<Building> buildings;
    private double lastFrameTimestamp=0; 
    private double frameTimeSum=0; 
    private int nbFrame=0; 

    private void Initializer() {
        Const = new Constantes();
        map = new Map();
        physicsEngine=new PhysicsEngine(map);
        buildings = map.getBuilding();
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
                timer.start();
                timeline.play();
                lastFrameTimestamp=System.nanoTime();
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

        ArrayList<Building> buildingstation = map.getBuildingWithStation();

        for (Building building : buildingstation) {
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
                String startingBuildingName = from.getValue();
            	String goalName = to.getValue();
            	DroneType dt = typeDrone.getValue();
            	Priority p = priority.getValue();
            	MissionType mt = typeMission.getValue();
            	
            	DroneCharacteristics dc = new DroneCharacteristics(dt);
            	
            	Building buildingDepart = map.getBuildingByName(startingBuildingName);
            	Station stationDepart = buildingDepart.getStation();
            	
            	Vect3 posDepart = stationDepart.getPosition();
            	Vect3 speed = new Vect3(0,0,0);
            	Vect3 size = new Vect3(0,0,0);
            	Sphere sph = new Sphere(posDepart, dc.getRadius());
            	
            	Building buildingArr = map.getBuildingByName(goalName);
            	Station stationArr = buildingArr.getStation();
            	
            	Vect3 posArr = stationArr.getPosition();
            	//posArr.setZ(buildingArr.getSize().getZ()+stationArr.getPosition().getZ());
            	
            	Objective obj = new Objective(posArr);
            	
            	Mission mission = new Mission(obj, p);
            	
            	Drone d = new Drone(posDepart, speed, size, sph, dc, 80, 0, 0, mission);
            	
            	map.addDrone(d);
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
                        new KeyValue(x, Const.CANVAS_WIDTH),
                        new KeyValue(y, Const.CANVAS_HEIGHT)
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);

        compt = 100;

        final Canvas canvas = new Canvas(Const.CANVAS_WIDTH, Const.CANVAS_HEIGHT);
        canvas.setTranslateX((Const.BORDER_FRAME * 2) + Const.GROUPBOX_WIDTH);
        canvas.setTranslateY(Const.BORDER_FRAME);
        Draw(canvas.getGraphicsContext2D());

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	nbFrame++;
            	
            	double currentTimestamp=System.nanoTime();
            	double elapsedTime=0.000000001*(currentTimestamp-lastFrameTimestamp);//seconds
            	lastFrameTimestamp=currentTimestamp;
            	frameTimeSum+=elapsedTime;
            	
            	physicsEngine.updateMap(elapsedTime);//update world according to elapsed time
            	
            	//System.out.print("Average Frame time : "+CollisionTools.round(1000*frameTimeSum/nbFrame,2)+" ms     ");
                Draw(canvas.getGraphicsContext2D());
                
                
            }
        };
        framePanel.getChildren().add(canvas);
    }

    public void Draw(GraphicsContext gc) {
        gc.setFill(Color.web("#d5d5d5"));
        gc.fillRect(0, 0, Const.CANVAS_WIDTH, Const.CANVAS_HEIGHT);
        for (Building building : buildings) {
            gc.setFill(Color.web("#888888"));
            gc.fillRect(building.getPosition().getX(), building.getPosition().getY(), building.getSize().getX(), building.getSize().getY());
            if (building.getStation() != null){
                gc.setFill(Color.WHITE);
                gc.fillText(building.getName(), building.getPosition().getX() + Const.BORDER_FRAME, building.getPosition().getY() + Const.BORDER_FRAME, 100);
                gc.fillRect(building.getStation().getPosition().getX(), building.getStation().getPosition().getY(), building.getStation().getSize().getX(), building.getStation().getSize().getY());                
            }

        }
        
        ArrayList<Drone> drones = map.getDrone();
        
        for(Drone drone : drones) {
                //System.out.println("ici");
        	gc.setFill(Color.web("#121212"));
        	double width = 2*drone.getCharacteristics().getRadius()*100;//TODO adjust with proper constant (ratio between drone radius in meters and screen size in pixels)
        	
        	//System.out.println(drone.getPosition().getX()+" "+drone.getPosition().getY()+" "+drone.getPosition().getZ()+" "+ width+" "+width);
        	//System.out.println("Pos "+drone.getPosition().toStringLen(50,3)+" Speed "+drone.getSpeed().toStringLen(50,3)+ " Motor consumption "+drone.getMotorConsumption()+" W");
        	
        	gc.fillOval(drone.getPosition().getX(), drone.getPosition().getY(), width, width);
        	
        	gc.fillText("z="+CollisionTools.round(drone.getPosition().getZ(),2), drone.getPosition().getX(), drone.getPosition().getY());
        	gc.fillText(CollisionTools.round(drone.getBatteryLevelRelative()*100,1) +"%", drone.getPosition().getX(), drone.getPosition().getY()+width*2);
        }
    }

}
