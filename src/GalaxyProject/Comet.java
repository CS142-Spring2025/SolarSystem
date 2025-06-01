package GalaxyProject;

public class Comet {

// Comet class - similar to asteroid but with tail effect
public class Comet extends Celestial {
    private double dx, dy; // Direction of movement
    private double tailLength;
    private static final Random random = new Random();
    
    public Comet(String name) {
        super(name);
        this.size = 6 + random.nextInt(8); // Size between 6-14 pixels
        this.mass = size * 0.3; // Lighter than asteroids
        this.tailLength = size * 3; // Tail length proportional to size
        
        // Initialize from edge like asteroids
        initializeFromEdge();
        initializeMovement();
    }
    
    public Comet(String name, double x, double y, double dx, double dy) {
        super(name, x, y);
        this.dx = dx;
        this.dy = dy;
        this.size = 6 + random.nextInt(8);
        this.mass = size * 0.3;
        this.tailLength = size * 3;
    }
    
    private void initializeFromEdge() {
        int gridWidth = 800;
        int gridHeight = 600;
        
        int edge = random.nextInt(4);
        
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
        double centerX = 400;
        double centerY = 300;
        
        double directionX = centerX - this.x;
        double directionY = centerY - this.y;
        
        double distance = Math.sqrt(directionX * directionX + directionY * directionY);
        double speed = 2.0 + random.nextDouble() * 3.0; // Faster than asteroids (2-5 pixels/tick)
        
        this.dx = (directionX / distance) * speed;
        this.dy = (directionY / distance) * speed;
        
        // Add randomness
        this.dx += (random.nextDouble() - 0.5) * 1.0;
        this.dy += (random.nextDouble() - 0.5) * 1.0;
    }
    
    @Override
    public void update() {
        // Linear movement (faster than asteroids)
        x += dx;
        y += dy;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d.create();
        
        // Draw tail first (behind the comet)
        drawTail(g);
        
        // Draw comet nucleus
        g.setColor(new Color(200, 200, 255)); // Icy blue-white
        g.fillOval((int)(x - size/2), (int)(y - size/2), size, size);
        
        // Add bright center
        g.setColor(Color.WHITE);
        g.fillOval((int)(x - size/4), (int)(y - size/4), size/2, size/2);
        
        g.dispose();
    }
    
    private void drawTail(Graphics2D g) {
        // Calculate tail direction (opposite to movement)
        double tailDx = -dx;
        double tailDy = -dy;
        
        // Normalize tail direction
        double tailMagnitude = Math.sqrt(tailDx * tailDx + tailDy * tailDy);
        if (tailMagnitude > 0) {
            tailDx = (tailDx / tailMagnitude) * tailLength;
            tailDy = (tailDy / tailMagnitude) * tailLength;
        }
        
        // Draw tail as gradient
        for (int i = 0; i < 10; i++) {
            double alpha = 1.0 - (i / 10.0); // Fade out
            int tailX = (int)(x + tailDx * i / 10.0);
            int tailY = (int)(y + tailDy * i / 10.0);
            int tailSize = size + i;
            
            g.setColor(new Color(255, 255, 200, (int)(alpha * 100))); // Yellowish tail
            g.fillOval(tailX - tailSize/2, tailY - tailSize/2, tailSize, tailSize);
        }
    }
    
    // Getters for movement
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}
}
