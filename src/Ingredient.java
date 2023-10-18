/**
 * The type Ingredient.
 */
public class Ingredient {
    private int ingredientNum;
    private double amount;

    /**
     * Gets ingredient num.
     *
     * @return the ingredient num
     */
    public int getIngredientNum() {
        return ingredientNum;
    }

    /**
     * Sets ingredient num.
     *
     * @param ingredientNum the ingredient num
     */
    public void setIngredientNum(int ingredientNum) {
        this.ingredientNum = ingredientNum;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Instantiates a new Ingredient.
     *
     * @param ingredientNum the ingredient num
     * @param amount        the amount
     */
    public Ingredient(int ingredientNum, double amount) {
        this.ingredientNum = ingredientNum;
        this.amount = amount;
    }
}
