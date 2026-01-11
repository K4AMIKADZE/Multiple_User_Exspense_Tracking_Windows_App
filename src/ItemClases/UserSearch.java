package ItemClases;

public class UserSearch {

    private int id;
    private String User;
    private String password;
    private String Data_base;
    private String profileID;
    private String Sector;
    private String key;


    public UserSearch(int id, String user, String password,String Data_base,String profileID, String Sector, String key) {
        this.id = id;
        this.User = user;
        this.password = password;
        this.Data_base = Data_base;
        this.profileID = profileID;
        this.Sector = Sector;
        this.key = key;
    }


    public int getId() {
        return id;
    }


    public String getUser() {
        return User;
    }


    public String getPassword() {
        return password;
    }


    public String getKey() {
        return key;
    }
    
    public String getData_base() {
        return Data_base;
    }

    public String getProfileID() {
        return profileID;
    }

      public String getSector() {
        return Sector;
    }
    

    @Override
    public String toString() {
        return "UserSearch [id=" + id + ", User=" + User + ", password=" + password + ", Data_base=" + Data_base
                + ", profileID=" + profileID + ", key=" + key + "]";
    }


  


}
