package GalaxyProjectFinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *<pre>
 * Class         Asteroid
 * File          Asteroid.java
 * Description   Class representing the details of a asteroid in a galaxy
 *               simulation. Defines the behavior of an asteroid, randomly
 *               places along the edges and moves inwards. Collisions may also occur
 *               and shows when colliding with other celestial bodies.
 * Project       GalaxyProjectFinal
 * Date          6/18/2025
 * @see          java.awt.Color
 * @see          java.awt.Graphics
 * @see          java.awt.Graphics2D
 * @see          java.util.Random
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*/


public class Asteroid extends Celestial {
    private double dx, dy, rotationAngle, rotationSpeed;
    private static final Random random = new Random();
    
    public Asteroid(double x, double y) {
        super(x, y, 20, 10);
        init();
        initializeFromEdge();
        initializeMovement();
    }
    
    public Asteroid(double x, double y, double dx, double dy) {
        super(x, y, 20, 10);
        this.dx = dx;
        this.dy = dy;
        init();
    }
    
    private void init() {
        this.rotationAngle = random.nextDouble() * 360;
        this.rotationSpeed = (random.nextDouble() - 0.5) * 4;
    }
    
    private void initializeFromEdge() {
        int edge = random.nextInt(4);
        double[] pos = {random.nextDouble() * 800, -getSize(), 800 + getSize(), random.nextDouble() * 600,
                       random.nextDouble() * 800, 600 + getSize(), -getSize(), random.nextDouble() * 600};
        setX(pos[edge * 2]);
        setY(pos[edge * 2 + 1]);
    }
    
    private void initializeMovement() {
        double dirX = 400 - getX(), dirY = 300 - getY();
        double dist = Math.sqrt(dirX * dirX + dirY * dirY);
        double speed = 1.0 + random.nextDouble() * 2.0;
        
        this.dx = (dirX / dist) * speed + (random.nextDouble() - 0.5) * 0.5;
        this.dy = (dirY / dist) * speed + (random.nextDouble() - 0.5) * 0.5;
    }
    
    @Override
    public void update() {
        setX(getX() + dx);
        setY(getY() + dy);
        rotationAngle = (rotationAngle + rotationSpeed + 360) % 360;
    }
    
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getX(), getY());
        g2d.rotate(Math.toRadians(rotationAngle));
        
        // Draw asteroid shape
        int s = getSize();
        g2d.setColor(new Color(139, 69, 19));
        int[] xPoints = {-s/2, -s/3, s/3, s/2, s/3, -s/4};
        int[] yPoints = {-s/3, -s/2, -s/2, 0, s/2, s/2};
        g2d.fillPolygon(xPoints, yPoints, 6);
        
        // Add craters
        g2d.setColor(new Color(101, 67, 33));
        g2d.fillOval(-s/4, -s/6, s/3, s/4);
        g2d.fillOval(s/6, s/8, s/4, s/4);
        
        g2d.rotate(-Math.toRadians(rotationAngle));
        g2d.translate(-getX(), -getY());
    }
    
   
    
    public double getDx() { 
        return dx; 
    }
    
    public double getDy() { 
        return dy; 
    }
    
    public void setDx(double dx) {
        this.dx = dx; 
    }
    
    public void setDy(double dy) { 
        this.dy = dy; 
    }

    /** 
     * Returns true if “this” asteroid overlaps the other Celestial. 
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
    
    
    // Handle collision response (bounce off each other)
    public void handleCollision(Asteroid other) {
        // Mark both as destroyed so GalaxySimulation’s removeIf(...) kicks in
        this.setDestroyed(true);
        other.setDestroyed(true);

        System.out.println(
        "Asteroid at (" + String.format("%.1f", getX()) 
      + "," + String.format("%.1f", getY()) 
      + ") collided with Asteroid at (" 
      + String.format("%.1f", other.getX()) + "," 
      + String.format("%.1f", other.getY()) 
      + ") → both were destroyed"
        );
    }
}