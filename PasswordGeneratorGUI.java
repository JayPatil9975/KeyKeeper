import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class PasswordGeneratorGUI extends JFrame {
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";

    private JTextField lengthField;
    private JTextArea passwordArea;

    public PasswordGeneratorGUI() {
        setTitle("Password Generator");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // mainPanel.setLayout(new GridLayout(5, 3, 10, 10));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JLabel instructionsLabel = new JLabel("Password Manager:");
        instructionsLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        instructionsLabel.setForeground(Color.BLUE);

        JLabel lengthLabel = new JLabel("Password Length:");
        lengthLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        lengthLabel.setForeground(Color.BLACK);

        lengthField = new JTextField("12");
        lengthField.setFont(new Font("Calibri", Font.PLAIN, 16));
        lengthField.setBackground(Color.WHITE);

        JButton generateButton = new JButton("Generate Password");
        generateButton.setFont(new Font("Calibri", Font.PLAIN, 16));
        generateButton.setBackground(Color.GREEN);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });

        JButton saveButton = new JButton("Save to File");
        saveButton.setFont(new Font("Calibri", Font.PLAIN, 16));
        saveButton.setBackground(Color.ORANGE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        JLabel passwordLabel = new JLabel("Generated Password:");
        passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.BLACK);

        passwordArea = new JTextArea();
        passwordArea.setEditable(false);
        passwordArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        passwordArea.setBackground(Color.WHITE);

        mainPanel.add(instructionsLabel);
        mainPanel.add(new JLabel());
        mainPanel.add(lengthLabel);
        mainPanel.add(lengthField);
        mainPanel.add(generateButton);
        mainPanel.add(saveButton);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordArea);

        add(mainPanel);

        setVisible(true);
    }

    private void generatePassword() {
        try {
            int passwordLength = Integer.parseInt(lengthField.getText());

            SecureRandom random = new SecureRandom();
            StringBuilder password = new StringBuilder();

            String allCharacters = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;

            for (int i = 0; i < passwordLength; i++) {
                int randomIndex = random.nextInt(allCharacters.length());
                password.append(allCharacters.charAt(randomIndex));
            }

            passwordArea.setText(password.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for password length.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Password to File");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(passwordArea.getText());
                JOptionPane.showMessageDialog(this, "Password saved to: " + fileToSave.getAbsolutePath(), "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving password to file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordGeneratorGUI();
            }
        });
    }
}
