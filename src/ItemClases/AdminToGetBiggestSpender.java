package ItemClases;

public class AdminToGetBiggestSpender {

    private String name;
    private double amount;



    public AdminToGetBiggestSpender(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }



    public String getName() {
        return name;
    }



    public double getAmount() {
        return amount;
    }

    public void addinfo(double extra){
        amount+=extra;
    }

    

    

}
