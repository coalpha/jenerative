package util.image;

import java.io.File;
import javax.imageio.ImageIO;

import java.awt.Frame;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;

import util.config.IConfig;

import opre.op.Option;
import opre.re.Result;
import static opre.op.Option.*;
import static opre.re.Result.*;

public class ImageLoader {
   private static final String lastImagePathKey = "ImageLoader.lastImagePath";

   private IConfig<String, String> config;
   public ImageLoader(IConfig<String, String> config) {
      this.config = (
         Option
            .fromNullable(config)
            .expect("ImageLoader.ImageLoader got a null config")
      );
   }

   public BufferedImage load() {
      File image = getImageFile();
      if (image != null && image.exists()) {
         return loadImageFromFile(image);
      }
      throw new RuntimeException("Unable to get image file!");
   }

   private Option<File> getLastImage() {
      return config.get(lastImagePathKey).map(path -> new File(path));
   }
   
   private void setLastImage(File image) {
      config.set(lastImagePathKey, image.getAbsolutePath());
      config.save();
   }

   /**
    * <b>Side Effect!</b>
    * Calls this#setLastImage.
    */
   private File chooseImage() {
      var newImage = new ImageChooser().actuallyGetFile();
      this.setLastImage(newImage);
      return newImage;
   }

   private File getImageFile() {
      var lastImage = getLastImage();
      var lastImageIsGood = lastImage.map(File::exists).unwrap_or(false);
      if (lastImageIsGood) {
         return lastImage.unwrap();
      } else {
         return this.chooseImage();
      }
   }

   private Result<BufferedImage, String> loadImageFromFile(File file) {
      return trycatch(() -> { return ImageIO.read(file); });
   }
}
