package GenerallyUsedCode;

import java.util.ArrayList;

import javax.crypto.SecretKey;

import DBControllers.ReadingInformatioFromDB;
import ItemClases.GetCurrentUser;
import ItemClases.UserExspensesTable;
import ItemClases.UserInfoConvertPDF;
import ItemClases.UserInfoDisplayTableVIew;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GetCurrentUsersInformation {

    static ArrayList<UserExspensesTable> ExpensesOfUser = new ArrayList<>();
    static ArrayList<UserExspensesTable> CurrentUserInfo = new ArrayList<>();

    // for user to add remove items
    static ObservableList<UserInfoDisplayTableVIew> info = FXCollections.observableArrayList();
    static ArrayList<UserInfoConvertPDF> infoforPDF = new ArrayList<>();
    // for program to calculate averages etc and put info into charts
    static ArrayList<UserInfoDisplayTableVIew> ChartsInformation = new ArrayList<>();

  







    public static ObservableList<UserInfoDisplayTableVIew> Readinfo() throws Exception{
        
        ExpensesOfUser.clear();
        
        info.clear();
        // get current user info
     ArrayList<GetCurrentUser> CurrentUser = Logic.ReadCurrentUsersInfoToTxtFile();
        
        ExpensesOfUser = ReadingInformatioFromDB.GetCurrentUserTableViewStuff();
        
        SecretKey key = EncryptionBlock.decodeKey(CurrentUser.get(0).getKey());

        for(int i = 0; i < ExpensesOfUser.size(); i++){

            try {
            String date = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getDate(), key);
            String type = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getType(), key);
            String location = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getLocation(), key);
            String currency = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getCurrency(), key);
            String transaction = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getTrasactionmethod(), key);
            String Spent = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getSpent(), key);
             info.add(new UserInfoDisplayTableVIew(ExpensesOfUser.get(i).getId(),date, type, location, currency, transaction, Spent));
            
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }

        return info;
    }

    public static ArrayList<UserInfoConvertPDF> ReadinfoForPDF() throws Exception{
        
        ExpensesOfUser.clear();
        
        info.clear();
        // get current user info
     ArrayList<GetCurrentUser> CurrentUser = Logic.ReadCurrentUsersInfoToTxtFile();
        
        ExpensesOfUser = ReadingInformatioFromDB.GetCurrentUserTableViewStuff();
        
        SecretKey key = EncryptionBlock.decodeKey(CurrentUser.get(0).getKey());

        for(int i = 0; i < ExpensesOfUser.size(); i++){

            try {
            String date = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getDate(), key);
            String type = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getType(), key);
            String location = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getLocation(), key);
            String currency = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getCurrency(), key);
            String transaction = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getTrasactionmethod(), key);
            String Spent = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getSpent(), key);
             infoforPDF.add(new UserInfoConvertPDF(ExpensesOfUser.get(i).getId(), date, type, location, currency, transaction, Spent));
            
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }

        return infoforPDF;
    }



     public static ArrayList<UserInfoDisplayTableVIew> GetInfoForCharsUser() throws Exception{
        
        ExpensesOfUser.clear();
        ChartsInformation.clear();
          // get current user info
     ArrayList<GetCurrentUser> CurrentUser = Logic.ReadCurrentUsersInfoToTxtFile();
        
        ExpensesOfUser = ReadingInformatioFromDB.GetCurrentUserTableViewStuff();
        
        SecretKey key = EncryptionBlock.decodeKey(CurrentUser.get(0).getKey());
        

        for(int i = 0; i < ExpensesOfUser.size(); i++){

            try {
            String date = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getDate(), key);
            String type = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getType(), key);
            String location = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getLocation(), key);
            String currency = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getCurrency(), key);
            String transaction = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getTrasactionmethod(), key);
            String Spent = EncryptionBlock.decrypt(ExpensesOfUser.get(i).getSpent(), key);
            ChartsInformation.add(new UserInfoDisplayTableVIew(ExpensesOfUser.get(i).getId(),date, type, location, currency, transaction, Spent));
        } catch (javax.crypto.BadPaddingException e) {
         // Catches if wrong key so diffrent user cant decrypt other user information
        } catch (Exception e) {
           e.printStackTrace();
        }
        }

        return ChartsInformation;
    }



}
