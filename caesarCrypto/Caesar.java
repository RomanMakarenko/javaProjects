package javaProjects.caesarCrypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Caesar {
    public static final String PUNCTUATION_RULE_1 = ", ";
    public static final String PUNCTUATION_RULE_2 = ". ";

    public static String code(String text, int shiftValue, Map<Integer, Character> alphabetMap) {
        ArrayList<Character> charList = Caesar.getCodeCharList(text, shiftValue, alphabetMap);
        String codedString = Utils.charListToString(charList);
        return codedString;
    }

    public static String deCode(String text, int shiftValue, Map<Integer, Character> alphabetMap) {
        ArrayList<Character> charList = Caesar.getDecodeCharList(text, shiftValue, alphabetMap);
        String decodedString = Utils.charListToString(charList);
        return decodedString;
    }

    private static ArrayList<Character> getCodeCharList(String text, int shiftValue, Map<Integer, Character> alphabetMap) {
        ArrayList<Character> textSymbols = Utils.getArrayListFromArray(text.toCharArray());
        ArrayList<Character> shiftedTextSymbols = new ArrayList<>();
        for (int i = 0; i < textSymbols.size(); i++) {
            int charNumberBeforeShift = Utils.getKey(alphabetMap, ' ');
            try {
                charNumberBeforeShift = Utils.getKey(alphabetMap, textSymbols.get(i));
            } catch (NullPointerException e) {
                System.out.println("Replace unsupported char: " + textSymbols.get(i) + " to space symbol");
            }
            int charNumberAfterShift = charNumberBeforeShift + shiftValue <= alphabetMap.size()
                    ? charNumberBeforeShift + shiftValue
                    : (charNumberBeforeShift + shiftValue) - alphabetMap.size();
            shiftedTextSymbols.add(alphabetMap.get(charNumberAfterShift));
        }
        return shiftedTextSymbols;
    }

    private static ArrayList<Character> getDecodeCharList(String text, int shiftValue, Map<Integer, Character> alphabetMap) {
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

    public static HashSet<Integer> getListOfPossibleByRulesKeys(ArrayList<String> fileByRows, Map<Integer, Character> alphabetMap, String rule) {
        HashSet<Integer> listOfPossibleKeys = new HashSet<>();
        for (String row : fileByRows) {
            for (int i = 0; i < alphabetMap.size(); i++) {
                String decodeRow = deCode(row, i, alphabetMap);
                if (decodeRow.contains(rule)) {
                    listOfPossibleKeys.add(i);
                }
            }
        }
        return listOfPossibleKeys;
    }

    public static int getCryptoKeyByBruteForce(String fileByRows, Map<Integer, Character> alphabetMap) {
        HashSet<Integer> possibleKeysByPunctuationRule1 = new HashSet<>();
        HashSet<Integer> possibleKeysByPunctuationRule2 = new HashSet<>();
        HashSet<Integer> possibleKeysByPunctuationRule3 = new HashSet<>();
        HashSet<Integer> possibleKeysByGrammarRule1 = new HashSet<>();
        for (int i = 0; i < alphabetMap.size(); i++) {
            String decodeRow = deCode(fileByRows, i, alphabetMap);
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



    private static Map<String, String> getStatisticMap(String codeText, String exampleText, Alphabet alphabet) {
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

    public static String decodeByStatisticMap(String codeText, String exampleText, Alphabet alphabet) {
        Map<String, String> statisticMap = getStatisticMap(codeText, exampleText, alphabet);
        char[] codeSymbols = codeText.toCharArray();
        String decodeText = "";
        for (Character codeSymbol : codeSymbols) {
            decodeText += statisticMap.get(codeSymbol.toString());
        }
        return decodeText;
    }
}
