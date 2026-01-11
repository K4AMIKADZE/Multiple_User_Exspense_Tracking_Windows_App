package ItemClases;

public class UserExspensesTable {

    private int id;
    private String date;
    private String type;
    private String location;
    private String currency;
    private String trasactionmethod;
    private String spent;
    private String User_Database;
    private String Sector;


    public UserExspensesTable(int id, String date, String type, String location, String currency,
            String trasactionmethod, String spent, String user_Database, String sector) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.location = location;
        this.currency = currency;
        this.trasactionmethod = trasactionmethod;
        this.spent = spent;
        this.User_Database = user_Database;
        this.Sector = sector;
    }


    public int getId() {
        return id;
    }


    public String getDate() {
        return date;
    }


    public String getType() {
        return type;
    }


    public String getLocation() {
        return location;
    }


    public String getCurrency() {
        return currency;
    }


    public String getTrasactionmethod() {
        return trasactionmethod;
    }


    public String getSpent() {
        return spent;
    }


    public String getUser_Database() {
        return User_Database;
    }


    public String getSector() {
        return Sector;
    }


    @Override
    public String toString() {
        return "UserExspensesTable [id=" + id + ", date=" + date + ", type=" + type + ", location=" + location
                + ", currency=" + currency + ", trasactionmethod=" + trasactionmethod + ", spent=" + spent
                + ", User_Database=" + User_Database + ", Sector=" + Sector + "]";
    }

    
    


}
