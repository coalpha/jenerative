package halftone;

import config.Config;
import util.image.ImageLoader;

import java.awt.Frame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Canvas;

import java.awt.event.*;

public class Halftone {
   private static WindowAdapter windowCloseReaper = new WindowAdapter() {
      public void windowClosing(WindowEvent drop){
         System.exit(0);
      }
   };

   public static void main(String[] args) {
      var config = new Config("halftone/config.ini");
      var il = new ImageLoader(config);
      var img = il.load();
      var frame = new Frame();
      frame.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
      frame.setExtendedState(
         frame.getExtendedState() | Frame.MAXIMIZED_BOTH
      );
      frame.addWindowListener(windowCloseReaper);
      frame.setVisible(true);
      var ctx = (Graphics2D) frame.getGraphics();
      // System.out.println(ctx);
      ctx.drawImage(img, 0, 0, 100, 100, null);
   }
}

