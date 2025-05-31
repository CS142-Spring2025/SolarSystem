import java.util.ArrayList;
import java.util.List;

/*
 * The "Brains" of the galaxy.  Will contain/manage all celestial objects
 * because each object knows it's own position, size and behavior, the model will provide:
 * a way to add new objects, a way to update the galaxy's state each tick of the timer, and
 * a way to let the GUI access the object for drawing.
 */

 public class GalaxySimulation {
    private List<Celestial> objects;   //list that hold all celestial objects

    public GalaxySimulation() {
        objects = new ArrayList<>();
    }

    public boid addObject(Celestial obj) { //adds new objects to the list called from GalaxyMain after user input
        objects.add(obj);
    }

    public List<Celestial> getObjects() {
        return objects;
    }

        public void update() {   //loops through all objects and calls their update().
            for (Celestial obj : objects) {
                obj.update();
         
            }
            //placeholder for possible collision logic?
        }
 }