package ItemClases;

public class MessageUsers {

    private int id;
    private String sender;
    private String message;
    private String date;


    public MessageUsers(int id, String sender, String message, String date) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }


    public int getId() {
        return id;
    }


    public String getSender() {
        return sender;
    }


    public String getMessage() {
        return message;
    }


    public String getDate() {
        return date;
    }
    
    
    

}
