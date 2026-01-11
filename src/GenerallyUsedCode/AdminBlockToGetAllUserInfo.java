package GenerallyUsedCode;
import java.util.ArrayList;
import javax.crypto.SecretKey;
import DBControllers.ReadingInformatioFromDB;
import ItemClases.AdminThreeTable2;
import ItemClases.MessageUsers;
import ItemClases.UserExspensesTable;
import ItemClases.UserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AdminBlockToGetAllUserInfo {

  
    
    // all user keys

    

    static ArrayList<UserInfo> AllInfo = new ArrayList<>();

    // for user to add remove items
    static ObservableList<UserExspensesTable> info = FXCollections.observableArrayList();
    static ObservableList<AdminThreeTable2> info2 = FXCollections.observableArrayList();

    





    // decrypt info for admin block to use to get statistics
    public static ArrayList<UserExspensesTable> getAllInfoForAdmin(){
        ArrayList<UserExspensesTable> AllInfromation = ReadingInformatioFromDB.GetCurrentUserTableViewStuff();
        ArrayList<UserExspensesTable> DecryptedExpensesOfUser = new ArrayList<>();
         ArrayList<SecretKey> keys = ReadingInformatioFromDB.GetAllUserKeys();
        DecryptedExpensesOfUser.clear();

 
    for(int j = 0; j < keys.size(); j++){

    for(int i = 0; i < AllInfromation.size(); i++){
            try {
            String date = EncryptionBlock.decrypt(AllInfromation.get(i).getDate(), keys.get(j));
            String type = EncryptionBlock.decrypt(AllInfromation.get(i).getType(), keys.get(j));
            String location = EncryptionBlock.decrypt(AllInfromation.get(i).getLocation(), keys.get(j));
            String currency = EncryptionBlock.decrypt(AllInfromation.get(i).getCurrency(), keys.get(j));
            String transaction = EncryptionBlock.decrypt(AllInfromation.get(i).getTrasactionmethod(), keys.get(j));
            String Spent = EncryptionBlock.decrypt(AllInfromation.get(i).getSpent(), keys.get(j));
            String User_database = EncryptionBlock.decrypt(AllInfromation.get(i).getUser_Database(), keys.get(j));
            String Sector = EncryptionBlock.decrypt(AllInfromation.get(i).getSector(), keys.get(j));
            DecryptedExpensesOfUser.add(new UserExspensesTable(AllInfromation.get(i).getId(), date, type, location, currency, transaction, Spent, User_database, Sector));
            
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }

            
            

    }

    return DecryptedExpensesOfUser;
}


public static ArrayList<UserInfo> DecryptAllInfo(){
   
     ArrayList<SecretKey> keys = ReadingInformatioFromDB.GetAllUserKeys();
    ArrayList<UserInfo> onlyusers = ReadingInformatioFromDB.ReadUserInfo();
    AllInfo.clear();
    for(int j = 0; j < keys.size(); j++){

    for(int i = 0; i < onlyusers.size(); i++){
            try {
            String user = EncryptionBlock.decrypt(onlyusers.get(i).getUser(), keys.get(j));
            String pass = "KYS";
            String database = EncryptionBlock.decrypt(onlyusers.get(i).getUser_Database(), keys.get(j));
            String pic = EncryptionBlock.decrypt(onlyusers.get(i).getProfillePic(), keys.get(j));
            String joined = EncryptionBlock.decrypt(onlyusers.get(i).getJoined(), keys.get(j));
            String sectord = EncryptionBlock.decrypt(onlyusers.get(i).getSector(), keys.get(j));
            String age = onlyusers.get(i).getAge();
            
            AllInfo.add(new UserInfo(onlyusers.get(i).getId(), user, pass, database, pic, joined, sectord, age));
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }
    }

    return AllInfo;
}



public static ObservableList<UserExspensesTable> getInfoForTableView(){
    ArrayList<UserExspensesTable> AllInfromation = ReadingInformatioFromDB.GetCurrentUserTableViewStuff();
    ArrayList<SecretKey> keys = ReadingInformatioFromDB.GetAllUserKeys();
    
    info.clear();
    for(int j = 0; j < keys.size(); j++){

    for(int i = 0; i < AllInfromation.size(); i++){
            try {
            String date = EncryptionBlock.decrypt(AllInfromation.get(i).getDate(), keys.get(j));
            String type = EncryptionBlock.decrypt(AllInfromation.get(i).getType(), keys.get(j));
            String location = EncryptionBlock.decrypt(AllInfromation.get(i).getLocation(), keys.get(j));
            String currency = EncryptionBlock.decrypt(AllInfromation.get(i).getCurrency(), keys.get(j));
            String transaction = EncryptionBlock.decrypt(AllInfromation.get(i).getTrasactionmethod(), keys.get(j));
            String Spent = EncryptionBlock.decrypt(AllInfromation.get(i).getSpent(), keys.get(j));
            String User_database = EncryptionBlock.decrypt(AllInfromation.get(i).getUser_Database(), keys.get(j));
            String Sector = EncryptionBlock.decrypt(AllInfromation.get(i).getSector(), keys.get(j));
            info.addAll(new UserExspensesTable(AllInfromation.get(i).getId(), date, type, location, currency, transaction, Spent, User_database, Sector));
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }
    }

    return info;
}



public static ObservableList<MessageUsers> getinfoForMessageTable2(String key){

    // for message block
     ObservableList<MessageUsers> message2 = FXCollections.observableArrayList();
     ArrayList<MessageUsers> messagearray2 = ReadingInformatioFromDB.ReadingForMessageTable2();
    
    SecretKey ke = EncryptionBlock.decodeKey(key);

    for(int i = 0; i < messagearray2.size(); i++){
        
            try {
            String sender = EncryptionBlock.decrypt(messagearray2.get(i).getSender(), ke);
            String message = EncryptionBlock.decrypt(messagearray2.get(i).getMessage(), ke);
            String date = EncryptionBlock.decrypt(messagearray2.get(i).getDate(), ke);
            
            message2.addAll(new MessageUsers(messagearray2.get(i).getId(), sender, message, date));
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }
    

    return message2;
}



public static ObservableList<AdminThreeTable2> getAdmingthree2tableinfo(){
    ArrayList<AdminThreeTable2> AllInfromation = ReadingInformatioFromDB.getInfoAboutUserTOremove();
    ArrayList<SecretKey> keys = ReadingInformatioFromDB.GetAllUserKeys();

   
    
    info2.clear();
    for(int j = 0; j < keys.size(); j++){

    for(int i = 0; i < AllInfromation.size(); i++){
            try {
            int id = AllInfromation.get(i).getId();
            String type = EncryptionBlock.decrypt(AllInfromation.get(i).getUser(), keys.get(j));
            String location = EncryptionBlock.decrypt(AllInfromation.get(i).getJoined(), keys.get(j));
            String currency = AllInfromation.get(i).getKey();
            
            
            info2.addAll(new AdminThreeTable2(id,type,location,currency));
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }
    }

   
    return info2;
}
 

public static ArrayList<Integer> getIDs(String key){

    // for message block
     ArrayList<UserExspensesTable> UserInfo = ReadingInformatioFromDB.GetCurrentUserTableViewStuff();
     ArrayList<Integer> id = new ArrayList<>();
    
    SecretKey ke = EncryptionBlock.decodeKey(key);

    for(int i = 0; i < UserInfo.size(); i++){
        
            try {
            String sender = EncryptionBlock.decrypt(UserInfo.get(i).getCurrency(), ke);
            
            id.add(UserInfo.get(i).getId());
            
            
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }
    

    return id;
}

public static ArrayList<Integer> getMessageID(String key){

    // for message block
     ArrayList<MessageUsers> UserInfo = ReadingInformatioFromDB.ReadingForMessageTable2();
     ArrayList<Integer> id = new ArrayList<>();
    
    SecretKey ke = EncryptionBlock.decodeKey(key);

    for(int i = 0; i < UserInfo.size(); i++){
        
            try {
            String sender = EncryptionBlock.decrypt(UserInfo.get(i).getMessage(), ke);
            
            id.add(UserInfo.get(i).getId());
            
            
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }
    

    return id;
}



   
    


}
