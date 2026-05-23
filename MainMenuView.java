import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * MainMenuView represents the main menu panel of the Green Property Exchange application.
 * It displays a list of properties and provides buttons to add new properties or view details of selected properties.
 */
public class MainMenuView extends JPanel{
    
    // Components

    /** List model to hold property names for display. */
    private DefaultListModel<String> propertyListModel;
    /** JList to display the properties. */
    private JList<String> propertyList;
    /** Button to add a new property. */
    private JButton addPropertyButton;
    /** Button to view details of the selected property. */
    private JButton viewPropertyButton;

    /**
     * Constructor to initialize the MainMenuView.
     * Sets up the layout, styles, and components of the main menu.
     */
    public MainMenuView() {
        // Layout and Styling
        setLayout(new BorderLayout(20, 20)); 
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        // Light green background
        setBackground(new Color(240, 255, 240)); 
        
        // - Title -
        JLabel titleLabel = new JLabel("Green Property Exchange", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        // Dark green text
        titleLabel.setForeground(new Color(34, 139, 34));
        add(titleLabel, BorderLayout.NORTH);

        // - Property List -
        propertyListModel = new DefaultListModel<>();
        propertyList = new JList<>(propertyListModel);
        // This ensures only one property can be selected at a time
        propertyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        propertyList.setFont(new Font("Arial", Font.PLAIN, 16));
        propertyList.setFixedCellHeight(35);
        propertyList.setBackground(Color.WHITE);
        
        // This makes the list scrollable if there are many properties
        JScrollPane scrollPane = new JScrollPane(propertyList);

        // Border for the list
        TitledBorder listBorder = BorderFactory.createTitledBorder(
            // This creates a green border
            new LineBorder(new Color(34, 139, 34), 1), 
            " Current Properties "
        );

        // Style the border title
        listBorder.setTitleColor(new Color(34, 139, 34));
        listBorder.setTitleFont(new Font("Arial", Font.BOLD, 12));
        scrollPane.setBorder(listBorder);

        add(scrollPane, BorderLayout.CENTER);

        // -- Buttons Panel --
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // Grid for equal sizing, 20px gap
        buttonPanel.setOpaque(false);
        
        // - Add Button -
        addPropertyButton = new JButton("Add New Property");
        styleButton(addPropertyButton);
    
        // - View Button -
        viewPropertyButton = new JButton("View Property Details");
        styleButton(viewPropertyButton);

        buttonPanel.add(addPropertyButton);
        buttonPanel.add(viewPropertyButton);

        // - Wrapper -

        // Wrapper is used to add footer text below buttons
        JPanel bottomWrapper = new JPanel(new BorderLayout());
        bottomWrapper.setOpaque(false);
        bottomWrapper.add(buttonPanel, BorderLayout.CENTER);
        
        // - Footer Text -
        JLabel footer = new JLabel("Select a property above and click View, or create a new one.", SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        footer.setForeground(Color.GRAY);
        bottomWrapper.add(footer, BorderLayout.SOUTH);
        add(bottomWrapper, BorderLayout.SOUTH);
    }

    // - Helpers -

    /**
     * Styles a JButton with custom fonts, colors, and padding.
     * 
     * @param button The JButton to be styled.
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        // Dark green background with white text
        button.setBackground(new Color(34, 139, 34)); 
        button.setForeground(Color.WHITE);            
        button.setOpaque(true);
        button.setFocusPainted(false); 
        button.setBorderPainted(false);               
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    }

    /**
     * Updates the property list displayed in the main menu.
     * 
     * @param properties An ArrayList of Property objects to be displayed.
     */
    public void updatePropertyList(ArrayList<Property> properties) {
        // Clear existing entries
        propertyListModel.clear();

        // Populate the list with new properties
        if (properties.isEmpty()) {
            // If no properties, show text and disable view button
            propertyListModel.addElement("  No properties listed yet.");
            propertyList.setEnabled(false);
            viewPropertyButton.setEnabled(false);
            viewPropertyButton.setBackground(Color.LIGHT_GRAY);
        } else {
            // If properties exist, enable list and view button
            propertyList.setEnabled(true);
            viewPropertyButton.setEnabled(true);
            viewPropertyButton.setBackground(new Color(34, 139, 34));
            
            // Add each property to the list
            for (Property property : properties) {
                String type = property.getClass().getSimpleName();
                propertyListModel.addElement("   " + type + "  -  " + property.getPropertyName());
            }
        }
    }

    // - Getters -

    /**
     * Gets the name of the currently selected property from the list.
     * 
     * @return The name of the selected property as a String, or null if no valid selection is made.
     */
    public String getSelectedPropertyName() {
        // Get the selected value from the list
        String selected = propertyList.getSelectedValue();

        // If no selection or placeholder text, return null
        if (selected == null || selected.equals("No properties listed yet.")) {
            return null;
        }

        // Extract and return the property name
        String[] parts = selected.split(" - ", 2);
        return parts.length > 1 ? parts[1].trim() : selected.trim();
    }

    /**
     * Getter for the Add Property button.
     * 
     * @return The JButton for adding a new property.
     */
    public JButton getAddPropertyButton() { 
        return addPropertyButton; 
    }

    /**
     * Getter for the View Property Details button.
     * 
     * @return The JButton for viewing property details.
     */
    public JButton getViewPropertyButton() { 
        return viewPropertyButton; 
    }
}
