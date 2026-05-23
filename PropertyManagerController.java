import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * PropertyManagerController class acts as the controller in the MVC architecture,
 * managing interactions between the PropertyManager model and PropertyManagerView.
 */
public class PropertyManagerController {

    // Model and View
    private PropertyManager model; 
    private PropertyManagerView view;

    // Current State
    private Property currentProperty;

    // Modes
    private boolean isBookingMode = false;
    private int tempCheckInDay = -1;
    private boolean isEditingRateMode = false;

    // Constructor

    /**
     * Constructor to initialize the PropertyManagerController with the model and view.
     * 
     * @param model The PropertyManager model.
     * @param view  The PropertyManagerView view.
     */
    public PropertyManagerController(PropertyManager model, PropertyManagerView view) {
        this.model = model;
        this.view = view;

        // Attach Listeners
        attachOpeningScreenListeners();
        attachMainMenuListeners();
        attachCreatePropertyListeners();
        attachPropertyDetailsListeners();
        attachManagePropertyListeners();
    }

    // - Listeners -

    /**
     * Attaches listeners for the opening screen.
     */
    private void attachOpeningScreenListeners() {
        view.getStartButton().addActionListener(e -> view.showMainMenu());
    }

    /**
     * Attaches listeners for the main menu buttons.
     */
    private void attachMainMenuListeners() {
        view.getAddPropertyButton().addActionListener(e -> view.showAddPropertyScreen());
        view.getViewPropertyButton().addActionListener(e -> loadPropertyDetails());
    }

    /**
     * Attaches listeners for the create property screen buttons.
     */
    private void attachCreatePropertyListeners() {
        view.getCancelAddPropertyButton().addActionListener(e -> view.showMainMenu());
        view.getSubmitAddPropertyButton().addActionListener(e -> createNewProperty());
    }

    /**
     * Attaches listeners for the property details screen buttons.
     */
    private void attachPropertyDetailsListeners() {

        // Back to Main Menu
        view.getBackToMainMenuButton().addActionListener(e -> {
            resetModes(); 
            view.showMainMenu();
        });
        
        // Booking
        view.getMakeReservationButton().addActionListener(e -> startBookingMode());
        
        // Edit Rate
        view.getEditDateRateButton().addActionListener(e -> toggleEditDateRateMode()); 

        // Manage Property
        view.getManagePropertyButton().addActionListener(e -> {
            resetModes();
            if (currentProperty != null) {
                view.showManagePropertyScreen(currentProperty);
            }
        });

        // Calendar Date Clicks
        view.setCalendarDateListener(e -> handleDateClick(e));

        // View Date Range
        view.getViewDateRangeButton().addActionListener(e -> viewDateRangeAvailability());
    }

    /**
     * Prompts the user for a date range and calculates available/booked days within that range.
     */
    private void viewDateRangeAvailability() {
        if (currentProperty == null) {
            view.showMessage("Error: No property is currently selected.");
            return;
        }

        // Prompt for Start Day
        String startDayInput = JOptionPane.showInputDialog(
            "Enter Check-In Day (1-30) for the range count:"
        );
        if (startDayInput == null) return; 

        // Prompt for End Day
        String endDayInput = JOptionPane.showInputDialog(
            "Enter Check-Out Day (" + startDayInput + "-30) for the range count:"
        );
        if (endDayInput == null) return; 

        try {
            int startDay = Integer.parseInt(startDayInput.trim());
            int endDay = Integer.parseInt(endDayInput.trim());

            if (startDay < 1 || startDay > 30 || endDay < 2 || endDay > 31 || endDay <= startDay) {
                view.showMessage("Error: Invalid date range. Check-out must be after check-in, and dates must be within 1-30.");
                return;
            }

            // Perform Counting
            int bookedCount = 0;
            int availableCount = 0;
            
            // Loop from check-in day up to (but not including) check-out day
            for (int day = startDay; day <= endDay; day++) {
                Date date = currentProperty.getCalendar().getDate(day);
                if (date != null) {
                    if (date.isReserved()) {
                        bookedCount++;
                    } else {
                        availableCount++;
                    }
                }
            }

            // 4. Display Results
            String result = String.format(
                "--- Availability for Days %d to %d ---\n" +
                "Total Nights in Range: %d\n" +
                "Available Nights: %d\n" +
                "Booked Nights: %d",
                startDay, endDay,
                endDay - startDay + 1,
                availableCount,
                bookedCount
            );
            
            view.showMessage(result);

        } catch (NumberFormatException ex) {
                view.showMessage("Error: Please enter valid numbers for the days.");
        }
    }

    /**
     * Attaches listeners for the manage property screen buttons.
     */
    private void attachManagePropertyListeners() {

        // Back to Details
        view.getManageBackButton().addActionListener(e -> {
            if (currentProperty != null) {
                view.updatePropertyDetails(currentProperty);
            }
        });

        // Save Changes
        view.getSaveButton().addActionListener(e -> savePropertyChanges());
        // Delete Property
        view.getDeleteButton().addActionListener(e -> deleteProperty());
    }

    // - Logic methods -

    /**
     * Creates a new property based on user input and adds it to the model.
     */
    private void createNewProperty() {
        String propertyName = view.getPropertyNameInput();
        int typeIndex = view.getPropertyTypeIndex();

        // Validation
        if (propertyName.isEmpty()) {
            view.showMessage("Error: Property name cannot be empty.");
            return;
        }

        if (!model.isNameUnique(propertyName)) {
            view.showMessage("Error: Property name must be unique relative to others.");
            return;
        }

        // Create Property Object
        Property newProperty;
        switch (typeIndex) {
            case 0 -> newProperty = new EcoApartment(propertyName);      // 1.0x Rate
            case 1 -> newProperty = new SustainableHouse(propertyName);  // 1.2x Rate
            case 2 -> newProperty = new GreenResort(propertyName);       // 1.35x Rate
            case 3 -> newProperty = new EcoGlamping(propertyName);       // 1.5x Rate
            default -> newProperty = new EcoApartment(propertyName);
        }

        if (model.addProperty(newProperty)) {
            view.showMessage("Success: " + propertyName + " created.");
            view.showMainMenu();
        } else {
            view.showMessage("Error: Could not add property.");
        }
    }

    /**
     * Loads the details of the selected property into the view.
     */
    private void loadPropertyDetails() {
        String selectedName = view.getSelectedPropertyName();

        if (selectedName == null) {
            view.showMessage("Please select a property from the list first.");
            return;
        }

        // Fetch Property from Model
        currentProperty = model.getPropertyByName(selectedName);

        if (currentProperty != null) {
            view.updatePropertyDetails(currentProperty);
        } else {
            view.showMessage("Error: Property not found in database.");
        }
    }

    /**
     * Saves changes made to the current property's name and base price.
     */
    private void savePropertyChanges() {
        if (currentProperty == null) { 
            return;
        }
        
        String newName = view.getEditedName();
        String priceString = view.getEditedPrice();
        boolean changesMade = false;

        // Name Logic
        if (!newName.equals(currentProperty.getPropertyName())) {
            if (newName.isEmpty()) {
                view.showMessage("Error: Name cannot be empty.");
                return;
            }
            if (model.isNameUnique(newName)) {
                currentProperty.setName(newName);
                changesMade = true;
            } else {
                view.showMessage("Error: Name already taken.");
                return;
            }
        }

        // Price Logic
        if (currentProperty.getReservations().isEmpty()) {
            try {
                // 1. First, make sure it is actually a number
                double newPrice = Double.parseDouble(priceString);
                
                // 2. Logic: Check for decimal places manually
                if (priceString.contains(".")) {
                    int dotIndex = priceString.indexOf('.');
                    String afterDotString = priceString.substring(dotIndex + 1);
                    
                    if (afterDotString.length() > 2) {
                        view.showMessage("Error: Too many decimal places. Max is 2.");
                        return;
                    }
                }
    
                // 3. Continue with the logic...
                if (Double.compare(newPrice, currentProperty.getBasePrice()) != 0) {
                    if (newPrice < 100.0) {
                        view.showMessage("Error: Price must be >= 100.00");
                        return;
                    }
    
                    if (currentProperty.setBasePrice(newPrice)) {
                        changesMade = true;
                    }
                }
    
            } catch (NumberFormatException e) {
                view.showMessage("Error: Invalid price format.");
                return;
            }
        }

        if (changesMade) {
            view.showMessage("Property updated successfully!");
            // Go back to the Details view immediately to show the new info
            view.updatePropertyDetails(currentProperty);
        } else {
            view.showMessage("No changes were saved.");
        }
    }

    /** 
     * Deletes the current property after user confirmation.
     */
    private void deleteProperty() {
        if (currentProperty == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            null, 
            "Are you sure you want to delete \"" + currentProperty.getPropertyName() + 
            "\"?\nThis action cannot be undone.",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        // Proceed if confirmed
        if (confirm == JOptionPane.YES_OPTION) {
            if (model.removeProperty(currentProperty)) {
                view.showMessage("Property deleted.");
                currentProperty = null; // Clear selection
                view.showMainMenu();    // Go back to start
            } else {
                view.showMessage("Error: Cannot delete property with active reservations.");
            }
        }
    }

    /**
     * Toggles the edit date rate mode on or off.
     */
    private void toggleEditDateRateMode() {
        if (currentProperty == null) {
            return;
        }
        
        // If we were booking, cancel booking mode first
        if (isBookingMode) {
            resetModes();
        }

        isEditingRateMode = !isEditingRateMode; // Toggle

        if (isEditingRateMode) {
            view.setDetailsInstruction("EDIT MODE: Click dates to change rates. Click 'Edit Rate' again to stop.");
        } else {
            view.setDetailsInstruction("Click a date to view details.");
        }
    }

    /**
     * Processes the editing of a date's rate.
     * 
     * @param day The day of the month to edit.
     */
    private void processEditDateRate(int day) {
        Date date = currentProperty.getCalendar().getDate(day);

        // Cannot edit booked dates
        if (date.isReserved()) {
            view.showMessage("Error: Cannot modify rate of a booked date.");
            return;
        }

        // For new modifier
        String input = JOptionPane.showInputDialog("Current Mod: " + (int)(date.getEnvironmentalModifier()*100) + "%\nEnter new mod (80%-120%):");
        
        if (input != null) {
            try {
                // Parse does not accept % sign, so we remove it first
                double mod = Double.parseDouble(input.replace("%","").trim()) / 100.0;

                // Set new modifier
                if (date.setEnvironmentalModifier(mod)) {
                    view.showMessage("Updated!");
                
                    view.updatePropertyDetails(currentProperty); 
                    
                    // Because updatePropertyDetails() resets it to "View Details", we overwrite it here.
                    view.setDetailsInstruction("EDIT MODE: Click dates to change rates. Click 'Edit Rate' again to stop.");
                    
                } else {
                    view.showMessage("Error: Must be 80% - 120%.");
                }
            } catch (Exception e) { 
                view.showMessage("Invalid Number."); 
            }
        }
    }

    /**
     * Shows details for a specific date, including reservation info if booked.
     * 
     * @param day The day of the month to show details for.
     */
    private void showDateDetails(int day) {
        Date date = currentProperty.getCalendar().getDate(day);
        
        if (date.isReserved()) {
            // Finds the reservation for that date
            for (Reservation reservation : currentProperty.getReservations()) {
                if (day >= reservation.getCheckIn() && day < reservation.getCheckOut()) {
                    
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("--- Reservation Details ---\n");
                    stringBuilder.append("Guest: ").append(reservation.getGuest().getName()).append("\n");
                    stringBuilder.append("Check-In: Day ").append(reservation.getCheckIn()).append("\n");
                    stringBuilder.append("Check-Out: Day ").append(reservation.getCheckOut()).append("\n\n");
                    
                    // Add Breakdown Section
                    stringBuilder.append("--- Price Breakdown ---\n");
                    ArrayList<Double> breakdown = reservation.getPriceBreakdown();
                    int currentDay = reservation.getCheckIn();
                    
                    for (Double price : breakdown) {
                        stringBuilder.append("Day ").append(currentDay).append(":  P ")
                          .append(String.format("%,.2f", price)).append("\n");
                        currentDay++;
                    }
                    
                    stringBuilder.append("------------------------------");
                    stringBuilder.append("\nTotal Price: P ").append(String.format("%,.2f", reservation.getTotalPrice()));
                    stringBuilder.append("\n------------------------------");

                    stringBuilder.append("\n\nDo you want to CANCEL this reservation?");
                    
                    // Scrollable Text Area for better formatting
                    JTextArea textArea = new JTextArea(stringBuilder.toString());
                    textArea.setEditable(false);
                    textArea.setOpaque(false);
                    textArea.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12)); // Aligns numbers nicely
                    
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(350, 250));
                    scrollPane.setBorder(null);

                    // Confirm Cancellation
                    int choice = JOptionPane.showConfirmDialog(null, scrollPane, "Manage Reservation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                    
                    if (choice == JOptionPane.YES_OPTION) {
                        currentProperty.removeReservation(reservation);
                        view.showMessage("Reservation cancelled successfully.");
                        view.updatePropertyDetails(currentProperty);
                    }
                    return; // Exit loop once found
                }
            }
        }
        
        // If not reserved, show the daily price
        double price = currentProperty.getPricePerNight(day);
        String formattedPrice = String.format("%,.2f", price); 
        view.showMessage("Day " + day + " is Available.\nPrice per Night: P " + formattedPrice);
    }

    /**
     * Handles clicks on calendar dates based on the current mode.
     * 
     * @param e The ActionEvent triggered by clicking a date.
     */
    private void handleDateClick(ActionEvent e) {
        int day = Integer.parseInt(e.getActionCommand());

        if (isBookingMode) {
            processBookingSelection(day);
        } else if (isEditingRateMode) {
            processEditDateRate(day);
        } else {
            showDateDetails(day);
        }
    }

    /**
     * Starts the booking mode for the current property.
     */
    private void startBookingMode() {
        if (currentProperty == null) {
            return;
        }
        
        // Reset any existing highlights first
        if (tempCheckInDay != -1 || isEditingRateMode) {
            view.updatePropertyDetails(currentProperty);
        }

        isBookingMode = true;
        isEditingRateMode = false;
        tempCheckInDay = -1;
        view.setDetailsInstruction("Booking Mode: Select Check-In Date.");
    }

    /**
     * Processes the booking selection for check-in and check-out dates.
     * 
     * @param day The day of the month selected.
     */
    private void processBookingSelection(int day) {
        Date d = currentProperty.getCalendar().getDate(day);
        if (d.isReserved()) { 
            view.showMessage("Date Booked."); 
            return; 
        }

        // Check-In Selection
        if (tempCheckInDay == -1) {
            if (day == 30) { 
                view.showMessage("Cannot check-in on Day 30."); 
                return; 
            }

            tempCheckInDay = day;
            view.highlightCalendarDate(day);
            view.setDetailsInstruction("Select Check-Out Date.");
        } else {
            if (day <= tempCheckInDay) { 
                
                view.showMessage("Invalid Check-Out. Resetting Selection.");
                tempCheckInDay = -1;
                view.updatePropertyDetails(currentProperty); 
                view.setDetailsInstruction("Select Check-In Date.");
                return; 
            }
            finishBooking(tempCheckInDay, day);
        }
    }

     /**
     * Finalizes the booking process and shows the receipt.
     * 
     * @param checkIn  The check-in day.
     * @param CheckOut The check-out day.
     */
     private void finishBooking(int checkIn, int CheckOut) {
        String guestName = JOptionPane.showInputDialog("Enter Guest Name:");

        if (guestName != null && !guestName.trim().isEmpty()) {
            try {
                Guest guest = new Guest(guestName);
                Reservation reservation = new Reservation(guest, currentProperty, checkIn, CheckOut);

                if (currentProperty.addReservation(reservation)) {
                    // Stringbuilder is used for efficient string concatenation
                    StringBuilder receipt = new StringBuilder();
                    receipt.append("Booking Confirmed for ").append(guestName).append("!\n");
                    receipt.append("------------------------------------------------\n");
                    
                    ArrayList<Double> breakdown = reservation.getPriceBreakdown();
                    int currentDay = checkIn;
                    
                    for (Double price : breakdown) {
                        receipt.append("Day ").append(currentDay).append(": PHP ").append(String.format("%.2f", price)).append("\n");
                        currentDay++;
                    }
                    
                    receipt.append("------------------------------------------------\n");
                    receipt.append("TOTAL: PHP ").append(String.format("%.2f", reservation.getTotalPrice()));
                    
                    // Show the detailed receipt
                    view.showMessage(receipt.toString());
                    
                    // Refresh Calendar
                    view.updatePropertyDetails(currentProperty);
                } else {
                    view.showMessage("Error: Could not complete booking.");
                }
            } catch (Exception e) {
                view.showMessage("Error: " + e.getMessage());
            }
        }

        // Reset Booking State
        resetModes();
    }

    // - Helper -

    /**
     * Resets the current modes (booking/editing) and updates the view.
     */
    private void resetModes() {
        isBookingMode = false;
        isEditingRateMode = false;
        tempCheckInDay = -1;

        // Reset instruction text
        if (currentProperty != null) {
            view.updatePropertyDetails(currentProperty);
        }
    }
}