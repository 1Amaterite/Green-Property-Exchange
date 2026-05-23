/**
 * Represents a sustainable house property with a specific multiplier.
 */
public class SustainableHouse extends Property {
    
    /**
     * Constructor for SustainableHouse.
     * 
     * @param propertyName The name of the sustainable house property.
     */
    public SustainableHouse(String propertyName) {
        super(propertyName);
    }

    /**
     * Gets the property multiplier for a sustainable house.
     * 
     * @return The multiplier value of 1.20.
     */
    @Override
    public double getPropertyMultiplier() {
        return 1.20;
    }
    
}
