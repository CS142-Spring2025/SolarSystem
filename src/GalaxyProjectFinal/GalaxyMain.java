package GalaxyProjectFinal;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.*;

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
        System.out.println("                  ****Welcome to the Galaxy Simulation!****");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("This is a program where you can create your own galaxy by choosing celestial objects!");
        System.out.println("Note: You need at least " + MIN_STARS + " star(s) to create a galaxy.\n");
        System.out.println("-------------------------------------------------------------------------------------");
        
        boolean running = true;
        while (running) {
            displayMenu();
            displayCurrentCounts();
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    addStarWithFlow(scanner);
                    break;
                case 2:
                    addSpaceObjectsMenu(scanner);
                    break;
                case 3:
                    launchGalaxyGUI();
                    break;
                case 4:
                    startNewGalaxy(scanner);
                    break;
                case 5:
                    addComet(scanner);
                    break;
                case 6:
                    addBlackHolesWithFlow(scanner);
                    break;
                case 7:
                    launchGalaxyGUI();
                    break;
                case 8:
                    running = false;
                    System.out.println("Exiting simulation. Goodbye!");
                    continue;
                default:
                    System.err.println("Invalid choice. Please enter 1-5.");
            }
        }
        scanner.close();
    }
    
    private static void addComet(Scanner scanner) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addComet'");
    }

    private static void displayMenu() {
        System.out.println("\n=== GALAXY BUILDER ===");
        System.out.println("1. Add Star System  (creates star + planets + moons)");
        System.out.println("2. Add Space Objects  (asteroids, comets, black holes)");
        System.out.println("3. Launch Simulation");
        System.out.println("4. Start Over");
        System.out.println("5. Exit");
        System.out.println();
    }
    
    private static void displayCurrentCounts() {
        System.out.println("Your Galaxy: " + stars.size() + " stars, " + planets.size() + " planets, " + 
                          moons.size() + " moons, " + asteroids.size() + " asteroids, " + 
                          comets.size() + " comets, " + blackHoles.size() + " black holes");
    }
    
    private static void addStarWithFlow(Scanner scanner) {
        if (stars.size() >= MAX_STARS) {
            System.err.println("❌ Maximum number of stars (" + MAX_STARS + ") reached!");
            return;
        }
           
        // Create actual Star object
        double x = 50 + Math.random() * 700;
        double y = 50 + Math.random() * 500;
        Star star = new Star(x, y);
        stars.add(star);
        System.out.println("\nSTAR: Great! You added a star to the galaxy!");
        
        // Ask if they want to add planets
        if (planets.size() < MAX_PLANETS) {
            System.out.print("Would you like to add a planet to orbit this star? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("y") || response.equals("yes")) {
                addPlanetsWithFlow(scanner);
            }
        } else {
            System.err.println("(Maximum planets already reached)");
        }
    }
    
    private static void addPlanetsWithFlow(Scanner scanner) {
        System.out.print("How many planets would you like to add? (Max remaining: " + 
                        (MAX_PLANETS - planets.size()) + "): ");
        int numPlanets = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        int maxToAdd = Math.min(numPlanets, MAX_PLANETS - planets.size());
        
        for (int i = 0; i < maxToAdd; i++) {
            // Create actual Planet object - it needs something to orbit around
            Star centerStar = stars.get(0); // for now, orbit the first star
            Planet planet = new Planet(centerStar, 45 + Math.random() * 45, 0.02 + Math.random() * 0.03);
            planets.add(planet);
        }
        
        System.out.println("PLANET: Great! You added " + maxToAdd + " planet(s) to the galaxy!");
        
        // Ask if they want to add moons
        if (moons.size() < MAX_MOONS && !planets.isEmpty()) {
            System.out.print("Would you like to add moons to orbit your planets? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("y") || response.equals("yes")) {
                addMoonsWithFlow(scanner);
            }
        } else if (moons.size() >= MAX_MOONS) {
            System.out.println("(Maximum moons already reached)");
        }
    }
    
    private static void addMoonsWithFlow(Scanner scanner) {
        System.out.print("How many moons would you like to add? (Max remaining: " + 
                        (MAX_MOONS - moons.size()) + "): ");
        int numMoons = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        int maxToAdd = Math.min(numMoons, MAX_MOONS - moons.size());
        
        for (int i = 0; i < maxToAdd; i++) {
            // Create actual Moon object - needs a planet to orbit around
            Planet parentPlanet = planets.get(i % planets.size()); // Distribute moons among planets
            Moon moon = new Moon(parentPlanet, 30 + Math.random() * 20, 0.05 + Math.random() * 0.03);
            moons.add(moon);
        }
        
        System.out.println("MOON: Great! You added " + maxToAdd + " moon(s) to the galaxy!");
    }
    
    private static void addAsteroidsWithFlow(Scanner scanner) {
        if (asteroids.size() >= MAX_ASTEROIDS) {
            System.err.println("❌ Maximum number of asteroids (" + MAX_ASTEROIDS + ") reached!");
            return;
        }
        
        System.out.print("How many asteroids would you like to add? (Max remaining: " + 
                        (MAX_ASTEROIDS - asteroids.size()) + "): ");
        int numAsteroids = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        int maxToAdd = Math.min(numAsteroids, MAX_ASTEROIDS - asteroids.size());
        
        for (int i = 0; i < maxToAdd; i++) {
            // Create actual Asteroid object
            Asteroid asteroid = new Asteroid(Math.random() * 800, Math.random() * 600);
            asteroids.add(asteroid);
        }
        
        System.out.println("ASTEROID: Great! You added " + maxToAdd + " asteroid(s) to the galaxy!");
    }
    
    private static void addCometsWithFlow(Scanner scanner) {
        if (comets.size() >= MAX_COMETS) {
            System.err.println("❌ Maximum number of comets (" + MAX_COMETS + ") reached!");
            return;
        }
        
        System.out.print("How many comets would you like to add? (Max remaining: " + 
                        (MAX_COMETS - comets.size()) + "): ");
        int numComets = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        int maxToAdd = Math.min(numComets, MAX_COMETS - comets.size());
        
        for (int i = 0; i < maxToAdd; i++) {
            // Create actual Comet object
            Comet comet = new Comet();
            comets.add(comet);
        }
        
        System.out.println("COMET: Great! You added " + maxToAdd + " comet(s) to the galaxy!");
    }
    
    private static void addBlackHolesWithFlow(Scanner scanner) {
        if (blackHoles.size() >= MAX_BLACK_HOLES) {
            System.err.println("❌ Maximum number of black holes (" + MAX_BLACK_HOLES + ") reached!");
            return;
        }
        
        System.out.print("How many black holes would you like to add? (Max remaining: " + 
                        (MAX_BLACK_HOLES - blackHoles.size()) + "): ");
        int numBlackHoles = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        int maxToAdd = Math.min(numBlackHoles, MAX_BLACK_HOLES - blackHoles.size());
        
        for (int i = 0; i < maxToAdd; i++) {
            // Create actual BlackHole object at a random position
            double x = Math.random() * 800;
            double y = Math.random() * 600;
            BlackHole blackHole = new BlackHole(x, y);
            blackHoles.add(blackHole);
        }
        
        System.out.println("BLACK HOLE: Great! You added " + maxToAdd + " black hole(s) to the galaxy!");
    }
    
    private static void addSpaceObjectsMenu(Scanner scanner) {
        System.out.println("\n--- SPACE OBJECTS MENU ---");
        System.out.println("1. Asteroids (" + asteroids.size() + "/" + MAX_ASTEROIDS + ")");
        System.out.println("2. Comets (" + comets.size() + "/" + MAX_COMETS + ")");
        System.out.println("3. Black Holes (" + blackHoles.size() + "/" + MAX_BLACK_HOLES + ")");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                addAsteroidsWithFlow(scanner);
                break;
            case 2:
                addCometsWithFlow(scanner);
                break;
            case 3:
                addBlackHolesWithFlow(scanner);
                break;
            case 4:
                return;
            default:
                System.err.println("Invalid choice. Please enter 1-4.");
        }
    }
    
    private static void startNewGalaxy(Scanner scanner) {
        if (!stars.isEmpty() || !planets.isEmpty() || !moons.isEmpty() || 
            !asteroids.isEmpty() || !comets.isEmpty() || !blackHoles.isEmpty()) {
            
            System.out.print("Are you sure you want to start over? This will clear your current galaxy (y/n): ");
            String response = scanner.nextLine().toLowerCase();
            
            if (response.equals("y") || response.equals("yes")) {
                clearGalaxy();
                System.out.println("GALAXY: Starting fresh! Your galaxy has been cleared.");
            } else {
                System.out.println("Cancelled. Your current galaxy is preserved.");
            }
        } else {
            System.err.println("Your galaxy is already empty. Start adding celestial objects!");
        }
    }
    
    private static void clearGalaxy() {
        stars.clear();
        planets.clear();
        moons.clear();
        asteroids.clear();
        comets.clear();
        blackHoles.clear();
    }
                  
    private static void launchGalaxyGUI() {
        int totalObjects = stars.size() + planets.size() + moons.size() + 
                          asteroids.size() + comets.size() + blackHoles.size();
        if (totalObjects == 0) {
            System.err.println("❌ No objects in galaxy yet! Add some celestial objects first.\n");
            return;
        }
        
        if (stars.size() < MIN_STARS) {
            System.err.println("❌ You need at least " + MIN_STARS + " star(s) to launch the GUI!\n");
            return;
        }
        
        System.out.println("ROCKET: Launching Galaxy GUI...");
        
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
        
        //create the GalaxyGUI panel with the model (for control buttons)
        GalaxyGUI galaxyPanel = new GalaxyGUI(model);

        // Launch the GUI and pass it the model
        JFrame frame = new JFrame("Galaxy Simulation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.add(new GalaxyGUI(model));
        
        JLabel speedLabel = new JLabel("Click Speed Up/Slow Down multiple times.  Delay: " 
        + galaxyPanel.getAnimationSpeed() + " ms");
        
        // Create control buttons
        JPanel controls = new JPanel();
        JButton pauseButton = new JButton("Pause/Resume");
        JButton speedUpButton = new JButton("Speed Up");
        JButton slowDownButton = new JButton("Slow Down");
        JButton returnButton = new JButton("Return to Main");

        controls.add(speedLabel);
        controls.add(pauseButton);
        controls.add(speedUpButton);
        controls.add(slowDownButton);
        controls.add(returnButton);

        // Layout: add simulation panel + controls
        frame.setLayout(new BorderLayout());
        frame.add(galaxyPanel, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);

        // Add button actions
        pauseButton.addActionListener(e -> galaxyPanel.togglePause());
        speedUpButton.addActionListener(e -> galaxyPanel.speedUp());
        slowDownButton.addActionListener(e -> galaxyPanel.slowDown());
        returnButton.addActionListener(e -> {
            frame.dispose();
            System.out.println("\nReturning to main program...");
            // Optionally trigger something in GalaxyMain here
        });

        // Handle "X" close action
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("\nGalaxy GUI closed. Returning to main program...");
                // You can call back into GalaxyMain here if needed
            }
        });

        frame.pack();
        frame.setVisible(true);

        System.out.println("SPARKLES: Galaxy GUI launched successfully!");
        System.out.println("You can continue adding objects or exit the program.\n");
    }
}

