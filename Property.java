import java.util.ArrayList;
import java.util.List;

/**
 * Represents a property with a name, calendar, and reservations.
 * It allows managing reservations, pricing, and displaying property details.
 */
public abstract class Property { 
    
    /** Unique name for the property. */
    private   String propertyName;
    /** A calendar (30 days) for availability. */
    protected Calendar calendar;
    /** List of all reservations for the property. */
    protected ArrayList<Reservation> reservations;
    /** Base price per night, the first price is default before any multipliers. */
    protected double basePrice;

    /**
     * Constructor to initialize Property with a name.
     * The calendar is initialized with default settings.
     * 
     * @param propertyName The unique name of the property as a String.
     */
    public Property(String propertyName) {
        this.propertyName = propertyName;
        this.calendar = new Calendar();
        this.reservations = new ArrayList<>();
        this.basePrice = 1500.00;
    }

    // - Abstract Method -

    /**
     * Abstract method to get the property type multiplier.
     * Each subclass must implement this to provide its specific multiplier.
     * 
     * @return The property type multiplier as a double.
     */
    public abstract double getPropertyMultiplier();

    // - Getters -

    /**
     * Calculates the price per night for a given day number,
     * considering the base price, property multiplier, and environmental modifier.
     * 
     * @param dayNumber The day number (1-30) for which to calculate the price.
     * @return          The calculated price per night as a double.
     */
    public double getPricePerNight(int dayNumber) {
        Date date = this.calendar.getDate(dayNumber);

        if (date == null) {
            return 0.0; // Date not found
        }

        // Calculate final price
        return this.basePrice * getPropertyMultiplier() * date.getEnvironmentalModifier();
    }

    /**
     * Calculates the estimated earnings from all reservations.
     * 
     * @return The total estimated earnings as a double.
     */
    public double getEstimatedEarnings() {
        double total = 0;
        for (Reservation reservation : reservations) {
            total += reservation.getTotalPrice();
        }
        return total;
    }

    /**
     * Getter for the name of the property.
     * 
     * @return The name of the property as a String.
     */
    public String getPropertyName() { 
        return propertyName; 
    }

    /**
     * Getter for the calendar of the property.
     * 
     * @return The Calendar object associated with the property.
     */
    public Calendar getCalendar() { 
        return calendar; 
    }
    
    /**
     * Getter for the list of reservations for the property.
     * 
     * @return List of Reservation objects.
     */
    public List<Reservation> getReservations() { 
        return new ArrayList<>(reservations); 
    }
    
    /**
     * Getter for the price of the property (price of the first date).
     * Getting only the first date as all dates should have the same price.
     * 
     * @return The price as a double per night.
     */
    public double getBasePrice() {
        return basePrice;
    }

    // - Setters -

    /**
     * Updates the property's name.
     * 
     * @param name The new name for the property.
     */
    public void setName(String name) {
        this.propertyName = name;
    }

    /**
     * Sets a new price for all dates in the calendar.
     * Cannot change price if there are existing reservations
     * or if the new price is below 100.00. 
     *
     * @param newPrice The new price to set for all dates.
     * @return         True if the price was successfully updated, false otherwise.
     */
    public boolean setBasePrice(double newPrice) {
        if (!reservations.isEmpty()) {
            return false; // Cannot change price if there are existing reservations
        }

        if (newPrice < 100.00) {
            return false;
        }

        this.basePrice = newPrice;
        return true;
    }

    // - Reservation Methods -

    /**
     * Adds a reservation to the property's list of reservations.
     * 
     * @param reservation The Reservation object to add.
     * @return            True if the reservation was successfully added, false otherwise.
     */
    public boolean addReservation(Reservation reservation) {
        // Logic error prevention
        if (!calendar.isRangeAvailable(reservation.getCheckIn(), reservation.getCheckOut())) {
            return false; // Dates are not available
        }

        // Mark the dates as reserved in the calendar
        calendar.markReserved(reservation.getCheckIn(), reservation.getCheckOut());

        // Add the reservation to the list
        reservations.add(reservation);
        return true;
    }

    /**
     * Removes a reservation from the property's list of reservations.
     * Also updates the calendar to mark the dates as available.
     * 
     * @param reservation The Reservation object to remove.
     */
    public void removeReservation(Reservation reservation) {
        // Update the calendar to available
        calendar.removeMarkReserved(reservation.getCheckIn(), reservation.getCheckOut());

        // Remove the reservation from the list
        reservations.remove(reservation);
    }
}