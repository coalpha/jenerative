package config;

public class Entry {
   String key;
   String val;

   protected Entry(String _key, String _val) {
      this.key = _key;
      this.val = _val;
   }

   public String display() {
      return String.format(
         "Config.Entry {\n"
         + "   key: %s\n"
         + "   val: %s\n"
         + "}",
         this.key,
         this.val
      );
   }

   @Override
   public String toString() {
      return this.key + "=" + this.val;
   }

   public static Entry fromString(String s) {
      var equalsIndex = s.indexOf('=');
      if (equalsIndex == -1) {
         return null;
      }
      // otherwise
      return new Entry(
         s.substring(0, equalsIndex),
         s.substring(equalsIndex + 1)
      );
   }
}
