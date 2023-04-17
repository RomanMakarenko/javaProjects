package javaProjects.caesarCrypto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Alphabet {
    private final String UA_ALPHABET = "АаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщьЮюЯя";
    private final String RU_ALPHABET = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщъЫыьЭэЮюЯя";
    private final String EN_ALPHABET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    private final String SPECIAL_SYMBOLS = " .,”:-!?`'\"—()";
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

    public String getAlphabet() {
        return this.alphabet;
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
}
