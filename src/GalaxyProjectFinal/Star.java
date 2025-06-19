package GalaxyProjectFinal;

import java.awt.*;

 /**
 * Defines the behavior of stars
 *               which are the center of the solar system
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
*/


// A Star class extends the superclass Celestial
public class Star extends Celestial {
    
    // A Star constructor which calls the super to initialize the x and y coordinates, with mass 1500 and size 30
    public Star(double x, double y) {
        super(x, y, 1500, 30);
    }
    
    // An overriden update method that returns nothing because in this case the sun (A dwarf star) is not moving
    @Override
    public void update() {
    }
    
    // An overriden draw method which draws out a circular yellow sun
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)(getX() - getSize() / 2), (int)(getY() - getSize() / 2), getSize(), getSize());
    }
    
    // An overriden getType method which returns the String of the type "Star"
    @Override
    public String getType() {
        return "Star";
    }
}
