package javaProjects.caesarCrypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Caesar {
    private final Alphabet alphabet;
    private final Map<Integer, Character> alphabetMap;
    private final String PUNCTUATION_RULE_1 = ", ";
    private final String PUNCTUATION_RULE_2 = ". ";

    public Caesar(Alphabet alphabet) {
        this.alphabet = alphabet;
        alphabetMap = alphabet.getAlphabetMap();
    }

    public String code(String text, int shiftValue) {
        ArrayList<Character> charList = this.getCodeCharList(text, shiftValue);
        String codedString = Utils.charListToString(charList);
        return codedString;
    }

    public String deCode(String text, int shiftValue) {
        ArrayList<Character> charList = this.getDecodeCharList(text, shiftValue);
        String decodedString = Utils.charListToString(charList);
        return decodedString;
    }

    private ArrayList<Character> getCodeCharList(String text, int shiftValue) {
        ArrayList<Character> textSymbols = Utils.getArrayListFromArray(text.toCharArray());
        ArrayList<Character> shiftedTextSymbols = new ArrayList<>();
        for (int i = 0; i < textSymbols.size(); i++) {
            int charNumberBeforeShift = Utils.getKey(alphabetMap, '*');
            try {
                charNumberBeforeShift = Utils.getKey(alphabetMap, textSymbols.get(i));
            } catch (NullPointerException e) {
                System.out.println("Replace unsupported char: " + textSymbols.get(i) + " to * symbol");
            }
            int charNumberAfterShift = charNumberBeforeShift + shiftValue <= alphabetMap.size()
                    ? charNumberBeforeShift + shiftValue
                    : (charNumberBeforeShift + shiftValue) - alphabetMap.size();
            shiftedTextSymbols.add(alphabetMap.get(charNumberAfterShift));
        }
        return shiftedTextSymbols;
    }

    private ArrayList<Character> getDecodeCharList(String text, int shiftValue) {
        ArrayList<Character> textSymbols = Utils.getArrayListFromArray(text.toCharArray());
        ArrayList<Character> shiftedTextSymbols = new ArrayList<>();
        for (int i = 0; i < textSymbols.size(); i++) {
            int charNumberBeforeShift = Utils.getKey(alphabetMap, textSymbols.get(i));
            int charNumberAfterShift = charNumberBeforeShift - shiftValue > 0
                    ? charNumberBeforeShift - shiftValue
                    : (charNumberBeforeShift - shiftValue) + alphabetMap.size();
            shiftedTextSymbols.add(alphabetMap.get(charNumberAfterShift));
        }
        return shiftedTextSymbols;
    }

    public int getCryptoKeyByBruteForce(String fileContent) {
        HashSet<Integer> possibleKeysByPunctuationRule1 = new HashSet<>();
        HashSet<Integer> possibleKeysByPunctuationRule2 = new HashSet<>();
        HashSet<Integer> possibleKeysByPunctuationRule3 = new HashSet<>();
        HashSet<Integer> possibleKeysByGrammarRule1 = new HashSet<>();
        for (int i = 0; i < alphabetMap.size(); i++) {
            String decodeRow = this.deCode(fileContent, i);
            if (decodeRow.contains(PUNCTUATION_RULE_1)) {
                possibleKeysByPunctuationRule1.add(i);
            }
            if (decodeRow.contains(PUNCTUATION_RULE_2)) {
                possibleKeysByPunctuationRule2.add(i);
            }
            if (decodeRow.endsWith(".")) {
                possibleKeysByPunctuationRule3.add(i);
            }
            if (Character.isUpperCase(decodeRow.charAt(0))) {
                possibleKeysByGrammarRule1.add(i);
            }
        }
        HashMap<Integer, Integer> statisticMap = new HashMap<>();
        int valueCounter = 0;
        for (int i = 1; i < alphabetMap.size(); i++) {
            valueCounter = possibleKeysByPunctuationRule1.contains(i) ? valueCounter + 1 : valueCounter;
            valueCounter = possibleKeysByPunctuationRule2.contains(i) ? valueCounter + 1 : valueCounter;
            valueCounter = possibleKeysByPunctuationRule3.contains(i) ? valueCounter + 1 : valueCounter;
            valueCounter = possibleKeysByGrammarRule1.contains(i) ? valueCounter + 1 : valueCounter;
            statisticMap.put(i, valueCounter);
            valueCounter = 0;
        }

        Integer maxNumberOfOccurrences = -1;
        for (Integer value : statisticMap.values()) {
            maxNumberOfOccurrences = value > maxNumberOfOccurrences ? value : maxNumberOfOccurrences;
        }
        Integer possibleKey = Utils.getKey(statisticMap, maxNumberOfOccurrences);
        return possibleKey;
    }

    private Map<String, String> getStatisticMap(String codeText, String exampleText) {
        String[] sortedCodeSymbols = Utils.sortedArrayByValue(alphabet.getSymbolsFrequencyMap(codeText));
        String[] sortedExampleSymbols = Utils.sortedArrayByValue(alphabet.getSymbolsFrequencyMap(exampleText));
        Map<String, String> statisticMap = new HashMap<>();
        for (int i = 0; i < sortedCodeSymbols.length; i++) {
            try {
                statisticMap.put(sortedCodeSymbols[i], sortedExampleSymbols[i]);
            } catch (Exception e) {
                System.out.println("Other text does not contain symbol");
            }
        }
        return statisticMap;
    }

    public String decodeByStatisticMap(String codeText, String exampleText) {
        Map<String, String> statisticMap = this.getStatisticMap(codeText, exampleText);
        char[] codeSymbols = codeText.toCharArray();
        String decodeText = "";
        for (Character codeSymbol : codeSymbols) {
            decodeText += statisticMap.get(codeSymbol.toString());
        }
        return decodeText;
    }
}
