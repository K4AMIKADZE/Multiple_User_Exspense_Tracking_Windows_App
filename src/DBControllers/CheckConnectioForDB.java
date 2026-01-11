package DBControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


import GenerallyUsedCode.Logic;
import ItemClases.DataBaseVariables;

public class CheckConnectioForDB {

    static ArrayList<DataBaseVariables> info = new ArrayList<>();


    public static String CheckDBStatus() throws Exception{
         info = Logic.ReadDatabaseInformation();
         if(!info.isEmpty()){
         try(Connection con  = DriverManager.getConnection(info.get(0).getUrl(), info.get(0).getUser(), info.get(0).getPassword())) {

            if (con != null && !con.isClosed()) {
            return "Online";
        } 
        else {
            
            return "Problem"; 
        }

    } catch (SQLException e) {
        String sqlState = e.getSQLState();

        if ("28000".equals(sqlState)) {
            
            return "Credential error";
        }

        System.err.println("SQL Error: " + e.getMessage());
        return "Offline"; 
    }
    
        
    
}
else{
    return "No database";
}

}
}
