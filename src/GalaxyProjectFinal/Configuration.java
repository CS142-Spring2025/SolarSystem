package GalaxyProjectFinal;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
          .append(p.getX()).append(",").append(p.getY()).append(",")
          .append(p.getRadius()).append(",").append(p.getSpeed()).append("\n");
    }
    for (Moon m : moons) {
        sb.append("Moon,")
          .append(m.getX()).append(",").append(m.getY()).append(",")
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

public static Configuration loadFromFile(String filename) throws IOException {
    List<Star> stars = new ArrayList<>();
    List<Planet> planets = new ArrayList<>();
    List<Moon> moons = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();
    List<Comet> comets = new ArrayList<>();
    List<BlackHole> blackHoles = new ArrayList<>();

    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        switch (parts[0]) {
            case "Star":
                stars.add(new Star(
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2])));
                break;

            case "Planet":
                Planet p = new Planet(
                    new Star(Double.parseDouble(parts[1]), Double.parseDouble(parts[2])),  // dummy center
                    Double.parseDouble(parts[3]),
                    Double.parseDouble(parts[4]));
                planets.add(p);
                break;

            case "Moon":
                Moon m = new Moon(
                    new Planet(new Star(Double.parseDouble(parts[1]), Double.parseDouble(parts[2])), 0, 0), // dummy center
                    Double.parseDouble(parts[3]),
                    Double.parseDouble(parts[4]));
                moons.add(m);
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
                Comet c = new Comet(cx, cy, dx, dy);
                comets.add(c);
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
        Map<Star, Star> starMap = new HashMap<>();
        for (Star s : stars) {
            Star clone = new Star(s.getX(), s.getY());
            clonedStars.add(clone);
            starMap.put(s, clone);
    }

        List<Planet> clonedPlanets = new ArrayList<>();
            Map<Planet, Planet> planetMap = new HashMap<>();
        for (Planet p : planets) {
            Star originalCenter = (Star) p.getCenter();  // assuming getCenter() returns the Star
            Star newCenter = starMap.get(originalCenter);
            Planet clone = new Planet(newCenter, p.getRadius(), p.getSpeed());
            clonedPlanets.add(clone);
            planetMap.put(p, clone);
        }

        List<Moon> clonedMoons = new ArrayList<>();
        for (Moon m : moons) {
        Planet originalCenter = (Planet) m.getCenter();  // assuming getCenter() returns the Planet
        Planet newCenter = planetMap.get(originalCenter);
        clonedMoons.add(new Moon(newCenter, m.getRadius(), m.getSpeed()));
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