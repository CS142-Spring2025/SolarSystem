package GalaxyProject;

import java.awt.*;

public class Celestial {
    private double x;
    private double y;
    private double mass;
    private int size;
    
    public Celestial(double x, double y, double mass, int size) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.size = size;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getMass() {
        return mass;
    }
    
    public int getSize() {
        return size;
    }
    
    public void update() {
        
    }
    
    public void draw(Graphics g) {
        
    }
    
}
