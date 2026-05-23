/**
 * GreenResort class represents a type of property.
 */
public class GreenResort extends Property{

    /**
     * Constructor to initialize GreenResort with a property name.
     * 
     * @param propertyName The name of the property.
     */
    public GreenResort(String propertyName) {
        super(propertyName);
    }

    /**
     * Getter for the property multiplier.
     * 
     * @return The property multiplier as a double.
     */
    @Override
    public double getPropertyMultiplier() {
        return 1.35;
    }
}
