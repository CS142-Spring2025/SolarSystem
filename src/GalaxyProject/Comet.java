
        
package GalaxyProject;

public class Comet extends Celestial {
    private double dx, dy, tailLength;
    private static final Random random = new Random();
    
    public Comet(String name) {
        super(name);
        this.size = 6 + random.nextInt(8);
        this.mass = size * 0.3;
        this.tailLength = size * 3;
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
        int edge = random.nextInt(4);
        double[] pos = {random.nextDouble() * 800, -size, 800 + size, random.nextDouble() * 600, 
                       random.nextDouble() * 800, 600 + size, -size, random.nextDouble() * 600};
        this.x = pos[edge * 2];
        this.y = pos[edge * 2 + 1];
    }
    
    private void initializeMovement() {
        double dirX = 400 - this.x, dirY = 300 - this.y;
        double dist = Math.sqrt(dirX * dirX + dirY * dirY);
        double speed = 2.0 + random.nextDouble() * 3.0;
        
        this.dx = (dirX / dist) * speed + (random.nextDouble() - 0.5);
        this.dy = (dirY / dist) * speed + (random.nextDouble() - 0.5);
    }
    
    @Override
    public void update() {
        x += dx;
        y += dy;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        // Draw tail
        double tailDx = -dx, tailDy = -dy;
        double tailMag = Math.sqrt(tailDx * tailDx + tailDy * tailDy);
        if (tailMag > 0) {
            tailDx = (tailDx / tailMag) * tailLength;
            tailDy = (tailDy / tailMag) * tailLength;
            
            for (int i = 0; i < 10; i++) {
                double alpha = 1.0 - i / 10.0;
                int tailX = (int)(x + tailDx * i / 10.0);
                int tailY = (int)(y + tailDy * i / 10.0);
                g2d.setColor(new Color(255, 255, 200, (int)(alpha * 100)));
                g2d.fillOval(tailX - (size + i)/2, tailY - (size + i)/2, size + i, size + i);
            }
        }
        
        // Draw comet nucleus
        g2d.setColor(new Color(200, 200, 255));
        g2d.fillOval((int)(x - size/2), (int)(y - size/2), size, size);
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)(x - size/4), (int)(y - size/4), size/2, size/2);
    }
    
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}
