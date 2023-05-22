package javaProjects.caesarCrypto;

public enum Operations {
    ENCRYPT("ENCRYPT"),
    DECRYPT("DECRYPT"),
    BRUTE_FORCE("BRUTE_FORCE"),
    FREQUENCY("FREQUENCY");

    private final String operation;

    Operations(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public String getPrefix() {
        return operation + "ED";
    }
}
