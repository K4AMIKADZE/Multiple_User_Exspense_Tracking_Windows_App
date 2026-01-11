package ItemClases;

public class UserInfo {

    private int id;
    private String User;
    private String Password;
    private String User_Database;
    private String ProfillePic;
    private String Joined;
    private String Sector;
    private String Age;


    public UserInfo(int id, String user, String password, String user_Database, String profillePic, String joined,
            String sector, String age) {
        this.id = id;
        User = user;
        Password = password;
        User_Database = user_Database;
        ProfillePic = profillePic;
        Joined = joined;
        Sector = sector;
        Age = age;
    }


    public int getId() {
        return id;
    }


    public String getUser() {
        return User;
    }


    public String getPassword() {
        return Password;
    }


    public String getUser_Database() {
        return User_Database;
    }


    public String getProfillePic() {
        return ProfillePic;
    }


    public String getJoined() {
        return Joined;
    }


    public String getSector() {
        return Sector;
    }


    public String getAge() {
        return Age;
    }


    @Override
    public String toString() {
        return "UserInfo [id=" + id + ", User=" + User + ", Password=" + Password + ", User_Database=" + User_Database
                + ", ProfillePic=" + ProfillePic + ", Joined=" + Joined + ", Sector=" + Sector + ", Age=" + Age + "]";
    }


    


    

}
