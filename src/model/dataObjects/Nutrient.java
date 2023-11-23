package model.dataObjects;

/**
 * The model.dataObjects.Nutrient class represents a nutrient with a name and an amount.
 */
public class Nutrient {
    private String name;
    private double amount;

    private String unit;


    /**
     * Get the name of the nutrient.
     *
     * @return the name of the nutrient
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the nutrient.
     *
     * @param name the name of the nutrient
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the amount of the nutrient.
     *
     * @return the amount of the nutrient
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Set the amount of the nutrient.
     *
     * @param amount the amount of the nutrient
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Constructs a model.dataObjects.Nutrient object with the given name and amount.
     *
     * @param name   the name of the nutrient
     * @param amount the amount of the nutrient
     * @param unit   the unit
     */
    public Nutrient(String name, double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

}
