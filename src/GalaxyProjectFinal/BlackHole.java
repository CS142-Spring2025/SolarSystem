package GalaxyProjectFinal;

import java.awt.*;

public class BlackHole extends Celestial {
    private double pullStrength;

    public BlackHole(int x, int y, int size, double mass, double pullStrength) {
        super(x, y, 1500, 18);
        this.pullStrength = pullStrength;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval((int)(getX() - getSize() / 2), (int)(getY() - getSize() / 2), getSize(), getSize());
        
        g.setColor(Color.RED);
        g.fillOval((int)(getX() - getSize() / 2), (int)(getY() - getSize() / 2), getSize(), getSize());
    }

    @Override
    public String getType() {
        return "BlackHole";
    }
}

