package util;
import java.io.File;

public class FS {
   public static File File(String path) {
      if (path == null) {
         return null;
      }
      return new File(path);
   }
}
