package javaProjects.caesarCrypto;

import java.io.*;
import java.util.ArrayList;

public class FileActions {
    public static String readFromFile(String fileName) {
        String fileContent = new String();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                fileContent += bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return fileContent;
    }

    public static void writeToFile(String fileName, String content) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                PrintWriter printWriter = new PrintWriter(fileWriter);
        ) {
            printWriter.println(content);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
