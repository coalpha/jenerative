package sketches.halftone;

import java.awt.*;

@SuppressWarnings("serial")
class HalftoneCanvas extends Canvas {
   private void initBuffer() {
      var bs = super.getBufferStrategy();
      if (bs == null) {
         super.createBufferStrategy(2);
      }
   }

   public HalftoneCanvas() {
      System.out.println("aaaa");
      super.setBackground(Color.GRAY);
      super.setSize(300, 300);
      // initBuffer();
      System.out.println(getBufferStrategy());
   }

   public void paint(final Graphics gtx) {
      // var ctx = (Graphics2D) gtx;
      // ctx.drawString("It is a custom canvas area", 70, 70);
      // ctx.dispose();
      System.out.println("paint!");
   }
}
