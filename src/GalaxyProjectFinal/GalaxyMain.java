package GalaxyProjectFinal;

import java.util.Random;
import java.util.Scanner;

public class GalaxyMain {
    // Maximum limits for each celestial object type
    private static final int MAX_STARS = 5;
    private static final int MAX_PLANETS = 10;
    private static final int MAX_MOONS = 15;
    private static final int MAX_ASTEROIDS = 20;
    private static final int MAX_COMETS = 8;
    private static final int MAX_BLACK_HOLES = 2;
    private static final int[] COUNT = {MAX_STARS, MAX_PLANETS, MAX_MOONS, MAX_ASTEROIDS, MAX_COMETS, MAX_BLACK_HOLES};
    private static final int[] countObject = {0, 0, 0, 0, 0, 0};
    private static final String[] OBJECT_TYPE = {"Star", "Planet", "Moon", "Asteroid", "Comet", "Black Hole"};
    private static Random random = new Random();
    
    public static void main(String[] args) {
        System.out.println("                  ****Welcome to the Galaxy Simulation!****");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("This is a program where you can create your own galaxy by choosing celestial objects!");
        
        Scanner console = new Scanner(System.in);
        boolean runProgram = true;
        
        while (runProgram) {
            displayMenu();
            displayList();
            System.out.print("Enter your choice (1-9): ");

            if (!console.hasNextInt()) {
                System.err.println("Invalid choice. Try again.");
                console.next();
                continue;
            }

            int input = console.nextInt();
            System.out.println();
            
            switch(input) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    addObjects(input - 1, console);
                    break;
                case 7:
                    simulateGalaxy();
                    break;
                case 8:
                    launchGUI();
                    break;
                case 9:
                    System.out.println("Exiting the Galaxy Simulation!");
                    runProgram = false;
                    break;
                default:
                    System.err.println("Invalid menu choice. Try again.");
            }
        }
        console.close();
    }
    
    private static void displayMenu() {
        System.out.println("\nChoose an object to add to your galaxy:");
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
    
    private static void displayList() {
        System.out.println("\n*** Chosen Galaxy Objects ***");
        boolean empty = true;
        for (int i = 0; i < OBJECT_TYPE.length; i++) {
            if (countObject[i] > 0) {
                System.out.println(OBJECT_TYPE[i] + "s: " + countObject[i]);
                empty = false;
            }
        }
        if (empty) {
            System.out.println("No celestial objects added yet.");
        }
        System.out.println("--------------------------------");
    }
    
    private static void addObjects(int index, Scanner console) {
        String type = OBJECT_TYPE[index].toLowerCase();
        int max = COUNT[index];
        int current = countObject[index];
        int remaining = max - current;

        if (remaining == 0) {
            System.out.println("You already have the maximum number of " + type + "s.");
            return;
        }

        System.out.print("How many " + type + "s to add? (Max: " + remaining + "): ");

        if (!console.hasNextInt()) {
            System.err.println("Invalid object number. Try again.");
            console.next();
            return;
        }

        int amount = console.nextInt();
        if (amount <= 0) {
            System.out.println("You must add at least one.");
            return;
        }

        int added = Math.min(amount, remaining);
        countObject[index] += added;

        if (added == 1) {
            System.out.println("You have added " + added + " " + type + "s.");
        } else {
            System.out.println("You have added " + added + " " + type + "s.");
        }

        if (countObject[index] == max) {
            System.err.println("Maximum number of " + type + "s reached!");
        }
    }

    // NEED TO DO
    private static void simulateGalaxy() {
        System.out.println(" ");
    }

    // NEED TO DO
    private static void launchGUI() {
        System.out.println(" ");
    }
}