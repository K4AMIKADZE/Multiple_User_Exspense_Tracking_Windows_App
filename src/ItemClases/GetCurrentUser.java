package ItemClases;

public class GetCurrentUser {

    private String name;
    private String database;
    private String Sector;
    private String key;
    private int profilepicId;



    public GetCurrentUser(String name, String database,String Sector,String key, int profilepicId) {
        this.name = name;
        this.database = database;
        this.Sector = Sector;
        this.key = key;
        this.profilepicId = profilepicId;
    }



    public String getName() {
        return name;
    }



    public String getDatabase() {
        return database;
    }



    public int getProfilepicId() {
        return profilepicId;
    }



    public String getSector() {
        return Sector;
    }



    public String getKey() {
        return key;
    }

    

    
    

}
