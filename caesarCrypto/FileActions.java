package javaProjects.caesarCrypto;

import java.io.*;

public class FileActions {
    public String readFromFile(String fileName) {
        String fileContent = new String();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                fileContent += bufferedReader.readLine() + "\n";
            }
        } catch (IOException e) {
            UI ui = new UI();
            ui.showText("There is problem with which you try to read: " + fileName);
        }
        return fileContent;
    }

    public void writeToFile(String fileName, String content) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                PrintWriter printWriter = new PrintWriter(fileWriter);
        ) {
            printWriter.println(content);
        } catch (IOException e) {
            UI ui = new UI();
            ui.showText("There is problem with which you try to write: " + fileName);
        }
    }
}
