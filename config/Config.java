package config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

public class Config {
   private final File configFile;
   private final List<Entry> entries = new ArrayList<>(1);

   private static void IOException(IOException e, String what) {
      e.printStackTrace();
      System.err.println(e);
      System.err.println("Failed to " + what + "config file");
      System.exit(1);
   }

   /**
    * Creates the config file if it does not exist
    */
   private void createConfigFile() {
      try {
         configFile.createNewFile();
      } catch (IOException e) {
         Config.IOException(e, "create");
      }
   }

   private List<String> readConfigFile() {
      try {
         return Files.readAllLines(
            configFile.toPath(),
            StandardCharsets.UTF_8
         );
      } catch (IOException e) {
         Config.IOException(e, "read");
      }
      return null;
   }

   /**
    * Includes a bug where if there's a duplicate key,
    * it still adds it to the list
    */
   public Config(String filePath) {
      configFile = new File(filePath);

      createConfigFile();
      for (var line : readConfigFile()) {
         var entry = Entry.fromString(line);
         if (entry != null) {
            entries.add(entry);
         }
      }
   }

   /**
    * Naive getter
    * O(n ^ 2)
    */
   public Entry getEntry(String key) {
      for (var entry : entries) {
         if (entry.key.equals(key)) {
            return entry;
         }
      }
      return null;
   }

   public String get(String key) {
      var entry = getEntry(key);
      if (entry == null) {
         return null;
      } else {
         return entry.val;
      }
   }

   public void set(String key, String val) {
      var entry = getEntry(key);
      if (entry == null) {
         entries.add(new Entry(key, val));
      } else {
         entry.val = val;
      }
   }

   public void clear() {
      entries.clear();
   }

   public void printEntries() {
      entries
         .stream()
         .map((entry) -> entry.display())
         .forEach(System.out::println);
   }

   private String createConfigString() {
      var sb = new StringBuilder();
      for (var entry : entries) {
         sb.append(entry);
         sb.append('\n');
      }
      return sb.toString();
   }

   private boolean overwriteConfigFile(String outString) {
      try {
         var fw = new FileWriter(configFile.getAbsolutePath(), false);
         fw.write(outString);
         fw.close();
         return true;
      } catch (IOException e) {
         return false;
      }
   }

   public void writeEntriesToFile() {
      var outString = createConfigString();
      var res = overwriteConfigFile(outString);
      if (res) {
         System.err.println(outString);
         System.out.println("Wrote " + configFile.getAbsolutePath());
      } else {
         System.err.println("Couldn't write config file!");
      }
   }
}
