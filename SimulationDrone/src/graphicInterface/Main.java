/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicInterface;

import javafx.application.Application;
import simulationDrones.SimulationDrone;

/**
 *
 * @author Emilien
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    /*
        CODE SOURCE TROUVÉ SUR INTERNET : Classes AnimationTimerTest et AnimatedCircleOnCanvas
    */
    public static void main(String[] args) {
        Application.launch(SimulationDrone.class);
        //Application.launch(AnimationTimerTest.class);
    }
}
