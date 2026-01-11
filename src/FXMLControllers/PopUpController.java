package FXMLControllers;

import java.util.ArrayList;

import DBControllers.ReadingInformatioFromDB;
import GenerallyUsedCode.AdminBlockToGetAllUserInfo;
import GenerallyUsedCode.Logic;
import ItemClases.AdminGetAllInfoAboutUser;
import ItemClases.Settings;
import ItemClases.UserExspensesTable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PopUpController {

    @FXML
    private Label one; // username

    @FXML
    private Label two; // sector

    @FXML
    private Label three; // date

    @FXML
    private Label four; // type

    @FXML
    private Label five; // location

    @FXML
    private Label six; // currency

    @FXML
    private Label seven; // spend

    @FXML
    private Label eight; // transaction


    @FXML
    private Label DATE;

    @FXML
    private Label totalspend;

    @FXML
    private Label avg;

    @FXML
    private Label total;


    

    public void PopUpEXtraInfo(UserExspensesTable item){
        one.setText(item.getUser_Database());
        two.setText(item.getSector());
        three.setText(item.getDate());
        four.setText(item.getType());
        five.setText(item.getLocation());
        six.setText(item.getCurrency());
        seven.setText(item.getSpent() + " " + item.getCurrency());
        eight.setText(item.getTrasactionmethod());



        // get currency rates
        double UsdToEur = 0;
        double RubToEur = 0;
        double GbpToEur = 0;
            
        ArrayList<Settings> settings = ReadingInformatioFromDB.getSettings();
        for(Settings s : settings){
            if(s.getSettingname().equalsIgnoreCase("Usd to Eur Convertion")){
              UsdToEur = Double.parseDouble(s.getSettingValue());
            }
             if(s.getSettingname().equalsIgnoreCase("Rub to Eur Conversion")){
              RubToEur = Double.parseDouble(s.getSettingValue());
            }
             if(s.getSettingname().equalsIgnoreCase("Gbp to Eur Conversion")){
              GbpToEur = Double.parseDouble(s.getSettingValue());
            }
        }



        ArrayList<UserExspensesTable> info = AdminBlockToGetAllUserInfo.getAllInfoForAdmin();
        ArrayList<UserExspensesTable> info2 = new ArrayList<>();

        // get current users info
        for(UserExspensesTable s : info){
            if(s.getUser_Database().equalsIgnoreCase(item.getUser_Database())){
                info2.add(s);
            }
        }

        double totalpurchases = 0;
        double average = 0;
        int allPurhcases = info2.size();
       
        for(UserExspensesTable s : info2){

          if(s.getCurrency().equalsIgnoreCase("Eur")){
            totalpurchases+= Double.parseDouble(s.getSpent());
          }
          else if(s.getCurrency().equalsIgnoreCase("Usd")){
            
            totalpurchases+= Double.parseDouble(s.getSpent()) * UsdToEur;
          }
          else if(s.getCurrency().equalsIgnoreCase("Rub")){
            
            totalpurchases+= Double.parseDouble(s.getSpent()) * RubToEur;
          }
           else if(s.getCurrency().equalsIgnoreCase("Gbp")){
            
            totalpurchases+= Double.parseDouble(s.getSpent()) * GbpToEur;
          }
          else{
            totalpurchases+= Double.parseDouble(s.getSpent());
            
          }


        }
            
        
        average = totalpurchases / allPurhcases;
        //get current date
        DATE.setText(Logic.GetCurrentDate());
        // total spend
        totalspend.setText(String.valueOf(String.format("%.2f", totalpurchases)));
        // avg spendings
        avg.setText(String.valueOf(String.format("%.2f", average)));
        // get total of purchases
        total.setText(String.valueOf(allPurhcases));
        
        
    }

  
}








       
