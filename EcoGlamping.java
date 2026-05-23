/**
 * EcoGlamping class represents a type of property.
 */
public class EcoGlamping extends Property {

    /**
     * Constructor to initialize EcoGlamping with a property name.
     * 
     * @param propertyName The name of the property.
     */
    public EcoGlamping(String propertyName) {
        super(propertyName);
    }

    /**
     * Getter for the property multiplier.
     * 
     * @return The property multiplier as a double.
     */
    @Override
    public double getPropertyMultiplier() {
        return 1.50;
    }
    
}
