public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int total = 0;
        while (x < 10) {
            System.out.println(total);
            x = x + 1;
            total = total + x;
        }
    }
}