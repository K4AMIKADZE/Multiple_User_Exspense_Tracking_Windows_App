package DBControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import GenerallyUsedCode.Logic;
import ItemClases.DataBaseVariables;

public class UpdateInfromationInDB {

     static ArrayList<DataBaseVariables> info = new ArrayList<>();


     public static void UpdateSettings(int id, String ChangeTo){
        info = Logic.ReadDatabaseInformation();

        try(Connection con = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {
            String query = "UPDATE `userinfo`.`settings` SET `SettingValue` = ? WHERE `Id` = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,ChangeTo);
            ps.setInt(2,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
