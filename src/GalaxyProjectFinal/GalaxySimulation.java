package GalaxyProjectFinal;
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

    public void addObject(Celestial obj) { //adds new objects to the list called from GalaxyMain after user input
        objects.add(obj);
    }

    public List<Celestial> getObjects() {
        return objects;
    }

    public void update() {   //loops through all objects and calls their update().
            
        for (Celestial obj : objects) {
            obj.update();
         
        }
            //check collisions and black hole pull
        for (int i = 0; i < objects.size(); i++) {
            Celestial a = objects.get(i);
            if (a.isDestroyed()) continue;

            for (int j = i + 1; j < objects.size(); j++) {
                Celestial b = objects.get(j);
                if (b.isDestroyed()) continue;

                // Check collisions (using existing helper methods)
                //asteroid-asteroid collision
                if (a instanceof Asteroid && b instanceof Asteroid) {
                    Asteroid aAst = (Asteroid) a;
                    Asteroid bAst = (Asteroid) b;
                    if (aAst.collidesWith(bAst)) {
                        aAst.handleCollision(bAst);
                    }
                } else if (a instanceof Comet && b instanceof Comet) {
                    Comet aCom = (Comet) a;
                    Comet bCom = (Comet) b;
                    if (aCom.collidesWith(bCom)) {
                        aCom.handleCollision(bCom);
                    }
                } else if ((a instanceof Comet && b instanceof Asteroid) || (a instanceof Asteroid && b instanceof Comet)) {
                    Comet c = (a instanceof Comet) ? (Comet) a : (Comet) b;
                    Asteroid m = (a instanceof Asteroid) ? (Asteroid) a : (Asteroid) b;
                    if (c.collidesWith(m)) {
                        c.handleCollision(m);
                    }
                }
               
                
                ////black hole gravitational pull
                
                if (a instanceof BlackHole) {
                    ((BlackHole) a).applyGravitationalPull(b);
                }
                if (b instanceof BlackHole) {
                    ((BlackHole) b).applyGravitationalPull(a);
                }
            }    
        }      
               
        // Remove destroyed objects
        objects.removeIf(Celestial::isDestroyed);
    }
}


