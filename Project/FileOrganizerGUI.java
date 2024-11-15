import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileOrganizerGUI extends JFrame {

    public FileOrganizerGUI() {
        setTitle("Organize");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create components
        JButton organizeButton = new JButton("Organize");
        organizeButton.setPreferredSize(new Dimension(120, 120));
        organizeButton.setBackground(Color.ORANGE);
        organizeButton.setForeground(Color.BLACK);
        organizeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        organizeButton.setFocusPainted(false);
        organizeButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        organizeButton.setUI(new RoundedButtonUI()); // Set custom UI for the button

        // Add action listener to the button
        organizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ask the user for the source directory to organize
                JFileChooser sourceChooser = new JFileChooser();
                sourceChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int sourceReturnValue = sourceChooser.showOpenDialog(null);
                if (sourceReturnValue == JFileChooser.APPROVE_OPTION) {
                    File sourceDirectory = sourceChooser.getSelectedFile();
                    String sourceDirPath = sourceDirectory.getAbsolutePath();

                    // Ask the user for the target directory to move the organized files into
                    JFileChooser targetChooser = new JFileChooser();
                    targetChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int targetReturnValue = targetChooser.showSaveDialog(null);
                    if (targetReturnValue == JFileChooser.APPROVE_OPTION) {
                        File targetDirectory = targetChooser.getSelectedFile();
                        String targetDirPath = targetDirectory.getAbsolutePath();

                        try {
                            FileOrganizer.organize(sourceDirPath, targetDirPath);
                            JOptionPane.showMessageDialog(null, "Files organized successfully!");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error organizing files: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Create panel and add components
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50)); // Adjust layout as needed
        panel.setBackground(Color.BLACK); // Set the background color of the panel to black
        panel.add(organizeButton);

        // Add panel to the frame
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileOrganizerGUI();
            }
        });
    }
}