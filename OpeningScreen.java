import java.awt.*;
import javax.swing.*;

/**
 * OpeningScreen class represents the opening screen of the Green Property Exchange application.
 */
public class OpeningScreen extends JPanel{
    
    /** Start button to enter the system. */
    private JButton startButton;

    /**
     * Constructor to initialize the opening screen with title, subtitle, credits, and start button.
     */
    public OpeningScreen() {
        setLayout(new GridBagLayout());         
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Light Green background
        setBackground(new Color(240, 255, 240)); 
    
        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // - Title -
        JLabel titleLabel = new JLabel("<html><center>Green Property Exchange</center></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(34, 139, 34)); // Forest Green
        add(titleLabel, gbc);

        // - Subtitle -
        gbc.gridy++;
        JLabel subtitleLabel = new JLabel("Property Management Simulator", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitleLabel.setForeground(new Color(60, 60, 60));
        add(subtitleLabel, gbc);

        // - Spacing -
        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);

        // - Credits -
        gbc.gridy++;
        JLabel creditsLabel = new JLabel("<html><center>MCO Project<br>Created by: <b>Pete Navato & Alexa Pleyto</b></center></html>", SwingConstants.CENTER);
        creditsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(creditsLabel, gbc);


        // - Start Button -
        gbc.gridy++;
        gbc.insets = new Insets(40, 10, 10, 10); // Spacing above button
        
        startButton = new JButton("ENTER SYSTEM");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(220, 55));

        startButton.setBackground(new Color(34, 139, 34)); // Solid Green
        startButton.setForeground(Color.WHITE);            // White Text
        startButton.setOpaque(true);                       // Ensure background is painted
        startButton.setFocusPainted(false);                // No focus border
        startButton.setBorderPainted(false);               // Flat look;
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(startButton, gbc);
    }

    /**
     * Getter for the start button.
     * 
     * @return JButton The start button.
     */
    public JButton getStartButton() {
        return startButton;
    }
}
