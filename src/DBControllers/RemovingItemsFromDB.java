package DBControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import GenerallyUsedCode.Logic;
import ItemClases.DataBaseVariables;

public class RemovingItemsFromDB {

    static ArrayList<DataBaseVariables> info = new ArrayList<>();


    public static void RemoveItemFromDB(int id){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "DELETE FROM `useractivity`.`userexspenses`WHERE Id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    public static void RemoveAllInfoFromTerminal(){
        info = Logic.ReadDatabaseInformation();
        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "TRUNCATE TABLE useractivity.terminalactions;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }

       public static void RemoveUsersMessage(int id){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "DELETE FROM `useractivity`.`messages`WHERE Id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    // remove user fully

     public static void RemoveUsersInfo(int id){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "DELETE FROM `useractivity`.`userexspenses` WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }




    public static void RemoveMainUserInfo(int id){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "DELETE FROM `userinfo`.`users` WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void RemoveUserMessagess(int id){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "DELETE FROM `useractivity`.`messages` WHERE Id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }



}
