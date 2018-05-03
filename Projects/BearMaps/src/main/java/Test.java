import java.util.ArrayList;


public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(0, 2);
        test.add(0, 3);
        System.out.println(test.toString());
    }
}
