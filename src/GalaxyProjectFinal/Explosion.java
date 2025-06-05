package GalaxyProjectFinal;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Explosion {
    private final double x, y;
    private      int    age   = 0;
    private final int    maxAge = 20;  // number of frames before it vanishes

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
    public void draw(Graphics2D g) {
        // Compute an alpha 0→255 (fade out as age→maxAge)
        int alpha = (int) (255 * (1.0 - (double) age / maxAge));
        if (alpha < 0) alpha = 0;

        // Compute a radius that grows, e.g. 0→30 px
        int maxRadius = 30;
        int r = (int) (maxRadius * (double) age / maxAge);

        // Draw a hollow circle with that alpha
        Color c = new Color(255, 200, 0, alpha);
        g.setColor(c);
        g.setStroke(new BasicStroke(3));
        Shape circle = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);
        g.draw(circle);
    }
}