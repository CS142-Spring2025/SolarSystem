package GalaxyProject;

import java.awt.*;

// Uses a Celestial class that stores an x coordinate, y coordinate, mass and size
public class Celestial {
    private double x;
    private double y;
    private double mass;
    private int size;
    
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
    
    // A update method that returns nothing and will be overriden by subclasses
    public void update() {
        
    }
    
    // A draw method that returns nothing and will be overriden by subclasses
    public void draw(Graphics g) {
        
    }
    
    // A getType method that returns an error if type cannot be found
    public String getType() {
        throw new UnsupportedOperationException("Type not found");
    }
    
}
