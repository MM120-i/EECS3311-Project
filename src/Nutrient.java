/**
 * The Nutrient class represents a nutrient with a name and an amount.
*/
public class Nutrient {
    public String name;
    public double amount;

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
     * Constructs a Nutrient object with the given name and amount.
     *
     * @param name   the name of the nutrient
     * @param amount the amount of the nutrient
     */
    public Nutrient(String name, String amount) {
        this.name = name;
        this.amount = Double.parseDouble(amount);
    }
}
