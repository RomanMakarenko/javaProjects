package javaProjects.caesarCrypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Alphabet {
    private final String UA_ALPHABET = "АаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщьЮюЯя";
    private final String RU_ALPHABET = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщъЫыьЭэЮюЯя";
    private final String EN_ALPHABET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    private final String SPECIAL_SYMBOLS = " .,”:-!?`'()—\"";
    private final String NUMBERS = "0123456789";
    private String alphabet;

    public Alphabet(String language) {
        this.alphabet = switch (language) {
            case "UA" -> UA_ALPHABET;
            case "RU" -> RU_ALPHABET;
            case "EN" -> EN_ALPHABET;
            default -> UA_ALPHABET;
        };
    }

    public String getAlphabetWithSpecialSymbols() {
        return this.alphabet + this.SPECIAL_SYMBOLS + this.NUMBERS;
    }

    public Map<Integer, Character> getAlphabetMap() {
        ArrayList<Character> alphabetSymbols = Utils.getArrayListFromArray(this.alphabet.toCharArray());
        ArrayList<Character> specialSymbols = Utils.getArrayListFromArray(SPECIAL_SYMBOLS.toCharArray());
        ArrayList<Character> numbers = Utils.getArrayListFromArray(NUMBERS.toCharArray());
        alphabetSymbols.addAll(specialSymbols);
        alphabetSymbols.addAll(numbers);
        Map<Integer, Character> alphabetMap = new HashMap<>();
        for (int i = 0; i < alphabetSymbols.size(); i++) {
            alphabetMap.put(i + 1, alphabetSymbols.get(i));
        }
        return alphabetMap;
    }

    public Map<String, Integer> getDefaultSymbolsFrequencyMap() {
        Map<String, Integer> symbolsFrequencyMap = new HashMap<>();
        char[] symbols = this.getAlphabetWithSpecialSymbols().toCharArray();
        for (Character symbol: symbols) {
            symbolsFrequencyMap.put(symbol.toString(), 0);
        }
        return symbolsFrequencyMap;
    }

    public Map<String, Integer> getSymbolsFrequencyMap(String text) {
        Map<String, Integer> symbolsFrequencyMap = this.getDefaultSymbolsFrequencyMap();
        ArrayList<Character> textSymbols = Utils.getArrayListFromArray(text.toCharArray());
        for (Character symbol : textSymbols) {
            try {
                int value = symbolsFrequencyMap.get(symbol.toString());
                symbolsFrequencyMap.put(symbol.toString(), value + 1);
            } catch (NullPointerException e) {
                System.out.println("Replace unsupported char: " + symbol + " to space symbol");
                int value = symbolsFrequencyMap.get(" ");
                symbolsFrequencyMap.put(symbol.toString(), value + 1);
            }
        }
        return symbolsFrequencyMap;
    }
}
