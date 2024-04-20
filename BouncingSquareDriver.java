import javax.swing.*;

public class BouncingSquareDriver {

   public static void main(String[] args)
   {
       JFrame frame = new JFrame("Animation!");
       frame.setSize(500, 525);  //This time we're going for a 500x500 panel
       frame.setLocation(490, 250);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setContentPane(new BouncingSquarePanel());
       frame.setVisible(true);
   }
}
