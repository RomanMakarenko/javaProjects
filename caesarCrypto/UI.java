package javaProjects.caesarCrypto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UI {
    private static final int WIDTH = 720;
    private static final int HEIGHT = 260;
    private static final int SYMBOLS_IN_ROW = 50;
    private final JFrame jFrame = new JFrame();
    private final JPanel jPanel = new JPanel();

    private String statisticFileContent;
    private File baseFile;
    private int cryptoKey;
    private String fileContent;
    private String languageValue;
    private Caesar caesar;

    public UI () {}

    public UI (File baseFile) {
        this.baseFile = baseFile;
        initialize();
        this.cryptoKey = caesar.getCryptoKeyByBruteForce(fileContent);
    }

    public UI (File baseFile, File frequencyFile) {
        this.baseFile = baseFile;
        initialize();
        this.statisticFileContent = this.getFileContent(frequencyFile);
    }

    public UI (File baseFile, int cryptoKey) {
        this.baseFile = baseFile;
        this.cryptoKey = cryptoKey;
        initialize();
    }

    private void initialize() {
        this.fileContent = this.getFileContent(baseFile);
        this.languageValue = Utils.getLanguage(fileContent);
        Alphabet alphabet = new Alphabet(languageValue);
        this.caesar = new Caesar(alphabet);
    }

    public Caesar getCaesarAnalyzer() {
        return this.caesar;
    }

    public int getCryptoKey() {
        return this.cryptoKey;
    }

    public String getBaseFileContent() {
        return this.fileContent;
    }

    public String getStatisticFileContent() {
        return this.statisticFileContent;
    }

    public String getFileContent(File file) {
        return FileActions.readFromFile(file.toString());
    }

    public void showLanguageMessage() {
        JOptionPane.showMessageDialog(null, "Possible text language: " + this.languageValue);
    }

    public void showPossibleKeyMessage() {
        JOptionPane.showMessageDialog(null, "Possible crypto key: " + this.cryptoKey);
    }

    public void showFileContent() {
        this.showText(fileContent);
    }

    public void showText(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setColumns(SYMBOLS_IN_ROW);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane);
    }

    public void startUI() {
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
                    baseFile = fileChooser.getSelectedFile();
                    initialize();
                    showText(fileContent);
                    showLanguageMessage();
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
        JButton codeButton = new JButton(Operations.ENCRYPT.getOperation());
        codeButton.setBounds(5, 130, 200, 90);
        codeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cryptoKey = (int) spinner.getValue();
                try {
                    String encryptedContent = caesar.code(fileContent, cryptoKey);
                    showText(encryptedContent);
                    String newFullFileName = Utils.getNewFullFileName(baseFile.toString(), Operations.ENCRYPT.getOperation());
                    FileActions.writeToFile(newFullFileName, encryptedContent);
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
        JButton deCodeButton = new JButton(Operations.DECRYPT.getOperation());
        deCodeButton.setBounds(210, 130, 200, 90);
        deCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cryptoKey = (int) spinner.getValue();
                try {
                    String decryptedContent = caesar.deCode(fileContent, cryptoKey);
                    showText(decryptedContent);
                    String newFullFileName = Utils.getNewFullFileName(baseFile.toString(), Operations.DECRYPT.getOperation());
                    FileActions.writeToFile(newFullFileName, decryptedContent);
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
        JButton bruteForceButton = new JButton(Operations.BRUTE_FORCE.getOperation());
        bruteForceButton.setBounds(420, 15, 120, 205);
        bruteForceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cryptoKey = caesar.getCryptoKeyByBruteForce(fileContent);
                    showPossibleKeyMessage();
                    String decryptedContent = caesar.deCode(fileContent, cryptoKey);
                    showText(decryptedContent);
                    String newFullFileName = Utils.getNewFullFileName(baseFile.toString(), Operations.BRUTE_FORCE.getOperation());
                    FileActions.writeToFile(newFullFileName, decryptedContent);
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
                    showText(statisticFileContent);
                } else {
                    JOptionPane.showMessageDialog(null, "Open command canceled");
                }
            }
        });
        jPanel.add(secondFileButton);

        // Button for statistic analyze
        JButton statisticAnalyzeButton = new JButton(Operations.FREQUENCY.getOperation());
        statisticAnalyzeButton.setBounds(560, 120, 150, 100);
        statisticAnalyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String decryptedContent = caesar.decodeByStatisticMap(fileContent, statisticFileContent);
                    String newFullFileName = Utils.getNewFullFileName(baseFile.toString(), Operations.FREQUENCY.getOperation());
                    FileActions.writeToFile(newFullFileName, decryptedContent);
                    showText(decryptedContent);
                } catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(null, "Not both files opened");
                }
            }
        });
        jPanel.add(statisticAnalyzeButton);

        jFrame.setVisible(true);
    }
}
