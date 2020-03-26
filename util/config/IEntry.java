package util.config;
import util.*;

public interface IEntry<K,V> extends IDisplay, IStringSerialize {
   K getKey();
   V getVal();
   void setVal(V newVal);
}
