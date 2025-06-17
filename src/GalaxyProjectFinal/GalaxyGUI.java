package GalaxyProjectFinal;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;

/**
 *
 * @author Emma Dennis
 * CIS142 - Final Project
 * 5/29/2025
 * 
 */
/*
 * The visual display - the GUI file
 * The GUI acts like a projector asking the model for the list of objects.
 * it loops through the list and asks each object to draw itself (no grid - positions are continuous / floating-point pixel coords)
 * 
 * It inherits from JPanel and will use a swing timer to advance the simulation
 * on each timer tick, updates simulation state and repaints the view
 * Provides controls for pausing, speeding up, slowing down the simulation
 * provide basic controls - start, pause, etc...)
 * 
 * delegates all drawing to the Celestial objects themselves
 * Displays temp explosion effects during collisions
 */

  public class GalaxyGUI extends JPanel {
   private GalaxySimulation model;
   private Configuration initialConfig;
   private Timer timer;
   private boolean isPaused = false;
   private int animationSpeed = 100; // initial speed
   
    
    //constructor
    public GalaxyGUI(GalaxySimulation model, Configuration initialConfig) {
      this.model = model;
      this.initialConfig = initialConfig;
      setPreferredSize(new Dimension(800, 600));  //can change the size;
      setBackground(Color.BLACK);

      
      //set up timer - start at 100ms for animation speed 
      timer = new Timer(100, e -> {
         model.update();   //update all objects positions
         repaint();        //and redraw everything
      });
      timer.start();
   }

   // Expose the current delay, so external code can show it:
    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void stopSimulation() {
      if (timer != null && timer.isRunning()) {
         timer.stop();         
      }
    }
            

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g; //allows for rotations/scaling  
      
      //draw objects from the model
      for (Celestial obj : model.getObjects()) {
         if (!obj.isDestroyed()) {   //if object is not destoryed - 
         obj.draw(g2d);  //ask each object to draw itself
         }
      }

        // Draw active Explosions (on top of everything)
    for (Explosion ex : model.getExplosions()) {
        ex.draw(g2d);
    }
   }

   //methods for button behaviors 
   /**
    * 
    **/
   public void togglePause() {
      if (isPaused) {
         timer.start();
      } else {
         timer.stop();
      }
      isPaused = !isPaused;
   }
   
   public void speedUp() {
      if (animationSpeed > 20) {
         animationSpeed -= 20;
         timer.setDelay(animationSpeed);
      }
   }

   public void slowDown() {
      if (animationSpeed < 500) {
         animationSpeed += 20;
         timer.setDelay(animationSpeed);
      }
   }

   public void restart() {
      //stop the current timer
      this.stopSimulation();

      //create a fresh simulation model
      GalaxySimulation newModel = new GalaxySimulation();

      //add all objects from the configuration to the model
            for (Star star : initialConfig.getStars()) newModel.addObject(star);
            for (Planet planet : initialConfig.getPlanets()) newModel.addObject(planet);
            for (Moon moon : initialConfig.getMoons()) newModel.addObject(moon);
            for (Asteroid asteroid : initialConfig.getAsteroids()) newModel.addObject(asteroid);
            for (Comet comet : initialConfig.getComets()) newModel.addObject(comet);
            for (BlackHole blackHole : initialConfig.getBlackHoles()) newModel.addObject(blackHole);

      //Launch new GUI
      GalaxyGUI newGUI = new GalaxyGUI(newModel, initialConfig);

      JFrame newFrame = new JFrame("Galaxy Simulation (Restarted)");
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setLayout(new BorderLayout());
            newFrame.add(newGUI, BorderLayout.CENTER);
            newFrame.add(GalaxyMain.createControlsPanel(newGUI, newFrame), BorderLayout.SOUTH);
            newFrame.pack();
            newFrame.setVisible(true);

      //dispose current frame
      JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
      if (currentFrame != null) {
         currentFrame.dispose();  // close current window
      }
      
   }

   public void saveConfiguration() {
      String name = JOptionPane.showInputDialog(null, "Enter a name for this configuration:", 
                                                "Save Configuration", JOptionPane.PLAIN_MESSAGE);
      if (name == null || name.trim().isEmpty()) {
         System.out.println("❌ Save cancelled or empty name.");
         return;
      }

      String filename = "config_" + name.trim() + ".txt";

      try (PrintWriter writer = new PrintWriter(filename)) {
         writer.println(initialConfig.serialize());
         System.out.println("✅ Configuration saved as " + filename);
      } catch (IOException e) {
         System.err.println("❌ Failed to save configuration: " + e.getMessage());
      }
   }

   }
   