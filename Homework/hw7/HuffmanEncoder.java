import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> table = new HashMap<>();
        for (int i = 0; i < inputSymbols.length; i++) {
            if (table.containsKey(inputSymbols[i])) {
                int freq = table.get(inputSymbols[i]);
                table.replace(inputSymbols[i], freq++);
            } else {
                table.put(inputSymbols[i], 1);
            }
        }

        return table;
    }

    public static void main(String[] args) {
        char[] input = FileUtils.readFile(args[0]);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        Map<Character, Integer> freqTable = buildFrequencyTable(input);
        BinaryTrie decodingTrie = new BinaryTrie(freqTable);
        ow.writeObject(decodingTrie);

        Map<Character, BitSequence> lookupTable = decodingTrie.buildLookupTable();
        List<BitSequence> sequences = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            sequences.add(lookupTable.get(input[i]));
        }

        BitSequence massiveSeq = BitSequence.assemble(sequences);
        ow.writeObject(massiveSeq);

    }
}
