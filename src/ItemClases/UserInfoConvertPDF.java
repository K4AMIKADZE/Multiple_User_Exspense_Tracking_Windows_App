package ItemClases;

public class UserInfoConvertPDF {
     private int id;
    private String Date;
    private String Type;
    private String Location;
    private String Currency;
    private String Transaction;
    private String Spent;



    public UserInfoConvertPDF(int id, String date, String type, String location, String currency, String transaction, String spent) {
        this.id = id;
        this.Date = date;
        this.Type = type;
        this.Location = location;
        this.Currency = currency;
        this.Transaction = transaction;
        this.Spent = spent;
    }



    public int getId() {
        return id;
    }



    public String getDate() {
        return Date;
    }



    public String getType() {
        return Type;
    }



    public String getLocation() {
        return Location;
    }



    public String getCurrency() {
        return Currency;
    }



    public String getTransaction() {
        return Transaction;
    }



    public String getSpent() {
        return Spent;
    }



    @Override
    public String toString() {
        return "test2 [id=" + id + ", Date=" + Date + ", Type=" + Type + ", Location=" + Location + ", Currency="
                + Currency + ", Transaction=" + Transaction + ", Spent=" + Spent + "]";
    }
}
