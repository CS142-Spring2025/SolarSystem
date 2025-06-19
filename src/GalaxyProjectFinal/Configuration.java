package GalaxyProjectFinal;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



 /**
 * Configuration class to save and manage placement of objects and saving to a file
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

public class Configuration {
    private List<Star> stars;
    private List<Planet> planets;
    private List<Moon> moons;
    private List<Asteroid> asteroids;
    private List<Comet> comets;
    private List<BlackHole> blackHoles;

    public Configuration(List<Star> stars, List<Planet> planets, List<Moon> moons,
                         List<Asteroid> asteroids, List<Comet> comets, List<BlackHole> blackHoles) {
        this.stars = stars;
        this.planets = planets;
        this.moons = moons;
        this.asteroids = asteroids;
        this.comets = comets;
        this.blackHoles = blackHoles;
    }

    public List<Star> getStars() {
        return stars;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public List<Moon> getMoons() {
        return moons;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }   

    public List<Comet> getComets() {
        return comets;
    }

    public List<BlackHole> getBlackHoles() {
        return blackHoles;
    }

    public String serialize() {
    StringBuilder sb = new StringBuilder();

    for (Star s : stars) {
        sb.append("Star,").append(s.getX()).append(",").append(s.getY()).append("\n");
    }
    for (Planet p : planets) {
        sb.append("Planet,")
          .append(p.getXCenter()).append(",").append(p.getYCenter()).append(",")
          .append(p.getRadius()).append(",").append(p.getSpeed()).append("\n");
    }
    for (Moon m : moons) {
        sb.append("Moon,")
          .append(m.getXCenter()).append(",").append(m.getYCenter()).append(",")
          .append(m.getRadius()).append(",").append(m.getSpeed()).append("\n");
    }
    for (Asteroid a : asteroids) {
        sb.append("Asteroid,").append(a.getX()).append(",").append(a.getY()).append("\n");
    }
    for (Comet c : comets) {
        sb.append("Comet,").append(c.getX()).append(",").append(c.getY())
          .append(",").append(c.getX()).append(",").append(c.getY()).append("\n");
    }
    for (BlackHole b : blackHoles) {
        sb.append("BlackHole,").append(b.getX()).append(",").append(b.getY()).append("\n");
    }

    return sb.toString();
    }

    private static Star findStarByPosition(List<Star> stars, double x, double y) {
        for (Star star : stars) {
            if (Math.abs(star.getX() - x) < 0.001 && Math.abs(star.getY() - y) < 0.001) {
                return star;
            }
        }
        return null;
    }

    private static Planet findPlanetByPosition(List<Planet> planets, double x, double y) {
    for (Planet p : planets) {
        if (Math.abs(p.getX() - x) < 0.001 && Math.abs(p.getY() - y) < 0.001) {
            return p;
        }
    }
    return null;
}

public static Configuration loadFromFile(String filename) throws IOException {
    List<Star> stars = new ArrayList<>();
    List<Planet> planets = new ArrayList<>();
    List<Moon> moons = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();
    List<Comet> comets = new ArrayList<>();
    List<BlackHole> blackHoles = new ArrayList<>();

    Map<String, Star> starMap = new HashMap<>();
    Map<String, Planet> planetMap = new HashMap<>();

    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        switch (parts[0]) {
            case "Star":
                double sx = Double.parseDouble(parts[1]);
                double sy = Double.parseDouble(parts[2]);
                Star star = new Star(sx, sy);
                stars.add(star);
                starMap.put(sx + "," + sy, star);
                break;

            case "Planet":
                double px = Double.parseDouble(parts[1]);
                double py = Double.parseDouble(parts[2]);
                double radius = Double.parseDouble(parts[3]);
                double speed = Double.parseDouble(parts[4]);

               Star centerStar = findStarByPosition(stars, px, py);
                if (centerStar == null) {
                    System.err.println("⚠️ No matching star found for planet center at (" + px + ", " + py + ")");
                    centerStar = new Star(px, py);
                }

                Planet planet = new Planet(centerStar, radius, speed);
                planets.add(planet);
                planetMap.put(px + "," + py, planet); // assume center also holds planet
                break;

            case "Moon":
                double mx = Double.parseDouble(parts[1]);
                double my = Double.parseDouble(parts[2]);
                double mRadius = Double.parseDouble(parts[3]);
                double mSpeed = Double.parseDouble(parts[4]);

                Planet centerPlanet = findPlanetByPosition(planets, mx, my);
                if (centerPlanet == null) {
                    System.err.println("⚠️ No matching planet found for moon center at (" + mx + ", " + my + ")");
                    centerPlanet = new Planet(new Star(mx, my), 0, 0);
                }

                Moon moon = new Moon(centerPlanet, mRadius, mSpeed);
                moons.add(moon);
                break;

            case "Asteroid":
                asteroids.add(new Asteroid(
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2])));
                break;

            case "Comet":
                double cx = Double.parseDouble(parts[1]);
                double cy = Double.parseDouble(parts[2]);
                double dx = Double.parseDouble(parts[3]);
                double dy = Double.parseDouble(parts[4]);
                comets.add(new Comet(cx, cy, dx, dy));
                break;

            case "BlackHole":
                blackHoles.add(new BlackHole(
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2])));
                break;
        }
    }
    reader.close();
    return new Configuration(stars, planets, moons, asteroids, comets, blackHoles);
    }


        
    public Configuration cloneInitial() {
        List<Star> clonedStars = new ArrayList<>();
        for (Star s : stars) {
            Star clone = new Star(s.getX(), s.getY());
            clonedStars.add(clone);
            
    }

        List<Planet> clonedPlanets = new ArrayList<>();
        for (Planet p : planets) {
            Star originalCenter = (Star) p.getCenter();
            Star newCenter = findStarByPosition(clonedStars, originalCenter.getX(), originalCenter.getY());
            if (newCenter == null) {
                System.err.println("⚠️ Couldn't find cloned star for planet at center (" + originalCenter.getX() + ", " + originalCenter.getY() + ")");
                newCenter = new Star(originalCenter.getX(), originalCenter.getY());
            }
        

            for (Star cs : clonedStars) {
                if (Math.abs(cs.getX() - originalCenter.getX()) < 0.001 &&
                    Math.abs(cs.getY() - originalCenter.getY()) < 0.001) {
                    newCenter = cs;
                    break;
                }
            }  

            Planet clone = new Planet(newCenter, p.getRadius(), p.getSpeed());
            clonedPlanets.add(clone);
    }

        List<Moon> clonedMoons = new ArrayList<>();
        for (Moon m : moons) {
            Planet originalCenter = (Planet) m.getCenter();
            Planet newCenter = findPlanetByPosition(clonedPlanets, originalCenter.getX(), originalCenter.getY());
            if (newCenter == null) {
                System.err.println("⚠️ Couldn't find cloned planet for moon at center (" + originalCenter.getX() + ", " + originalCenter.getY() + ")");
                newCenter = new Planet(new Star(originalCenter.getX(), originalCenter.getY()), 0, 0);
            }

            for (Planet cp : clonedPlanets) {
                if (Math.abs(cp.getX() - originalCenter.getX()) < 0.001 &&
                    Math.abs(cp.getY() - originalCenter.getY()) < 0.001) {
                    newCenter = cp;
                    break;
                }
            }
            if (newCenter == null) {
                System.err.println("⚠️ Couldn't find cloned planet for moon at center (" +
                                    originalCenter.getX() + ", " + originalCenter.getY() + ")");
                continue;
            }

            Moon clone = new Moon(newCenter, m.getRadius(), m.getSpeed());
            clonedMoons.add(clone);

        }

        List<Asteroid> clonedAsteroids = new ArrayList<>();
        for (Asteroid a : asteroids) {
            clonedAsteroids.add(new Asteroid(a.getX(), a.getY()));
        }

        List<Comet> clonedComets = new ArrayList<>();
        for (Comet c : comets) {
            clonedComets.add(new Comet(c.getX(), c.getY(), c.getDx(), c.getDy()));
        }

        List<BlackHole> clonedBlackHoles = new ArrayList<>();
        for (BlackHole bh : blackHoles) {
            clonedBlackHoles.add(new BlackHole(bh.getX(), bh.getY()));
        }

        return new Configuration(clonedStars, clonedPlanets, clonedMoons, clonedAsteroids, clonedComets, clonedBlackHoles);
}

}