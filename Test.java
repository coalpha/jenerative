import static opre.re.Result.*;

public class Test {
   public static void main(String[] args) {
      var r = trycatch(() -> {
         if (Math.random() > .5) {
            throw new RuntimeException("haha");
         } else {
            return 1;
         }
      });
      r.consume2(System.out::println, System.out::println);
   }
}
