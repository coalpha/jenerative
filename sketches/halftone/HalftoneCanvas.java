package sketches.halftone;

import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
class HalftoneCanvas extends Canvas {
   private BufferedImage buffer;
   private Dimension raster;
   private int width;
   private int height;

   private void initBuffer() {
      this.buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
   }

   public HalftoneCanvas(int width, int height) {
      this.width = width;
      this.height = height;
      System.out.println("HalftoneCanvas::new();");
      super.setBackground(Color.GRAY);
      super.setSize(300, 300);
      initBuffer();
      // System.out.println(getBufferStrategy());
   }

   @Override
   

   public void paint(final Graphics gtx) {
      // var ctx = (Graphics2D) gtx;
      // ctx.drawString("It is a custom canvas area", 70, 70);
      // ctx.dispose();
      System.out.println("HalftoneCanvas#paint");
   }
}
