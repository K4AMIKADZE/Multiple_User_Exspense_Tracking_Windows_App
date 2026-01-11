package ItemClases;

public class DataBaseVariables {
    
    private String url;
    private String user;
    private String password;
    private String key;


    public DataBaseVariables(String url, String user, String password, String key) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.key = key;
    }


    public String getUrl() {
        return url;
    }


    public String getUser() {
        return user;
    }


    public String getPassword() {
        return password;
    }


    public String getKey() {
        return key;
    }

    

    

}
