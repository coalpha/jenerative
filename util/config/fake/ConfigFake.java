package util.config.fake;

import util.config.*;

import java.io.PrintStream;

import opre.Option;

public class ConfigFake<K, V> implements IConfig<K, V> {
   @Override
   public Option<V> get(K key) {
      return Option.None();
   }

   @Override
   public Option<IEntry<K, V>> getEntry(K key) {
      return Option.None();
   }

   @Override
   public void set(K key, V val) {}

   @Override
   public void clear() {}

   @Override
   public void displayEntries() {}

   @Override
   public void displayEntries(PrintStream out) {}

   @Override
   public void save() {}

   @Override
   public String display() {
      return "";
   }

   @Override
   public String toString() {
      return "config.fake.Config";
   }
}
