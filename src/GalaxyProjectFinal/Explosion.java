package GalaxyProjectFinal;

import java.awt.*;
import java.awt.geom.Ellipse2D;


 /**
 * Defines behavior for explosrions when collisions occur
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


public class Explosion {
    private final double x, y;
    private int age   = 0;
    private final int maxAge = 20;  // number of frames before it vanishes

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Called each tick; returns false when it’s “done” */
    public boolean update() {
        age++;
        return age < maxAge;
    }

    /** Draws one frame: an expanding, fading ring */
    public void draw(Graphics2D g2d) {
        // Compute an alpha 0→255 (fade out as age→maxAge)
        int alpha = (int) (255 * (1.0 - (double) age / maxAge));
        if (alpha < 0) alpha = 0;

        // Compute a radius that grows, e.g. 0→30 px
        int maxRadius = 30;
        int r = (int) (maxRadius * (double) age / maxAge);

        // Draw a hollow circle with that alpha
        Color baseColor = (age % 2 == 0) ? Color.RED : Color.YELLOW;
        // Apply the fading alpha to that base color
        Color c = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);

        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval((int) x - r, (int) y - r, r * 2, r * 2);
    }
}