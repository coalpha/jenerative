package util.image;

import java.awt.Frame;
import java.awt.FileDialog;

import javax.imageio.ImageIO;

import java.io.File;

import opre.Option;
import opre.Panic;

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
      String filename;
      while (true) {
         this.choose();
         filename = this.getFilename().expect("Expected the user to choose a file");
         if (ImageChooser.hasImageExtension(filename)) {
            // all good
            break;
         } else {
            // would really like to JOptionPane but no swing argh
         }
      }

      return new File(
         this.getDirectoryString().expect("How did this even happen?")
         + filename
      );
   }

   /**
    * The first time this is called, it will return false
    */
   boolean fileChoiceIsImage() {
      var filename = this.getFileChoiceOrPanicIfNoChoice();
      return ImageChooser.hasImageExtension(filename);
   }

   String getFileChoiceOrPanicIfNoChoice() {
      return this.getFilename().expect("Expected a file to be chosen");
   }

   void choose() {
      super.setVisible(true);
   }

   Option<String> getDirectoryString() {
      return Option.fromAny(super.getDirectory());
   }

   /**
    * Returns
    * Some(String) if the user selected a file
    * None if the user canceled the dialog
    */
   Option<String> getFilename() {
      return Option.fromAny(super.getFile());
   }
}
