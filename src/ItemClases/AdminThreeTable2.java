package ItemClases;

public class AdminThreeTable2 {

    private int id;
    private String user;
    private String joined;
    private String key;


    public AdminThreeTable2(int id, String user, String joined, String key) {
        this.id = id;
        this.user = user;
        this.joined = joined;
        this.key = key;
    }


    public int getId() {
        return id;
    }


    public String getUser() {
        return user;
    }


    public String getJoined() {
        return joined;
    }


    public String getKey() {
        return key;
    }


    @Override
    public String toString() {
        return "AdminThreeTable2 [id=" + id + ", user=" + user + ", joined=" + joined + ", key=" + key + "]";
    }

    

    
    

}
