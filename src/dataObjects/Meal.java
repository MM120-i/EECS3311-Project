package dataObjects;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The type dataObjects.Meal.
 */
public class Meal {
    private int mealID;
    private LocalDate date;
    private int mealType; // 1 = breakfast, 2 = lunch, 3 = dinner, 4= snack
    private ArrayList<Ingredient> ingredients = new ArrayList<>();


    /**
     * Gets meal id.
     *
     * @return the meal id
     */
    public int getMealID() {
        return mealID;
    }

    /**
     * Sets meal id.
     *
     * @param mealID the meal id
     */
    public void setMealID(int mealID) {
        this.mealID = mealID;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets meal type.
     *
     * @return the meal type
     */
    public int getMealType() {
        return mealType;
    }

    /**
     * Sets meal type.
     *
     * @param mealType the meal type
     */
    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    /**
     * Gets ingredients.
     *
     * @return the ingredients
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Sets ingredients.
     *
     * @param ingredients the ingredients
     */
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Instantiates a new dataObjects.Meal.
     *
     * @param mealID      the meal id
     * @param date        the date
     * @param mealType    the meal type
     * @param ingredients the ingredients
     */
    public Meal(int mealID, LocalDate date, int mealType, ArrayList<Ingredient> ingredients) {
        this.mealID = mealID;
        this.date = date;
        this.mealType = mealType;
        this.ingredients = ingredients;
    }

    public Meal() {
    }
}
