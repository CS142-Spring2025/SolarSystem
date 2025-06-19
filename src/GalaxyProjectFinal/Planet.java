package GalaxyProjectFinal;
import java.awt.*;


 /**
 * Defines the behavior of planets
 *               which orbit around a star
 *
 * <p><b>Project:</b> GalaxyProjectFinal</p>
 * <p><b>Date:</b> 6/18/2025</p>
 *
 * @author Kaitlyn Le
 * @author Ruth Karen Nakigozi
 * @author Emma Dennis
 * @see java.awt.Color
 * @see java.awt.Graphics
 * @see java.awt.Graphics2D
 * @see java.util.Random
 */



// A Planet class extends the Orbit class, which is a subclass of Celestial
public class Planet extends Orbit{
    
    // A Planet constructor which calls the super for the star's x and y coordinates, the orbit's radius and speed, and mass 850 and size 20
    // Grabs star's coordinates in order to know the center point and orbit around it
    public Planet(Star star, double radius, double speed) {
        super(star, radius, speed, 850, 20);
    }
    
    // An overriden draw method which draws out a circular blue planet 
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int)(getX() - getSize() / 2), (int)(getY() - getSize() / 2), getSize(), getSize());
    }
    
    // An overriden getType method which returns the String of the type "Planet"
    @Override
    public String getType() {
        return "Planet";
    }

    
}
