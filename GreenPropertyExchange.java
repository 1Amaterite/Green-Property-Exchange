
import javax.swing.SwingUtilities;


/**
 * Main class for the Green Property Exchange application.
 * Initializes the MVC components and starts the GUI.
 */
public class GreenPropertyExchange {

    /**
     * Main method to run the Green Property Exchange application.
     * Initializes the PropertyManager and handles user interaction via console.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Initialize Model
            // This manages the properties and bookings
            PropertyManager propertyManager = new PropertyManager();

            // Initialize View
            // This handles the GUI representation
            PropertyManagerView propertyManagerView = new PropertyManagerView(propertyManager);

            // Initialize Controller
            // This manages user interactions and updates the Model and View
            PropertyManagerController propertyManagercontroller = new PropertyManagerController(propertyManager, propertyManagerView);

            // Link Controller to View
            // This allows the View to communicate user actions to the Controller
            propertyManagerView.setController(propertyManagercontroller);

            // Make the GUI visible
            // This starts the user interface
            propertyManagerView.setVisible(true);
        });
    }
}
