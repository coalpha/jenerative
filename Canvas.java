import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Canvas extends JFrame {
   private static WindowAdapter windowCloseReaper = new WindowAdapter() {
      public void windowClosing(WindowEvent drop){
         System.exit(0);
      }
   };

   public void maximize() {
      super.setExtendedState(
         super.getExtendedState() | JFrame.MAXIMIZED_BOTH
      );
   }

   Canvas() {
      super("Electron App");
      super.addWindowListener(Canvas.windowCloseReaper);
      super.setSize(400, 300);
      maximize();
      super.setVisible(true);
      super.setLocationByPlatform(true);
      super.setContentPane(rootPane);
   }

   @Override
   public void paintComponents(Graphics g) {
      
   }
}
