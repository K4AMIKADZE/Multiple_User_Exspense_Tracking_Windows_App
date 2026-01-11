package ItemClases;

public class TerminalVariables {

    private int id;
    private String user;
    private String type;
    private String actionmessage;
    private String date;



    public TerminalVariables(int id, String user, String type, String actionmessage, String date) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.actionmessage = actionmessage;
        this.date = date;
    }



    public int getId() {
        return id;
    }



    public String getUser() {
        return user;
    }



    public String getType() {
        return type;
    }



    public String getActionmessage() {
        return actionmessage;
    }



    public String getDate() {
        return date;
    }



    @Override
    public String toString() {
        return "TerminalVariables [id=" + id + ", user=" + user + ", type=" + type + ", actionmessage=" + actionmessage
                + ", date=" + date + "]";
    }


    
    

    

}
