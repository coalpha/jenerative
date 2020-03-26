package util.image;

import java.awt.Frame;
import java.awt.FileDialog;

import javax.imageio.ImageIO;

import java.io.File;

import opre.op.Option;

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

   Option<String> getDirectoryString() {
      return Option.fromNullable(super.getDirectory());
   }

   Option<String> getFilename() {
      return Option.fromNullable(super.getFile());
   }

   void choose() {
      super.setVisible(true);
   }

   boolean fileChoiceIsImage() {
      return (
         this.getFilename()
            .map(ImageChooser::hasImageExtension)
            .unwrap_or(false)
      );
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
}
