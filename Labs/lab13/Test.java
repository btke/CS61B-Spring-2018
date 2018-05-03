public class Test {
    public static void main(String[] args) {
        String[] test = new String[]{"cat", "elephant", "ball", "fuck", "apple", "giant", "dick"};
        String[] sorted = RadixSort.sort(test);
        for (int i = 0; i < sorted.length; i++) {
            System.out.println(sorted[i]);
        }
    }
}
