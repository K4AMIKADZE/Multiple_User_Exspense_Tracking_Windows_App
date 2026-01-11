package GenerallyUsedCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import javax.crypto.SecretKey;

import ItemClases.DataBaseVariables;
import ItemClases.GetCurrentUser;

public class Logic {

    // Database txt file
    static String DBtxtFile = "src/Database.txt";

    //read Database Information
    public static ArrayList<DataBaseVariables> ReadDatabaseInformation(){

        ArrayList<DataBaseVariables> info = new ArrayList<>();
        info.clear();

        try(BufferedReader read = new BufferedReader(new FileReader(DBtxtFile))) {
            
            String line;

            while ((line = read.readLine()) !=null) {
                String[] niam = line.split(" ");

                String url = niam[0];
                String user = niam[1];
                String password = niam[2];
                String key = niam[3];

                SecretKey keydecoded = EncryptionBlock.decodeKey(key);
                info.add(new DataBaseVariables(EncryptionBlock.decrypt(url, keydecoded), EncryptionBlock.decrypt(user, keydecoded), EncryptionBlock.decrypt(password, keydecoded),key));
            }
            

        } catch (Exception e) {
            System.out.println(e);
        }

        return info;
    }

    // write into database txt file
    public static void WriteDatabaseInformation(String url, String name, String pass, String key){
        try(BufferedWriter write = new BufferedWriter(new FileWriter(DBtxtFile))){
            write.write(url + " " + name  + " " + pass  + " " + key);
        } catch (Exception e) {
            System.out.println(e);
        }

        
    }

    // gets current date for program to work with
    public static String GetCurrentDate(){
        LocalDate date = LocalDate.now();
        String DateToString = String.valueOf(date);
        return DateToString;
    }

    public static String GetFullDate(){
        LocalDate dateone = LocalDate.now();
        String normaldate = String.valueOf(dateone);

        LocalDateTime date = LocalDateTime.now();
        String DateToString = String.valueOf(date);
        String time = DateToString.substring(11,19);

        String reeturninfo = normaldate +" "+time;
        return reeturninfo;
    }

     public static String GetFullDatePDF(){
      

        LocalDateTime date = LocalDateTime.now();
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd]_[HH-mm-ss]");
        return date.format(formatter);
    }

    // add current user to the txt file to use later
    public static void AddCurrentUsersInfoToTxtFile(String username, String UsersDatabaseName,String sector,String key, String profilePic){

        String filename = "src/CurrentUser.txt";

        SecretKey keyToUse = EncryptionBlock.decodeKey(key);

        try(BufferedWriter write = new BufferedWriter(new FileWriter(filename))) {
            write.write(EncryptionBlock.encrypt(username, keyToUse) + " " + EncryptionBlock.encrypt(UsersDatabaseName, keyToUse) + " " + EncryptionBlock.encrypt(sector, keyToUse) + " " + key +  " " + profilePic);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // read current user from txt file

    public static ArrayList<GetCurrentUser> ReadCurrentUsersInfoToTxtFile(){

        String filename = "src/CurrentUser.txt";
        ArrayList<GetCurrentUser> userinfo = new ArrayList<>();

        try(BufferedReader read = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = read.readLine()) !=null){
                
                String[] niam = line.split(" ");

                String name = niam[0];
                String database = niam[1];
                String Sector = niam[2];
                String Key = niam[3];
                int profilepicid = Integer.parseInt(niam[4]);

                SecretKey keyToUse = EncryptionBlock.decodeKey(Key);

                userinfo.add(new GetCurrentUser(EncryptionBlock.decrypt(name, keyToUse), EncryptionBlock.decrypt(database, keyToUse), EncryptionBlock.decrypt(Sector, keyToUse),Key, profilepicid));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return userinfo;
    }



    //database four last digits
    public static String getUsersDatabaseDigits(){
        Random random = new Random();
        int fourDigits = random.nextInt(8000)+1000;
        return String.valueOf(fourDigits);
    }

    //random profile picture id
     public static String getIconId(){
        Random random = new Random();
        int fourDigits = random.nextInt(5);
        return String.valueOf(fourDigits);
    }

    



    // creating Error or just terminal message to help understant what is happening
    // public static ArrayList<DataBaseVariables> CreateTerminalMessage(String User, String Type, String ActionMessage, String Date){


    //     return;
    // }



}
