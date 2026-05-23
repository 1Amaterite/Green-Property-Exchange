import java.util.ArrayList;

/**
 * Represents a single reservation made by a guest for a property.
 * It includes details such as the guest, property, check-in and check-out dates,
 * price breakdown, and total price.
 */
public class Reservation {

    /** Guest who made the reservation. */
    private Guest guest;
    /** Property to which is being reserved. */
    private Property property;
    /** Check-in Date. */
    private int checkIn;
    /** Check-out Date. */
    private int checkOut;
    /** Breakdown of the price per night of the stay. */
    private ArrayList<Double> priceBreakdown;
    /** Total price for the entire stay. */
    private double totalPrice;

    /**
     * The constructor to initialize a Reservation with guest, property,
     * check-in, and check-out dates.
     * 
     * @param guest     Guest who made the reservation.
     * @param property  Property being reserved.
     * @param checkIn   Check-in date.
     * @param checkOut  Check-out date.
     */
    public Reservation(Guest guest, Property property, int checkIn, int checkOut) {
        // Logic error prevention
        if (checkIn >= checkOut) {
            throw new IllegalArgumentException("Check-in date must be before check-out date.");
        }
        
        this.guest = guest;
        this.property = property;
        this.checkIn = checkIn;
        this.checkOut = checkOut;

        calculatePrice();
    }

    /**
     * This calculates both the breakdown of prices and the 
     * total price for the reservation.
     */
    private void calculatePrice() {
        this.priceBreakdown = new ArrayList<>();
        this.totalPrice = 0.0;

        for (int i = checkIn; i < checkOut; i++) {
            double nightlyPrice = property.getPricePerNight(i);

            priceBreakdown.add(nightlyPrice);
            totalPrice += nightlyPrice;
        }
    }

    // - Getters -

    /**
     * Getter for the guest who made the reservation.
     * 
     * @return The Guest object.
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * Getter for the property being reserved.
     * 
     * @return The Property object.
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Getter for the number of the check-in date.
     * 
     * @return The check-in date.
     */
    public int getCheckIn() {
        return checkIn;
    }

    /**
     * Getter for the number of the check-out date.
     * 
     * @return The check-out date.
     */
    public int getCheckOut() {
        return checkOut;
    }

    /**
     * Getter for the price breakdown per night.
     * 
     * @return A copy of the price breakdown.
     */
    public ArrayList<Double> getPriceBreakdown() {
        return new ArrayList<>(priceBreakdown);
    }

    /**
     * Getter for the total price of the reservation.
     * 
     * @return The total price as a double.
     */
    public double getTotalPrice() {
        return totalPrice;
    }
}
