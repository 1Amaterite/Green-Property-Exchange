import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * View for creating a new property listing.
 * This class provides a form for entering property details
 * and buttons to submit or cancel the creation process.
 */
public class CreatePropertyView extends JPanel {
    /**
     * Input field for the property name.
     */
    private JTextField propertyNameTextField;
    /**
     * Dropdown for selecting the property type.
     */
    private JComboBox<String> propertyTypeComboBox;
    /**
     * Button to submit the new property listing.
     */
    private JButton submitButton;
    /**
     * Button to cancel the property creation process.
     */
    private JButton cancelButton;

    /**
     * Constructor to initialize the CreatePropertyView UI components.
     * Sets up the layout, labels, input fields, and buttons with styling.
     */
    public CreatePropertyView() {
        // Layout is 20px padding
        setLayout(new BorderLayout(20, 20)); 
        // Light green background
        setBackground(new Color(240, 255, 240));
        // Border padding
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // - Title -
        JLabel title = new JLabel("Add New Property Listing", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        // Forest green color
        title.setForeground(new Color(34, 139, 34)); 
        add(title, BorderLayout.NORTH);

        // - Form Panel (Center) -
        JPanel formPanel = new JPanel(new GridBagLayout());
        // Transparent to show background
        formPanel.setOpaque(false); 
        
        // - Layout constraints -
        GridBagConstraints gbc = new GridBagConstraints();
        // Insets for spacing
        gbc.insets = new Insets(10, 10, 10, 10);
        // Fill horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // - Instruction Subtitle -
        JLabel subtitle = new JLabel("Enter the details below to create a new property.", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        formPanel.add(subtitle, gbc);

        // Add vertical space after subtitle
        gbc.gridy++;
        // 20px vertical space
        formPanel.add(Box.createVerticalStrut(20), gbc); 

        // - Name Label -
        gbc.gridy++;
        JLabel nameLabel = new JLabel("Property Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        // Dark gray color
        nameLabel.setForeground(new Color(50, 50, 50));
        formPanel.add(nameLabel, gbc);

        // - Name Input -
        gbc.gridy++;
        propertyNameTextField = new JTextField(20); 
        propertyNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        // Light border with padding
        propertyNameTextField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1), 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        formPanel.add(propertyNameTextField, gbc);

        // - Type Label -
        gbc.gridy++;
        // Insets for spacing
        gbc.insets = new Insets(20, 10, 10, 10);
        JLabel typeLabel = new JLabel("Property Type:");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        // Dark gray color
        typeLabel.setForeground(new Color(50, 50, 50));
        formPanel.add(typeLabel, gbc);

        // - Types -
        gbc.gridy++;
        // Insets for spacing
        gbc.insets = new Insets(10, 10, 10, 10);
        String[] types = {
            "Eco-Apartment", 
            "Sustainable House", 
            "Green Resort", 
            "Eco-Glamping"
        };
        // This JComboBox will hold the property types 
        propertyTypeComboBox = new JComboBox<>(types);
        propertyTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        propertyTypeComboBox.setBackground(Color.WHITE);
        propertyTypeComboBox.setPreferredSize(new Dimension(300, 40));
        formPanel.add(propertyTypeComboBox, gbc);
        add(formPanel, BorderLayout.CENTER);

        // -- Buttons Panel --
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        // - Cancel Button -
        cancelButton = new JButton("Cancel");
        styleButton(cancelButton, new Color(200, 200, 200), Color.BLACK);
        
        // - Create Listing Button -
        submitButton = new JButton("Create Listing");
        styleButton(submitButton, new Color(34, 139, 34), Color.WHITE);

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // - Helper -

    /**
     * Styles a JButton with the specified background and foreground colors.
     *
     * @param button     the JButton to style
     * @param background the background color
     * @param foreground the foreground color
     */
    private void styleButton(JButton button, Color background, Color foreground) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setBorderPainted(false); 
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 45));
    }

    /**
     * Clears the form inputs, resetting the text field and dropdown to default.
     */
    public void clearForm() {
        propertyNameTextField.setText("");
        propertyTypeComboBox.setSelectedIndex(0);
    }

    // - Getters -

    /**
     * Gets the trimmed text input for the property name.
     * 
     * @return The property name input as a trimmed String.
     */
    public String getPropertyNameInput() {
        // Trimmed to remove leading/trailing spaces
        return propertyNameTextField.getText().trim();
    }

    /**
     * Gets the selected index of the property type from the dropdown.
     * 
     * @return The selected index of the property type JComboBox.
     */
    public int getPropertyTypeIndex() {
        return propertyTypeComboBox.getSelectedIndex();
    }

    /**
     * Gets the submit button for adding action listeners.
     * 
     * @return The JButton for submitting the new property listing.
     */
    public JButton getSubmitButton() {
        return submitButton;
    }

    /**
     * Gets the cancel button for adding action listeners.
     * 
     * @return The JButton for cancelling the property creation process.
     */
    public JButton getCancelButton() {
        return cancelButton;
    }
}