import java.util.Date;

public class User {

    private int ID;
    private String firstName;
    private String lastName;

    private int isMale; // male is 1 female is 0

    private Date dob;
    private int height;
    private int weight;

    private int prefersMetric;

    private int BMR;

    public int getBMR() {
        return BMR;
    }

    public void setBMR(int BMR) {
        this.BMR = BMR;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int isMale() {
        return isMale;
    }

    public void setMale(int male) {
        isMale = male;
    }

    public int getIsMale() {
        return isMale;
    }

    public void setIsMale(int isMale) {
        this.isMale = isMale;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrefersMetric() {
        return prefersMetric;
    }

    public void setPrefersMetric(int prefersMetric) {
        this.prefersMetric = prefersMetric;
    }
}
