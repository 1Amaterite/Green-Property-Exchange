import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * PropertyManagerView class represents the main GUI for the Property Manager application,
 * utilizing a CardLayout to switch between different views.
 */
public class PropertyManagerView extends JFrame implements PropertyManagerViewInterface {

    /** The PropertyManager model used by the view. */
    private PropertyManager model;
    /** The PropertyManagerController for handling user interactions. */
    private PropertyManagerController controller;

    // Layout 
    
    /** CardLayout to manage different views. */
    private CardLayout cardLayout;
    /** Main panel that holds all sub-panels. */
    private JPanel mainPanel;

    // Start

    /** Opening screen panel. */
    private OpeningScreen openingScreen;

    // The 4 Separate Views (Sub-panels)

    /** Sub-view for the main menu. */
    private MainMenuView mainMenuView;
    /** Sub-view for creating a property. */
    private CreatePropertyView createPropertyView;
    /** Sub-view for displaying property details. */
    private PropertyDetailsView propertyDetailsView;
    /** Sub-view for managing a property. */
    private ManagePropertyView managePropertyView;
  
    /**
     * Constructor to initialize the PropertyManagerView with the given model.
     * 
     * @param model The PropertyManager model to be used by the view.
     */
    public PropertyManagerView(PropertyManager model) {
        this.model = model;

        setTitle("Green Property Exchange");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Card Layout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Start
        openingScreen = new OpeningScreen();

        // Initialize Sub-Views
        mainMenuView = new MainMenuView();
        createPropertyView = new CreatePropertyView();
        propertyDetailsView = new PropertyDetailsView();
        managePropertyView = new ManagePropertyView();

        // Add Sub-Views to main panel with unique identifiers
        mainPanel.add(openingScreen, "Opening Screen");
        mainPanel.add(mainMenuView, "Main Menu");
        mainPanel.add(createPropertyView, "Create Property");
        mainPanel.add(propertyDetailsView, "Property Details");
        mainPanel.add(managePropertyView, "Manage Property");

        add(mainPanel);
    }

    /**
     * Sets the controller for the view.
     * 
     * @param controller The PropertyManagerController to be set.
     */
    public void setController(PropertyManagerController controller) {
        this.controller = controller;
    }

    // Interface

    /**
     * Gets the Start button from the opening screen.
     * 
     * @return JButton The Start button.
     */
    @Override
    public JButton getStartButton() {
        return openingScreen.getStartButton();
    }

    /**
     * Shows the main menu view and refreshes the property list.
     */
    @Override
    public void showMainMenu() {
        cardLayout.show(mainPanel, "Main Menu");
        refreshPropertyList();
        createPropertyView.clearForm(); // Clear form when returning
    }

    /**
     * Shows the add property screen.
     */
    @Override
    public void showAddPropertyScreen() {
        cardLayout.show(mainPanel, "Create Property");
    }

    /**
     * Shows the property details screen for the given property.
     * 
     * @param property The Property object whose details are to be displayed.
     */
    @Override
    public void showManagePropertyScreen(Property property) {
        boolean hasReservations = !property.getReservations().isEmpty();

        managePropertyView.setDetails(property.getPropertyName(), property.getBasePrice(), hasReservations);
        cardLayout.show(mainPanel, "Manage Property");
    }

    /**
     * Updates the property details view with the given property.
     * 
     * @param property The Property object whose details are to be displayed.
     */
    @Override
    public void updatePropertyDetails(Property property) {
        propertyDetailsView.updateView(property);
        cardLayout.show(mainPanel, "Property Details");
        mainPanel.revalidate(); 
        mainPanel.repaint();
    }

    /**
     * Refreshes the property list in the main menu view.
     */
    @Override
    public void refreshPropertyList() {
        mainMenuView.updatePropertyList(model.getProperties());
    }

    /**
     * Displays a message dialog with the given message.
     * 
     * @param message The message to be displayed.
     */
    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Sets the instruction text in the property details view.
     * 
     * @param text The instruction text to be set.
     */
    @Override
    public void setDetailsInstruction(String text) {
        propertyDetailsView.setInstructionText(text);
    }

    /**
     * Sets the calendar date listener in the property details view.
     * 
     * @param listener The ActionListener to be set for calendar date selection.
     */
    @Override
    public void setCalendarDateListener(ActionListener listener) {
        propertyDetailsView.setDateListener(listener);
    }

    /**
     * Highlights the specified date in the property details view's calendar.
     * 
     * @param day The day of the month to highlight.
     */
    @Override
    public void highlightCalendarDate(int day) {
        // We pick Cyan as the highlight color
        propertyDetailsView.highlightDate(day, Color.CYAN);
    }

    /**
     * Gets the selected property name from the main menu view.
     * 
     * @return String The selected property name.
     */
    @Override
    public String getSelectedPropertyName() { 
        return mainMenuView.getSelectedPropertyName(); 
    }
    
    /**
     * Gets the property name input from the create property view.
     * 
     * @return String The property name input.
     */
    @Override
    public String getPropertyNameInput() { 
        return createPropertyView.getPropertyNameInput(); 
    }

    /**
     * Gets the property type index from the create property view.
     * 
     * @return int The property type index.
     */
    @Override
    public int getPropertyTypeIndex() { 
        return createPropertyView.getPropertyTypeIndex(); 
    }

    /**
     * Gets the property edited name input from the create property view.
     * 
     * @return String The edited name input.
     */
    @Override
    public String getEditedName() { 
        return managePropertyView.getEditedName(); 
    }

    /**
     * Gets the edited price input from the manage property view.
     * 
     * @return String The edited price input.
     */
    @Override
    public String getEditedPrice() { 
        return managePropertyView.getEditedPrice(); 
    }

    /**
     * Gets the Add Property button from the main menu view.
     * 
     * @return JButton The Add Property button.
     */
    @Override
    public JButton getAddPropertyButton() { 
        return mainMenuView.getAddPropertyButton(); 
    }

    /**
     * Gets the View Property button from the main menu view.
     * 
     * @return JButton The View Property button.
     */
    @Override
    public JButton getViewPropertyButton() { 
        return mainMenuView.getViewPropertyButton(); 
    }

    /**
     * Gets the Submit Add Property button from the create property view.
     * 
     * @return JButton The Submit Add Property button.
     */
    @Override
    public JButton getSubmitAddPropertyButton() { 
        return createPropertyView.getSubmitButton(); 
    }

    /**
     * Gets the Cancel Add Property button from the create property view.
     * 
     * @return JButton The Cancel Add Property button.
     */
    @Override
    public JButton getCancelAddPropertyButton() { 
        return createPropertyView.getCancelButton(); 
    }

    /**
     * Gets the Back to Main Menu button from the property details view.
     * 
     * @return JButton The Back to Main Menu button.
     */
    @Override
    public JButton getBackToMainMenuButton() { 
        return propertyDetailsView.getBackToMainMenuButton(); 
    }

    /**
     * Gets the Make Reservation button from the property details view.
     * 
     * @return JButton The Make Reservation button.
     */
    @Override
    public JButton getMakeReservationButton() { 
        return propertyDetailsView.getBookReservationButton(); 
    }

    /**
     * Gets the Manage Property button from the property details view.
     * 
     * @return JButton The Manage Property button.
     */
    @Override
    public JButton getManagePropertyButton() { 
        return propertyDetailsView.getManagePropertyButton(); 
    }

    /**
     * Gets the Back button from the manage property view.
     * 
     * @return JButton The Back button.
     */
    @Override
    public JButton getManageBackButton() { 
        return managePropertyView.getBackButton(); 
    }

    /**
     * Gets the Edit Date/Rate button from the property details view.
     * 
     * @return JButton The Edit Date/Rate button.
     */
    @Override
    public JButton getEditDateRateButton() { 
        return propertyDetailsView.getEditRateButton(); 
    }

    /**
     * Gets the Save Changes button from the manage property view.
     * 
     * @return JButton The Save Changes button.
     */
    @Override
    public JButton getSaveButton() { 
        return managePropertyView.getSaveButton(); 
    }

    /**
     * Gets the Delete Property button from the manage property view.
     * 
     * @return JButton The Delete Property button.
     */
    @Override
    public JButton getDeleteButton() { 
        return managePropertyView.getDeleteButton(); 
    }

    /**
     * Gets the View Date Range button from the property details view.
     * 
     * @return JButton The View Date Range button.
     */
    @Override
    public JButton getViewDateRangeButton() {
        return propertyDetailsView.getViewDateRangeButton();
    }
}