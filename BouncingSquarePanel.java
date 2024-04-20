import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

class BouncingSquarePanel extends JPanel
{
   //Start with two static final constants.  "final" is a keyword that
   //means you can't ever change a value.
   public static final int FRAME = 500;
   public int frameCount;
   private static final Color BACKGROUND = Color.BLACK;
   //fields
   private BufferedImage myImage;  //temporary storage area for the graphics
   private Graphics myBuffer;
   private Timer t;
   private Paddle leftPaddle;
   private Paddle rightPaddle;
   private BouncingSquare ball;
   private ArrayList<Animatable> animationObjects;
   private boolean up;
   private boolean down;
   private boolean w;
   private boolean s;

   /*
      Finally, add a new field, an int variable, to count the frames.
   */

   //We just need one constructor - initialize all the fields and start the animation!
   public BouncingSquarePanel() {
      frameCount = 0;
      myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      myBuffer.setColor(BACKGROUND);    //The next two lines draw the background rectangle for the first time
      myBuffer.fillRect(0, 0, FRAME, FRAME);
      //r, x-position, y-position, color, dR, minR, and maxR.
      //int s, int x, int y, Color c, int dX, int dY
      ball = new BouncingSquare(); //Instantiate an inflating circle...
      leftPaddle = new Paddle(10, 10, 250, Color.WHITE);
      rightPaddle = new Paddle(10, 480, 250, Color.WHITE);
      animationObjects = new ArrayList<Animatable>();
      animationObjects.add(ball);
      animationObjects.add(rightPaddle);
      animationObjects.add(leftPaddle);
      down = false;
      up = false;
      w = false;
      s = false;
      addKeyListener(new Key());  //Key is a private class defined below
      setFocusable(true);  //Don't forget this!
      t = new Timer(5, new AnimationListener());
      t.start();
   }
   
   public void paintComponent(Graphics g)  //Required method to paint to the screen
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);  //Draw the buffered image we've stored as a field
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial Black", Font.BOLD, 20));
      g.drawString("" + leftPaddle.getScore(), 10, 30);
      g.drawString("" + rightPaddle.getScore(), 470, 30);
   }

   
   public void animate()
   {
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      // collisions: 0-14, 15-28, 29-42, 43-56, 57-70
      // speeds: 5, 3, 1, 2, 4
      //Loop through the ArrayList of Animatable objects; do an animation step on each one & draw it
      double x = Math.random();
      for (int i = 0; i < 3; i++) {
         animationObjects.get(i).step();
         animationObjects.get(i).drawMe(myBuffer);
      }
      if (ball.leftCollide(leftPaddle) == 2) {
         ball.setDX(ball.getDX()*-1);
      }
      if (ball.rightCollide(rightPaddle) == 2) {
         ball.setDX(ball.getDX()*-1);
      }

      boolean collide = false;
      if (ball.rightCollide(rightPaddle) == 1) {
         ball.setX(250);
         ball.setY(262);
         rightPaddle.setX(480);
         leftPaddle.setX(10);
         leftPaddle.setY(250);
         rightPaddle.setY(250);
         collide = true;
          if (x < 0.2) {
            ball.setDY(3);
         }
         else if (x < 0.4) {
            ball.setDY(2);
         }
         else if (x < 0.6) {
            ball.setDY(1);
         }
         else if (x < 0.8) {
            ball.setDY(-1);
         }
         else {
            ball.setDY(-2);
         }
         leftPaddle.setScore(leftPaddle.getScore()+1);
      }
      if (ball.leftCollide(leftPaddle) == 1) {
         ball.setX(250);
         ball.setY(262);
         rightPaddle.setX(480);
         leftPaddle.setX(10);
         leftPaddle.setY(250);
         rightPaddle.setY(250);
         if (x < 0.2) {
            ball.setDY(3);
         }
         else if (x < 0.4) {
            ball.setDY(2);
         }
         else if (x < 0.6) {
            ball.setDY(1);
         }
         else if (x < 0.8) {
            ball.setDY(-1);
         }
         else {
            ball.setDY(-2);
         }
         rightPaddle.setScore(rightPaddle.getScore()+1);
         collide = true;
      }
      repaint();

      if (collide) {
         try {
            TimeUnit.SECONDS.sleep(2);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }
   }
   
   //private classes
   
   private class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)  //Timer calls this every 5 ms
      {
         animate();  //...hence animation!
      }
   }

   private class Key extends KeyAdapter //Make ONE class that EXTENDS KeyAdapter, and tell it what to do when keys are pressed or released
   {
      public void keyPressed(KeyEvent e) //Make ONE method for key presses; this is overridden, and will be called whenever a key is pressed
      {
         if(e.getKeyCode() == KeyEvent.VK_UP && !up) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            rightPaddle.setDY(rightPaddle.getDY() - 3);  //Subtract 2 from Square's dX value, effectively setting the value to 0.
            up = true;  //Now, the user is holding down the left key, so set this to true.  Why do we need to keep track of this?  So that holding down one (or even two) key works as expected.
         }
         if(e.getKeyCode() == KeyEvent.VK_DOWN && !down) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            rightPaddle.setDY(rightPaddle.getDY() + 3);  //Subtract 2 from Square's dX value, effectively setting the value to 0.
            down = true;  //Now, the user is holding down the left key, so set this to true.  Why do we need to keep track of this?  So that holding down one (or even two) key works as expected.
         }
         if(e.getKeyCode() == KeyEvent.VK_W && !w) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            leftPaddle.setDY(leftPaddle.getDY() - 3);  //Subtract 2 from Square's dX value, effectively setting the value to 0.
            w = true;  //Now, the user is holding down the left key, so set this to true.  Why do we need to keep track of this?  So that holding down one (or even two) key works as expected.
         }
         if(e.getKeyCode() == KeyEvent.VK_S && !s) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            leftPaddle.setDY(leftPaddle.getDY() + 3);  //Subtract 2 from Square's dX value, effectively setting the value to 0.
            s = true;  //Now, the user is holding down the left key, so set this to true.  Why do we need to keep track of this?  So that holding down one (or even two) key works as expected.
         }

      }

      public void keyReleased(KeyEvent e) //Also overridden; ONE method that will be called any time a key is released
      {
         if(e.getKeyCode() == KeyEvent.VK_UP) // If the user lets go of the left arrow
         {
            rightPaddle.setDY(rightPaddle.getDY() + 3);  //Again: add 2, don't set to 0 precisely.
            up = false;  //User is no longer holding the left key, so set this back to false.
         }
         if(e.getKeyCode() == KeyEvent.VK_DOWN) // If the user lets go of the left arrow
         {
            rightPaddle.setDY(rightPaddle.getDY() - 3);  //Again: add 2, don't set to 0 precisely.
            down = false;  //User is no longer holding the left key, so set this back to false.
         }
         if(e.getKeyCode() == KeyEvent.VK_W) // If the user lets go of the left arrow
         {
            leftPaddle.setDY(leftPaddle.getDY() + 3);  //Again: add 2, don't set to 0 precisely.
            w = false;  //User is no longer holding the left key, so set this back to false.
         }
         if(e.getKeyCode() == KeyEvent.VK_S) // If the user lets go of the left arrow
         {
            leftPaddle.setDY(leftPaddle.getDY() - 3);  //Again: add 2, don't set to 0 precisely.
            s = false;  //User is no longer holding the left key, so set this back to false.
         }
      }
   }

}
