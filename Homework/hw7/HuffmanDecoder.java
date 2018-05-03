
import java.util.ArrayList;

public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie trie = (BinaryTrie) or.readObject();
        BitSequence seq = (BitSequence) or.readObject();
        ArrayList<Character> chars = new ArrayList<>();

        while (seq.length() > 0) {
            Match ch = trie.longestPrefixMatch(seq);
            chars.add(ch.getSymbol());
            System.out.println(ch.getSymbol());
            seq = seq.allButFirstNBits(ch.getSequence().length());
        }

        char[] words = new char[chars.size()];
        for (int i = 0; i < chars.size(); i++) {
            words[i] = chars.get(i);
        }

        FileUtils.writeCharArray(args[1], words);

    }
}
