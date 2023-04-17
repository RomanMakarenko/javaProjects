package javaProjects.caesarCrypto;

import java.io.*;
import java.util.ArrayList;

public class FileActions {
    public static ArrayList<String> readFromFileByRows(String fileName) {
        ArrayList<String> fileRows = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                fileRows.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return fileRows;
    }

    public static void writeToFileByRows(String fileName, ArrayList<String> arrayList) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                PrintWriter printWriter = new PrintWriter(fileWriter);
        ) {
            for (String row : arrayList) {
                printWriter.println(row);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
