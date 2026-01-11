package DBControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKey;

import GenerallyUsedCode.EncryptionBlock;
import GenerallyUsedCode.Logic;
import ItemClases.DataBaseVariables;

public class CreatingChemesInDB {

    static ArrayList<DataBaseVariables> info = new ArrayList<>();


    // call all method in one

    public static void DatabaseAllInOne() throws Exception{
        
        String status  = CheckConnectioForDB.CheckDBStatus();

        if(status.equalsIgnoreCase("online")){
        CreateMainScheme();
        CreateMainTable();
        CreateMainSchemeForStoringUserInputs();
        CreateTablesForStoringUsersMessages();
        CreateTablesForStoringUsersExspenses();
        CreateTablesForStoringUsersTerminal();
        CreateSettingTable();
        AddingInformationToTheDatabase.InsertSettings();
        AddAdminIfNotExists();
        }
       
        
    }

   

    // sign in logic
    public static void CreateMainScheme(){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "CREATE DATABASE IF NOT EXISTS `UserInfo`";

            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void CreateMainTable(){

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

            String query = "CREATE TABLE IF NOT EXISTS `UserInfo`.`Users` (`Id` INT NOT NULL AUTO_INCREMENT,`User` VARCHAR(120) NOT NULL,`Password` VARCHAR(120) NOT NULL,`User_Database` VARCHAR(120) NOT NULL,`ProfillePic` VARCHAR(120) NOT NULL,`Joined` VARCHAR(120) NOT NULL,`Sector` VARCHAR(120) NOT NULL,`Age` VARCHAR(120) NOT NULL, PRIMARY KEY (`Id`));";
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void AddAdminIfNotExists() throws Exception{

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

            String query = "INSERT INTO `userinfo`.`users` (`User`, `Password`, `User_Database`, `ProfillePic`, `Joined`, `Sector`, `Age`) SELECT ?, ?, ?, ?, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `userinfo`.`users` WHERE `Id` = ?)";

            
            PreparedStatement ps = con.prepareStatement(query);
            SecretKey key = EncryptionBlock.getKey();
            String user = EncryptionBlock.encrypt("admin", key);
            String Password = "$2a$10$Jvbc0TvN/emIulyoPL4HAOljiml343BQhm35nLZlr3bOJWfb8PTta";
            String Database = EncryptionBlock.encrypt("admin1597",key);
            String profilePic = EncryptionBlock.encrypt("42069",key);
            String Joined = EncryptionBlock.encrypt(String.valueOf(Logic.GetCurrentDate()),key);
            String sector = EncryptionBlock.encrypt("Administrator",key);
            String Age = Base64.getEncoder().encodeToString(key.getEncoded());
            ps.setString(1, user);
            ps.setString(2, Password);
            ps.setString(3, Database);
            ps.setString(4, profilePic);
            ps.setString(5, Joined);
            ps.setString(6, sector);
            ps.setString(7, Age);
            ps.setInt(8, 1);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    



    public static void CreateMainSchemeForStoringUserInputs(){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "CREATE DATABASE IF NOT EXISTS `UserActivity`";

            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void CreateTablesForStoringUsersMessages(){

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

            String query = "CREATE TABLE IF NOT EXISTS `useractivity`.`messages` (`Id` INT NOT NULL AUTO_INCREMENT,`Sender` VARCHAR(120) NOT NULL,`Receiver` VARCHAR(120) NOT NULL,`Message` VARCHAR(255) NOT NULL,`Arrived` VARCHAR(120) NOT NULL,PRIMARY KEY (`Id`));";

            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
        }


    }


    public static void CreateTablesForStoringUsersExspenses(){

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            
            String query = "CREATE TABLE IF NOT EXISTS `useractivity`.`userexspenses` (`Id` INT NOT NULL AUTO_INCREMENT,`Date` VARCHAR(120) NOT NULL,`Type` VARCHAR(120) NOT NULL,`Location` VARCHAR(120) NOT NULL,`Currency` VARCHAR(120) NOT NULL,`TransMethod` VARCHAR(120) NOT NULL,`Spent` VARCHAR(120) NOT NULL,`User_Database` VARCHAR(120) NOT NULL ,`Sector` VARCHAR(120) NOT NULL, PRIMARY KEY (`Id`));";
                                
            PreparedStatement ps = con.prepareStatement(query);

            ps.executeUpdate(); 


        } catch (SQLException e) {
            System.out.println(e);
        }

    }

      public static void CreateTablesForStoringUsersTerminal(){

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            
            String query = "CREATE TABLE IF NOT EXISTS `useractivity`.`terminalactions` (`Id` INT NOT NULL AUTO_INCREMENT,`User` VARCHAR(120) NOT NULL,`Type` VARCHAR(120) NOT NULL,`ActionMessage` VARCHAR(120) NOT NULL,`Date` VARCHAR(120) NOT NULL,PRIMARY KEY (`Id`));";
                                
            PreparedStatement ps = con.prepareStatement(query);

            ps.executeUpdate(); 


        } catch (SQLException e) {
            System.out.println(e);
        }

    }


    public static void CreateSettingTable(){

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            
            String query = "CREATE TABLE IF NOT EXISTS `userinfo`.`settings` (`Id` INT NOT NULL AUTO_INCREMENT,`SettingName` VARCHAR(120) NOT NULL,`SettingValue` VARCHAR(120) NOT NULL, PRIMARY KEY (`Id`));";
                                
            PreparedStatement ps = con.prepareStatement(query);

            ps.executeUpdate(); 


        } catch (SQLException e) {
            System.out.println(e);
        }

    }





    



    




    

    

}
