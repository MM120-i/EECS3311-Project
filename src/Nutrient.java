public class Nutrient {
    public String name;
    public double amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Nutrient(String name, String amount) {
        this.name = name;
        this.amount = Double.parseDouble(amount);
    }
}
