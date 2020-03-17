package util.ui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

@SuppressWarnings("serial")
public class CanvasHolder extends Frame {
   private static final WindowAdapter windowReaper = new WindowAdapter() {
      public void windowClosing(final WindowEvent windowEvent) {
         System.exit(0);
      }
   };

   // private Canvas c;
   public CanvasHolder(String title) {
      super(title);
      super.setIgnoreRepaint(true);
      super.addWindowListener(CanvasHolder.windowReaper);
   }

   public void setMaximized(boolean b) {
      var state = super.getExtendedState();
      if (b) {
         state |= Frame.MAXIMIZED_BOTH;
      } else {
         state &= ~Frame.MAXIMIZED_BOTH;
      }
      super.setExtendedState(state);
   }

   // public Canvas getCanvas() {
   //    return this.c;
   // }

   // public void setCanvas(Canvas c) {
   //    this.c = c;
   // }
}
