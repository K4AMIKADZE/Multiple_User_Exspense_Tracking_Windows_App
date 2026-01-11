package FXMLControllers;

import java.io.IOException;
import java.util.ArrayList;

import DBControllers.ReadingInformatioFromDB;
import DBControllers.RemovingItemsFromDB;
import DBControllers.UpdateInfromationInDB;
import GenerallyUsedCode.AdminBlockToGetAllUserInfo;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.ProfilePicLogic;
import GenerallyUsedCode.TerminalLogic;
import GenerallyUsedCode.Logic;
import ItemClases.AdminThreeTable2;
import ItemClases.Settings;
import ItemClases.TerminalVariables;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AdminBlockThreeController {


    @FXML
     TableView<TerminalVariables> table;

    @FXML
    TableColumn<TerminalVariables, Integer> id;

    @FXML
    TableColumn<TerminalVariables, String> user;

    @FXML
    TableColumn<TerminalVariables, String> type;

    @FXML
    TableColumn<TerminalVariables, String> mess;

    @FXML
    TableColumn<TerminalVariables, String> date;

    // for table
    ObservableList<TerminalVariables> infotable = FXCollections.observableArrayList();

    @FXML
    Button update;

    @FXML
    Button removeall;

    @FXML
    Label lab;

    @FXML
    ImageView UserImage;

    @FXML
    Button logbutton;

    @FXML
    ImageView logimage;


    // for table
    ObservableList<AdminThreeTable2> infotable2 = FXCollections.observableArrayList();

    @FXML
    TableView<AdminThreeTable2> table2;

    @FXML
    TableColumn<AdminThreeTable2, Integer> idd;

    @FXML
    TableColumn<AdminThreeTable2, String> userr;

    @FXML
    TableColumn<AdminThreeTable2, String> joinedd;

    @FXML
    TableColumn<AdminThreeTable2, String> keyy;

    @FXML
    Button rem;

   @FXML
   Label labela;
   @FXML
   CheckBox ch;

   @FXML
   TableView<Settings> table3;

    @FXML
    TableColumn<Settings, Integer> ID3;

    @FXML
    TableColumn<Settings, String> Setting;

    @FXML
    TableColumn<Settings, Integer> value;

     ObservableList<Settings> infotable3 = FXCollections.observableArrayList();

     @FXML
     TextField set1;

     @FXML
     TextField set2;

     @FXML
     TextField setid;

     @FXML
     Button Clear;

     @FXML
     Label problem;

     @FXML
    MenuButton TrueFalse;



    public void initialize() throws Exception{

        
        
        addinfoIntoTableView();
        
        addinfoIntoTableView2();
        
      LoadProfilePicture();

        LogoutButtonImage();

        //setting table settings
        getSettingsToDisplay();
        lookForSelectedSettings();
        
      table2.getSelectionModel().selectedItemProperty().addListener((obs, wasSelected, isNowSelected) -> {
        
    if (isNowSelected != null) {
        ch.setDisable(false);
        
    } 
});

      ch.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
        AdminThreeTable2 item = table2.getSelectionModel().getSelectedItem();
        if (isNowSelected && item !=null) {
             rem.setDisable(false);
             
             
        } if(!isNowSelected) {
            rem.setDisable(true);
        }
    });
    }

    public void getTrueFalse(ActionEvent event){
      MenuItem item = (MenuItem) event.getSource();
      set2.setText(item.getText());
    }


    public void getSettingsToDisplay(){
      
      ArrayList<Settings> settings = ReadingInformatioFromDB.getSettings();
      
        infotable3.addAll(settings);

        ID3.setCellValueFactory(new PropertyValueFactory<>("id"));
        Setting.setCellValueFactory(new PropertyValueFactory<>("Settingname"));
        value.setCellValueFactory(new PropertyValueFactory<>("SettingValue"));
        
        
        table3.setItems(infotable3);
        
      
    }

   public void lookForSelectedSettings() {
    table3.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
        if (newItem != null) {
           
            set1.setText(newItem.getSettingname()); 
            set2.setText(newItem.getSettingValue());
            setid.setText(String.valueOf(newItem.getId()));
        } 
        if(!set1.getText().equalsIgnoreCase("Usd to Eur Convertion") && !set1.getText().equalsIgnoreCase("Rub to Eur Conversion") && !set1.getText().equalsIgnoreCase("Gbp to Eur Conversion")){
          TrueFalse.setVisible(true);
        }
        else{
          TrueFalse.setVisible(false);
        }
    });
}

public void updateValue(){
  
    String valueOfTheSettings = set2.getText().toLowerCase();
    String changeTo = "";
    Boolean add = false;

    if(!set1.getText().equalsIgnoreCase("Usd to Eur Convertion") && !set1.getText().equalsIgnoreCase("Rub to Eur Conversion") && !set1.getText().equalsIgnoreCase("Gbp to Eur Conversion")){
      
      switch (valueOfTheSettings) {
      case "true":
        changeTo = "True";
        add = true;
        break;
        case "false":
        changeTo = "False";
        add = true;
        break;
    
      default:
      problem.setText("Please use True/False");
      add =false;
        return;
      
    }
    if(add){
      UpdateInfromationInDB.UpdateSettings(Integer.valueOf(setid.getText()), changeTo);
      TrueFalse.setVisible(false);
    }
    else{
      problem.setText("Please use True/False");
    }
    
  }
  else{
    
    try {
      Double.parseDouble(set2.getText());
    } catch (Exception e) {
      problem.setText("You can change only into numbers");
      return;
    }
    UpdateInfromationInDB.UpdateSettings(Integer.valueOf(setid.getText()), set2.getText());
  }
    
    infotable3.clear();
    getSettingsToDisplay();
    problem.setText("Value was updated");
    deleteTextAfterPause3();
 
    
}

public void defaults(){
  UpdateInfromationInDB.UpdateSettings(Integer.valueOf(1), "False");
  UpdateInfromationInDB.UpdateSettings(Integer.valueOf(2), "True");
  UpdateInfromationInDB.UpdateSettings(Integer.valueOf(3), "True");
  UpdateInfromationInDB.UpdateSettings(Integer.valueOf(4), "True");
  infotable3.clear();
    getSettingsToDisplay();
    problem.setText("Default values are implemented");
    deleteTextAfterPause3();
}



public void clearSettings(){
        setid.setText("");
            set1.setText("");
            set2.setText("");
            problem.setText("Info cleared");
            deleteTextAfterPause3();
}












    public void updat() throws Exception{
      addinfoIntoTableView2();
       labela.setText("Users updated");
        deleteTextAfterPause2();
        
    }

    public void getselectedUser() throws Exception{
      AdminThreeTable2 item = table2.getSelectionModel().getSelectedItem();
      if(item != null ){
         // add label info
      
        removeiuser(item);
      }
      else{
        labela.setText("Something went wrong");
        deleteTextAfterPause2();
      }
    }

    public void removeiuser(AdminThreeTable2 item) throws Exception{
     
      ArrayList<Integer> usersIds = AdminBlockToGetAllUserInfo.getIDs(item.getKey());
      ArrayList<Integer> usersIdsMessages = AdminBlockToGetAllUserInfo.getMessageID(item.getKey());
      int kiek = usersIds.size();
      
      RemovingItemsFromDB.RemoveMainUserInfo(item.getId());
      for(int i = 0; i < usersIds.size(); i++){
        RemovingItemsFromDB.RemoveUsersInfo(usersIds.get(i));
        
      }
      for(int i = 0; i < usersIdsMessages.size(); i++){
        
      RemovingItemsFromDB.RemoveUserMessagess(usersIdsMessages.get(i));
      }
      addinfoIntoTableView2();
      labela.setText("Deleted [" + item.getUser() + "] " + "And was deleted with [" + kiek + "] items");
      TerminalLogic.insertInfoIntoDB("Admin", "Removed User", "User [" + item.getUser() + "] was removed with " + kiek + " info", Logic.GetFullDate());
      
      
      ch.setDisable(true);
      ch.setSelected(false);
      rem.setDisable(true);
      deleteTextAfterPause2();
    
    }

      public void addinfoIntoTableView2() throws Exception{
        
        infotable2 = AdminBlockToGetAllUserInfo.getAdmingthree2tableinfo();
        idd.setCellValueFactory(new PropertyValueFactory<>("id"));
        userr.setCellValueFactory(new PropertyValueFactory<>("user"));
        joinedd.setCellValueFactory(new PropertyValueFactory<>("joined"));
        keyy.setCellValueFactory(new PropertyValueFactory<>("key"));
        
        table2.setItems(infotable2);
    }
    

    public void LogoutButtonImage(){
      Image image = new Image("aImages/LogOut.png");
      logimage.setImage(image);
    }

    public void addinfoIntoTableView() throws Exception{
        infotable = TerminalLogic.gettingDBinfoReadable();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        mess.setCellValueFactory(new PropertyValueFactory<>("actionmessage"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.setItems(infotable);

        startRefreshTimeline();
        
    }

    private void refreshTable() {
    try {
        infotable = TerminalLogic.gettingDBinfoReadable();
        table.setItems(infotable);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void startRefreshTimeline() {
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(15), e -> refreshTable())
    );
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
}

    public void DeleteInfo() throws Exception{
        infotable.clear();
        RemovingItemsFromDB.RemoveAllInfoFromTerminal();
        lab.setText("All information was deleted");
        deleteTextAfterPause();
        infotable = TerminalLogic.gettingDBinfoReadable();
        addinfoIntoTableView();
        
    }

      public void UpdateInfo() throws Exception{
        infotable = TerminalLogic.gettingDBinfoReadable();
        addinfoIntoTableView();
        lab.setText("Information was updated");
        deleteTextAfterPause();
    }

      public void deleteTextAfterPause(){
        int duration = 5;
        duration++;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> lab.setText(""));
        pause.play();
    }
     public void deleteTextAfterPause3(){
        int duration = 5;
        duration++;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> problem.setText(""));
        pause.play();
    }
    public void deleteTextAfterPause2(){
        int duration = 10;
        duration++;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> labela.setText(""));
        pause.play();
    }

    public void LoadProfilePicture(){
      String Path = ProfilePicLogic.GetUserIcon();
      Image image = new Image(Path);
      UserImage.setImage(image);
    }

    public void exit() throws IOException{
      enterNewScene(new ActionEvent(logbutton, null),   "Log in","login.fxml");
     }

        public void enterhome() throws IOException{
        enterNewScene(new ActionEvent(logbutton, null),   "Admin's Managment block","AdminBlockOne.fxml");
      }
         public void enetcharts() throws IOException{
        enterNewScene(new ActionEvent(logbutton, null),   "Admin's Managment block","AdminBlockTwo.fxml");
      }

         public void entermessages() throws IOException{
        enterNewScene(new ActionEvent(logbutton, null),   "Admin's Managment block","MessageBlock.fxml");
      }

      
        public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}




}
