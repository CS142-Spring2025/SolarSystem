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

    public void update() {
    // 1) Move every object
    for (Celestial obj : objects) {
        obj.update();
    }

    // 2) Check all pairs for collisions, then do black-hole pull
    for (int i = 0; i < objects.size(); i++) {
        Celestial a = objects.get(i);
        if (a.isDestroyed()) continue;

        for (int j = i + 1; j < objects.size(); j++) {
            Celestial b = objects.get(j);
            if (b.isDestroyed()) continue;

            // --- COMET hits anything: both destroyed ---
            if (a instanceof Comet) {
                Comet comA = (Comet) a;
                if (comA.collidesWith(b)) {
                    System.out.println(">>> Comet-Collision: destroying both "
                        + comA.getClass().getSimpleName() 
                        + " and " + b.getClass().getSimpleName());
                    comA.setDestroyed(true);
                    b.setDestroyed(true);
                    continue;
                }
            }
            if (b instanceof Comet) {
                Comet comB = (Comet) b;
                if (comB.collidesWith(a)) {
                    System.out.println(">>> Comet-Collision: destroying both "
                        + a.getClass().getSimpleName() 
                        + " and " + comB.getClass().getSimpleName());
                    a.setDestroyed(true);
                    comB.setDestroyed(true);
                    continue;
                }
            }

            // --- ASTEROID vs. ASTEROID: destroy both if they overlap ---
            if (a instanceof Asteroid && b instanceof Asteroid) {
                Asteroid astA = (Asteroid) a;
                Asteroid astB = (Asteroid) b;
                if (astA.collidesWith(astB)) {
                    System.out.println(">>> Asteroid-Asteroid collision: destroying both.");
                    astA.setDestroyed(true);
                    astB.setDestroyed(true);
                    continue;
                }
            }

            // --- ASTEROID vs. PLANET or PLANET vs. ASTEROID ---
            if (a instanceof Asteroid && b instanceof Planet) {
                Asteroid ast = (Asteroid) a;
                Planet  pl  = (Planet)  b;
                if (ast.collidesWith(pl)) {
                    System.out.print(">>> Asteroid-Planet collision: destroying Asteroid. ");
                    ast.setDestroyed(true);

                    // Asteroid hit Planet → 50/50 on planet
                    if (Math.random() < 0.5) {
                        pl.setDestroyed(true);
                        System.out.println("Planet was destroyed too.");
                    } else {
                        System.out.println("Planet survived.");
                    }
                    continue;
                }
            }
            if (a instanceof Planet && b instanceof Asteroid) {
                Planet  pl  = (Planet)  a;
                Asteroid ast = (Asteroid) b;
                if (ast.collidesWith(pl)) {
                    // Planet hit Asteroid → destroy Asteroid, planet survives
                    System.out.println(">>> Planet-Asteroid collision: destroying Asteroid; Planet survives.");
                    ast.setDestroyed(true);
                    continue;
                }
            }

            // (Planet-Planet collisions skipped per rules)

            // --- BLACK HOLE gravitational pull ---
            if (a instanceof BlackHole) {
                ((BlackHole) a).applyGravitationalPull(b);
            }
            if (b instanceof BlackHole) {
                ((BlackHole) b).applyGravitationalPull(a);
            }
        }
    }

    // 3) Remove destroyed objects
    objects.removeIf(Celestial::isDestroyed);
}
               
        
}


