package nl.hanze.t12.mvc.model;
import java.util.Random;
import java.awt.*;


/**
 * dit is de klasse Subscriber en dit staat voor abbonomenthouders.
 */

// Gele Subcriber Auto'sss


public class Subscriber extends Car {
    private static final Color COLOR = Color.YELLOW;
 /**
  * dit is de constructor
  */
    public Subscriber() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);

    }

    /**
     * dit geeft een kleur terug
     * @return
     */
    public Color getColor() {

        return COLOR;
    }



}
