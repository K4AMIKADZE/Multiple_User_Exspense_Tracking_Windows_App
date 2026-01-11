package FXMLControllers;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import DBControllers.ReadingInformatioFromDB;
import GenerallyUsedCode.AdminBlockToGetAllUserInfo;
import GenerallyUsedCode.GenerateAdminPDF;
import GenerallyUsedCode.GetCurrentUsersInformation;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.Logic;
import GenerallyUsedCode.TerminalLogic;
import ItemClases.AdminToGetBiggestSpender;
import ItemClases.Settings;
import ItemClases.UserExspensesTable;
import ItemClases.UserInfoConvertPDF;
import ItemClases.UserInfoDisplayTableVIew;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminPageOneController {


   // table view
    @FXML
    TableView<UserExspensesTable> table;

     @FXML
    TableColumn<UserExspensesTable, Integer> Id;

    @FXML
    TableColumn<UserExspensesTable, String> date;

    @FXML
    TableColumn<UserExspensesTable, String> type;

    @FXML
    TableColumn<UserExspensesTable, String> locationt;

    @FXML
    TableColumn<UserExspensesTable, String> currency;

    @FXML
    TableColumn<UserExspensesTable, String> transaction;

    @FXML
    TableColumn<UserExspensesTable, String> spent;

    @FXML
    TableColumn<UserExspensesTable, String> User;

    @FXML
    TableColumn<UserExspensesTable, String> Sector;


    @FXML
    Button Export;
    

    // display info admin
    @FXML
    Label kiek;

    @FXML
    Label total;

    @FXML
    Label average;

    @FXML
    Label topspender;

    @FXML
    Label biggest;

    @FXML
    Label smallest;

    @FXML
    Label recent;

    @FXML
    ImageView UserImage;


    @FXML
    TextField search;

    @FXML
    Button charts;

    @FXML
    Button logbutton;

    @FXML
    ImageView logimage;

    // for more info
    int clicks = 0;

    // displays info into table view
    ObservableList<UserExspensesTable> info = FXCollections.observableArrayList();
    FilteredList<UserExspensesTable> filteredInfo;

     ArrayList<UserExspensesTable> DecryptedExpensesOfUser = new ArrayList<>();


     public void initialize(){
        DecryptedExpensesOfUser.clear();
        info.clear();
        
        DecryptedExpensesOfUser = AdminBlockToGetAllUserInfo.getAllInfoForAdmin();
        if(!DecryptedExpensesOfUser.isEmpty()){
        info = AdminBlockToGetAllUserInfo.getInfoForTableView();
        addinfoToTableView();
        getLabelInfo();
        }
        LoadProfilePicture();
        LogoutButtonImage();

        // check if user want more info
        checkIFPressed();
     }

     public void checkIFPressed() {
    table.setOnMouseClicked(e -> {
        if (e.getClickCount() == 2) {
            UserExspensesTable item = table.getSelectionModel().getSelectedItem();
            if (item != null) {
                try {
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/POPUP.fxml"));
                    Parent root = loader.load(); 

                    // Get the controller instance
                    PopUpController controller = loader.getController();
                    controller.PopUpEXtraInfo(item); 

                    // Create and show popup stage
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Extended Information about " + item.getUser_Database());
                    stage.setResizable(false);
                    //stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });
}


      public void LogoutButtonImage(){
      Image image = new Image("aImages/LogOut.png");
      logimage.setImage(image);
    }

     public void LoadProfilePicture(){
      Image image = new Image("aImages/manager.png");
      UserImage.setImage(image);
    }

     public void exit() throws IOException{
      enterNewScene(new ActionEvent(logbutton, null),   "Log in","login.fxml");
     }


      public void getLabelInfo(){


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




        int count = DecryptedExpensesOfUser.size();

        kiek.setText(String.valueOf(count));

        double sum = 0;
        double averaget = 0;
        for(UserExspensesTable s : DecryptedExpensesOfUser){

          if(s.getCurrency().equalsIgnoreCase("Eur")){
            sum+= Double.parseDouble(s.getSpent());
          }
          else if(s.getCurrency().equalsIgnoreCase("Usd")){
            
            sum+= Double.parseDouble(s.getSpent()) * UsdToEur;
          }
          else if(s.getCurrency().equalsIgnoreCase("Rub")){
            
            sum+= Double.parseDouble(s.getSpent()) * RubToEur;
          }
           else if(s.getCurrency().equalsIgnoreCase("Gbp")){
            
            sum+= Double.parseDouble(s.getSpent()) * GbpToEur;
          }
          else{
            sum+= Double.parseDouble(s.getSpent());
            
          }


        }

        averaget = sum / count;

        total.setText(String.valueOf(String.format("%.2f", sum)) + "Eur");
        average.setText(String.valueOf(String.format("%.2f", averaget)));

        ArrayList<AdminToGetBiggestSpender> listOfUsersExspenses = new ArrayList<>();

        for(UserExspensesTable s : DecryptedExpensesOfUser){
          String user = s.getUser_Database();
          double value = Double.parseDouble(s.getSpent());
          boolean found = false;
          for(AdminToGetBiggestSpender ss : listOfUsersExspenses){
            if(user.equals(ss.getName())){
              ss.addinfo(value);
              found = true;
            }
          }
          if(!found){
            listOfUsersExspenses.add(new AdminToGetBiggestSpender(user, value));
          }

          
        }
       
            double max =listOfUsersExspenses.get(0).getAmount();
            String name = listOfUsersExspenses.get(0).getName();
           for(AdminToGetBiggestSpender s : listOfUsersExspenses){
            if(max < s.getAmount()){
              name = s.getName();
              max = s.getAmount();
              
            }
           
           }
           topspender.setText(name);


           //biggest and smallest purhase

           double max2 = Double.parseDouble(DecryptedExpensesOfUser.get(0).getSpent());
           String maxUser = DecryptedExpensesOfUser.get(0).getUser_Database();
           String maxcur =DecryptedExpensesOfUser.get(0).getCurrency();
            double min = Double.parseDouble(DecryptedExpensesOfUser.get(0).getSpent());
           String minUser = DecryptedExpensesOfUser.get(0).getUser_Database();
           String mincur =DecryptedExpensesOfUser.get(0).getCurrency();
          for(UserExspensesTable s : DecryptedExpensesOfUser){
            if(max2 < Double.parseDouble(s.getSpent())){
              max2 = Double.parseDouble(s.getSpent());
              maxUser = s.getUser_Database();
              mincur = s.getCurrency();
            }
            if(max2 < Double.parseDouble(s.getSpent())){
               min = Double.parseDouble(s.getSpent());
               minUser = s.getUser_Database();
               maxcur = s.getCurrency();
            }
          }
          

          biggest.setText(maxUser + " " + max2 + maxcur);
          smallest.setText(minUser + " " + min + mincur);
          
          LocalDate maxdate = LocalDate.parse(DecryptedExpensesOfUser.get(0).getDate());
          String name3 = DecryptedExpensesOfUser.get(0).getUser_Database();
          String value3 = DecryptedExpensesOfUser.get(0).getSpent();
          String CUR3 = DecryptedExpensesOfUser.get(0).getCurrency();
           for(UserExspensesTable s : DecryptedExpensesOfUser){
            if(maxdate.isBefore(LocalDate.parse(s.getDate()))){
              maxdate = LocalDate.parse(s.getDate());
              name3 = s.getUser_Database();
              value3 = s.getSpent();
              CUR3 = s.getCurrency();
              
            }
           
           }

          recent.setText(name3 + " " + value3 + CUR3);


      }



       public void addinfoToTableView(){
        
        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        User.setCellValueFactory(new PropertyValueFactory<>("User_Database"));
        Sector.setCellValueFactory(new PropertyValueFactory<>("Sector"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationt.setCellValueFactory(new PropertyValueFactory<>("location"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        transaction.setCellValueFactory(new PropertyValueFactory<>("trasactionmethod"));
        spent.setCellValueFactory(new PropertyValueFactory<>("spent"));
        
        info.sort(Comparator.comparing(UserExspensesTable::getDate).reversed());
        filteredInfo = new FilteredList<>(info, p -> true);
        
        
         table.setItems(filteredInfo);

         search.textProperty().addListener((obs, oldValue, newValue) -> {
         filteredInfo.setPredicate(user -> {
            if (newValue == null || newValue.isEmpty()) {
                return true; 
            }
            String lowerCase = newValue.toLowerCase();

            return user.getCurrency().toLowerCase().contains(lowerCase) ||
                   user.getUser_Database().toLowerCase().contains(lowerCase) ||
                   user.getType().toLowerCase().contains(lowerCase) ||
                   user.getDate().toLowerCase().contains(lowerCase) ||
                   user.getLocation().toLowerCase().contains(lowerCase) ||
                   user.getSpent().toLowerCase().contains(lowerCase) ||
                   user.getSector().toLowerCase().contains(lowerCase) ||
                   user.getTrasactionmethod().toLowerCase().contains(lowerCase) ||
                   String.valueOf(user.getId()).contains(lowerCase);
        });
    });
      }




            public void enterHome() throws IOException{
        enterNewScene(new ActionEvent(charts, null),   "Admin's Managment block","AdminBlockTwo.fxml");
      }

           public void entermessage() throws IOException{
        enterNewScene(new ActionEvent(charts, null),   "Admin's Managment block","MessageBlock.fxml");
      }

         public void enterterminal() throws IOException{
        enterNewScene(new ActionEvent(charts, null),   "Admin's Managment block","AdminBlockThree.fxml");
      }



         public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}


  public void ExportAdminPDF() throws IOException{

    ArrayList<UserExspensesTable> user = new ArrayList<>();
    user = AdminBlockToGetAllUserInfo.getAllInfoForAdmin();

    GenerateAdminPDF.CreatePDF(user, "Admin");
    user.clear();
  }


}
