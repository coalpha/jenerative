package util.image;

import java.io.File;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import util.config.IConfig;

import opre.Option;
import opre.Result;
import static opre.Result.*;

public class ImageLoader {
   private static final String lastImagePathKey = "ImageLoader.lastImagePath";

   private IConfig<String, String> config;
   public ImageLoader(IConfig<String, String> config) {
      this.config = (
         Option
            .fromAny(config)
            .expect("ImageLoader.ImageLoader got a null config")
      );
   }

   public BufferedImage load() {
      File image = this.getImageFile();
      if (image.exists()) {
         return (
            this.loadImageFromFile(image)
               .expect("Unable to load image from selected file")
         );
      }
      // somehow the image file was deleted
      // during the time we selected it
      throw new RuntimeException("Panic at ImageLoader#load: Not possible??");
   }

   private File getImageFile() {
      var lastImage = this.getLastImageChoice();
      var lastImageIsGood = lastImage.map(File::exists).unwrap_or(false);

      if (lastImageIsGood) {
         return lastImage.unwrap();
      } else {
         return this.choseImageAndSaveChoice();
      }
   }

   private Option<File> getLastImageChoice() {
      return this.config.get(lastImagePathKey).map(path -> new File(path));
   }

   /**
    * <b>Side Effect!</b>
    * Calls this#setLastImage.
    */
   private File choseImageAndSaveChoice() {
      var newImage = new ImageChooser().actuallyGetFile();
      this.setLastImage(newImage);
      return newImage;
   }

   private void setLastImage(File image) {
      this.config.set(lastImagePathKey, image.getAbsolutePath());
      this.config.save();
   }

   /**
    * TODO
    * Don't use the try catch block
    */
   private Result<BufferedImage, String> loadImageFromFile(File file) {
      try {
         return Ok(ImageIO.read(file));
      } catch (Throwable e) {
         return Err(e.toString());
      }
   }
}
