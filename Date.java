/**
 * Date class represents a specific day in a month with reservation status
 * and environmental modifier for pricing adjustments.
 */
public class Date {
    private int dayNumber;
    private boolean isReserved;
    private double environmentModifier; // 1.0 = 100%, 0.9 = 90%, 1.2 = 120% etc.

    /**
     * Constructor to initialize a Date object with a specific day number.
     * 
     * @param dayNumber The day number (1-30) for this date.
     */
    public Date(int dayNumber) {
        this.dayNumber = dayNumber;
        this.isReserved = false;
        this.environmentModifier = 1.0; // Default is 100%
    }

    // - Getters -

    /**
     * Getter for the day number.
     * 
     * @return The day number as an integer (1-30).
     */
    public int getDayNumber() {
        return dayNumber;
    }

    /**
     * Getter to check if the date is reserved.
     * 
     * @return True if the date is reserved, false otherwise.
     */
    public boolean isReserved() {
        return isReserved;
    }

    /**
     * Getter for the environmental modifier.
     * 
     * @return The environmental modifier as a double. 
     */
    public double getEnvironmentalModifier() {
        return environmentModifier;
    }

    // - Setters -

    /**
     * Setter to set the reservation status of the date.
     * This is used when a booking is made or canceled.
     * 
     * @param isReserved New reservation status, true to mark as reserved, false to mark as not reserved.
     */
    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    /**
     * Setter to set the environmental modifier.
     * The modifier must be between 0.8(80%) and 1.2(120%) (inclusive).
     * 
     * @param modifier Value to set the environmental modifier.
     * @return         True if the modifier is valid and set, false otherwise.
     */
    public boolean setEnvironmentalModifier(double modifier) {
        // Validity check for modifier
        if (modifier >= 0.8 && modifier <= 1.2) {
            this.environmentModifier = modifier;
            return true;
        } else {
            return false;
        }
    }
}
