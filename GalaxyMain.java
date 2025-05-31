import java.util.ArrayList;
import java.util.Scanner;

public class GalaxyMain {
    // Store all celestial objects in a list
    private static ArrayList<Celestial> galaxyObjects = new ArrayList<>();
    
    // Maximum limits for each celestial object type
    private static final int MAX_STARS = 5;
    private static final int MAX_PLANETS = 10;
    private static final int MAX_MOONS = 15;
    private static final int MAX_ASTEROIDS = 20;
    private static final int MAX_COMETS = 8;
    private static final int MAX_BLACK_HOLES = 2;
    
    // Minimum requirements (optional - we can adjust or remove these)
    private static final int MIN_STARS = 1;
    
    // Counters for each object type
    private static int starCount = 0;
    private static int planetCount = 0;
    private static int moonCount = 0;
    private static int asteroidCount = 0;
    private static int cometCount = 0;
    private static int blackHoleCount = 0;
    
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
            
            Celestial newObject = null;
            switch (choice) {
                case 1:
                    newObject = createStar(scanner);
                    break;
                case 2:
                    newObject = createPlanet(scanner);
                    break;
                case 3:
                    newObject = createMoon(scanner);
                    break;
                case 4:
                    newObject = createAsteroid(scanner);
                    break;
                case 5:
                    newObject = createComet(scanner);
                    break;
                case 6:
                    newObject = createBlackHole(scanner);
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
            
            if (newObject != null) {
                galaxyObjects.add(newObject);
                System.out.println(newObject.getName() + " added to the galaxy!\n");
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
        System.out.println("Stars: " + starCount + "/" + MAX_STARS + 
                          " | Planets: " + planetCount + "/" + MAX_PLANETS + 
                          " | Moons: " + moonCount + "/" + MAX_MOONS);
        System.out.println("Asteroids: " + asteroidCount + "/" + MAX_ASTEROIDS + 
                          " | Comets: " + cometCount + "/" + MAX_COMETS + 
                          " | Black Holes: " + blackHoleCount + "/" + MAX_BLACK_HOLES);
        System.out.println();
    }
    
    private static Star createStar(Scanner scanner) {
        if (starCount >= MAX_STARS) {
            System.out.println("❌ Maximum number of stars (" + MAX_STARS + ") reached!");
            return null;
        }
        System.out.print("Enter name of the star: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Star name cannot be empty!");
            return null;
        }
        starCount++;
        return new Star(name);
    }
    
    private static Planet createPlanet(Scanner scanner) {
        if (planetCount >= MAX_PLANETS) {
            System.out.println("❌ Maximum number of planets (" + MAX_PLANETS + ") reached!");
            return null;
        }
        System.out.print("Enter name of the planet: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Planet name cannot be empty!");
            return null;
        }
        planetCount++;
        return new Planet(name);
    }
    
    private static Moon createMoon(Scanner scanner) {
        if (moonCount >= MAX_MOONS) {
            System.out.println("❌ Maximum number of moons (" + MAX_MOONS + ") reached!");
            return null;
        }
        System.out.print("Enter name of the moon: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Moon name cannot be empty!");
            return null;
        }
        moonCount++;
        return new Moon(name);
    }
    
    private static Asteroid createAsteroid(Scanner scanner) {
        if (asteroidCount >= MAX_ASTEROIDS) {
            System.out.println("❌ Maximum number of asteroids (" + MAX_ASTEROIDS + ") reached!");
            return null;
        }
        System.out.print("Enter name of the asteroid: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Asteroid name cannot be empty!");
            return null;
        }
        asteroidCount++;
        return new Asteroid(name);
    }
    
    private static Comet createComet(Scanner scanner) {
        if (cometCount >= MAX_COMETS) {
            System.out.println("❌ Maximum number of comets (" + MAX_COMETS + ") reached!");
            return null;
        }
        System.out.print("Enter name of the comet: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Comet name cannot be empty!");
            return null;
        }
        cometCount++;
        return new Comet(name);
    }
    
    private static BlackHole createBlackHole(Scanner scanner) {
        if (blackHoleCount >= MAX_BLACK_HOLES) {
            System.out.println("❌ Maximum number of black holes (" + MAX_BLACK_HOLES + ") reached!");
            return null;
        }
        System.out.print("Enter name of the black hole: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Black hole name cannot be empty!");
            return null;
        }
        blackHoleCount++;
        return new BlackHole(name);
    }
    
    private static void simulateGalaxy() {
        if (galaxyObjects.isEmpty()) {
            System.out.println("❌ No objects in galaxy yet! Add some celestial objects first.\n");
            return;
        }
        
        System.out.println("\n🔭 Simulating your galaxy...");
        System.out.println("=".repeat(50));
        
        for (Celestial obj : galaxyObjects) {
            obj.updatePosition();  // This assumes you have this method in Celestial
            System.out.println("📍 " + obj);
        }
        
        System.out.println("=".repeat(50));
        System.out.println("✨ Simulation complete!\n");
    }
    
    private static void launchGalaxyGUI() {
        if (galaxyObjects.isEmpty()) {
            System.out.println("❌ No objects in galaxy yet! Add some celestial objects first.\n");
            return;
        }
        
        if (starCount < MIN_STARS) {
            System.out.println("❌ You need at least " + MIN_STARS + " star(s) to launch the GUI!\n");
            return;
        }
        
        System.out.println("🚀 Launching Galaxy GUI...");
        
        // Instantiate the model with the collected celestial objects
        GalaxySimulationGrid model = new GalaxySimulationGrid(galaxyObjects);
        
        // Launch the GUI and pass it the model
        GalaxyGUI gui = new GalaxyGUI(model);
        gui.launch(); // or gui.setVisible(true); depending on your GUI implementation
        
        System.out.println("✨ Galaxy GUI launched successfully!");
        System.out.println("You can continue adding objects or exit the program.\n");
    }
}