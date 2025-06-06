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
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addStarWithFlow(scanner);
                    break;

                case 2:
                    // This brings up the “Asteroid/Comet/Black Hole” submenu
                    addSpaceObjectsMenu(scanner);
                    break;

                case 3:
                    launchGalaxyGUI();
                    break;

                case 4:
                    startNewGalaxy(scanner);
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting simulation. Goodbye!");
                    break;

                default:
                    System.err.println("Invalid choice. Please enter 1-5.");
            }
        }

        scanner.close();
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
                addPlanetsWithFlow(scanner, star);
            }
        } else {
            System.err.println("(Maximum planets already reached)");
        }
    }
    
    private static void addPlanetsWithFlow(Scanner scanner, Star star) {
        System.out.print("How many planets would you like to add? (Max remaining: " + (MAX_PLANETS - planets.size()) + "): ");
        int numPlanets = scanner.nextInt();
        scanner.nextLine();

        int maxToAdd = Math.min(numPlanets, MAX_PLANETS - planets.size());
        ArrayList<Planet> justAdded = new ArrayList<>();

        for (int i = 0; i < maxToAdd; i++) {
            double distance = 60;
            double spacing = 35;
            double orbitDistance = distance + i * spacing;
            double orbitSpeed = 0.015 + Math.random() * 0.01;

            Planet planet = new Planet(star, orbitDistance, orbitSpeed);
            planets.add(planet);
            justAdded.add(planet);
        }

        System.out.println("PLANET: Great! You added " + justAdded.size() + " planet(s) to orbit that star!");

        if (moons.size() < MAX_MOONS && !justAdded.isEmpty()) {
            System.out.print("Would you like to add moons to orbit your planets? (y/n): ");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("y") || response.equals("yes")) {
                addMoonsWithFlow(scanner, justAdded);
            }
        } else if (moons.size() >= MAX_MOONS) {
            System.out.println("(Maximum moons already reached)");
        }
    }
    
    private static void addMoonsWithFlow(Scanner scanner, ArrayList<Planet> planetList) {
        System.out.print("How many moons would you like to add? (Max remaining: " + (MAX_MOONS - moons.size()) + "): ");
        int numMoons = scanner.nextInt();
        scanner.nextLine();

        int maxToAdd = Math.min(numMoons, MAX_MOONS - moons.size());

        for (int i = 0; i < maxToAdd; i++) {
            double distance = 20;
            double spacing = 10;
            
            Planet mainPlanet = planetList.get(i % planetList.size());
            double orbitDistance = distance + (i % 3) * spacing;
            double orbitSpeed = 0.03 + Math.random() * 0.01;

            Moon moon = new Moon(mainPlanet, orbitDistance, orbitSpeed);
            moons.add(moon);
        }

        System.out.println("MOON: Great! You added " + maxToAdd + " moon(s) to orbit your planets!");
    }
    
    private static void addAsteroid(Scanner scanner) {
        if (asteroids.size() >= MAX_ASTEROIDS) {
            System.err.println("X Maximum number of asteroids (" + MAX_ASTEROIDS + ") reached!");
            return;
        }

        // Create actual Asteroid object
        Asteroid asteroid = new Asteroid(Math.random() * 800, Math.random() * 600);
        asteroids.add(asteroid);
        System.out.println("* Asteroid added to the galaxy!");
    }
    
    private static void addComet(Scanner scanner) {
        if (comets.size() >= MAX_COMETS) {
            System.err.println("X Maximum number of comets (" + MAX_COMETS + ") reached!");
            return;
        }
        
        
        // Create actual Comet object
        Comet comet = new Comet();
        comets.add(comet);
        System.out.println("* Comet added to the galaxy!");
    }
    
    private static void addBlackHole(Scanner scanner) {
        if (blackHoles.size() >= MAX_BLACK_HOLES) {
            System.err.println("❌ Maximum number of black holes (" + MAX_BLACK_HOLES + ") reached!");
            return;
        }

        // Create actual BlackHole object at a random position
        double x = Math.random() * 800;
        double y = Math.random() * 600;
        BlackHole blackHole = new BlackHole(x, y);
        blackHoles.add(blackHole);
        System.out.println("* Black hole added to the galaxy!");
    }
    
    private static void addSpaceObjectsMenu(Scanner scanner) {
        System.out.println("\n--- SPACE OBJECTS MENU ---");
        System.out.println("1. Asteroid (" + asteroids.size() + "/" + MAX_ASTEROIDS + ")");
        System.out.println("2. Comet (" + comets.size() + "/" + MAX_COMETS + ")");
        System.out.println("3. Black Hole (" + blackHoles.size() + "/" + MAX_BLACK_HOLES + ")");
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
                return;  // back to top‐level menu
            default:
                System.err.println("Invalid choice. Please enter 1-4.");
        }
    }

    private static void addAsteroidsWithFlow(Scanner scanner) {
    int remaining = MAX_ASTEROIDS - asteroids.size();
    if (remaining <= 0) {
        System.err.println("❌ You already have the maximum of " + MAX_ASTEROIDS + " asteroids.");
        return;
    }

    System.out.print("You have " + asteroids.size() + "/" + MAX_ASTEROIDS +
                     " asteroids. How many would you like to add? ");
    int num = scanner.nextInt();
    scanner.nextLine();  // consume newline

    int toAdd = Math.min(num, remaining);
    for (int i = 0; i < toAdd; i++) {
        Asteroid a = new Asteroid(Math.random() * 800, Math.random() * 600);
        asteroids.add(a);
    }
    System.out.println("Added " + toAdd + " asteroid(s). Total now: " +
                       asteroids.size() + "/" + MAX_ASTEROIDS + ".");
}

private static void addCometsWithFlow(Scanner scanner) {
    int remaining = MAX_COMETS - comets.size();
    if (remaining <= 0) {
        System.err.println("❌ You already have the maximum of " + MAX_COMETS + " comets.");
        return;
    }

    System.out.print("You have " + comets.size() + "/" + MAX_COMETS +
                     " comets. How many would you like to add? ");
    int num = scanner.nextInt();
    scanner.nextLine();  // consume newline

    int toAdd = Math.min(num, remaining);
    for (int i = 0; i < toAdd; i++) {
        Comet c = new Comet();
        comets.add(c);
    }
    System.out.println("Added " + toAdd + " comet(s). Total now: " +
                       comets.size() + "/" + MAX_COMETS + ".");
}

private static void addBlackHolesWithFlow(Scanner scanner) {
    int remaining = MAX_BLACK_HOLES - blackHoles.size();
    if (remaining <= 0) {
        System.err.println("❌ You already have the maximum of " + MAX_BLACK_HOLES + " black holes.");
        return;
    }

    System.out.print("You have " + blackHoles.size() + "/" + MAX_BLACK_HOLES +
                     " black holes. How many would you like to add? ");
    int num = scanner.nextInt();
    scanner.nextLine();  // consume newline

    int toAdd = Math.min(num, remaining);
    for (int i = 0; i < toAdd; i++) {
        double x = Math.random() * 800;
        double y = Math.random() * 600;
        BlackHole bh = new BlackHole(x, y);
        blackHoles.add(bh);
    }
    System.out.println("Added " + toAdd + " black hole(s). Total now: " +
                       blackHoles.size() + "/" + MAX_BLACK_HOLES + ".");
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
        
        // Create the simulation model
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

        pauseButton.addActionListener(e -> galaxyPanel.togglePause());
        speedUpButton.addActionListener(e -> {
            galaxyPanel.speedUp();
            speedLabel.setText(
                "Click Speed Up/Slow Down multiple times.  Delay: "
                + galaxyPanel.getAnimationSpeed() + " ms"
            );
        });
        slowDownButton.addActionListener(e -> {
            galaxyPanel.slowDown();
            speedLabel.setText(
                "Click Speed Up/Slow Down multiple times.  Delay: "
                + galaxyPanel.getAnimationSpeed() + " ms"
            );
        });
        returnButton.addActionListener(e -> {
            // Disposing the frame will trigger windowClosed() below
            frame.dispose();
        });

        
        controls.add(speedLabel);
        controls.add(pauseButton);
        controls.add(speedUpButton);
        controls.add(slowDownButton);
        controls.add(returnButton);

        // Layout: add simulation panel + controls
        frame.setLayout(new BorderLayout());
        frame.add(galaxyPanel, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);

       final Object lock = new Object();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // windowClosing is called when X is clicked. After dispose, windowClosed follows.
                System.out.println("\nGalaxy GUI closed. Returning to main program...");
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // Called after dispose(). Wake up main thread.
                synchronized (lock) {
                    lock.notify();
                }
            }
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
        

        synchronized (lock) {
        try {
            lock.wait();
        } catch (InterruptedException ignored) { }
    }
    }
}
