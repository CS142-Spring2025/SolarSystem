
 package GalaxyProject;

public class Asteroid extends Celestial {
    private double dx, dy, rotationAngle, rotationSpeed;
    private static final Random random = new Random();
    
    public Asteroid(String name) {
        super(name);
        init();
        initializeFromEdge();
        initializeMovement();
    }
    
    public Asteroid(String name, double x, double y, double dx, double dy) {
        super(name, x, y);
        this.dx = dx;
        this.dy = dy;
        init();
    }
    
    private void init() {
        this.size = 8 + random.nextInt(12);
        this.mass = size * 0.5;
        this.rotationAngle = random.nextDouble() * 360;
        this.rotationSpeed = (random.nextDouble() - 0.5) * 4;
    }
    
    private void initializeFromEdge() {
        int edge = random.nextInt(4);
        double[] pos = {random.nextDouble() * 800, -size, 800 + size, random.nextDouble() * 600,
                       random.nextDouble() * 800, 600 + size, -size, random.nextDouble() * 600};
        this.x = pos[edge * 2];
        this.y = pos[edge * 2 + 1];
    }
    
    private void initializeMovement() {
        double dirX = 400 - this.x, dirY = 300 - this.y;
        double dist = Math.sqrt(dirX * dirX + dirY * dirY);
        double speed = 1.0 + random.nextDouble() * 2.0;
        
        this.dx = (dirX / dist) * speed + (random.nextDouble() - 0.5) * 0.5;
        this.dy = (dirY / dist) * speed + (random.nextDouble() - 0.5) * 0.5;
    }
    
    @Override
    public void update() {
        x += dx;
        y += dy;
        rotationAngle = (rotationAngle + rotationSpeed + 360) % 360;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(rotationAngle));
        
        // Draw asteroid shape
        g2d.setColor(new Color(139, 69, 19));
        int[] xPoints = {-size/2, -size/3, size/3, size/2, size/3, -size/4};
        int[] yPoints = {-size/3, -size/2, -size/2, 0, size/2, size/2};
        g2d.fillPolygon(xPoints, yPoints, 6);
        
        // Add craters
        g2d.setColor(new Color(101, 67, 33));
        g2d.fillOval(-size/4, -size/6, size/3, size/4);
        g2d.fillOval(size/6, size/8, size/4, size/4);
        
        g2d.rotate(-Math.toRadians(rotationAngle));
        g2d.translate(-x, -y);
    }
    
    // Collision detection
    public boolean collidesWith(Celestial other) {
        double distance = Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
        return distance < (size + other.getSize()) / 2;
    }
    
    // Handle collision response (bounce off each other)
    public void handleCollision(Asteroid other) {
        // Simple elastic collision - swap velocities
        double tempDx = this.dx;
        double tempDy = this.dy;
        this.dx = other.dx;
        this.dy = other.dy;
        other.dx = tempDx;
        other.dy = tempDy;
    }
    
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}
