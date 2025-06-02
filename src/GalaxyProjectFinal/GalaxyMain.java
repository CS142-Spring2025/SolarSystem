package GalaxyProjectFinal;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class GalaxyMain {
    // Maximum limits for each celestial object type
    private static final int MAX_STARS = 5;
    private static final int MAX_PLANETS = 10;
    private static final int MAX_MOONS = 15;
    private static final int MAX_ASTEROIDS = 20;
    private static final int MAX_COMETS = 8;
    private static final int MAX_BLACK_HOLES = 2;
    
    // Minimum requirements
    private static final int MIN_STARS = 1;
    
    // Lists to store actual objects instead of just counters
    private static List<Star> stars = new ArrayList<>();
    private static List<Planet> planets = new ArrayList<>();
    private static List<Moon> moons = new ArrayList<>();
    private static List<Asteroid> asteroids = new ArrayList<>();
    private static List<Comet> comets = new ArrayList<>();
    private static List<BlackHole> blackHoles = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🌌 Welcome to the Galaxy Simulation 🌌");
        System.out.println("You can create your own galaxy by choosing celestial objects.");
        System.out.println("Note: You need at least " + MIN_STARS + " star(s) to create a galaxy.\n");
        
        boolean running = true;
        while (running) {
            displayMenu();
            displayCurrentCounts();
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    addStar(scanner);
                    break;
                case 2:
                    addPlanet(scanner);
                    break;
                case 3:
                    addMoon(scanner);
                    break;
                case 4:
                    addAsteroid(scanner);
                    break;
                case 5:
                    addComet(scanner);
                    break;
                case 6:
                    addBlackHole(scanner);
                    break;
                case 7:
                    simulateGalaxy();
                    break;
                case 8:
                    launchGalaxyGUI();
                    break;
                case 9:
                    running = false;
                    System.out.println("Exiting simulation. Goodbye!");
                    continue;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("Choose an object to add to your galaxy:");
        System.out.println("1. Star (Max: " + MAX_STARS + ")");
        System.out.println("2. Planet (Max: " + MAX_PLANETS + ")");
        System.out.println("3. Moon (Max: " + MAX_MOONS + ")");
        System.out.println("4. Asteroid (Max: " + MAX_ASTEROIDS + ")");
        System.out.println("5. Comet (Max: " + MAX_COMETS + ")");
        System.out.println("6. Black Hole (Max: " + MAX_BLACK_HOLES + ")");
        System.out.println("7. Simulate Current Galaxy");
        System.out.println("8. Launch Galaxy GUI");
        System.out.println("9. Exit");
    }
    
    private static void displayCurrentCounts() {
        System.out.println("\nCurrent Galaxy Contents:");
        System.out.println("Stars: " + stars.size() + "/" + MAX_STARS + 
                          " | Planets: " + planets.size() + "/" + MAX_PLANETS + 
                          " | Moons: " + moons.size() + "/" + MAX_MOONS);
        System.out.println("Asteroids: " + asteroids.size() + "/" + MAX_ASTEROIDS + 
                          " | Comets: " + comets.size() + "/" + MAX_COMETS + 
                          " | Black Holes: " + blackHoles.size() + "/" + MAX_BLACK_HOLES);
        System.out.println();
    }
    
    private static void addStar(Scanner scanner) {
        if (stars.size() >= MAX_STARS) {
            System.out.println("❌ Maximum number of stars (" + MAX_STARS + ") reached!");
            return;
        }
        System.out.print("Enter name of the star: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Star name cannot be empty!");
            return;
        }
        
        // Create actual Star object
        Star star = new Star();
        stars.add(star);
        System.out.println("⭐ " + name + " added to the galaxy!\n");
    }
    
    private static void addPlanet(Scanner scanner) {
        if (planets.size() >= MAX_PLANETS) {
            System.out.println("❌ Maximum number of planets (" + MAX_PLANETS + ") reached!");
            return;
        }
        System.out.print("Enter name of the planet: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Planet name cannot be empty!");
            return;
        }
        
        // Create actual Planet object
        Planet planet = new Planet();
        planets.add(planet);
        System.out.println("🪐 " + name + " added to the galaxy!\n");
    }
    
    private static void addMoon(Scanner scanner) {
        if (moons.size() >= MAX_MOONS) {
            System.out.println("❌ Maximum number of moons (" + MAX_MOONS + ") reached!");
            return;
        }
        System.out.print("Enter name of the moon: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Moon name cannot be empty!");
            return;
        }
        
        // Create actual Moon object
        Moon moon = new Moon();
        moons.add(moon);
        System.out.println("🌙 " + name + " added to the galaxy!\n");
    }
    
    private static void addAsteroid(Scanner scanner) {
        if (asteroids.size() >= MAX_ASTEROIDS) {
            System.out.println("❌ Maximum number of asteroids (" + MAX_ASTEROIDS + ") reached!");
            return;
        }
        System.out.print("Enter name of the asteroid: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Asteroid name cannot be empty!");
            return;
        }
        
        // Create actual Asteroid object
        Asteroid asteroid = new Asteroid(name);
        asteroids.add(asteroid);
        System.out.println("☄️ " + name + " added to the galaxy!\n");
    }
    
    private static void addComet(Scanner scanner) {
        if (comets.size() >= MAX_COMETS) {
            System.out.println("❌ Maximum number of comets (" + MAX_COMETS + ") reached!");
            return;
        }
        System.out.print("Enter name of the comet: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Comet name cannot be empty!");
            return;
        }
        
        // Create actual Comet object
        Comet comet = new Comet(name);
        comets.add(comet);
        System.out.println("☄️ " + name + " added to the galaxy!\n");
    }
    
    private static void addBlackHole(Scanner scanner) {
        if (blackHoles.size() >= MAX_BLACK_HOLES) {
            System.out.println("❌ Maximum number of black holes (" + MAX_BLACK_HOLES + ") reached!");
            return;
        }
        System.out.print("Enter name of the black hole: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Black hole name cannot be empty!");
            return;
        }
        
        // Create actual BlackHole object
        BlackHole blackHole = new BlackHole();
        blackHoles.add(blackHole);
        System.out.println("⚫ " + name + " added to the galaxy!\n");
    }
    
    private static void simulateGalaxy() {
        int totalObjects = stars.size() + planets.size() + moons.size() + 
                          asteroids.size() + comets.size() + blackHoles.size();
        if (totalObjects == 0) {
            System.out.println("❌ No objects in galaxy yet! Add some celestial objects first.\n");
            return;
        }
        
        System.out.println("\n🔭 Simulating your galaxy...");
        System.out.println("=".repeat(50));
        
        // Create the simulation grid
        GalaxySimulation model = new GalaxySimulation();
        
        // Add all the created objects to the model
        for (Star star : stars) {
            model.addObject(star);
        }
        for (Planet planet : planets) {
            model.addObject(planet);
        }
        for (Moon moon : moons) {
            model.addObject(moon);
        }
        for (Asteroid asteroid : asteroids) {
            model.addObject(asteroid);
        }
        for (Comet comet : comets) {
            model.addObject(comet);
        }
        for (BlackHole blackHole : blackHoles) {
            model.addObject(blackHole);
        }
        
        // Run the simulation
        model.runSimulation();
        
        System.out.println("=".repeat(50));
        System.out.println("✨ Simulation complete!\n");
    }
    
    private static void launchGalaxyGUI() {
        int totalObjects = stars.size() + planets.size() + moons.size() + 
                          asteroids.size() + comets.size() + blackHoles.size();
        if (totalObjects == 0) {
            System.out.println("❌ No objects in galaxy yet! Add some celestial objects first.\n");
            return;
        }
        
        if (stars.size() < MIN_STARS) {
            System.out.println("❌ You need at least " + MIN_STARS + " star(s) to launch the GUI!\n");
            return;
        }
        
        System.out.println("🚀 Launching Galaxy GUI...");
        
        // Create the simulation grid
        GalaxySimulation model = new GalaxySimulation();
        
        // Add all the created objects to the model
        for (Star star : stars) {
            model.addObject(star);
        }
        for (Planet planet : planets) {
            model.addObject(planet);
        }
        for (Moon moon : moons) {
            model.addObject(moon);
        }
        for (Asteroid asteroid : asteroids) {
            model.addObject(asteroid);
        }
        for (Comet comet : comets) {
            model.addObject(comet);
        }
        for (BlackHole blackHole : blackHoles) {
            model.addObject(blackHole);
        }
        
        // Launch the GUI and pass it the model
        GalaxyGUI gui = new GalaxyGUI(model);
        gui.launch();
        
        System.out.println("✨ Galaxy GUI launched successfully!");
        System.out.println("You can continue adding objects or exit the program.\n");
    }
}   