package GalaxyProject;

public class BlackHole extends Celestial {
    private double pullStrength, eventHorizonRadius, influenceRadius, rotationAngle;
    private static final Random random = new Random();
    
    public BlackHole(String name) {
        super(name);
        init();
        if (x == 0 && y == 0) { x = 400; y = 300; }
    }
    
    public BlackHole(String name, double x, double y) {
        super(name, x, y);
        init();
    }
    
    private void init() {
        this.size = 25 + random.nextInt(15);
        this.mass = size * 5.0;
        this.pullStrength = mass * 0.1;
        this.eventHorizonRadius = size * 0.6;
        this.influenceRadius = size * 8;
        this.rotationAngle = 0;
    }
    
    @Override
    public void update() {
        rotationAngle = (rotationAngle + 2.0) % 360;
    }
    
    public void applyGravitationalPull(Celestial other) {
        if (other == this) return;
        
        double distance = Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
        
        if (distance <= eventHorizonRadius) {
            other.setDestroyed(true);
            return;
        }
        
        if (distance <= influenceRadius && distance > 0) {
            double pullForce = pullStrength / (distance * distance);
            double dirX = (x - other.getX()) / distance;
            double dirY = (y - other.getY()) / distance;
            
            if (other instanceof Asteroid) {
                Asteroid a = (Asteroid) other;
                a.setDx(a.getDx() + dirX * pullForce);
                a.setDy(a.getDy() + dirY * pullForce);
            } else if (other instanceof Comet) {
                Comet c = (Comet) other;
                c.setDx(c.getDx() + dirX * pullForce);
                c.setDy(c.getDy() + dirY * pullForce);
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(rotationAngle));
        
        // Accretion disk
        for (int i = 3; i >= 1; i--) {
            g2d.setColor(new Color(255, 100, 0, 50 + i * 30));
            int diskSize = size * (i + 1);
            g2d.fillOval(-diskSize/2, -diskSize/2, diskSize, diskSize);
        }
        
        // Black hole and event horizon
        g2d.setColor(Color.BLACK);
        g2d.fillOval(-size/2, -size/2, size, size);
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.drawOval(-size/2, -size/2, size, size);
        
        g2d.rotate(-Math.toRadians(rotationAngle));
        g2d.translate(-x, -y);
    }
    
    public double getPullStrength() { return pullStrength; }
    public double getEventHorizonRadius() { return eventHorizonRadius; }
    public double getInfluenceRadius() { return influenceRadius; }
}
     
