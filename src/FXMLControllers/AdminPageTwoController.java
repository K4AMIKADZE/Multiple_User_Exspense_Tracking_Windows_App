package FXMLControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import DBControllers.ReadingInformatioFromDB;
import GenerallyUsedCode.AdminBlockToGetAllUserInfo;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.ProfilePicLogic;
import ItemClases.AdminChartThird;
import ItemClases.AdminFifthChart;
import ItemClases.AdminTableView;
import ItemClases.AdminToGetBiggestSpender;
import ItemClases.Settings;
import ItemClases.UserChartAccordingYear;
import ItemClases.UserExspensesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminPageTwoController {


    @FXML
    private LineChart<String, Number> Chart1;

    @FXML
    private BarChart<String, Number> Chart2;

    @FXML
    private LineChart<String, Number> chart3;

    @FXML
    private StackedBarChart<String, Number> Chart4;

    @FXML
    private LineChart<String, Number> Chart5;

    @FXML
    private TableView<AdminTableView> table;

    @FXML
    private StackedBarChart<String, Number> Char6;


    @FXML
    Button logbutton;

    @FXML
    ImageView logimage;

    @FXML
    ImageView UserImage;


    @FXML
    Label showuser;

    @FXML
    Button home;

    @FXML
    TableColumn<AdminTableView, String> User;
    
    // for table
    ObservableList<AdminTableView> info = FXCollections.observableArrayList();
    

  

    // all info
    ArrayList<UserExspensesTable> CurrentUserInformation = new ArrayList<>();

    // array for the chart first
    ArrayList<UserChartAccordingYear> uniques = new ArrayList<>();

    // for second chart
    ArrayList<AdminToGetBiggestSpender> SpentAccordingToUser = new ArrayList<>();

    // for third chart 
     ArrayList<AdminChartThird> thirdchart = new ArrayList<>();

      // for fourth chart
    ArrayList<AdminToGetBiggestSpender> fourthcarts = new ArrayList<>();

    // for fifth chart
    ArrayList<AdminFifthChart> fifthChart = new ArrayList<>();

        // for fifth chart
    ArrayList<AdminFifthChart> calculatedSums = new ArrayList<>();

    // for sixth chart
    ArrayList<UserChartAccordingYear> uniqs = new ArrayList<>();
        

     public void initialize() throws Exception {
      CurrentUserInformation.clear();
      uniques.clear();
      SpentAccordingToUser.clear();
      thirdchart.clear();
      fourthcarts.clear();
      fifthChart.clear();
      calculatedSums.clear();


        CurrentUserInformation = AdminBlockToGetAllUserInfo.getAllInfoForAdmin();
        
        if(!CurrentUserInformation.isEmpty()){
       calculateYearlyExspenses();
       addinginfoToChart();
       CalculateEveryUsersExspenses();
       addinginfoToChartSecond();
       thirdchart();
       addngtothirdchart();
       fourthchart();
       addingtoFourhChart();
       tableview();
       fifthcharts(CurrentUserInformation.get(0).getUser_Database());
       test();
       addinfotoFifthChart(calculatedSums);
       showuser.setText(CurrentUserInformation.get(0).getUser_Database());
       sixthcharts();
       addingtosixthChart();
      }

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

     public void test(){
      table.getSelectionModel().selectedItemProperty().addListener((obs,oldval,newval) ->{
        if (newval != null) {
        String username = newval.getName().trim(); 
        showuser.setText(username);
       fifthcharts(String.valueOf(username));
       addinfotoFifthChart(calculatedSums);
    }
    
    
        
      });
     }

    public void calculateYearlyExspenses(){


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



      
      uniques.clear();
      

      for(int i = 0; i < CurrentUserInformation.size(); i++){
        String date = CurrentUserInformation.get(i).getDate();
        String formateddate = date.substring(0,4);
        double amount = Double.parseDouble(CurrentUserInformation.get(i).getSpent());
        
        boolean found = false;

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

     public void CalculateEveryUsersExspenses(){

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
      

      for(int i = 0; i < CurrentUserInformation.size(); i++){
        String user = CurrentUserInformation.get(i).getUser_Database();
        double amount = Double.parseDouble(CurrentUserInformation.get(i).getSpent());
        boolean found = false;

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

        for(AdminToGetBiggestSpender s : SpentAccordingToUser){
          if(s.getName().equals(user)){
            s.addinfo(amount);
            
            found = true;
          }
        }
        if(!found){
          SpentAccordingToUser.add(new AdminToGetBiggestSpender(user, amount));
          info.add(new AdminTableView(user));
        }
      }

    }


    //AdminChartThird thirdchart
    public void thirdchart(){
      

      for(int i = 0; i < CurrentUserInformation.size(); i++){
        String user = CurrentUserInformation.get(i).getUser_Database();
        boolean found = false;

        for(AdminChartThird s : thirdchart){
          if(s.getName().equals(user)){
            s.add();
            found = true;
          }
        }
        if(!found){
          thirdchart.add(new AdminChartThird(user,1));
        }
      }

    }

    public void fourthchart(){

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
      

      for(int i = 0; i < CurrentUserInformation.size(); i++){
        String sector = CurrentUserInformation.get(i).getSector();
        double exspense = Double.parseDouble(CurrentUserInformation.get(i).getSpent());
        boolean found = false;

         if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Eur")){
            exspense = exspense * 1;
          }
          else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Usd")){
            
            exspense = exspense * UsdToEur;
          }
          else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Rub")){
            
            exspense = exspense * RubToEur;
          }
           else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Gbp")){
            
            exspense = exspense * GbpToEur;
          }
          else{
            exspense = exspense * 1;
            
          }

        for(AdminToGetBiggestSpender s : fourthcarts){
          if(s.getName().equals(sector)){
            s.addinfo(exspense);
            found = true;
          }
        }
        if(!found){
          fourthcarts.add(new AdminToGetBiggestSpender(sector,exspense));
        }
      }

    }

    public void sixthcharts(){
      
      for(UserExspensesTable s : CurrentUserInformation){
        String currecy = s.getCurrency();
        double spent = Double.parseDouble(s.getSpent());
        boolean canbeadded = false;


        for(UserChartAccordingYear ss : uniqs){
          if(ss.getDate().equalsIgnoreCase(currecy)){
            canbeadded = true;
            ss.addamount(spent);
          }
        }
        if(!canbeadded){
          uniqs.add(new UserChartAccordingYear(currecy, spent));
        }
      }

     
      

    }


    public void addingtosixthChart(){
         XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Currency");
        
        
         for(UserChartAccordingYear user : uniqs){
        
        series.getData().add(new XYChart.Data<>(user.getDate(), user.getAmount()));

    }
    Char6.getData().clear();
    
    Char6.getData().add(series);
      }

    public void fifthcharts(String user){

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
      
        fifthChart.clear();
      for(int i = 0; i < CurrentUserInformation.size(); i++){
        String date = CurrentUserInformation.get(i).getDate();
        double exspense = Double.parseDouble(CurrentUserInformation.get(i).getSpent());


         if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Eur")){
            exspense = exspense * 1;
          }
          else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Usd")){
            
            exspense = exspense * UsdToEur;
          }
          else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Rub")){
            
            exspense = exspense * RubToEur;
          }
           else if(CurrentUserInformation.get(i).getCurrency().equalsIgnoreCase("Gbp")){
            
            exspense = exspense * GbpToEur;
          }
          else{
            exspense = exspense * 1;
            
          }
       
          if(CurrentUserInformation.get(i).getUser_Database().equalsIgnoreCase(user)){
            fifthChart.add(new AdminFifthChart(date,exspense));
          }

        }


         



        for(AdminFifthChart s1 : fifthChart){

          String date = s1.getDate();
          double exspense = s1.getAmount();
          boolean found = false;
          for(AdminFifthChart s2 : calculatedSums){
            if(s2.getDate().equals(date)){
              s2.add(exspense);
              found = true;
            }
          }
          if(!found){
            calculatedSums.add(new AdminFifthChart(date, exspense));
          }

        }


        
       
       
       
         
    
  

     
      

    }

    public void addinfotoFifthChart(ArrayList<AdminFifthChart> calculatedSums){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Date");

            calculatedSums.sort(Comparator.comparing(AdminFifthChart::getDate));
        
         for(AdminFifthChart S : calculatedSums){
          
        series.getData().add(new XYChart.Data<>(String.valueOf(S.getDate()), S.getAmount()));

    }

    Chart5.getData().clear();
    
    Chart5.getData().add(series);

    calculatedSums.clear();

      }

    public void addingtoFourhChart(){
         XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Sector");
        
        
         for(AdminToGetBiggestSpender user : fourthcarts){
        
        series.getData().add(new XYChart.Data<>(user.getName(), user.getAmount()));

    }
    Chart4.getData().clear();
    
    Chart4.getData().add(series);
      }

    public void addngtothirdchart(){
         XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("User");
        
        
        for (AdminChartThird user : thirdchart) {
        
        series.getData().add(new XYChart.Data<>(String.valueOf(user.getName()), user.getTimes()));
    }
    chart3.getData().clear();
    
    chart3.getData().add(series);
      }

    public void addinginfoToChart(){
         XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Date");

        uniques.sort(Comparator.comparing(UserChartAccordingYear::getDate));
        
        for (UserChartAccordingYear user : uniques) {
        
        series.getData().add(new XYChart.Data<>(String.valueOf(user.getDate()), user.getAmount()));
    }
    Chart1.getData().clear();
    
    Chart1.getData().add(series);
      }

      // add info to table view3
    public void tableview(){

        User.setCellValueFactory(new PropertyValueFactory<>("name"));
        info.sort(Comparator.comparing(AdminTableView::getName));
        table.setItems(info);

      }

    public void addinginfoToChartSecond(){
         XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("User");
        
        
        for (AdminToGetBiggestSpender user : SpentAccordingToUser) {
        
        series.getData().add(new XYChart.Data<>(String.valueOf(user.getName()), user.getAmount()));
    }
    Chart2.getData().clear();
    
    Chart2.getData().add(series);
      }

    public void enterHome() throws IOException{
        enterNewScene(new ActionEvent(home, null),   "Admin's Managment block","AdminBlockOne.fxml");
      }

    public void entermessage() throws IOException{
        enterNewScene(new ActionEvent(home, null),   "Admin's Managment block","MessageBlock.fxml");
      }

    public void enterterminal() throws IOException{
        enterNewScene(new ActionEvent(home, null),   "Admin's Managment block","AdminBlockThree.fxml");
      }

    public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}


}
