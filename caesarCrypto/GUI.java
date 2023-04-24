package javaProjects.caesarCrypto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class GUI {
    private static final int WIDTH = 720;
    private static final int HEIGHT = 260;
    private static final int SYMBOLS_IN_ROW = 50;
    private static String fileContent;
    private static String codeContent;
    private static String codeFileName = "code.txt";
    private static String decodeContent;
    private static String decodeFileName = "decode.txt";
    private static String statisticFileContent;
    private static Alphabet alphabet;
    private static String languageValue;
    private static Caesar caesar;

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
                    fileContent = FileActions.readFromFile(file.toString());
                    JTextArea textArea = new JTextArea(fileContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                    languageValue = Utils.getLanguage(fileContent);
                    alphabet = new Alphabet(languageValue);
                    caesar = new Caesar(alphabet);
                    JOptionPane.showMessageDialog(null, "Possible text language: " + languageValue);
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


        // ADD code btn
        JButton codeButton = new JButton("CODE");
        codeButton.setBounds(5, 130, 200, 90);
        codeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cryptoKey = (int) spinner.getValue();
                try {
                    codeContent = caesar.code(fileContent, cryptoKey);
                    JTextArea textArea = new JTextArea(codeContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                    FileActions.writeToFile(codeFileName, codeContent);
                } catch (NullPointerException exception) {
                    if (fileContent == null) {
                        JOptionPane.showMessageDialog(null, "Source file was not chosen");
                    } else {
                        JOptionPane.showMessageDialog(null, "Text contains not supported characters");
                    }
                }
            }
        });
        jPanel.add(codeButton);

        // ADD decode btn
        JButton deCodeButton = new JButton("DECODE");
        deCodeButton.setBounds(210, 130, 200, 90);
        deCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cryptoKey = (int) spinner.getValue();
                try {
                    decodeContent = caesar.deCode(fileContent, cryptoKey);
                    JTextArea textArea = new JTextArea(decodeContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                    FileActions.writeToFile(decodeFileName, decodeContent);
                } catch (NullPointerException exception) {
                    if (fileContent == null) {
                        JOptionPane.showMessageDialog(null, "Source file was not chosen");
                    } else {
                        JOptionPane.showMessageDialog(null, "Text contains not supported characters");
                    }
                }
            }
        });
        jPanel.add(deCodeButton);

        // ADD bruteforce btn
        JButton bruteForceButton = new JButton("BRUTE FORCE");
        bruteForceButton.setBounds(420, 15, 120, 205);
        bruteForceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int cryptoKey = caesar.getCryptoKeyByBruteForce(fileContent);
                    JOptionPane.showMessageDialog(null, "Possible crypto key: " + cryptoKey);
                    decodeContent = caesar.deCode(fileContent, cryptoKey);
                    JTextArea textArea = new JTextArea(decodeContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                    FileActions.writeToFile(decodeFileName, decodeContent);
                } catch (NullPointerException exception) {
                    if (fileContent == null) {
                        JOptionPane.showMessageDialog(null, "Source file was not chosen");
                    } else {
                        JOptionPane.showMessageDialog(null, "Text contains not supported characters");
                    }
                }
            }
        });
        jPanel.add(bruteForceButton);

        // Button for add second file for statistic analyze
        JButton secondFileButton = new JButton("Select second file");
        secondFileButton.setBounds(560, 15, 150, 100);
        secondFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int option = fileChooser.showOpenDialog(jFrame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    statisticFileContent = FileActions.readFromFile(file.toString());
                    JTextArea textArea = new JTextArea(statisticFileContent);
                    textArea.setColumns(SYMBOLS_IN_ROW);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane);
                } else {
                    JOptionPane.showMessageDialog(null, "Open command canceled");
                }
            }
        });
        jPanel.add(secondFileButton);

        // Button for statistic analyze
        JButton statisticAnalyzeButton = new JButton("STATISTIC ANALYZER");
        statisticAnalyzeButton.setBounds(560, 120, 150, 100);
        statisticAnalyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    decodeContent = caesar.decodeByStatisticMap(fileContent, statisticFileContent);
                } catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(null, "Not both files opened");
                }
                JTextArea textArea = new JTextArea(decodeContent);
                textArea.setColumns(SYMBOLS_IN_ROW);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(null, scrollPane);
            }
        });
        jPanel.add(statisticAnalyzeButton);

        jFrame.setVisible(true);
    }
}
