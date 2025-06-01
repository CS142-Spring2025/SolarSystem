
package GalaxyProject;

public class Blackhole {

// BlackHole class - pulls nearby objects inward
public class BlackHole extends Celestial {
    private double pullStrength; // How much it pulls nearby objects
    private double eventHorizonRadius; // Objects closer than this get destroyed
    private double influenceRadius; // Objects within this range are affected
    private double rotationAngle; // For visual spinning effect
    private static final Random random = new Random();
    
    public BlackHole(String name) {
        super(name);
        this.size = 25 + random.nextInt(15); // Size between 25-40 pixels
        this.mass = size * 5.0; // Very massive
        this.pullStrength = mass * 0.1; // Pull strength based on mass
        this.eventHorizonRadius = size * 0.6; // Event horizon smaller than visual size
        this.influenceRadius = size * 8; // Large influence radius
        this.rotationAngle = 0;
        
        // Black holes are typically placed manually or at specific locations
        // Default to center if no position specified
        if (x == 0 && y == 0) {
            this.x = 400; // Center X
            this.y = 300; // Center Y
        }
    }
    
    public BlackHole(String name, double x, double y) {
        super(name, x, y);
        this.size = 25 + random.nextInt(15);
        this.mass = size * 5.0;
        this.pullStrength = mass * 0.1;
        this.eventHorizonRadius = size * 0.6;
        this.influenceRadius = size * 8;
        this.rotationAngle = 0;
    }
    
    @Override
    public void update() {
        // Black holes don't move, but they rotate for visual effect
        rotationAngle += 2.0; // Spin slowly
        if (rotationAngle >= 360) rotationAngle -= 360;
    }
    
    // Method to apply gravitational pull to other objects
    public void applyGravitationalPull(Celestial other) {
        if (other == this) return; // Don't pull self
        
        double distance = getDistanceTo(other);
        
        // Check if object is within event horizon (gets destroyed)
        if (distance <= eventHorizonRadius) {
            // Mark object for destruction (you'll need to handle this in your simulation)
            other.setDestroyed(true); // Assuming you add this method to Celestial
            return;
        }
        
        // Apply gravitational pull if within influence radius
        if (distance <= influenceRadius && distance > 0) {
            double pullForce = pullStrength / (distance * distance); // Inverse square law
            
            // Calculate direction toward black hole
            double directionX = (this.x - other.getX()) / distance;
            double directionY = (this.y - other.getY()) / distance;
            
            // Apply force (this assumes other objects have velocity that can be modified)
            if (other instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) other;
                asteroid.setDx(asteroid.getDx() + directionX * pullForce);
                asteroid.setDy(asteroid.getDy() + directionY * pullForce);
            } else if (other instanceof Comet) {
                Comet comet = (Comet) other;
                comet.setDx(comet.getDx() + directionX * pullForce);
                comet.setDy(comet.getDy() + directionY * pullForce);
            }
            // You can add more object types as needed
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d.create();
        
        // Draw accretion disk (spinning matter around black hole)
        g.translate(x, y);
        g.rotate(Math.toRadians(rotationAngle));
        
        // Accretion disk
        for (int i = 3; i >= 1; i--) {
            int diskSize = size * (i + 1);
            int alpha = 50 + (i * 30);
            g.setColor(new Color(255, 100, 0, alpha)); // Orange glow
            g.fillOval(-diskSize/2, -diskSize/2, diskSize, diskSize);
        }
        
        // Black hole itself (pure black)
        g.setColor(Color.BLACK);
        g.fillOval(-size/2, -size/2, size, size);
        
        // Event horizon glow
        g.setColor(new Color(255, 255, 255, 100));
        g.drawOval(-size/2, -size/2, size, size);
        
        g.dispose();
    }
    
    // Helper method to calculate distance to another object
    private double getDistanceTo(Celestial other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // Getters
    public double getPullStrength() { return pullStrength; }
    public double getEventHorizonRadius() { return eventHorizonRadius; }
    public double getInfluenceRadius() { return influenceRadius; }
}
}
