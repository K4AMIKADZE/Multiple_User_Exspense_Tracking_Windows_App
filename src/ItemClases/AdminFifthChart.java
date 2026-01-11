package ItemClases;



public class AdminFifthChart {

    private String date;
    private double amount;


    public AdminFifthChart(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }


    public String getDate() {
        return date;
    }


    public double getAmount() {
        return amount;
    }

    public void add(double extra){
        amount+=extra;
    }


    @Override
    public String toString() {
        return "AdminFifthChart [date=" + date + ", amount=" + amount + "]";
    }


    

    

    
}
