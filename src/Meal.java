import java.time.LocalDate;
import java.util.ArrayList;

public class Meal {
    private int mealID;
    private LocalDate date;
    private int mealType; // 1 = breakfast, 2 = lunch, 3 = dinner, 4= snack
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public int getMealID() {
        return mealID;
    }

    public void setMealID(int mealID) {
        this.mealID = mealID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMealType() {
        return mealType;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
