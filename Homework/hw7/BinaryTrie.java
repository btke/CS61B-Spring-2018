import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    private Map<Character, Integer> freq;
    private Character[] keys;
    private Node trie;

    private static class Node implements Comparable<Node>, Serializable {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        freq = frequencyTable;
        Object[] objectArray = frequencyTable.keySet().toArray();
        keys = Arrays.copyOf(objectArray, objectArray.length, Character[].class);
        trie = buildTrie(keys);
    }

    private Node buildTrie(Character[] k) {
        MinPQ<Node> fringe = new MinPQ<>();
        for (int i = 0; i < k.length; i++) {
            int frequency = freq.get(k[i]);
            fringe.insert(new Node(k[i], frequency, null, null));
        }

        while (fringe.size() > 1) {
            Node left  = fringe.delMin();
            Node right = fringe.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            fringe.insert(parent);
        }

        return fringe.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        return longestPrefixMatchHelper(querySequence, trie, 0, "");
    }

    private Match longestPrefixMatchHelper(BitSequence seq, Node t, int num, String s) {
        if (t.isLeaf()) {
            return new Match(new BitSequence(s), t.ch);
        } else {
            int bit = seq.bitAt(num);
            if (bit == 0) {
                return longestPrefixMatchHelper(seq, t.left, num + 1, s + '0');
            } else {
                return longestPrefixMatchHelper(seq, t.right, num + 1, s + '1');
            }
        }
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> table = new HashMap<>();
        buildTableHelper(table, trie, "");
        return table;
    }

    private void buildTableHelper(Map set, Node t, String s) {
        if (!t.isLeaf()) {
            buildTableHelper(set, t.left,  s + '0');
            buildTableHelper(set, t.right, s + '1');
        } else {
            set.put(t.ch, new BitSequence(s));
        }
    }
}
