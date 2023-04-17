package javaProjects.caesarCrypto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class GUI {
    private static final int WIDTH = 750;
    private static final int HEIGHT = 250;
    private static final int SYMBOLS_IN_ROW = 50;
    private static final String[] SUPPORTED_ALPHABETS = {"UA", "RU", "EN"};
    private static ArrayList<String> fileByRows;
    private static Alphabet alphabet;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel();
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jPanel);
        jPanel.setLayout(null);

        // Source file elements
        JLabel sourceLabel = new JLabel("Path to source file:");
        sourceLabel.setBounds(10, 25, 130, 25);
        jPanel.add(sourceLabel);

        // Button for choose file
        JButton selectFileButton = new JButton("Select file");
        selectFileButton.setBounds(150, 15, 260, 50);
        jPanel.add(selectFileButton);
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int option = fileChooser.showOpenDialog(jFrame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    fileByRows = FileActions.readFromFileByRows(file.toString());
                    String fileContent = "";
                    for (String fileRow : fileByRows) {
                        fileContent = fileContent + fileRow;
                    }
                    JTextArea textArea = new JTextArea(fileContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                } else {
                    JOptionPane.showMessageDialog(null, "Open command canceled");
                }
            }
        });

        // Cryptographic label
        JLabel codeLabel = new JLabel("Cryptographic key:");
        codeLabel.setBounds(10, 90, 130, 25);
        jPanel.add(codeLabel);

        // Spinner
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setBounds(150, 90, 260, 25);
        jPanel.add(spinner);

        // Language label
        JLabel languageLabel = new JLabel("Select language:");
        languageLabel.setBounds(10, 155, 130, 25);
        jPanel.add(languageLabel);

        // Language comboBox
        JComboBox<String> comboBox = new JComboBox<>(SUPPORTED_ALPHABETS);
        comboBox.setBounds(150, 155, 260, 25);
        jPanel.add(comboBox);

        // ADD code btn
        JButton codeButton = new JButton("CODE");
        codeButton.setBounds(420, 15, 100, 165);
        codeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String languageValue = (String) comboBox.getSelectedItem();
                int cryptoKey = (int) spinner.getValue();
                alphabet = new Alphabet(languageValue);
                ArrayList<String> codeFileByRows = new ArrayList<>();
                String codeContent = "";
                try {
                    for (String row : fileByRows) {
                        String codeRow = Caesar.code(row, cryptoKey, alphabet.getAlphabetMap());
                        codeFileByRows.add(codeRow);
                        codeContent = codeContent + codeRow;
                    }
                    JTextArea textArea = new JTextArea(codeContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                    FileActions.writeToFileByRows("code.txt", codeFileByRows);
                } catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(null, "Text contains not supported characters");
                }
            }
        });
        jPanel.add(codeButton);

        // ADD decode btn
        JButton deCodeButton = new JButton("DECODE");
        deCodeButton.setBounds(530, 15, 100, 165);
        deCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String languageValue = (String) comboBox.getSelectedItem();
                int cryptoKey = (int) spinner.getValue();
                alphabet = new Alphabet(languageValue);
                ArrayList<String> decodeFileByRows = new ArrayList<>();
                String decodeContent = "";
                try {
                    for (String row : fileByRows) {
                        String decodeRow = Caesar.deCode(row, cryptoKey, alphabet.getAlphabetMap());
                        decodeFileByRows.add(decodeRow);
                        decodeContent = decodeContent + decodeRow;
                    }
                    JTextArea textArea = new JTextArea(decodeContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                    FileActions.writeToFileByRows("decode.txt", decodeFileByRows);
                } catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(null, "Text contains not supported characters");
                }
            }
        });
        jPanel.add(deCodeButton);

        // ADD bruteforce btn
        JButton bruteForceButton = new JButton("BRUTE FORCE");
        bruteForceButton.setBounds(640, 15, 100, 165);
        bruteForceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String languageValue = "UA";
                try {
                    languageValue = Utils.getLanguage(fileByRows);
                    JOptionPane.showMessageDialog(null, "Possible text language: " + languageValue);
                } catch (NullPointerException exc) {
                    JOptionPane.showMessageDialog(null, "File was not selected");
                }
                alphabet = new Alphabet(languageValue);
                ArrayList<String> decodeFileByRows = new ArrayList<>();
                String decodeContent = "";
                try {
                    int cryptoKey = Caesar.getCryptoKeyByBruteForce(fileByRows, alphabet.getAlphabetMap());
                    JOptionPane.showMessageDialog(null, "Possible crypto key: " + cryptoKey);
                    for (String row : fileByRows) {
                        String decodeRow = Caesar.deCode(row, cryptoKey, alphabet.getAlphabetMap());
                        decodeFileByRows.add(decodeRow);
                        decodeContent = decodeContent + decodeRow;
                    }
                    JTextArea textArea = new JTextArea(decodeContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                    FileActions.writeToFileByRows("decode.txt", decodeFileByRows);
                } catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(null, "Text contains not supported characters");
                }
            }
        });
        jPanel.add(bruteForceButton);

        jFrame.setVisible(true);
    }
}
