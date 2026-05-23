import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * PropertyManagerViewInterface defines the methods for the Property Manager GUI,
 * including navigation, interaction, data retrieval, and button access.
 * The interface helps in separating the view's implementation from its usage in the controller.
 */
public interface PropertyManagerViewInterface {
    
    // Navigation

    /**
     * Displays a message to the user.
     * 
     * @param message The message to be displayed as a String.
     */
    void showMessage(String message);
    
    /**
     * Refreshes the property list in the main menu.
     */
    void refreshPropertyList();
    
    /**
     * Shows the main menu screen.
     */
    void showMainMenu();
    
    /**
     * Shows the add property screen.
     */
    void showAddPropertyScreen();
    
    /**
     * Updates the property details view with the given property.
     * 
     * @param property The Property object whose details are to be displayed.
     */
    void updatePropertyDetails(Property property);
    
    /**
     * Shows the manage property screen for the given property.
     * 
     * @param property The Property object to be managed.
     */
    void showManagePropertyScreen(Property property); 

    // Interaction

    /**
     * Sets the instruction text for the details section.
     * 
     * @param text The instruction text to be set as a String.
     */
    void setDetailsInstruction(String text);
    
    /**
     * Sets the listener for calendar date selection.
     * 
     * @param listener The ActionListener to be set for calendar date selection.
     */
    void setCalendarDateListener(ActionListener listener);
    
    /**
     * Highlights the specified date on the calendar.
     * 
     * @param day The day of the month to highlight as an int.
     */
    void highlightCalendarDate(int day);

    // Data Retrieval

    /**
     * Gets the property name input from the user.
     * 
     * @return The property name as a String.
     */
    String getPropertyNameInput();

    /**
     * Gets the selected property type index.
     * 
     * @return The index of the selected property type as an int.
     */
    int getPropertyTypeIndex();

    /**
     * Gets the selected property name from the main menu.
     * 
     * @return The selected property name as a String.
     */
    String getSelectedPropertyName();

    /**
     * Gets the edited property name from the manage property screen.
     * 
     * @return The edited property name as a String.
     */
    String getEditedName();

    /**
     * Gets the edited price from the manage property screen.
     * 
     * @return The edited price as a String.
     */
    String getEditedPrice();

    // Buttons

    /**
     * Gets the various buttons from the GUI for event handling.
     * 
     * @return JButton The requested button.
     */
    JButton getStartButton();

    /**
     * Gets the "Add Property" button.
     * 
     * @return JButton The "Add Property" button.
     */
    JButton getAddPropertyButton();

    /**
     * Gets the "View Property" button.
     * 
     * @return JButton The "View Property" button.
     */
    JButton getViewPropertyButton();

    /**
     * Gets the "Submit Add Property" button.
     * 
     * @return JButton The "Submit Add Property" button.
     */
    JButton getSubmitAddPropertyButton();
    
    /**
     * Gets the "Cancel Add Property" button.
     * 
     * @return JButton The "Cancel Add Property" button.
     */
    JButton getCancelAddPropertyButton();

    /**
     * Gets the "Back to Main Menu" button.
     * 
     * @return JButton The "Back to Main Menu" button.
     */
    JButton getBackToMainMenuButton();

    /**
     * Gets the "Make Reservation" button.
     * 
     * @return JButton The "Make Reservation" button.
     */
    JButton getMakeReservationButton();

    /**
     * Gets the "Manage Property" button.
     * 
     * @return JButton The "Manage Property" button.
     */
    JButton getManagePropertyButton(); 

    /**
     * Gets the "Edit Name" button.
     * 
     * @return JButton The "Edit Name" button.
     */
    JButton getEditDateRateButton();

    /**
     * Gets the "Save" button.
     * 
     * @return JButton The "Save" button.
     */
    JButton getSaveButton();

    /**
     * Gets the "Delete" button.
     * 
     * @return JButton The "Delete" button.
     */
    JButton getDeleteButton();

    /**
     * Gets the "Back" button from the manage property screen.
     * 
     * @return JButton The "Back" button.
     */
    JButton getManageBackButton();

    /**
     * Gets the "View Date Range" button.
     * 
     * @return JButton The "View Date Range" button.
     */
    JButton getViewDateRangeButton();
}