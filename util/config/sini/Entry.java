package util.config.sini;

import util.config.IEntry;

class Entry implements IEntry<String, String> {
   public static Entry fromString(String s) {
      var equalsIndex = s.indexOf('=');
      if (equalsIndex == -1) {
         return null;
      } else {
         return new Entry(
            s.substring(0, equalsIndex),
            s.substring(equalsIndex + 1)
         );
      }
   }

   private final String key;
   private String val;

   protected Entry(String key, String val) {
      this.key = key;
      this.val = val;
   }

   @Override
   public String getKey() {
      return this.key;
   }

   @Override
   public String getVal() {
      return this.val;
   }

   @Override
   public void setVal(String val) {
      this.val = val;
   }

   @Override
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
   public String stringSerialize() {
      return this.key + "=" + this.val;
   }
}
