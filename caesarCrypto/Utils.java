package javaProjects.caesarCrypto;

import java.util.*;

public class Utils {
    public static String charListToString(ArrayList<Character> charList) {
        StringBuilder sb = new StringBuilder(charList.size());
        for (Character symbol : charList) {
            sb.append(symbol);
        }
        return sb.toString();
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

    public static boolean isTextSupportLanguage(String fileContent, String languageMarker) {
        ArrayList<Character> languageMarkers = Utils.getArrayListFromArray(languageMarker.toCharArray());
        for (Character symbol : languageMarkers) {
            if (fileContent.contains(symbol.toString())) {
                return true;
            }
        }
        return false;
    }

    public static String getLanguage(String fileByRows) {
        final String UA_ALPHABET_MARKER = "іґІґЇї";
        final String RU_ALPHABET_MARKER = "ыЁёъ";
        final String EN_ALPHABET_MARKER = "fFhJjkKgqQr";
        if (isTextSupportLanguage(fileByRows, UA_ALPHABET_MARKER)) {
            return "UA";
        } else if (isTextSupportLanguage(fileByRows, RU_ALPHABET_MARKER)) {
            return "RU";
        } else if (isTextSupportLanguage(fileByRows, EN_ALPHABET_MARKER)) {
            return "EN";
        }
        return "unsupported language";
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static String[] sortedArrayByValue(Map<String, Integer> map) {
        Map<String, Integer> sortedMap = Utils.sortMapByValue(map);
        String[] sortedKeys = sortedMap.keySet().toArray(new String[0]);
        return sortedKeys;
    }

    public static String removePrefix(String fileName) {
        return fileName.replaceAll("\\[[^\\]]*\\]", "");
    }
}
