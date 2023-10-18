import java.util.Date;

/**
 * The type User.
 */
public class User {

    private int ID;
    private String name;

    private int isMale; // male is 1 female is 0

    private Date dob;
    private int height;
    private int weight;

    private int prefersMetric;

    private int BMR;

    /**
     * Gets bmr.
     *
     * @return the bmr
     */
    public int getBMR() {
        return BMR;
    }

    /**
     * Sets bmr.
     *
     * @param BMR the bmr
     */
    public void setBMR(int BMR) {
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
    public Date getDob() {
        return dob;
    }

    /**
     * Sets dob.
     *
     * @param dob the dob
     */
    public void setDob(Date dob) {
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

    public User(String name, int isMale, Date dob, int height, int weight, int prefersMetric, int BMR) {
        this.name = name;
        this.isMale = isMale;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.prefersMetric = prefersMetric;
        this.BMR = BMR;
    }

    public User() {
        this.name = null;
        this.isMale = 1;
        this.dob = new Date();
        this.height = 0;
        this.weight = 0;
        this.prefersMetric = 0;
        this.BMR = 0;
    }
}




