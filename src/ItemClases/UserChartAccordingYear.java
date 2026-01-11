package ItemClases;

public class UserChartAccordingYear {

    private String date;
    private double amount;

    
    public UserChartAccordingYear(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }


    public String getDate() {
        return date;
    }


    public double getAmount() {
        return amount;
    }

    public void addamount(double extra){
        amount += extra;
    }

    

}
