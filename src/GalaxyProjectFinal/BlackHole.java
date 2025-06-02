package GalaxyProjectFinal;

import java.awt.Color;
import java.awt.Graphics;

public class BlackHole extends Celestial {
    
    public BlackHole(double x, double y) {
        super(x, y, 2000, 22);
    }

    @Override
    public void update() {
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval((int)(getX() - getSize()/2), (int)(getY() - getSize()/2), getSize(), getSize());
        
        g.setColor(Color.RED);
        g.fillOval((int)(getX() - getSize()/2), (int)(getY() - getSize()/2), getSize(), getSize());
    }
    
    @Override
    public String getType() {
        return "BlackHole";
    }
}

