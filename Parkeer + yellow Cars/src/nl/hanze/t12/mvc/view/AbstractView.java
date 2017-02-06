package nl.hanze.t12.mvc.view;

import javax.swing.*;
import nl.hanze.t12.mvc.model.*;

public abstract class AbstractView extends JPanel {
	protected Simulator simulator;

	public AbstractView(Simulator simulator) {
		this.simulator=simulator;
		simulator.addView(this);
	}
	
	public Simulator getModel() {
		return simulator;
	}
	
	public void updateView() {
		repaint();
	}
}
