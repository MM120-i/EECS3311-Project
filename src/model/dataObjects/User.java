package model.dataObjects;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * The type model.dataObjects.User.
 */
public class User {


    private int ID;
    private String name;

    private int isMale; // male is 1 female is 0

    private LocalDate dob;
    private int height;
    private int weight;

    private int prefersMetric;

    private double BMR;
    private ArrayList<Meal> meals = new ArrayList<>();

    /**
     * Sets is male.
     *
     * @param isMale the is male
     */
    public void setIsMale(int isMale) {
        this.isMale = isMale;
    }

    /**
     * Gets meals.
     *
     * @return the meals
     */
    public ArrayList<Meal> getMeals() {
        return meals;
    }

    /**
     * Sets meals.
     *
     * @param meals the meals
     */
    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    /**
     * Gets bmr.
     *
     * @return the bmr
     */
    public double getBMR() {
        return BMR;
    }

    /**
     * Sets bmr.
     *
     * @param BMR the bmr
     */
    public void setBMR(double BMR) {
        this.BMR = BMR;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets id.
     *
     * @param ID the id
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name thename
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets male.
     *
     * @param male the male
     */
    public void setMale(int male) {
        isMale = male;
    }

    /**
     * Gets is male.
     *
     * @return the is male
     */
    public int getIsMale() {
        return isMale;
    }


    /**
     * Gets dob.
     *
     * @return the dob
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Sets dob.
     *
     * @param dob the dob
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gets prefers metric.
     *
     * @return the prefers metric
     */
    public int getPrefersMetric() {
        return prefersMetric;
    }

    /**
     * Sets prefers metric.
     *
     * @param prefersMetric the prefers metric
     */
    public void setPrefersMetric(int prefersMetric) {
        this.prefersMetric = prefersMetric;
    }

    /**
     * Instantiates a new model.dataObjects.User.
     *
     * @param name          the name
     * @param isMale        the is male
     * @param dob           the dob
     * @param height        the height
     * @param weight        the weight
     * @param prefersMetric the prefers metric
     * @param BMR           the bmr
     */
    public User(String name, int isMale, LocalDate dob, int height, int weight, int prefersMetric, int BMR) {
        this.name = name;
        this.isMale = isMale;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.prefersMetric = prefersMetric;
        this.BMR = BMR;
    }

    /**
     * Instantiates a new model.dataObjects.User.
     */
    public User() {
        this.name = null;
        this.isMale = 1;
        this.dob = LocalDate.now();
        this.height = 0;
        this.weight = 0;
        this.prefersMetric = 0;
        this.BMR = 0;
    }

    /**
     * Calculate bmr.
     */
    public void calculateBMR() {
        LocalDate currentDate = LocalDate.now();
        int age = (Period.between(getDob(), currentDate)).getYears();
        if (prefersMetric == 0) {
            if (isMale == 1) {
                setBMR(10 * getWeight() + 6.25 * getHeight() - 5 * (Period.between(getDob(), currentDate)).getYears());
            } else {
                setBMR(10 * getWeight() + 6.25 * getHeight() - 5 * (Period.between(getDob(), currentDate)).getYears() - 161);
            }
        } else {
            if (isMale == 1) {
                setBMR(4.53592 * getWeight() + 15.875 * getHeight() - 5 * (Period.between(getDob(), currentDate)).getYears());
            } else {
                setBMR(4.53592 * getWeight() + 15.875 * getHeight() - 5 * (Period.between(getDob(), currentDate)).getYears() - 161);
            }
        }
    }

    /**
     * Instantiates a new User.
     *
     * @param name the name
     */
    public User(String name) {
        this.name = name;
    }
}




