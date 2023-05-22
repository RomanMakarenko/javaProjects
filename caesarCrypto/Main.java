package javaProjects.caesarCrypto;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        UI ui = new UI();
        if (isBrutForce(args)) {
            bruteForce(args[1]);
        } else if (isFrequency(args)) {
            frequencyAnalyze(args[1], args[2]);
        } else if (isEncrypt(args)) {
            encrypt(args[1], Integer.parseInt(args[2]));
        } else if (isDecrypt(args)) {
            decrypt(args[1], Integer.parseInt(args[2]));
        }
        ui.startUI();
    }

    public static void bruteForce(String baseFileName) {
        File file = new File(baseFileName);
        UI ui = new UI(file);
        Caesar caesar = ui.getCaesarAnalyzer();
        int cryptoKeyByBruteForce = ui.getCryptoKey();
        FileActions fileActions = new FileActions();
        String newContent = caesar.deCode(fileActions.readFromFile(file.toString()), cryptoKeyByBruteForce);
        String prefix = Operations.BRUTE_FORCE.getPrefix();
        ui.showLanguageMessage();
        ui.showFileContent();
        ui.showPossibleKeyMessage();
        ui.showText(newContent);
        String newFullFileName = Utils.getNewFullFileName(baseFileName, prefix);
        fileActions.writeToFile(newFullFileName, newContent);
    }

    public static void frequencyAnalyze(String baseFileName, String secondFileName) {
        File file = new File(baseFileName);
        File secondFile = new File(secondFileName);
        UI ui = new UI(file, secondFile);
        Caesar caesar = ui.getCaesarAnalyzer();
        String newContent = caesar.decodeByStatisticMap(ui.getBaseFileContent(), ui.getStatisticFileContent());
        String prefix = Operations.FREQUENCY.getPrefix();
        ui.showFileContent();
        ui.showText(newContent);
        String newFullFileName = Utils.getNewFullFileName(baseFileName, prefix);
        FileActions fileActions = new FileActions();
        fileActions.writeToFile(newFullFileName, newContent);
    }

    public static void encrypt(String baseFileName, int key) {
        File file = new File(baseFileName);
        UI ui = new UI(file, key);
        Caesar caesar = ui.getCaesarAnalyzer();
        FileActions fileActions = new FileActions();
        String newContent = caesar.code(fileActions.readFromFile(file.toString()), key);
        String prefix = Operations.ENCRYPT.getPrefix();
        ui.showLanguageMessage();
        ui.showFileContent();
        ui.showText(newContent);
        String newFullFileName = Utils.getNewFullFileName(baseFileName, prefix);
        fileActions.writeToFile(newFullFileName, newContent);
    }

    public static void decrypt(String baseFileName, int key) {
        File file = new File(baseFileName);
        UI ui = new UI(file, key);
        Caesar caesar = ui.getCaesarAnalyzer();
        FileActions fileActions = new FileActions();
        String newContent = caesar.deCode(fileActions.readFromFile(file.toString()), key);
        String prefix = Operations.DECRYPT.getPrefix();
        ui.showLanguageMessage();
        ui.showFileContent();
        ui.showText(newContent);
        String newFullFileName = Utils.getNewFullFileName(baseFileName, prefix);
        fileActions.writeToFile(newFullFileName, newContent);
    }

    public static boolean isBrutForce(String[] args) {
        return args.length == 2 && Operations.BRUTE_FORCE.getOperation().equals(args[0]);
    }

    public static boolean isFrequency(String[] args) {
        return args.length == 3 && Operations.FREQUENCY.getOperation().equals(args[0]);
    }

    public static boolean isEncrypt(String[] args) {
        return args.length == 3 && Operations.ENCRYPT.getOperation().equals(args[0]);
    }

    public static boolean isDecrypt(String[] args) {
        return args.length == 3 && Operations.DECRYPT.getOperation().equals(args[0]);
    }
}
