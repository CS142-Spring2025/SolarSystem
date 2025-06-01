
package GalaxyProject;

public class Asteroid {

// Asteroid class - linear movement, enters from edges
public class Asteroid extends Celestial {
    private double dx, dy; // Direction of movement (velocity components)
    private double rotationAngle; // For visual rotation effect
    private double rotationSpeed;
    private static final Random random = new Random();
    
    public Asteroid(String name) {
        super(name);
        this.size = 8 + random.nextInt(12); // Size between 8-20 pixels
        this.mass = size * 0.5; // Mass proportional to size
        
        // Initialize position at random edge of screen
        initializeFromEdge();
        
        // Set movement direction toward center with some randomness
        initializeMovement();
        
        // Random rotation for visual effect
        this.rotationAngle = random.nextDouble() * 360;
        this.rotationSpeed = (random.nextDouble() - 0.5) * 4; // -2 to +2 degrees per tick
    }
    
    // Constructor with specific position and velocity (for controlled placement)
    public Asteroid(String name, double x, double y, double dx, double dy) {
        super(name, x, y);
        this.dx = dx;
        this.dy = dy;
        this.size = 8 + random.nextInt(12);
        this.mass = size * 0.5;
        this.rotationAngle = random.nextDouble() * 360;
        this.rotationSpeed = (random.nextDouble() - 0.5) * 4;
    }
    
    private void initializeFromEdge() {
        // Assume grid dimensions (these should be passed from SimulationGrid)
        int gridWidth = 800;  // You'll want to get this from your grid
        int gridHeight = 600;
        
        int edge = random.nextInt(4); // 0=top, 1=right, 2=bottom, 3=left
        
        switch(edge) {
            case 0: // Top edge
                this.x = random.nextDouble() * gridWidth;
                this.y = -size;
                break;
            case 1: // Right edge
                this.x = gridWidth + size;
                this.y = random.nextDouble() * gridHeight;
                break;
            case 2: // Bottom edge
                this.x = random.nextDouble() * gridWidth;
                this.y = gridHeight + size;
                break;
            case 3: // Left edge
                this.x = -size;
                this.y = random.nextDouble() * gridHeight;
                break;
        }
    }
    
    private void initializeMovement() {
        // Move generally toward center with some randomness
        double centerX = 400; // Grid center X
        double centerY = 300; // Grid center Y
        
        double directionX = centerX - this.x;
        double directionY = centerY - this.y;
        
        // Normalize and set speed
        double distance = Math.sqrt(directionX * directionX + directionY * directionY);
        double speed = 1.0 + random.nextDouble() * 2.0; // Speed between 1-3 pixels per tick
        
        this.dx = (directionX / distance) * speed;
        this.dy = (directionY / distance) * speed;
        
        // Add some randomness to direction
        this.dx += (random.nextDouble() - 0.5) * 0.5;
        this.dy += (random.nextDouble() - 0.5) * 0.5;
    }
    
    @Override
    public void update() {
        // Linear movement
        x += dx;
        y += dy;
        
        // Update rotation for visual effect
        rotationAngle += rotationSpeed;
        if (rotationAngle >= 360) rotationAngle -= 360;
        if (rotationAngle < 0) rotationAngle += 360;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        // Save original transform
        Graphics2D g = (Graphics2D) g2d.create();
        
        // Set color
        g.setColor(new Color(139, 69, 19)); // Brown color for asteroid
        
        // Translate to asteroid center and rotate
        g.translate(x, y);
        g.rotate(Math.toRadians(rotationAngle));
        
        // Draw irregular asteroid shape
        int[] xPoints = {-size/2, -size/3, size/3, size/2, size/3, -size/4};
        int[] yPoints = {-size/3, -size/2, -size/2, 0, size/2, size/2};
        g.fillPolygon(xPoints, yPoints, 6);
        
        // Add some detail/craters
        g.setColor(new Color(101, 67, 33));
        g.fillOval(-size/4, -size/6, size/3, size/4);
        g.fillOval(size/6, size/8, size/4, size/4);
        
        g.dispose();
    }
    
    // Getters for movement
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}
}
