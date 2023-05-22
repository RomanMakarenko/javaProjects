package javaProjects.caesarCrypto;

public enum Operations {
    ENCRYPT("ENCRYPT"),
    DECRYPT("DECRYPT"),
    BRUTE_FORCE("BRUTE_FORCE"),
    FREQUENCY("FREQUENCY");

    private final String prefix;

    Operations(String prefix) {
        this.prefix = prefix;
    }

    public String getOperation() {
        return prefix;
    }
}
