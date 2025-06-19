package GalaxyProjectFinal;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulation class that tracks celestial objects, collisions and explosions
 *
 * <p><b>Project:</b> GalaxyProjectFinal</p>
 * <p><b>Date:</b> 6/18/2025</p>
 *
 * @author Kaitlyn Le
 * @author Ruth Karen Nakigozi
 * @author Emma Dennis
 * @see java.awt.Color
 * @see java.awt.Graphics
 * @see java.awt.Graphics2D
 * @see java.util.Random
 */

public class GalaxySimulation {
    private List<Celestial> objects;           // list that holds all celestial objects
    private List<Explosion>  explosions = new ArrayList<>();  // active explosion

    public GalaxySimulation() {
        objects = new ArrayList<>();
    }

    public void addObject(Celestial obj) {
        objects.add(obj);
    }

    public List<Celestial> getObjects() {
        return objects;
    }

    /** Expose the current explosions so the GUI can draw them */
    public List<Explosion> getExplosions() {
        return explosions;
    }

    
       

    public void update() {
        // 1) Move each object according to its logic
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
                if (a instanceof Comet || b instanceof Comet) {
    Comet comet = (a instanceof Comet) ? (Comet) a : (Comet) b;
    Celestial other = (a instanceof Comet) ? b : a;

    if (!comet.isDestroyed() && !other.isDestroyed() && comet.collidesWith(other)) {
        comet.setDestroyed(true);
        other.setDestroyed(true);
        explosions.add(new Explosion(comet.getX(), comet.getY()));
        System.out.println(">>> Comet-Collision: destroying " + comet.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        continue;
    }
}

/*older comet logic 
if (a instanceof Comet) {
                    Comet comA = (Comet) a;
                    if (comA.collidesWith(b)) {
                        System.out.println(">>> Comet-Collision: destroying both "
                            + comA.getClass().getSimpleName()
                            + " and " + b.getClass().getSimpleName());
                        // spawn explosion at comet’s position
                        comA.setDestroyed(true);
                        b.setDestroyed(true);
                        explosions.add(new Explosion(comA.getX(), comA.getY()));
                        continue;
                    }
                }
                if (b instanceof Comet) {
                    Comet comB = (Comet) b;
                    if (comB.collidesWith(a)) {
                        System.out.println(">>> Comet-Collision: destroying both "
                            + a.getClass().getSimpleName()
                            + " and " + comB.getClass().getSimpleName());
                        // spawn explosion at comet’s position
                        a.setDestroyed(true);
                        comB.setDestroyed(true);
                        explosions.add(new Explosion(comB.getX(), comB.getY()));
                        continue;
                    }
                }
*/
                // --- ASTEROID vs. ASTEROID: destroy both if they overlap ---
                if (a instanceof Asteroid && b instanceof Asteroid) {
                    Asteroid astA = (Asteroid) a;
                    Asteroid astB = (Asteroid) b;
                    if (astA.collidesWith(astB)) {
                        System.out.println(">>> Asteroid-Asteroid collision: destroying both.");
                        // spawn explosion at astA’s position (or average of both)
                        astA.setDestroyed(true);
                        astB.setDestroyed(true);
                        explosions.add(new Explosion(astA.getX(), astA.getY()));
                        explosions.add(new Explosion(astB.getX(), astB.getY()));
                        continue;
                    }
                }

                // --- ASTEROID vs. PLANET or PLANET vs. ASTEROID ---
                if (a instanceof Asteroid && b instanceof Planet) {
                    Asteroid ast = (Asteroid) a;
                    Planet pl  = (Planet)  b;
                    if (ast.collidesWith(pl)) {
                        System.out.print(">>> Asteroid-Planet collision: destroying Asteroid. ");
                        // always destroy the asteroid
                        ast.setDestroyed(true);
                        explosions.add(new Explosion(ast.getX(), ast.getY()));

                        // 50/50 chance planet also destroyed
                        if (Math.random() < 0.5) {
                            pl.setDestroyed(true);
                            explosions.add(new Explosion(pl.getX(), pl.getY()));
                            System.out.println("Planet was destroyed too.");
                        } else {
                            System.out.println("Planet survived.");
                        }
                        continue;
                    }
                }
                if (a instanceof Planet && b instanceof Asteroid) {
                    Planet pl  = (Planet)  a;
                    Asteroid ast = (Asteroid) b;
                    if (ast.collidesWith(pl)) {
                        System.out.println(">>> Planet-Asteroid collision: destroying Asteroid; Planet survives.");
                        ast.setDestroyed(true);
                        explosions.add(new Explosion(ast.getX(), ast.getY()));
                        continue;
                    }
                }

                // --- ASTEROID vs. MOON or MOON vs. ASTEROID ---
                if (a instanceof Asteroid && b instanceof Moon) {
                    Asteroid ast = (Asteroid) a;
                    Moon     mo  = (Moon)     b;
                    if (ast.collidesWith(mo)) {
                        // always destroy the asteroid
                        ast.setDestroyed(true);
                        explosions.add(new Explosion(ast.getX(), ast.getY()));

                        // 50/50 chance moon destroyed
                        if (Math.random() < 0.5) {
                            mo.setDestroyed(true);
                            explosions.add(new Explosion(mo.getX(), mo.getY()));
                            System.out.println(">>> Asteroid-Moon collision: destroying Asteroid; Moon was destroyed.");
                        } else {
                            System.out.println(">>> Asteroid-Moon collision: destroying Asteroid; Moon survived.");
                        }
                        continue;
                    }
                }
                if (a instanceof Moon && b instanceof Asteroid) {
                    Moon     mo  = (Moon)     a;
                    Asteroid ast = (Asteroid) b;
                    if (ast.collidesWith(mo)) {
                        // moon hits asteroid → destroy asteroid, moon survives
                        ast.setDestroyed(true);
                        explosions.add(new Explosion(ast.getX(), ast.getY()));
                        System.out.println(">>> Moon-Asteroid collision: destroying Asteroid; Moon survives.");
                        continue;
                    }
                }
                
              
                
                // --- ASTEROID vs. STAR or STAR vs. ASTEROID ---
                if (a instanceof Asteroid && b instanceof Star) {
                    Asteroid ast = (Asteroid) a;
                    Star     st  = (Star)     b;
                    if (ast.collidesWith(st)) {
                        ast.setDestroyed(true);
                        explosions.add(new Explosion(ast.getX(), ast.getY()));
                        System.out.println(">>> Asteroid-Star collision: destroying Asteroid.");
                        continue;
                    }
                }
                if (a instanceof Star && b instanceof Asteroid) {
                    Star     st  = (Star)     a;
                    Asteroid ast = (Asteroid) b;
                    if (ast.collidesWith(st)) {
                        ast.setDestroyed(true);
                        explosions.add(new Explosion(ast.getX(), ast.getY()));
                        System.out.println(">>> Star-Asteroid collision: destroying Asteroid.");
                        continue;
                    }
                }

                // (Planet-Planet collisions skipped per rules)

                // --- BLACK HOLE gravitational pull ---
                if (a instanceof BlackHole) {
                    BlackHole bh = (BlackHole) a;
                    boolean wasDestroyed = b.isDestroyed();
                    bh.applyGravitationalPull(b);
                    if (!wasDestroyed && b.isDestroyed()) {
                        // spawn an explosion at b’s last position
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

        // 4) Advance all active explosions and remove any that have finished
        for (int i = 0; i < explosions.size(); i++) {
            if (!explosions.get(i).update()) {
                explosions.remove(i--);
            }
        }
    }
}
