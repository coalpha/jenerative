package sketches.halftone;

import java.awt.Color;
import java.awt.image.BufferedImage;
import util.TriConsumer;

class ColorMatrix implements util.IDisplay {
   public int width;
   public int height;
   public Color[][] data;

   public ColorMatrix(BufferedImage img) {
      this(img.getWidth(), img.getHeight());
      for (var y = 0; y < this.height; y++) {
         var row = this.data[y];
         for (var x = 0; x < this.width; x++) {
            var rgb = img.getRGB(x, y);
            var temp = new Color(rgb);
            row[x] = temp;
         }
      }
   }

   public ColorMatrix(int width, int height) {
      this.width = width;
      this.height = height;
      this.data = new Color[height][width];
   }

   public void forEach(TriConsumer<Color, Integer, Integer> fn) {
      System.out.println(this.display());
      for (var y = 0; y < this.height; y++) {
         var row = this.data[y];
         for (var x = 0; x < this.width; x++) {
            fn.accept(row[x], x, y);
         }
      }
   }

   @Override
   public String display() {
      var sb = new StringBuilder("ColorMatrix {");
      sb.append("\n   width: ");
      sb.append(this.width);
      sb.append(",\n   height: ");
      sb.append(this.height);
      sb.append(",\n}");
      return sb.toString();
   }
}
