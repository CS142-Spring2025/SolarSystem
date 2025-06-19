package GalaxyProjectFinal;
import java.awt.*;

/**
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *<pre>
 Class        Celestial
 File        Celestial.java
 Description   Parent class for celestial objects in the galaxy simulation.
               Contains common properties and methods for celestial objects.
 @author       <i>Kaitlyn Le</i><i>Ruth Karen Nakigozi</i><i>Emma Dennis</i>
 Date          5/29/2025
 History Log    
 *</pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

// Uses a Celestial class that stores an x coordinate, y coordinate, mass and size
public class Celestial {
    private double x;
    private double y;
    private double mass;
    private int size;
    private boolean destroyed = false;  // <-- NEW FIELD
    
    // A Celestial constructor which initializes the variables x, y, mass, and size
    public Celestial(double x, double y, double mass, int size) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.size = size;
    }
    
    // A getX method that returns the x coordinate
    public double getX() {
        return x;
    }
    
    // A getY method that returns the y coordinate
    public double getY() {
        return y;
    }
    
    // A getMass method that returns the mass
    public double getMass() {
        return mass;
    }
    
    // A getSize method that returns the drawing size
    public int getSize() {
        return size;
    }

    public void setMass(double mass) {
    this.mass = mass;
}

    public void setSize(int size) {
        this.size = size;
    }
    
    // A setX method that sets the updated x position
    public void setX(double x) {
        this.x = x;
    }
    
    // A setY method that sets the updated y position
    public void setY(double y) {
        this.y = y;
    }
    
    // A update method that returns nothing and will be overriden by subclasses
    public void update() {
        return;
    }
    
    // A draw method that returns nothing and will be overriden by subclasses
    public void draw(Graphics g) {
        return;
    }
    
    // A getType method that returns an error if type cannot be found
    public String getType() {
        throw new UnsupportedOperationException("Type not found");
    }


    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
    
}
