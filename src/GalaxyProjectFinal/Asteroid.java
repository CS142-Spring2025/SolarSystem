package GalaxyProjectFinal;
import java.awt.*;
import java.util.Random;

// Uses an Asteroid class which moves across the screen
// An Asteroid class extends the Celestial class
public class Asteroid extends Celestial {
    private double dx, dy, rotationAngle, rotationSpeed;
    private static final Random random = new Random();
    
    // An Asteroid constructor which calls the super with name and initializes movement properties
    // Generates random size, mass, rotation properties and spawns from screen edges
    public Asteroid(String name) {
        super(name);
        initializeProperties();
        initializeFromEdge();
        initializeMovement();
    }
    
    // An Asteroid constructor with specified position and velocity
    // Used for creating asteroids at specific locations with defined movement
    public Asteroid(String name, double x, double y, double dx, double dy) {
        super(name, x, y);
        this.dx = dx;
        this.dy = dy;
        initializeProperties();
    }
    
    // A method which initializes the asteroid's basic properties
    // Sets random size, mass based on size, and rotation characteristics  
    private void initializeProperties() {
        this.size = 8 + random.nextInt(12);
        this.mass = size * 0.5;
        this.rotationAngle = random.nextDouble() * 360;
        this.rotationSpeed = (random.nextDouble() - 0.5) * 4;
    }
    
    // A method which spawns the asteroid from a random edge of the screen
    // Positions asteroid outside visible area on one of four screen edges
    private void initializeFromEdge() {
        int edge = random.nextInt(4);
        double[] positions = {random.nextDouble() * 800, -size, 800 + size, random.nextDouble() * 600,
                             random.nextDouble() * 800, 600 + size, -size, random.nextDouble() * 600};
        this.x = positions[edge * 2];
        this.y = positions[edge * 2 + 1];
    }
    
    // A method which calculates initial movement direction toward screen center
    // Adds random variation to create natural asteroid movement patterns
    private void initializeMovement() {
        double directionX = 400 - this.x;
        double directionY = 300 - this.y;
        double distance = Math.sqrt(directionX * directionX + directionY * directionY);
        double speed = 1.0 + random.nextDouble() * 2.0;
        
        this.dx = (directionX / distance) * speed + (random.nextDouble() - 0.5) * 0.5;
        this.dy = (directionY / distance) * speed + (random.nextDouble() - 0.5) * 0.5;
    }
    
    // An overridden update method which moves the asteroid and rotates it
    // Updates position based on velocity and continuously rotates the asteroid
    @Override
    public void update() {
        x += dx;
        y += dy;
        rotationAngle = (rotationAngle + rotationSpeed + 360) % 360;
    }
    
    // An overridden draw method which draws an irregular brown asteroid shape
    // Creates a polygon with craters to represent a realistic asteroid appearance
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(rotationAngle));
        
        // Draw main asteroid body
        g2d.setColor(new Color(139, 69, 19));
        int[] xPoints = {-size/2, -size/3, size/3, size/2, size/3, -size/4};
        int[] yPoints = {-size/3, -size/2, -size/2, 0, size/2, size/2};
        g2d.fillPolygon(xPoints, yPoints, 6);
        
        // Add surface craters for detail
        g2d.setColor(new Color(101, 67, 33));
        g2d.fillOval(-size/4, -size/6, size/3, size/4);
        g2d.fillOval(size/6, size/8, size/4, size/4);
        
        g2d.rotate(-Math.toRadians(rotationAngle));
        g2d.translate(-x, -y);
    }
    
    // An overridden getType method which returns the String of the type "Asteroid"
    @Override
    public String getType() {
        return "Asteroid";
    }
    
    // A method which checks collision with another celestial object
    // Returns true if the distance between objects is less than combined radii
    public boolean collidesWith(Celestial other) {
        double distance = Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
        return distance < (size + other.getSize()) / 2;
    }
    
    // A method which handles collision response between two asteroids
    // Implements simple elastic collision by swapping velocities
    public void handleCollision(Asteroid other) {
        double tempDx = this.dx;
        double tempDy = this.dy;
        this.dx = other.dx;
        this.dy = other.dy;
        other.dx = tempDx;
        other.dy = tempDy;
    }
    
    // Getter and setter methods for velocity components
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}