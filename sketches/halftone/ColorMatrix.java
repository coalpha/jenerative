package sketches.halftone;

import java.awt.Color;
import java.awt.image.BufferedImage;
import util.TriConsumer;

class ColorMatrix {
   public int width;
   public int height;
   public Color[][] data;

   public ColorMatrix(BufferedImage img) {
      this(img.getWidth(), img.getHeight());
      for (var y = 0; y < this.height; y++) {
         var row = new Color[this.width];
         for (var x = 0; x < this.width; x++) {
            row[x] = new Color(img.getRGB(x, y));
            System.out.printf("data[%d][%d] = %b\n", y, x, row[x] == null);
         }
      }
   }

   public ColorMatrix(int width, int height) {
      this.width = width;
      this.height = height;
      this.data = new Color[height][width];
   }

   public void forEach(TriConsumer<Color, Integer, Integer> fn) {
      for (var y = 0; y < this.height; y++) {
         var row = this.data[y];
         for (var x = 0; x < this.width; x++) {
            fn.accept(row[x], x, y);
         }
      }
   }
}
