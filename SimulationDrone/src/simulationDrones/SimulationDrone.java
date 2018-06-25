/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationDrones;

import java.util.ArrayList;

import com.sun.org.apache.regexp.internal.recompile;

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
import javafx.geometry.Rectangle2D;
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
import physics.PhysicsEngine;
import physics.collisions.CollisionTools;
import physics.collisions.colliders.Collider;
import physics.collisions.colliders.RectCuboid;
import physics.collisions.colliders.Sphere;
import utilities.Constantes;
import utilities.Vect3;
import world.Building;
import world.Map;
import world.Station;
import world.WorldObject;
import world.drone.Drone;
import world.drone.DroneCharacteristics;
import world.drone.DroneType;
import world.drone.Mission;
import world.drone.MissionType;
import world.drone.Objective;
import world.drone.Priority;

/**
 *
 * @author Emilien
 */

//TODO create functions to draw each type of object, to make clearer code
public class SimulationDrone extends Application {

	private Rectangle2D viewCamRect;
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
            	posDepart.setZ(buildingDepart.getSize().getZ()+stationDepart.getSize().getZ()+10);
            	Vect3 speed = new Vect3(0,0,0);
            	Vect3 size = new Vect3(0,0,0);
            	Sphere sph = new Sphere(posDepart, dc.getRadius());
            	
            	Building buildingArr = map.getBuildingByName(goalName);
            	Station stationArr = buildingArr.getStation();
            	
            	Vect3 posArr = stationArr.getPosition();
            	posArr.setZ(buildingArr.getSize().getZ()+stationArr.getSize().getZ());
            	
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
        viewCamRect=new Rectangle2D(0, 0, Const.CANVAS_WIDTH, Const.CANVAS_HEIGHT);//current view area
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
            Rectangle2D r=getDrawingRect(building);
            gc.fillRect(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
            gc.setFill(Color.WHITE);
            gc.fillText(building.getName()+"\n"+building.getSize().toIntDimensions(), r.getMinX() + Const.BORDER_FRAME*0.25, r.getMinY() + Const.BORDER_FRAME*0.5, 100);
            //gc.fillRect(building.getPosition().getX(), building.getPosition().getY(), building.getSize().getX(), building.getSize().getY());
            
            if (building.getStation() != null){
                gc.setFill(Color.WHITE);
                r=getDrawingRect(building.getStation());
                gc.fillRect(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
                //gc.fillRect(building.getStation().getPosition().getX(), building.getStation().getPosition().getY(), building.getStation().getSize().getX(), building.getStation().getSize().getY());                
            }

        }
        
        ArrayList<Drone> drones = map.getDrone();
        
        for(Drone drone : drones) {
        	gc.setFill(Color.web("#121212"));
        	//double width = 2*drone.getCharacteristics().getRadius()*100;//TODO adjust with proper constant (ratio between drone radius in meters and screen size in pixels)
        	
        	//System.out.println(drone.getPosition().getX()+" "+drone.getPosition().getY()+" "+drone.getPosition().getZ()+" "+ width+" "+width);
        	//System.out.println("Pos "+drone.getPosition().toStringLen(50,3)+" Speed "+drone.getSpeed().toStringLen(50,3)+ " Motor consumption "+drone.getMotorConsumption()+" W");
        	
        	//gc.fillOval(drone.getPosition().getX(), drone.getPosition().getY(), width, width);
        	Rectangle2D r=getDrawingRect(drone);
        	gc.fillOval(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
        	
        	gc.fillText("z="+CollisionTools.round(drone.getPosition().getZ(),2), r.getMinX(), r.getMinY());
        	gc.fillText(CollisionTools.round(drone.getBatteryLevelRelative()*100,1) +"%", r.getMinX(), r.getMinY()+30);
        }
    }
    
    /**
     * returns the drawing rect of a world object, ready to be drawn on screen
     * @param w
     * @return
     */
    private Rectangle2D getDrawingRect(WorldObject w)
    {
    	//TODO use precomputed constants to avoid too much subfunctions calls
    	return getDrawingRectInPixels(getFittedToViewCamera(getRawDrawingRect(w)));    	
    }
    
    private Rectangle2D getDrawingRectInPixels(Rectangle2D r)
    {
    	return new Rectangle2D(r.getMinX()*Constantes.MeterToPixel, r.getMinY()*Constantes.MeterToPixel, r.getWidth()*Constantes.MeterToPixel, r.getHeight()*Constantes.MeterToPixel);
    }
    
    private Rectangle2D getRawDrawingRect(WorldObject w)
    {
    	Collider wcol=w.getCollider();
    	
    	if(wcol instanceof Sphere)
    	{
    		Vect3 c=wcol.getCenter();
    		double r=((Sphere) wcol).getRadius();
    		return new Rectangle2D(c.getX()-r, c.getY()-r, 2*r, 2*r);
    	}
    	else if(wcol instanceof RectCuboid)
    	{
    		Vect3 c=wcol.getCenter();
    		Vect3 s=((RectCuboid) wcol).getSize();
    		return new Rectangle2D(c.getX()-s.getX()*0.5, c.getY()-s.getY()*0.5, s.getX(), s.getY());
    	}
    	
    	return new Rectangle2D(0,0,0,0);
    }
    
    /**
     * fit the drawing rect according to the view rectangle
     * @param r
     * @return
     */
    private Rectangle2D getFittedToViewCamera(Rectangle2D r)
    {
    	return new Rectangle2D(	r.getMinX()-viewCamRect.getMinX(), 
    							r.getMinY()-viewCamRect.getMinY(), 
    							r.getWidth()*Const.CANVAS_WIDTH/viewCamRect.getWidth(), 
    							r.getHeight()*Const.CANVAS_HEIGHT/viewCamRect.getHeight());
    }
    
    /**
     * set the zoom of map display canvas, and center the view area
     * @param zoom
     */
    public void setZoom(double zoom)
    {
    	if(zoom<=0) zoom=1;
    	
    	double w=Const.CANVAS_WIDTH/zoom;
    	double h=Const.CANVAS_HEIGHT/zoom;
    	
    	viewCamRect=new Rectangle2D(0.5*(Const.CANVAS_WIDTH-w), 0.5*(Const.CANVAS_HEIGHT-h), w, h);
    }

}

