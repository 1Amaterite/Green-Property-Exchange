import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * ManagePropertyView represents the panel for managing property settings.
 * It allows users to edit property details or delete the listing.
 */
public class ManagePropertyView extends JPanel {
    
    // Components

    /** TextField for editing the property name. */
    private JTextField editNameTextField;
    /** TextField for editing the property price. */
    private JTextField priceTextField;
    /** Label for displaying instructions or warnings. */
    private JLabel instructionLabel;
    /** Button to save changes. */
    private JButton saveButton;
    /** Button to delete the property. */
    private JButton deleteButton;
    /** Button to go back to the previous screen. */
    private JButton backButton;

    /**
     * Constructor to initialize the ManagePropertyView.
     */
    public ManagePropertyView() {
        // Layout and Styling
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 255, 240));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // This will help position components
        GridBagConstraints gbc = new GridBagConstraints();
        // Padding
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // - Title -
        gbc.gridy = 0;
        JLabel title = new JLabel("Manage Property Settings", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(34, 139, 34));
        add(title, gbc);

        // - Subtitle -
        gbc.gridy++;
        JLabel subtitle = new JLabel("Update details or remove this listing.", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        add(subtitle, gbc);
        
        // Spacer
        gbc.gridy++;
        add(Box.createVerticalStrut(20), gbc);

        // - Edit Name -
        gbc.gridy++;
        JLabel nameLabel = new JLabel("Edit Property Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameLabel.setForeground(new Color(60, 60, 60));
        add(nameLabel, gbc);

        // Edit Name TextField
        gbc.gridy++;
        editNameTextField = createStyledTextField();
        add(editNameTextField, gbc);

        // - Edit Price -
        gbc.gridy++;
        JLabel priceLabel = new JLabel("Edit Base Price (PHP):");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        priceLabel.setForeground(new Color(60, 60, 60));
        add(priceLabel, gbc);

        // Edit Price TextField
        gbc.gridy++;
        priceTextField = createStyledTextField();
        add(priceTextField, gbc);

        // - Instruction Label -
        gbc.gridy++;
        instructionLabel = new JLabel(" ");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionLabel.setForeground(Color.RED);
        add(instructionLabel, gbc);

        // - Buttons -
        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10); 
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        buttonPanel.setOpaque(false);

        // - Back Button (Gray) -
        backButton = new JButton("Back");
        styleButton(backButton, new Color(200, 200, 200), Color.BLACK);

        // - Delete Button (Red)- 
        deleteButton = new JButton("Delete");
        styleButton(deleteButton, new Color(220, 53, 69), Color.WHITE);

        // - Save Button (Green) -
        saveButton = new JButton("Save");
        styleButton(saveButton, new Color(34, 139, 34), Color.WHITE);

        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, gbc);
    }

    // - Helpers -

    /**
     * Creates a styled JTextField with custom fonts and borders.
     * 
     * @return A styled JTextField.
     */
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1), 
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return textField;
    }

    /**
     * Styles a JButton with custom fonts, colors, and padding.
     * 
     * @param button          The JButton to be styled.
     * @param backgroundColor The background color for the button.
     * @param foregroundColor The text color for the button.
     */
    private void styleButton(JButton button, Color backgroundColor, Color foregroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 40));
    }

    // - Setters -

    /**
     * Sets the current property details in the text fields.
     * 
     * @param name            The current name of the property.
     * @param price           The current base price of the property.
     * @param hasReservations Indicates if there are existing reservations.
     */
    public void setDetails(String name, double price, boolean hasReservations) {
        editNameTextField.setText(name);
        priceTextField.setText(String.format("%.2f", price));

        if (hasReservations) {
            // If there are reservations, disable price editing
            priceTextField.setEnabled(false);
            priceTextField.setBackground(new Color(245, 245, 245));
            priceTextField.setToolTipText("Can't change price while reservations exist.");
            instructionLabel.setText("* Locked due to active reservations");
        } else {
            // If no reservations, enable price editing
            priceTextField.setEnabled(true);
            priceTextField.setBackground(Color.WHITE);
            priceTextField.setToolTipText(null);
            instructionLabel.setText(" ");
        }
    }

    // - Getters -

    /**
     * Gets the edited property name from the text field.
     * 
     * @return The edited property name as a String.
     */
    public String getEditedName() { 
        // Trim whitespace
        return editNameTextField.getText().trim(); 
    }
    
    /**
     * Gets the edited property price from the text field.
     * 
     * @return The edited property price as a String.
     */
    public String getEditedPrice() { 
        // Trim whitespace
        return priceTextField.getText().trim(); 
    }

    /**
     * Gets the Save button.
     * 
     * @return The JButton for saving changes.
     */
    public JButton getSaveButton() { 
        return saveButton; 
    }
    
    /**
     * Gets the Delete button.
     * 
     * @return The JButton for deleting the property.
     */
    public JButton getDeleteButton() { 
        return deleteButton; 
    }

    /**
     * Gets the Back button.
     * 
     * @return The JButton for going back.
     */
    public JButton getBackButton() { 
        return backButton; 
    }
}