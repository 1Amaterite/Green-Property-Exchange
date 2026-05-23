import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * PropertyDetailsView class represents the detailed view of a property,
 * including its calendar, information, and action buttons.
 */
public class PropertyDetailsView extends JPanel {
    
    // Components

    /** Labels for property name */
    private JLabel propertyNameLabel;
    /** Labels for property type */
    private JLabel propertyTypeLabel;
    /** Labels for property price */
    private JLabel propertyPriceLabel;
    /** Labels for property earnings */
    private JLabel propertyEarningsLabel;
    /** Label for instructions */
    private JLabel instructionLabel;

    /** Panel for calendar wrapper */
    private JPanel calendarWrapperPanel;
    /** Panel for calendar grid */
    private JPanel calendarGridPanel;
    /** Panel for legend */
    private JPanel legendPanel;
    
    /** Button for date range */
    private JButton viewDateRangeButton;
    /** Button for book reservation */
    private JButton bookReservationButton;
    /** Button for edit date rate */
    private JButton editDateRateButton;
    /** Button for manage property */
    private JButton managePropertyButton;
    /** Button for back to main menu */
    private JButton backToMainMenuButton;

    // Day Buttons

    /** Array of buttons for each day in the calendar */
    private JButton[] dayButtons = new JButton[31];
    /** ActionListener for date buttons */
    private ActionListener dateListener;

    // Theme Colors
    private final Color HEADER_GREEN = new Color(34, 139, 34); // Forest Green
    private final Color LIGHT_BG = new Color(245, 250, 245);  
    
    /**
     * Constructor to initialize the PropertyDetailsView.
     */
    public PropertyDetailsView() {
        // Layout and Styling
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(LIGHT_BG);

        // - Info Panel -
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 2, 2));
        infoPanel.setOpaque(false);

        propertyNameLabel = new JLabel("Name: -");
        propertyNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        propertyNameLabel.setForeground(new Color(20, 80, 20)); // Dark Green

        propertyTypeLabel = new JLabel("Type: -");
        propertyTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        propertyPriceLabel = new JLabel("Base Price: -");
        propertyPriceLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        propertyEarningsLabel = new JLabel("Total Earnings: -");
        propertyEarningsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        instructionLabel = new JLabel("Mode: Viewing Details");
        instructionLabel.setForeground(new Color(0, 100, 200)); 
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
    
        infoPanel.add(propertyNameLabel);
        infoPanel.add(propertyTypeLabel);
        infoPanel.add(propertyPriceLabel);
        infoPanel.add(propertyEarningsLabel);
        infoPanel.add(instructionLabel);

        add(infoPanel, BorderLayout.NORTH);
        
        // Legends
        legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setOpaque(false);
        legendPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 

        legendPanel.add(createLegend(new Color(210, 255, 210), 
                "Green dates: Reduced environmental impact (80-99% of base price)"));
        legendPanel.add(createLegend(Color.WHITE, 
                "White dates: Standard environmental effects (100% of base price)"));
        legendPanel.add(createLegend(new Color(255, 245, 210), 
                "Yellow dates: Increased environmental impact (101-120% of base price)"));

        // Calendar Wrapper (Main holder for header and grid)
        calendarWrapperPanel = new JPanel(new BorderLayout());
        calendarWrapperPanel.setBorder(new LineBorder(new Color(150, 200, 150), 1));

        // Days
        JPanel daysHeaderPanel = new JPanel(new GridLayout(1, 7));
        daysHeaderPanel.setBackground(HEADER_GREEN); // Green Header

        // Day Labels
        String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setForeground(Color.WHITE);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            dayLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            daysHeaderPanel.add(dayLabel);
        }
        
        JPanel calendarHeader = new JPanel(new BorderLayout());
        calendarHeader.setOpaque(false);
        
        calendarHeader.add(legendPanel, BorderLayout.NORTH);  
        
        calendarHeader.add(daysHeaderPanel, BorderLayout.CENTER); 
        
        calendarWrapperPanel.add(calendarHeader, BorderLayout.NORTH); 

        // Calendar Grid
        calendarGridPanel = new JPanel(new GridLayout(0, 7, 1, 1));
        calendarGridPanel.setBackground(new Color(200, 200, 200)); 
        calendarWrapperPanel.add(calendarGridPanel, BorderLayout.CENTER);

        add(calendarWrapperPanel, BorderLayout.CENTER);

        // - Buttons -

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        bookReservationButton = new JButton("Book Reservation");
        editDateRateButton = new JButton("Edit Rate"); 
        managePropertyButton = new JButton("Manage");  
        backToMainMenuButton = new JButton("Back");      
        viewDateRangeButton = new JButton("View Range"); 
        
        buttonPanel.add(viewDateRangeButton);
        buttonPanel.add(bookReservationButton);
        buttonPanel.add(editDateRateButton);
        buttonPanel.add(managePropertyButton);
        buttonPanel.add(backToMainMenuButton); 

        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Updates the view with the given property's details.
     *
     * @param property The property whose details are to be displayed.
     */
    public void updateView(Property property) {
        propertyNameLabel.setText(property.getPropertyName());
        propertyTypeLabel.setText(property.getClass().getSimpleName());

        int availableDates = property.getCalendar().countAvailableDates();

        double effectiveBasePrice = property.getBasePrice() * property.getPropertyMultiplier();
        propertyPriceLabel.setText(String.format("Base Price: P %,.2f  |  Available: %d/30", effectiveBasePrice, availableDates));
        
        propertyEarningsLabel.setText(String.format("Total Earnings: P %,.2f", property.getEstimatedEarnings()));
        
        instructionLabel.setText("Click a date to view details.");
        
        calendarGridPanel.removeAll();
        // Assuming Calendar class exists and has getDates() and getStartDay()
        Calendar calendar = property.getCalendar();
        int startDay = calendar.getStartDay();

        // Spacers
        for (int i = 0; i < startDay; i++) {
            JLabel spacer = new JLabel("");
            spacer.setOpaque(true);
            spacer.setBackground(new Color(200, 200, 200)); 
            calendarGridPanel.add(spacer);
        }

        // Day Buttons
        // Assuming Date class exists and has getDayNumber(), isReserved(), and getEnvironmentalModifier()
        for (Date date : calendar.getDates()) {
            int dayNum = date.getDayNumber();
            JButton dayButton = new JButton();
            
            dayButton.setMargin(new Insets(0, 0, 0, 0));
            dayButton.setOpaque(true);
            dayButton.setBorderPainted(false);
            dayButton.setFocusPainted(false);
            dayButton.setFont(new Font("Arial", Font.PLAIN, 11));

            String topContent = "<center><b>" + dayNum + "</b></center>";
            String bottomContent;

            if (date.isReserved()) {
                dayButton.setBackground(new Color(255, 220, 220)); // Pastel Red
                
                String guestName = "Booked";
                for (Reservation r : property.getReservations()) {
                    if (dayNum >= r.getCheckIn() && dayNum < r.getCheckOut()) {
                        guestName = r.getGuest().getName();
                        if (guestName.length() > 8) { 
                            guestName = guestName.substring(0, 6) + ".."; 
                            break;
                        }
                    }
                }
                bottomContent = "<center><span style='color:rgb(180,0,0);'>" + guestName + "</span></center>";
            } else {
                double dailyPrice = property.getPricePerNight(dayNum);
                double mod = date.getEnvironmentalModifier();
                
                // Color coding logic based on environmental modifier
                if (mod < 1.0) {
                    dayButton.setBackground(new Color(210, 255, 210)); // Pastel Green
                } else if (mod > 1.0) { 
                    dayButton.setBackground(new Color(255, 245, 210)); // Pastel Orange (Yellow)
                } else { 
                    dayButton.setBackground(Color.WHITE);
                }
                bottomContent = String.format("P%,.2f", dailyPrice);
            }

            // Multi-line button text using HTML
            dayButton.setText("<html>" +
                "<table width='100%' height='100%' cellpadding='0' cellspacing='0'>" + 
                "<tr><td valign='top' align='center' style='padding-top:2px;'>" + topContent + "</td></tr>" + 
                "<tr><td valign='bottom' align='center' style='padding-bottom:2px;'><font size='-2'>" + bottomContent + "</font></td></tr>" + 
                "</table>" +
                "</html>");

            dayButton.setActionCommand(String.valueOf(dayNum));
            if (dateListener != null) { 
                dayButton.addActionListener(dateListener);
            }

            dayButtons[dayNum] = dayButton;
            calendarGridPanel.add(dayButton);
        }
        calendarGridPanel.revalidate();
        calendarGridPanel.repaint();
    }

    // - Helper -
    
    /**
     * Creates a legend panel with a colored box and corresponding text.
     * 
     * @param color The color for the legend box.
     * @param text  The text description for the legend.
     * @return      A JPanel containing the legend item.
     */
    private JPanel createLegend(Color color, String text) {
        // Use a small vgap (3) to ensure components reserve space vertically
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3)); 
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel box = new JLabel("   "); // Spaced label for color box
        box.setOpaque(true);
        box.setBackground(color);
        // Explicitly requesting size/padding for the color box
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(2, 4, 2, 4) 
        ));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(box);
        panel.add(label);
        return panel;
    }

    /**
     * Highlights a specific date button with the given color.
     * 
     * @param day   The day number to highlight (1-30).
     * @param color The color to use for highlighting.
     */
    public void highlightDate(int day, Color color) {
        if (day >= 1 && day <= 30 && dayButtons[day] != null) {
            dayButtons[day].setBackground(color);
        }
    }

    // - Setters -

    /**
     * Sets the ActionListener for date buttons.
     * 
     * @param listener The ActionListener to be set for date buttons.
     */
    public void setDateListener(ActionListener listener) {
        this.dateListener = listener;
    }

    /**
     * Sets the instruction text displayed in the view.
     * 
     * @param text The instruction text to be displayed.
     */
    public void setInstructionText(String text) {
        instructionLabel.setText(text);
    }

    // Getters

    /**
     * Gets the Book Reservation button.
     * 
     * @return The JButton for booking reservations.
     */
    public JButton getBookReservationButton() { 
        return bookReservationButton; 
    }

    /**
     * Gets the Edit Rate button.
     * 
     * @return The JButton for editing rates.
     */
    public JButton getEditRateButton() { 
        return editDateRateButton; 

    }

    /**
     * Gets the Manage Property button.
     * 
     * @return The JButton for managing the property.
     */
    public JButton getManagePropertyButton() { 
        return managePropertyButton; 
    }

    /**
     * Gets the Back to Main Menu button.
     * 
     * @return The JButton for returning to the main menu.
     */
    public JButton getBackToMainMenuButton() { 
        return backToMainMenuButton; 
    }

    /**
     * Gets the View Date Range button.
     * 
     * @return The JButton for viewing date range statistics.
     */
    public JButton getViewDateRangeButton() { 
        return viewDateRangeButton; 
    }
}