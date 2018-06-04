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
    public int BORDER_FRAME;
    public int BORDER_GROUPBOX;
    public int GROUPBOX_HEIGHT;
    public int GROUPBOX_WIDTH;
    public int SCREEN_HEIGHT;
    public int SCREEN_WIDTH;
    
    public Constantes(){ 
        BORDER_FRAME = 25;
        BORDER_GROUPBOX = 16;
        GROUPBOX_HEIGHT = 500;
        GROUPBOX_WIDTH = 300;
        SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();
        SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    }
    
    
    
}
