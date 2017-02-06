package nl.hanze.t12.mvc.view;

import nl.hanze.t12.mvc.model.Car;
import nl.hanze.t12.mvc.model.Location;
import nl.hanze.t12.mvc.model.Simulator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ahmed Çiçek on 30-1-2017.
 */
public class CarparkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;

    /**
     * Constructor for objects of class CarPark
     */
    public CarparkView(Simulator simulator) {
        super(simulator);
        size = new Dimension(0, 0);
        setSize(800,500);
        this.setLayout(null);
        setBackground(Color.MAGENTA);
    }


    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < getModel().getParkeergarage().getNumberOfFloors(); floor++) {
            for(int row = 0; row < getModel().getParkeergarage().getNumberOfRows(); row++) {
                for(int place = 0; place < getModel().getParkeergarage().getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getModel().getParkeergarage().getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }

        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }

    }


    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }

}