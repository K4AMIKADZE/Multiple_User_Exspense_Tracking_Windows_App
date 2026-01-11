package ItemClases;

public class UniqueCurrency {
    private String CurrencyName;
    private double amount;


    public UniqueCurrency(String currencyName, double amount) {
        CurrencyName = currencyName;
        this.amount = amount;
    }


    public String getCurrencyName() {
        return CurrencyName;
    }


    public double getAmount() {
        return amount;
    }

    public void addnums(double extra){
        amount += extra;
    }


    @Override
    public String toString() {
        return "UniqueCurrency [CurrencyName=" + CurrencyName + ", amount=" + amount + "]";
    }
}
