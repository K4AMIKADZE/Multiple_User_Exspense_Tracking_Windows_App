package GenerallyUsedCode;

import java.util.ArrayList;

import javax.crypto.SecretKey;

import DBControllers.AddingInformationToTheDatabase;
import DBControllers.ReadingInformatioFromDB;
import ItemClases.TerminalVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class TerminalLogic {

    
    
    //  read db information and decrypt
    public static ObservableList<TerminalVariables> gettingDBinfoReadable() throws Exception{
        ArrayList<TerminalVariables> INFO = ReadingInformatioFromDB.ReadTerminal();
        ObservableList<TerminalVariables> DECRYPTED = FXCollections.observableArrayList();

        String key = ReadingInformatioFromDB.getAdminKey();
        SecretKey secretKey = EncryptionBlock.decodeKey(key);

        for(TerminalVariables s : INFO){
            int id = s.getId();
            String user = EncryptionBlock.decrypt(s.getUser(), secretKey);
            String type = EncryptionBlock.decrypt(s.getType(), secretKey);
            String actionmessage = EncryptionBlock.decrypt(s.getActionmessage(), secretKey);
            String date = EncryptionBlock.decrypt(s.getDate(), secretKey);
            DECRYPTED.add(new TerminalVariables(id, user, type, actionmessage, date));
        }


        return DECRYPTED;

    }

    // insert info into db and encrypt it
    public static void insertInfoIntoDB(String user, String type, String actionmessage, String date) throws Exception{

        String key = ReadingInformatioFromDB.getAdminKey();
        SecretKey secretKey = EncryptionBlock.decodeKey(key);

        AddingInformationToTheDatabase.insertTerminalMessage(EncryptionBlock.encrypt(user, secretKey), EncryptionBlock.encrypt(type, secretKey), EncryptionBlock.encrypt(actionmessage, secretKey), EncryptionBlock.encrypt(date, secretKey));

    }

}
