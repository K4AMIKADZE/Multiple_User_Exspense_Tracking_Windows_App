package DBControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import GenerallyUsedCode.Logic;
import ItemClases.DataBaseVariables;

public class AddingInformationToTheDatabase {

    // for storing db info
   static ArrayList<DataBaseVariables> info = new ArrayList<>();

    // isert users into Database
    public static void AddUserIntoDatabase(String user, String Password, String Database, String profilePic, String Joined, String sector, String Age){

        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "INSERT INTO `userinfo`.`users`(`User`,`Password`,`User_Database`,`ProfillePic`,`Joined`,`Sector`,`Age`)VALUES(?,?,?,?,?,?,?);";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, Password);
            ps.setString(3, Database);
            ps.setString(4, profilePic);
            ps.setString(5, Joined);
            ps.setString(6, sector);
            ps.setString(7, Age);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void AddUserExpenses(String Date, String Type, String Location, String Currency, String Transmethod, String spent, String database, String sector){

        info = Logic.ReadDatabaseInformation();
        

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "INSERT INTO `useractivity`.`userexspenses`(`Date`,`Type`,`Location`,`Currency`,`TransMethod`,`Spent`,`User_Database`,`Sector`)VALUES(?,?,?,?,?,?,?,?);";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, Date);
            ps.setString(2, Type);
            ps.setString(3, Location);
            ps.setString(4, Currency);
            ps.setString(5, Transmethod);
            ps.setString(6, spent);
            ps.setString(7, database);
            ps.setString(8, sector);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void insertMessage(String sender, String receiver, String Message, String arrived){

        ArrayList<DataBaseVariables> info2 = new ArrayList<>();
        info2 = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info2.get(0).getUrl(), info2.get(0).getUser(), info2.get(0).getPassword())) {
            String query = "INSERT INTO `useractivity`.`messages`(`Sender`,`Receiver`,`Message`,`Arrived`) VALUES (?,?,?,?);";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, Message);
            ps.setString(4, arrived);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }



     public static void insertTerminalMessage(String user, String type, String actionmessage, String date){

        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "INSERT INTO `useractivity`.`terminalactions`(`User`,`Type`,`ActionMessage`,`Date`)VALUES(?,?,?,?);";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, type);
            ps.setString(3, actionmessage);
            ps.setString(4, date);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public static void InsertSettings(){

        info = Logic.ReadDatabaseInformation();
        String[] settingname = {"Allow User Remove Items", "Allow Database edit", "Allow new registration", "Allow user connect", "Usd to Eur Convertion", "Rub to Eur Conversion", "Gbp to Eur Conversion"};
        String[] settingvalue = {"False", "True","True", "True", "0.93", "0.011", "1.15"};

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "INSERT INTO `userinfo`.`settings` (`SettingName`, `SettingValue`) " +
               "SELECT ?, ? FROM DUAL WHERE NOT EXISTS " +
               "(SELECT 1 FROM `userinfo`.`settings` WHERE SettingName = ?)";

            PreparedStatement ps = con.prepareStatement(query);
            for(int i = 0; i < settingname.length; i++){
            ps.setString(1, settingname[i]);
            ps.setString(2, settingvalue[i]);
            ps.setString(3, settingname[i]);
            ps.executeUpdate();
            }
           
            

        } catch (Exception e) {
            System.out.println(e);
        }

    }


 



    

}
