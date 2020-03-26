package util.image;

import java.awt.Frame;
import java.awt.FileDialog;

import javax.imageio.ImageIO;

import java.io.File;

import opre.Option;

@SuppressWarnings("serial")
class ImageChooser extends FileDialog {
   private static String[] imageExtensions = ImageIO.getReaderFileSuffixes();

   private static boolean hasImageExtension(String fileName) {
      for (var imageFileExtension : imageExtensions) {
         if (fileName.endsWith(imageFileExtension)) {
            return true;
         }
      }
      return false;
   }

   private static String defaultTitle = "Choose an image:";

   ImageChooser() {
      super((Frame) null, ImageChooser.defaultTitle, 0);
   }

   ImageChooser(String title) {
      super((Frame) null, title, 0);
   }

   /**
    * This is the one you want.
    * Actually returns a file.
    */
   File actuallyGetFile() {
      while (!this.fileChoiceIsImage()) {
         this.choose();
      }

      return new File(
         this.getDirectoryString().unwrap()
         + this.getFilename().unwrap()
      );
   }

   /**
    * The first time this is called, it will return false
    */
   boolean fileChoiceIsImage() {
      return (
         this.getFilename()
            .map(ImageChooser::hasImageExtension)
            .unwrap_or(false)
      );
   }

   void choose() {
      super.setVisible(true);
   }

   Option<String> getDirectoryString() {
      return Option.fromAny(super.getDirectory());
   }

   Option<String> getFilename() {
      return Option.fromAny(super.getFile());
   }
}
