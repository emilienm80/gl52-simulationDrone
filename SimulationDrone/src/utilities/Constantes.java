/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import javafx.stage.Screen;

/**
 *
 * @author Emilien
 */
public class Constantes {
	
	public static final double DegToRad=Math.PI/180;
	public static final double WsToWh=1.0/3600; //watt per second to watt per hour
	
	public static final double MeterToPixel=50;//1 meter in physicsEngine = ? pixels on screen, with a zoom of 1.0
	
    public int BORDER_FRAME;
    public int BORDER_GROUPBOX;
    public int GROUPBOX_HEIGHT;
    public int GROUPBOX_WIDTH;
    public int SCREEN_HEIGHT;
    public int SCREEN_WIDTH;
    public int FORM_SIZE;
    public int CANVAS_HEIGHT;
    public int CANVAS_WIDTH;
    
    public Constantes(){ 
        BORDER_FRAME = 25;
        BORDER_GROUPBOX = 16;
        GROUPBOX_HEIGHT = 700;
        GROUPBOX_WIDTH = 300;
        FORM_SIZE = 200;
        SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();
        SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
        
        CANVAS_HEIGHT = SCREEN_HEIGHT - (2 * BORDER_FRAME);
        CANVAS_WIDTH = SCREEN_WIDTH - ((BORDER_FRAME * 3) + GROUPBOX_WIDTH);
    }
    
    
    
}
