package nl.hanze.t12.mvc.main;

import nl.hanze.t12.mvc.controller.Controller;
import nl.hanze.t12.mvc.model.Simulator;
import nl.hanze.t12.mvc.view.CarparkView;
import nl.hanze.t12.mvc.view.InfoView;

import javax.swing.*;
import java.awt.*;

/**
 * Dit is de klase ParkingSimulator die zorgt ervoor dat alles op het scherm komt
 */
public class ParkingSimulator {
    private InfoView infoView;
    private Controller controller;
    private CarparkView carParkView;

    /**
     * Dit is de constructor
     */
    public ParkingSimulator() {
        createAndShowGUI();
    }

    /**
     * Dit is de methode createAndShowGUI die er voor zorgt dat het op het scherm gezet wordt .
     */
    private void createAndShowGUI(){
        Simulator simulator=new Simulator();//Dit is het model

        JFrame screen=new JFrame("Parkeergarage");
        screen.setSize(920, 550); // de afmetingen van de screen dat is het Frame
        screen.setResizable(false); // dat je het de width/height niet kan verstellen
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        screen.setVisible(true); // maak het zichtbaar

        // hier voeg je de simulator toe aan de nieuwe interface
        controller = new Controller(simulator);
        infoView = new InfoView(simulator);
        carParkView = new CarparkView(simulator);

        screen.getContentPane().add(controller, BorderLayout.SOUTH);
        screen.getContentPane().add(infoView, BorderLayout.WEST);
        screen.getContentPane().add(carParkView, BorderLayout.CENTER);
        screen.pack();
    }
}
