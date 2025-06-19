package GalaxyProjectFinal;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *<pre>
 Class        Comet
 File        Comet.java
 Description   Defines the behavior of Comets 
 @author       <i>Kaitlyn Le</i><i>Ruth Karen Nakigozi</i><i>Emma Dennis</i>
 Date          5/29/2025
 History Log    
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

public class Comet extends Celestial {
    private double dx, dy, tailLength;
    private static final Random random = new Random();
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
    
    public Comet() {
        super(0, 0, 0, 0); //we'll initialize size and mass below

        int size = 6 + random.nextInt(8);
        double mass = size * 0.3;
        this.tailLength = size * 3;

        // Use setters to update inherited fields
        setSize(size);
        setMass(mass);

        initializeFromEdge();
        initializeMovement();
    }
    
    public Comet(double x, double y, double dx, double dy) {
        super(x, y, 0, 0); // Placeholder for mass/size
        int newSize = 6 + random.nextInt(8);
        double newMass = newSize * 0.3;
        this.tailLength = newSize * 3;

        setSize(newSize);
        setMass(newMass);

        this.dx = dx;
        this.dy = dy;
    }
    
    private void initializeFromEdge() {
        int edge = random.nextInt(4);
        double[] pos = {
            random.nextDouble() * 800, -getSize(),
            800 + getSize(), random.nextDouble() * 600,
            random.nextDouble() * 800, 600 + getSize(),
            -getSize(), random.nextDouble() * 600
        };
        setX(pos[edge * 2]);
        setY(pos[edge * 2 + 1]);
    }
    
    private void initializeMovement() {
        double dirX = 400 - getX(), dirY = 300 - getY();
        double dist = Math.sqrt(dirX * dirX + dirY * dirY);
        double speed = 2.0 + random.nextDouble() * 3.0;

        this.dx = (dirX / dist) * speed + (random.nextDouble() - 0.5);
        this.dy = (dirY / dist) * speed + (random.nextDouble() - 0.5);
    }
    
    @Override
    public void update() {
       setX(getX() + dx);
       setY(getY() + dy);
    }
    
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double x = getX();
        double y = getY();
        int size = getSize();

        // Draw tail
        double tailDx = -dx, tailDy = -dy;
        double tailMag = Math.sqrt(tailDx * tailDx + tailDy * tailDy);
        if (tailMag > 0) {
            tailDx = (tailDx / tailMag) * tailLength;
            tailDy = (tailDy / tailMag) * tailLength;
            
            for (int i = 0; i < 10; i++) {
                double alpha = 1.0 - i / 10.0;
                int tailX = (int)(x + tailDx * i / 10.0);
                int tailY = (int)(y + tailDy * i / 10.0);
                g2d.setColor(new Color(255, 255, 200, (int)(alpha * 100)));
                g2d.fillOval(tailX - (size + i)/2, tailY - (size + i)/2, size + i, size + i);
            }
        }
        
        // Draw comet nucleus
        g2d.setColor(new Color(200, 200, 255));
        g2d.fillOval((int)(x - size/2), (int)(y - size/2), size, size);
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)(x - size/4), (int)(y - size/4), size/2, size/2);
    }
    
/**  
 * Returns true if “this” comet overlaps the other Celestial.
 * We treat “radius” = getSize()/2 for every object (Asteroid, Comet, Planet).
 */

public boolean collidesWith(Celestial other) {
    double dx = other.getX() - this.getX();
    double dy = other.getY() - this.getY();
    double dist = Math.hypot(dx, dy);

    double myRadius = this.getSize() / 2.0;
    double otherRadius;

    if (other instanceof Asteroid) {
        otherRadius = ((Asteroid) other).getSize() / 2.0;
    }
    else if (other instanceof Comet) {
        otherRadius = ((Comet) other).getSize() / 2.0;
    }
    else if (other instanceof Planet) {
        otherRadius = ((Planet) other).getSize() / 2.0;

    } else if (other instanceof Moon) {
        otherRadius = ((Moon) other).getSize() / 2.0;

    } else if (other instanceof Star) {
        otherRadius = ((Star) other).getSize() / 2.0;
    }
    else {
        
        return false;
    }

   return dist < (myRadius + otherRadius);
}


/** 
 * When two comets collide, destroy both.  Or if a comet hits an asteroid, destroy both.
 */
    public void handleCollision(Celestial other) {
        if (other instanceof Comet) {
            this.setDestroyed(true);
            other.setDestroyed(true);
        }
        else if (other instanceof Asteroid) {
            this.setDestroyed(true);
            other.setDestroyed(true);
            System.out.println(">>> Comet–Asteroid collision: " + this + " & " + other);
        }
    }
   
}