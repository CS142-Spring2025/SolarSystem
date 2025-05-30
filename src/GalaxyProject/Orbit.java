package GalaxyProject;

public class Orbit extends Celestial {
    private double XCenter;
    private double YCenter;
    private double radius;
    private double speed;
    private double angle;
    
    public Orbit(double XCenter, double YCenter, double radius, double speed, double angle, double mass, int size) {
        super(XCenter + radius, YCenter, mass, size);
        
        this.XCenter = XCenter;
        this.YCenter = YCenter;
        this.radius = radius;
        this.speed = speed;
        this.angle = 0;
    }
    
    public double getXCenter() {
        return XCenter;
    }
    
    public double getYCenter() {
        return YCenter;
    }
    
    public double getRadius(){
        return radius;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public void setCenter(double XCenter, double YCenter) {
        this.XCenter = XCenter;
        this.YCenter = YCenter;
    }
    
    @Override
    public void update() {
        angle += speed;
        if (angle > 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }   
        setX(XCenter + radius * Math.cos(angle));
        setY(YCenter + radius * Math.sin(angle));
    }
}
