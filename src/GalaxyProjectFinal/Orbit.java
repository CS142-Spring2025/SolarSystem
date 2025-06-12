package GalaxyProjectFinal;

// Uses a Orbit class that stores the XCenter coordinate, YCenter coordinate, radius, speed, and angle for the orbit
// A Orbit class extends the superclass Celestial 
public class Orbit extends Celestial {
    private double XCenter;
    private double YCenter;
    private double radius;
    private double speed;
    private double angle;
    protected Celestial center;
    
    // A Orbit constructor which initializes the XCenter coordinate, YCenter coordinate, radius, speed, and angle
    // Calls a super to initialize center x and y coordinates, mass and size
    public Orbit(Celestial center, double radius, double speed, double mass, int size) {
        super(center.getX() + radius, center.getY(), mass, size);
        
        this.center = center;
        this.radius = radius;
        this.speed = speed;
        this.angle = 0;
    }
    
    // A getXCenter method which returns the X coordinate of the orbit's center
    public double getXCenter() {
        return XCenter;
    }
    
    // A getYCenter method which returns the Y coordinate of the orbit's center
    public double getYCenter() {
        return YCenter;
    }

    public Celestial getCenter() {
        return center;
    }
    
    // A getRadius method which returns the radius of the orbit
    public double getRadius(){
        return radius;
    }
    
    // A getSpeed method which returns the speed of the orbit
    public double getSpeed() {
        return speed;
    }
    
    // A getAngle method which returns the angle of the orbit 
    public double getAngle() {
        return angle;
    }
    
    // A setCenter method which sets the X and Y center coordinates of the orbit
    public void setCenter(Celestial newCenter) {
        this.center = newCenter;
    }
    
    

    // An overriden update method which updates the position's of the x and y coordinates
    // Ensuring that it stays within 0 and 2PI
    @Override
    public void update() {
        angle += speed;
        
        if (angle > 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }   
        
        setX(center.getX() + radius * Math.cos(angle));
        setY(center.getY() + radius * Math.sin(angle));
    }
}
 