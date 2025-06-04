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
            // --- ASTEROID vs. MOON or MOON vs. ASTEROID ---
            if (a instanceof Asteroid && b instanceof Moon) {
                Asteroid ast = (Asteroid) a;
                Moon     mo  = (Moon)     b;
                if (ast.collidesWith(mo)) {
                    // Asteroid always destroyed
                    ast.setDestroyed(true);
                    
                    // 50/50 chance Moon is destroyed
                    if (Math.random() < 0.5) {
                        mo.setDestroyed(true);
                    System.out.println(">>> Asteroid‐Moon collision: destroying Asteroid; Moon was destroyed.");
                    } else {
                        System.out.println(">>> Asteroid‐Moon collision: destroying Asteroid; Moon survived.");
                    }
                    continue;
                }
            }
            if (a instanceof Moon && b instanceof Asteroid) {
                Moon     mo  = (Moon)     a;
                Asteroid ast = (Asteroid) b;
                if (ast.collidesWith(mo)) {
                    // Moon hits Asteroid: asteroid destroyed, moon survives
                    ast.setDestroyed(true);
                    System.out.println(">>> Moon‐Asteroid collision: destroying Asteroid; Moon survives.");
                    continue;
                }
            }

            // --- ASTEROID vs STAR or STAR vs. ASTEROID ---
            if (a instanceof Asteroid && b instanceof Star) {
                Asteroid ast = (Asteroid) a;
                Star     st  = (Star)     b;
                if (ast.collidesWith(st)) {
                    ast.setDestroyed(true);
                    System.out.println(">>> Asteroid‐Star collision: destroying Asteroid.");
                    continue;
                }
            }
            if (a instanceof Star && b instanceof Asteroid) {
                Star     st  = (Star)     a;
                Asteroid ast = (Asteroid) b;
                if (ast.collidesWith(st)) {
                    ast.setDestroyed(true);
                    System.out.println(">>> Star‐Asteroid collision: destroying Asteroid.");
                    continue;
                }
            }

            // (Planet-Planet collisions skipped for now)

            // --- BLACK HOLE gravitational pull ---
            if (a instanceof BlackHole) {
                BlackHole bh = (BlackHole) a;
                // Remember the other’s destroyed-state before pulling
                boolean wasDestroyed = b.isDestroyed();
                bh.applyGravitationalPull(b);
                // If it just got destroyed inside applyGravitationalPull, report it:
                if (!wasDestroyed && b.isDestroyed()) {
                    System.out.println(">>> BlackHole at (" 
                        + String.format("%.1f", bh.getX()) + "," 
                        + String.format("%.1f", bh.getY()) 
                        + ") sucked in " + b.getClass().getSimpleName()
                        + " at (" 
                        + String.format("%.1f", b.getX()) + "," 
                        + String.format("%.1f", b.getY()) + ")."
                    );
                }
            }
            if (b instanceof BlackHole) {
                BlackHole bh = (BlackHole) b;
                boolean wasDestroyed = a.isDestroyed();
                bh.applyGravitationalPull(a);
                if (!wasDestroyed && a.isDestroyed()) {
                    System.out.println(">>> BlackHole at (" 
                        + String.format("%.1f", bh.getX()) + "," 
                        + String.format("%.1f", bh.getY()) 
                        + ") sucked in " + a.getClass().getSimpleName()
                        + " at (" 
                        + String.format("%.1f", a.getX()) + "," 
                        + String.format("%.1f", a.getY()) + ")."
                    );
                }
            }
        }
    }

    // 3) Remove destroyed objects
    objects.removeIf(Celestial::isDestroyed);
}
               
        
}


