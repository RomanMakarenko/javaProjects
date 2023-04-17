package javaProjects.caesarCrypto;

import java.util.ArrayList;
import java.util.Map;

public class Utils {
    public static String charListToString(ArrayList<Character> charList) {
        String text = "";
        for (Character symbol : charList) {
            text += symbol.toString();
        }
        return text;
    }

    public static ArrayList<Character> getArrayListFromArray(char[] array) {
        ArrayList<Character> arrayList = new ArrayList<>();
        for (Character element : array) {
            arrayList.add(element);
        }
        return arrayList;
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(entry -> value.equals(entry.getValue()))
                .findFirst().map(Map.Entry::getKey)
                .orElse(null);
    }

    public static boolean isTextSupportLanguage(ArrayList<String> fileByRows, String languageMarker) {
        for (String row : fileByRows) {
            ArrayList<Character> languageMarkers = Utils.getArrayListFromArray(languageMarker.toCharArray());
            for (Character symbol : languageMarkers) {
                if (row.contains(symbol.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getLanguage(ArrayList<String> fileByRows) {
        final String UA_ALPHABET_MARKER = "іґІґ";
        final String RU_ALPHABET_MARKER = "ыЁёъ";
        final String EN_ALPHABET_MARKER = "fFhJjkKgqQr";
        if (isTextSupportLanguage(fileByRows, UA_ALPHABET_MARKER)) {
            return "UA";
        } else if (isTextSupportLanguage(fileByRows, RU_ALPHABET_MARKER)) {
            return "RU";
        } else if (isTextSupportLanguage(fileByRows, EN_ALPHABET_MARKER)) {
            return "EN";
        }
        return "UA";
    }
}
