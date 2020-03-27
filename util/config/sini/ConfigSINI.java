package util.config.sini;

import util.config.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import opre.Option;
import static opre.Option.*;

public class ConfigSINI implements IConfig<String, String> {
   private static void IOException(IOException e, String what) {
      e.printStackTrace();
      System.err.println(e);
      System.err.println("Failed to " + what + "config file");
      System.exit(1);
   }


   private final File configFile;
   private final List<Entry> entries = new ArrayList<>(1);

   /** Creates the config file if it does not exist */
   private void createConfigFile() {
      try {
         configFile.createNewFile();
      } catch (IOException e) {
         ConfigSINI.IOException(e, "create");
      }
   }

   private List<String> readConfigFile() {
      try {
         return Files.readAllLines(
            configFile.toPath(),
            StandardCharsets.UTF_8
         );
      } catch (IOException e) {
         ConfigSINI.IOException(e, "read");
      }
      return null;
   }

   /**
    * Includes a bug where if there's a duplicate key,
    * it still adds it to the list
    */
   public ConfigSINI(String filePath) {
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
   @Override
   public Option<IEntry<String, String>> getEntry(String key) {
      for (var entry : entries) {
         if (entry.getKey().equals(key)) {
            return Some(entry);
         }
      }
      return None();
   }

   @Override
   public Option<String> get(String key) {
      return this.getEntry(key).map(IEntry::getVal);
   }

   public void set(String key, String val) {
      var entry = this.getEntry(key);
      if (entry.is_none()) {
         entries.add(new Entry(key, val));
      } else {
         entry.unwrap().setVal(val);
      }
   }

   @Override
   public void clear() {
      entries.clear();
   }

   @Override
   public void displayEntries(PrintStream out) {
      out.print(this.display());
   }

   @Override
   public void displayEntries() {
      this.displayEntries(System.out);
   }

   @Override
   public String display() {
      var sb = new StringBuilder();
      for (var entry : this.entries) {
         sb.append(entry.display());
         sb.append('\n');
      }
      return sb.toString();
   }

   private String createConfigString() {
      var sb = new StringBuilder();
      for (var entry : entries) {
         sb.append(entry);
         sb.append('\n');
      }
      return sb.toString();
   }

   // TODO
   // Convert to Result::trycatch
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

   @Override
   public void save() {
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
