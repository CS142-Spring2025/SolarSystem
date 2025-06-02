package GalaxyProjectFinal;
import java.awt.*;

// Comet class extends Celestial, following the same simple pattern as Moon
public class Comet extends Celestial {
    private double XSpeed;
    private double YSpeed;
    private int age;
    private int ticks;
    
    // A Comet constructor which takes only a name and sets fixed mass and size values
    // Similar to how Moon has fixed mass 160 and size 10
    public Comet(double x, double y) {
        super(x, y, 80, 8); // Fixed mass 80, size 8
    }
    
    // An overridden update method - simple like Moon's update
    @Override
    public void update() {
        // Comets are relatively stationary in this simple version
        // Could add simple movement or effects here if needed
    }
    
    // An overridden draw method which draws a simple comet with tail
    @Override
    public void draw(Graphics g) {
        // Draw comet tail (simple trailing effect)
        g.setColor(new Color(255, 255, 200, 100));
        g.fillOval((int)(getX() - getSize() * 2), (int)(getY() - getSize()), getSize() * 3, getSize() * 2);
        
        // Draw comet nucleus (main body)
        g.setColor(new Color(200, 200, 255));
        g.fillOval((int)(getX() - getSize() / 2), (int)(getY() - getSize() / 2), getSize(), getSize());
        
        // Draw bright center
        g.setColor(Color.WHITE);
        g.fillOval((int)(getX() - getSize() / 4), (int)(getY() - getSize() / 4), getSize() / 2, getSize() / 2);
    }
    
    // An overridden getType method which returns the String of the type "Comet"
    @Override
    public String getType() {
        return "Comet";
    }
}