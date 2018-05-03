public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }

        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            String[] strings = returnStrings(wordToDeque(word), wordToDeque(word), "", "");
            return strings[0].equals(strings[1]);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            String[] strings = returnStrings(wordToDeque(word), wordToDeque(word), "", "");

            for (int i = 0; i < word.length() / 2; i++) {
                if (!cc.equalChars(word.charAt(i), word.charAt(word.length() - i - 1))) {
                    return false;
                }
            }
            return true;
        }
    }

    private String[] returnStrings(Deque first, Deque last, String start, String end) {
        if (first.isEmpty()) {
            return new String[]{start, end};
        }
        return returnStrings(first, last, start + first.removeFirst(), end + last.removeLast());
    }
}

