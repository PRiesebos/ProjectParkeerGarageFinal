package nl.hanze.t12.mvc.view;

import nl.hanze.t12.mvc.model.Simulator;

import javax.swing.*;
import java.awt.*;

/**
 * Dit is de klasse infoView die erft van AbstractView
 */
public class InfoView extends AbstractView {
    private JLabel tijd; // de tijd
    private JLabel overig; // overig
    private JLabel wachtrijexit; // wachtrij
    private JLabel wachtrijingang; // ingang
    private JLabel aantalautos; // aantal autos
    private JLabel abonnementhouders;// abbonomenthouders
    //int getalletje;

    /**
     * De constructor
     * @param simulator
     */
    public InfoView(Simulator simulator) {
        super(simulator);
        this.setPreferredSize(new Dimension(200, 500));
        tijd = new JLabel("Tijd: Days 0 : Hours 0 : Minutes 0");
        aantalautos = new JLabel("Aantal geparkeerde autos: 0 ");
        wachtrijexit = new JLabel("Auto's in wachtrij uitgang: 0");
        wachtrijingang = new JLabel("Auto's in wachtrij ingang: 0");
        abonnementhouders = new JLabel("Auto's van abonnement houders: 0");
        overig = new JLabel(" Aantal vrije plekken: 540");

        //getalletje = 0;
        add(tijd);
        add(aantalautos);
        add(overig);
        add(wachtrijingang);
        add(wachtrijexit);
        add(abonnementhouders);


    }

    @Override
    /**
     * dit update het.
     */
    public void updateView() {
        super.updateView();
        tijd.setText(simulator.getTime());
        aantalautos.setText(simulator.getAantalAutos());
        wachtrijexit.setText(simulator.getWachtrijExit());
        wachtrijingang.setText(simulator.getWachtrijIngang());
        overig.setText(simulator.getOverig());


    }

}
