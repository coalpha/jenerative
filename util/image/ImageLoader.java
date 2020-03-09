package util.image;

import java.io.File;
import javax.imageio.ImageIO;

import java.awt.Frame;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;

import config.Config;
import util.FS;

public class ImageLoader {
   private static String[] imageExtensions = ImageIO.getReaderFileSuffixes();

   private static boolean hasImageExtension(String fileName) {
      if (fileName == null) {
         return false;
      }
      for (var imageFileExtension : imageExtensions) {
         if (fileName.endsWith(imageFileExtension)) {
            return true;
         }
      }
      return false;
   }

   Config config;
   public ImageLoader(Config _config) {
      if (_config == null) {
         throw new NullPointerException("ImageLoader requires a non-null config");
      }
      config = _config;
   }

   private static final String lastImagePathKey = "ImageLoader.lastImagePath";
   /**
    * May return null if there is no last image path
    */
   private File getLastImage() {
      return FS.File(config.get(lastImagePathKey));
   }
   
   private void setLastImage(File image) {
      config.set(lastImagePathKey, image.getAbsolutePath());
      config.writeEntriesToFile();
   }

   /**
    * Never returns null.
    */
   private File GUIChooseImage() {
      var dialog = new FileDialog((Frame) null, "Choose an image:");
      do {
         dialog.setVisible(true);
         System.out.println(dialog.getFile());
      } while (!hasImageExtension(dialog.getFile()));
      return new File(dialog.getDirectory() + dialog.getFile());
   }

   private File getImageFile() {
      var lastImage = getLastImage();
      if (lastImage != null && lastImage.exists()) {
         return lastImage;
      }
      // otherwise
      var image = GUIChooseImage();
      setLastImage(image);
      return image;
   }

   private BufferedImage loadImageFromFile(File file) {
      try {
         return ImageIO.read(file);
      } catch (Throwable e) {
         return null;
      }
   }

   public BufferedImage load() {
      File image = getImageFile();
      if (image != null && image.exists()) {
         return loadImageFromFile(image);
      }
      throw new RuntimeException("Unable to get image file!");
   }
}
