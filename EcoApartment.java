/**
 * EcoApartment class represents a type of property.
 */
public class EcoApartment extends Property {

    /**
     * Constructor to initialize EcoApartment with a property name.
     * 
     * @param propertyName The name of the property.
     */
    public EcoApartment(String propertyName) {
        super(propertyName);
    }

    /**
     * Getter for the property multiplier.
     * 
     * @return The property multiplier as a double.
     */
    @Override
    public double getPropertyMultiplier() {
        return 1.00;
    }
}
