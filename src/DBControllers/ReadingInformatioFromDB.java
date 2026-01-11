package DBControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.crypto.SecretKey;

import GenerallyUsedCode.EncryptionBlock;
import GenerallyUsedCode.Logic;
import ItemClases.AdminThreeTable2;
import ItemClases.DataBaseVariables;
import ItemClases.MessageUsers;
import ItemClases.Settings;
import ItemClases.TerminalVariables;
import ItemClases.UserExspensesTable;
import ItemClases.UserInfo;
import ItemClases.UserSearch;

public class ReadingInformatioFromDB {

    

    public static ArrayList<UserInfo> ReadUserInfo(){
        ArrayList<DataBaseVariables> info = new ArrayList<>();
        info = Logic.ReadDatabaseInformation();
        ArrayList<UserInfo> UserInformation = new ArrayList<>();
        UserInformation.clear();
        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT Id, User, Password, User_Database, ProfillePic, Joined, Sector, Age   FROM userinfo.users";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                UserInfo items = new UserInfo(
                
                rs.getInt("Id"),
                rs.getString("User"),
                rs.getString("Password"),
                rs.getString("User_Database"),
                rs.getString("ProfillePic"),
                rs.getString("Joined"),
                rs.getString("Sector"),
                rs.getString("Age")


                );
                UserInformation.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return UserInformation;

    }


    public static ArrayList<UserSearch> GetUserToCheckSignIn(){
        ArrayList<DataBaseVariables> info = new ArrayList<>();
        info = Logic.ReadDatabaseInformation();
        
        ArrayList<UserSearch> UserInformation = new ArrayList<>();
        UserInformation.clear();

        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT Id, User, Password,User_Database,ProfillePic,Sector, Age FROM userinfo.users";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                UserSearch items = new UserSearch(
                
                rs.getInt("Id"),
                rs.getString("User"),
                rs.getString("Password"),
                rs.getString("User_Database"),
                rs.getString("ProfillePic"),
                rs.getString("Sector"),
                rs.getString("Age")
          


                );
                UserInformation.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
        return UserInformation;
    
    }


    public static ArrayList<UserExspensesTable> GetCurrentUserTableViewStuff(){
        ArrayList<DataBaseVariables> info = new ArrayList<>();
        info = Logic.ReadDatabaseInformation();
        
        ArrayList<UserExspensesTable> UserInformation = new ArrayList<>();
        UserInformation.clear();

        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT * FROM useractivity.userexspenses;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                UserExspensesTable items = new UserExspensesTable(
                
                rs.getInt("Id"),
                rs.getString("Date"),
                rs.getString("Type"),
                rs.getString("Location"),
                rs.getString("Currency"),
                rs.getString("TransMethod"),
                rs.getString("Spent"),
                rs.getString("User_Database"),
                rs.getString("Sector")

                );
                UserInformation.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
        return UserInformation;
    
    }


     public static ArrayList<SecretKey> GetAllUserKeys(){
        ArrayList<DataBaseVariables> info = new ArrayList<>();
        info = Logic.ReadDatabaseInformation();
        
        ArrayList<SecretKey> UserInformation = new ArrayList<>();
        UserInformation.clear();

        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT Age FROM userinfo.users;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String items = new String(
                
                rs.getString("Age")
                
                );
                UserInformation.add(EncryptionBlock.decodeKey(items));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
        return UserInformation;
    
    }


    public static ArrayList<MessageUsers> ReadingForMessageTable2(){
        ArrayList<DataBaseVariables> info = new ArrayList<>();
        info = Logic.ReadDatabaseInformation();
        
        ArrayList<MessageUsers> UserInformation = new ArrayList<>();
        UserInformation.clear();

        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT Id,Sender,Message,Arrived FROM useractivity.messages;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                MessageUsers items = new MessageUsers(
                
                rs.getInt("Id"),
                rs.getString("Sender"),
                rs.getString("Message"),
                rs.getString("Arrived")
          

                );
                UserInformation.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
        return UserInformation;
    
    }


       public static ArrayList<TerminalVariables> ReadTerminal(){
        ArrayList<DataBaseVariables> info = new ArrayList<>();
        info = Logic.ReadDatabaseInformation();
        
        ArrayList<TerminalVariables> UserInformation = new ArrayList<>();
        UserInformation.clear();

        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT * FROM useractivity.terminalactions;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TerminalVariables items = new TerminalVariables(
                
                rs.getInt("Id"),
                rs.getString("User"),
                rs.getString("Type"),
                rs.getString("ActionMessage"),
                rs.getString("Date")
          

                );
                UserInformation.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
        return UserInformation;
    
    }

    // get admin key


    public static String getAdminKey(){
       ArrayList<DataBaseVariables> info = new ArrayList<>();
       info = Logic.ReadDatabaseInformation();
        
       String key = "";

        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT Age FROM userinfo.users where Id = 1;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String items = new String(
                
                
                rs.getString("Age")
               
          

                );
                 key = items;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
        return key;
    
    }



      public static ArrayList<AdminThreeTable2> getInfoAboutUserTOremove(){
       ArrayList<DataBaseVariables> info = new ArrayList<>();
       info = Logic.ReadDatabaseInformation();
       ArrayList<AdminThreeTable2> USERinfo = new ArrayList<>();
        

        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT id, User_Database, Joined, Age FROM userinfo.users WHERE id <> 1;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                AdminThreeTable2 items = new AdminThreeTable2(
                
                
                rs.getInt("id"),
                rs.getString("User_Database"),
                rs.getString("Joined"),
                rs.getString("Age")
          

                );
                 USERinfo.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
        return USERinfo;
    
    }



    public static ArrayList<Settings> getSettings(){
       ArrayList<DataBaseVariables> info = new ArrayList<>();
       info = Logic.ReadDatabaseInformation();
       ArrayList<Settings> USERinfo = new ArrayList<>();
        USERinfo.clear();

        if (info == null || info.isEmpty()) {
        
        return USERinfo; 
    }
        try (Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

           
            String query = "SELECT * FROM userinfo.settings;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Settings items = new Settings(
                
                
                rs.getInt("id"),
                rs.getString("SettingName"),
                rs.getString("SettingValue")
                
          

                );
                 USERinfo.add(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return USERinfo;
        
    }

 
    

}
