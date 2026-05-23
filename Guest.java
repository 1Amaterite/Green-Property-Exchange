/**
 * Class representing a guest with a name.
 */
public class Guest {
    /** The name of the guest. */
    private String name;

    /**
     * Constructor to initialize a Guest with a name.
     * 
     * @param name The name of the guest.
     */
    public Guest(String name) {
        // Logic error prevention
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be empty.");
        }

        // Trim whitespace
        String trimmedName = name.trim();

        // Capitalize the first letter and set the name
        this.name = trimmedName.substring(0, 1).toUpperCase() + trimmedName.substring(1);
    }

    /**
     * Getter for the name of the guest.
     * 
     * @return The name of the guest as a String.
     */
    public String getName() {
        return name;
    }
}