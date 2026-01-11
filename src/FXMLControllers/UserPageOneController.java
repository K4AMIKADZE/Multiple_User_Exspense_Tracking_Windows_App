package FXMLControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import DBControllers.ReadingInformatioFromDB;
import GenerallyUsedCode.GetCurrentUsersInformation;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.Logic;
import GenerallyUsedCode.ProfilePicLogic;
import ItemClases.GetCurrentUser;
import ItemClases.Settings;
import ItemClases.UserChartAccordingYear;
import ItemClases.UserInfoDisplayTableVIew;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserPageOneController {

    @FXML
    Button BUT2;
    @FXML
    Button BUT3;

    @FXML
    Label amount;

    @FXML
    Label total;

    @FXML
    Label average;

    @FXML
    Label name;

    @FXML
    Button logbutton;

    @FXML
    ImageView logimage;

    @FXML
    ImageView UserImage;


   @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis; 

    @FXML
    private NumberAxis yAxis; 

    // array for the chart
    ArrayList<UserChartAccordingYear> uniques = new ArrayList<>();

    
    ArrayList<UserInfoDisplayTableVIew> CurrentUserInformation = new ArrayList<>();

    // get current user info
    ArrayList<GetCurrentUser> CurrentUser = Logic.ReadCurrentUsersInfoToTxtFile();


    public void initialize() throws Exception {

      CurrentUserInformation.clear();
      CurrentUserInformation = GetCurrentUsersInformation.GetInfoForCharsUser();

      // get calculated info
      CalculateInfo();

      calculateYearlyExspenses();
      addinginfoToChart();

      // show user
      getCurrentaName();

      //load profile pic
      LoadProfilePicture();
      
      

        
         LogoutButtonImage();
    }

    public void LogoutButtonImage(){
      Image image = new Image("aImages/LogOut.png");
      logimage.setImage(image);
    }

    public void LoadProfilePicture(){
      String Path = ProfilePicLogic.GetUserIcon();
      Image image = new Image(Path);
      UserImage.setImage(image);
    }

    public void exit() throws IOException{
      enterNewScene(new ActionEvent(logbutton, null),   "Log in","login.fxml");
     }



    public void CalculateInfo(){
      double amounta = CurrentUserInformation.size();
      double spenttotal = 0;
      double averagePurchase = 0;

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

      for(UserInfoDisplayTableVIew s : CurrentUserInformation){
        if(s.getCurrency().equalsIgnoreCase("Eur")){
            spenttotal+= Double.parseDouble(s.getSpent());
          }
          else if(s.getCurrency().equalsIgnoreCase("Usd")){
            
            spenttotal+= Double.parseDouble(s.getSpent()) * UsdToEur;
          }
          else if(s.getCurrency().equalsIgnoreCase("Rub")){
            
            spenttotal+= Double.parseDouble(s.getSpent()) * RubToEur;
          }
           else if(s.getCurrency().equalsIgnoreCase("Gbp")){
            
            spenttotal+= Double.parseDouble(s.getSpent()) * GbpToEur;
          }
          else{
            spenttotal+= Double.parseDouble(s.getSpent());
            
          }
      }
      

      averagePurchase = spenttotal/amounta;

      amount.setText(String.valueOf(String.format("%.0f", amounta)));
      total.setText(String.valueOf(String.format("%.2f", spenttotal)) + "Eur");
      average.setText(String.valueOf(String.format("%.2f", averagePurchase)));

    }


      public void addinginfoToChart(){
         XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Date");
        
            //sort according to date
            uniques.sort(Comparator.comparing(UserChartAccordingYear::getDate));
        
        for (UserChartAccordingYear user : uniques) {
        
        series.getData().add(new XYChart.Data<>(String.valueOf(user.getDate()), user.getAmount()));
    }
    barChart.getData().clear();
    
    barChart.getData().add(series);
      }

    public void calculateYearlyExspenses(){
      

      for(int i = 0; i < CurrentUserInformation.size(); i++){
        String date = CurrentUserInformation.get(i).getDate();
        String formateddate = date.substring(0,4);
        double amount = Double.parseDouble(CurrentUserInformation.get(i).getSpent());
        boolean found = false;

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

         if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Eur")){
            amount = amount * 1;
          }
          else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Usd")){
            
            amount = amount * UsdToEur;
          }
          else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Rub")){
            
            amount = amount * RubToEur;
          }
           else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Gbp")){
            
            amount = amount * GbpToEur;
          }
          else{
            amount = amount * 1;
            
          }

        for(UserChartAccordingYear s : uniques){
          if(s.getDate().equals(formateddate)){
            
            s.addamount(amount);
            found = true;
          }
        }
        if(!found){
          uniques.add(new UserChartAccordingYear(formateddate, amount));
        }
      }

    }


    public void getCurrentaName(){
      name.setText("Hello " + CurrentUser.get(0).getDatabase());
    }


      public void Button2press() throws IOException{
        enterNewScene(new ActionEvent(BUT2, null), CurrentUser.get(0).getName() + "'s Managment block","UserPageTwo.fxml");
    }

    public void Button3press() throws IOException{
        enterNewScene(new ActionEvent(BUT3, null), CurrentUser.get(0).getName() + "'s Managment block","MessageBlock.fxml");
    }

     public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}



}
