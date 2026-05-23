import java.util.ArrayList;

/**
 * PropertyManager class manages a collection of Property objects,
 * allowing addition, removal, and retrieval of properties.
 * It ensures property names are unique and handles reservations.
 */
public class PropertyManager {
    // List to store Property objects
    private ArrayList<Property> properties;

    /**
     * Constructor initializes the PropertyManager with an empty list of properties.
     */
    public PropertyManager() {
        this.properties = new ArrayList<>();
    }

    /**
     * Adds a new property if it is unique and not null.
     * 
     * @param property The Property object to be added.
     * @return         true if the property was added successfully, false otherwise.
     */
    public boolean addProperty(Property property) {

        if (property == null || !isNameUnique(property.getPropertyName()) || properties.contains(property)) {
            return false;                   // Property is not unique, already exists, or is null
        }
        return properties.add(property);    // Add property to the list and return the result
    }

    /**
     * Removes a property if it exists, is not null, and has no existing reservations.
     * 
     * @param property The Property object to be removed.
     * @return         true if the property was removed successfully, false otherwise.
     */
    public boolean removeProperty(Property property) {

        if (!properties.contains(property) || property == null || !property.getReservations().isEmpty()) {
            return false;                   // Property does not exist, is null, or has existing reservations
        }
        return properties.remove(property); // Remove property from the list and return the result
    }

    /**
     * Checks if a property name is unique (case-insensitive).
     * 
     * @param name The property name to check.
     * @return     true if the name is unique, false otherwise.
     */
    public boolean isNameUnique(String name) {
        for (Property property : properties) {
            if (property.getPropertyName().equalsIgnoreCase(name)) {
                return false;   // Name is not unique
            }
        }
        return true;            // Name is unique
    }

    /**
     * Retrieves a property by its name (case-insensitive).
     * 
     * @param name The name of the property to retrieve.
     * @return     The Property object if found, null otherwise.
     */
    public Property getPropertyByName(String name) {
        for (Property property : properties) {
            if (property.getPropertyName().equalsIgnoreCase(name)) {
                return property; // Return the matching property
            }
        }
        return null;             // No property found with the given name
    }

    /**
     * Retrieves the list of all properties.
     * 
     * @return An ArrayList of Property objects.
     */
    public ArrayList<Property> getProperties() {
        return new ArrayList<>(properties); // Return a copy of the properties list
    }
}