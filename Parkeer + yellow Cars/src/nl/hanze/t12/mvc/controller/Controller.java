package nl.hanze.t12.mvc.controller;

import javax.swing.*;
import nl.hanze.t12.mvc.model.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Dit is de klasse Controller die Abstractcontroller overerft en hij zet knopjes op het scherm
 */
public class Controller extends AbstractController implements ActionListener {

	private JButton start; // knopje start
	private JButton stop; // knopje stop
	private JButton pertien; // knopje pertien
	private JButton perhonderd; // knopje per honderd
	private JButton reset; // knopje reset - doet het niet

	/**
	 * Dit is de constructor van de klasse
	 * @param simulator
	 */
	public Controller(Simulator simulator) {
		super(simulator);
		setBackground(Color.cyan);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		start=new JButton("Start");
		start.addActionListener(this);
		stop=new JButton("Stop");
		stop.addActionListener(this);
		reset=new JButton("Reset");
		reset.addActionListener(this);
		pertien=new JButton("Laat auto's toe: 10min");
		pertien.addActionListener(this);
		perhonderd=new JButton("Laat auto's toe: 100min");
		perhonderd.addActionListener(this);

		add(start);
		add(stop);
		add(reset);
		add(pertien);
		add(perhonderd);
	}

	/**
	 * De methode actionPerformed zorgt ervoor dat de knop een actie uitvoert zodra je er op klikt
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==start) {
			simulator.run();
		}
		if (e.getSource()==pertien) {
			simulator.runTien();
		}
		if(e.getSource()==perhonderd){
			simulator.runHonderd();
		}
		if(e.getSource()==stop){
			simulator.stop();
		}
		if(e.getSource()==reset){
			simulator.ResetCar();
		}


	}
}
