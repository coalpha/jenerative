package sketches.halftone;

import util.config.IConfig;
import util.config.sini.ConfigSINI;
import util.config.fake.ConfigFake;
import util.ui.CanvasHolder;
import util.image.ImageLoader;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import opre.Panic;

import java.io.File;

public class Halftone {
   private static final IConfig<String, String> config = new ConfigFake<>();
   public static void main(final String[] args) {
      final var il = new ImageLoader(config);
      final var img = il.load();
      final var iw = img.getWidth();
      final var ih = img.getHeight();
      final var window = new CanvasHolder("Halftone");
      final var canvas = new HalftoneCanvas();
      window.add(canvas, 0);
      window.setSize(iw, ih);
      canvas.setSize(img.getWidth(), img.getHeight());
      window.pack();
      window.setResizable(false);
      window.setVisible(true);
      canvas.getGraphics().drawImage(img, 0, 0, null);
      var m = new ColorMatrix(img);

      Thread drawThread = new Thread(new Runnable() {
         final int SSSA = 40;
         final int xstep = img.getWidth() / SSSA;
         final int ystep = img.getHeight() / SSSA;
         int x = 0;
         int y = 0;
         @Override
         public void run() {
            while (true) {
               var ctx = canvas.getGraphics();
               ctx.drawImage(img, 0, 0, null);
               ctx.dispose();
               var img_ctx = img.getGraphics();
               int cx;
               int cy;
               if (y < SSSA) {
                  if (x < SSSA) {
                     x++;
                  } else {
                     x = 0;
                     y++;
                  }
                  cx = x * xstep;
                  cy = y * ystep;
                  img_ctx.setColor(m.data[cx + xstep][cy + ystep]);
                  img_ctx.fillRect(cx, cy, xstep, ystep);
                  img_ctx.setColor(m.data[cx][cy]);
                  img_ctx.fillOval(cx, cy, xstep, ystep);
                  img_ctx.dispose();
               } else {
                  try {
                     ImageIO.write(img, "png", new File("sketches/halftone/out.png"));
                  } catch (Throwable e) {
                     
                  }
                  System.exit(0);
               }
            }
         }
      });
      drawThread.run();
      canvas.setIgnoreRepaint(true);
   }
}

