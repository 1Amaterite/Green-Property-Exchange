import java.util.ArrayList;
import java.util.Random;

/**
 * Calendar class to manage a list of 30 dates.
 * Each date can be marked as reserved or available.
 */
public class Calendar {
    /**
     * Stores the list of 30 dates in the calendar.
     */
    private ArrayList<Date> dates;
    private final int startDay;

    /**
     * Constructor to initialize Calendar with a random start day (0-6).
     */
    public Calendar() {
        this(new Random().nextInt(7));
    }

    /**
     * Constructor to initialize Calendar with a specified start day.
     * 
     * @param startDay The starting day of the week (0 = Sun, 6 = Sat).
     */
    public Calendar(int startDay) {
        this.startDay = startDay;
        dates = new ArrayList<>();

        // Initialize for 30 days
        for (int i = 1; i <= 30; i++) {
            dates.add(new Date(i));
        }
    }

    // - Getters -

    /**
     * Getter for the list of dates in the calendar.
     * 
     * @return ArrayList of 30 Date objects.
     */
    public ArrayList<Date> getDates() {
        return dates;
    }

    /**
     * Getter for the starting day of the week.
     * 
     * @return The starting day as an integer (0 = Sun, 6 = Sat).
     */
    public int getStartDay() {
        return startDay;
    }

    /**
     * Getter for a specific date by day number.
     * 
     * @param dayNumber The day number (1-30).
     * @return          The Date object for the specified day, or null if invalid.
     */
    public Date getDate(int dayNumber) {
        // Validity for day number
        if (dayNumber >= 1 && dayNumber <= 30) {
            return dates.get(dayNumber - 1);
        }
        return null; // Null if invalid number
    }

    /**
     * Counts the number of available (not reserved) dates in the calendar.
     * 
     * @return The count of available dates as an integer.
     */
    public int countAvailableDates() {
        int count = 0;
        for (Date date : dates) {
            if (!date.isReserved()) {
                count++;
            }
        }
        return count;
    }

    // - Booking Logic -

    /**
     * Marks a range of dates as reserved.
     * Loop runs from checkIn to checkOut.
     * 
     * @param checkIn  Check-In date.
     * @param checkOut Check-Out date.
     */
    public void markReserved(int checkIn, int checkOut) {
        for (int i = checkIn; i < checkOut; i++) {
            Date date = getDate(i);
            if (date != null) {
                date.setReserved(true);
            }
        }
    }

    /**
     * Removes the reserved mark from a range of dates.
     * Loop runs from checkIn to checkOut.
     * 
     * @param checkIn  Check-In date.
     * @param checkOut Check-Out date.
     */
    public void removeMarkReserved(int checkIn, int checkOut) {
        for (int i = checkIn; i < checkOut; i++) {
            Date date = getDate(i);
            if (date != null) {
                date.setReserved(false);
            }
        }
    }

    /**
     * Checks if a range of dates is available (not reserved).
     * Loop runs from start to end.
     * 
     * @param start Check-In date.
     * @param end   Check-Out date.
     * @return      True if all dates in the range are available, false otherwise.
     */
    public boolean isRangeAvailable(int start, int end) {
        if (start < 1 || end > 31 || start >= end) {
            return false; // Invalid range
        }

        for (int i = start; i < end; i++) {
            Date date = getDate(i);
            // Check if date is null or reserved
            if (date == null || date.isReserved()) {
                return false;
            }
        }
        return true;
    }
}