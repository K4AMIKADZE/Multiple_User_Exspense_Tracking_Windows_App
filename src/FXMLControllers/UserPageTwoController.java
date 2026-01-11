package FXMLControllers;
import java.io.IOException;
import java.util.ArrayList;
import javax.crypto.SecretKey;
import DBControllers.AddingInformationToTheDatabase;
import DBControllers.ReadingInformatioFromDB;
import DBControllers.RemovingItemsFromDB;
import GenerallyUsedCode.EncryptionBlock;
import GenerallyUsedCode.GenerateUserPDF;
import GenerallyUsedCode.GetCurrentUsersInformation;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.Logic;
import GenerallyUsedCode.ProfilePicLogic;
import GenerallyUsedCode.TerminalLogic;
import ItemClases.GetCurrentUser;
import ItemClases.Settings;
import ItemClases.UserInfoConvertPDF;
import ItemClases.UserInfoDisplayTableVIew;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class UserPageTwoController {

    @FXML
    TextField search;

    @FXML
    TextField DateAdd;

    @FXML
    TextField TypeAdd;

    @FXML
    TextField LocationAdd;

    @FXML
    TextField CurrencyAdd;

    @FXML
    TextField TransactionAdd;

    @FXML
    TextField SpentAdd;

    @FXML
    Button CurrentDate;

    @FXML
    Button ClearInfo;

    @FXML
    Button RemoveInfo;

    @FXML
    Button AddInfo;

    @FXML
    Label Problem;

    @FXML
    Button BUT1;

    @FXML
    Button BUT3;

    @FXML
    Label name;

    @FXML
    Button logbutton;

    @FXML
    ImageView logimage;

    @FXML
    ImageView UserImage;

    @FXML
    Button Export;

    // table view
    @FXML
    TableView<UserInfoDisplayTableVIew> table;

     @FXML
    TableColumn<UserInfoDisplayTableVIew, Integer> Id;

    @FXML
    TableColumn<UserInfoDisplayTableVIew, String> date;

    @FXML
    TableColumn<UserInfoDisplayTableVIew, String> type;

    @FXML
    TableColumn<UserInfoDisplayTableVIew, String> locationt;

    @FXML
    TableColumn<UserInfoDisplayTableVIew, String> currency;

    @FXML
    TableColumn<UserInfoDisplayTableVIew, String> transaction;

    @FXML
    TableColumn<UserInfoDisplayTableVIew, String> spent;

    

   


    // displays info into table view
    ObservableList<UserInfoDisplayTableVIew> info = FXCollections.observableArrayList();
    
    FilteredList<UserInfoDisplayTableVIew> filteredInfo;

    // get current user info
    ArrayList<GetCurrentUser> CurrentUser = Logic.ReadCurrentUsersInfoToTxtFile();

    

    public void initialize() throws Exception {
        
    // reads info and places to table view
       
       info.clear();
      realoadTableData();
      
      addinfoToTableView();
       
      //get current user
       getCurrentaName();

       //load profile pic
       LoadProfilePicture();
       LogoutButtonImage();
        MakedelteButton();

        //delete from table with buttons
       Catchenter();
    }

      public void Catchenter(){
        table.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE)
            try {
                RemoveItem();
            } catch (Exception e) {
                e.printStackTrace();
            }

         
        });
    }
    

    // get currency when pressed
    public void currencys(ActionEvent event){
        MenuItem choice = (MenuItem) event.getSource();
        CurrencyAdd.setText(choice.getText());
    }

    public void transmethod(ActionEvent event){
        MenuItem choice = (MenuItem) event.getSource();
        TransactionAdd.setText(choice.getText());
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
   

    public void realoadTableData() throws Exception{
        info.clear();
       info.addAll(GetCurrentUsersInformation.Readinfo());
    }
    
    public void MakedelteButton(){
         table.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
        if (newItem != null) {
           RemoveInfo.setDisable(false);
          
        } 
    });
    }

    public void RemoveItem() throws Exception{
        
        UserInfoDisplayTableVIew item = table.getSelectionModel().getSelectedItem();

        Boolean canContinue = null;
        ArrayList<Settings> settings = ReadingInformatioFromDB.getSettings();
        for(Settings s : settings){
            if(s.getSettingname().equalsIgnoreCase("Allow User Remove Items")){
                canContinue = Boolean.parseBoolean(s.getSettingValue());
            }
        }

        if(canContinue){

        if(item !=null){
            
            RemovingItemsFromDB.RemoveItemFromDB(item.getId());
            realoadTableData();
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Removing item", "User succesfully removed item", Logic.GetFullDate());
        }
        else{
            Problem.setText("Please select item by clicking it ont table");
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Deleting info", "User didint select item to delete", Logic.GetFullDate());
        }
    }
    else{
        RemoveInfo.setDisable(true);
        Problem.setText("Removing items is not possble it is disabled by the admin");
        int id = 0000;
        if(item != null){
            id = item.getId();
        }
        TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Deleting restr", "User tried deleting info [" + id +"] ID its is disabled", Logic.GetFullDate());
    }
    
    }


    public void addinfoToTableView(){
        
        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationt.setCellValueFactory(new PropertyValueFactory<>("location"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        transaction.setCellValueFactory(new PropertyValueFactory<>("Transaction"));
        spent.setCellValueFactory(new PropertyValueFactory<>("spent"));

        
        filteredInfo = new FilteredList<>(info, p -> true);
         table.setItems(filteredInfo);

         search.textProperty().addListener((obs, oldValue, newValue) -> {
         filteredInfo.setPredicate(user -> {
            if (newValue == null || newValue.isEmpty()) {
                return true; 
            }
            String lowerCase = newValue.toLowerCase();

            return user.getCurrency().toLowerCase().contains(lowerCase) ||
                   user.getDate().toLowerCase().contains(lowerCase) ||
                   user.getType().toLowerCase().contains(lowerCase) ||
                   user.getDate().toLowerCase().contains(lowerCase) ||
                   user.getLocation().toLowerCase().contains(lowerCase) ||
                   user.getSpent().toLowerCase().contains(lowerCase) ||
                   user.getTransaction().toLowerCase().contains(lowerCase) ||
                   String.valueOf(user.getId()).contains(lowerCase);
        });
    });
    }



    public void AddUserExpenses() throws Exception{

        boolean CanBeAdded = true;

        if(DateAdd.getText().isEmpty() && TypeAdd.getText().isEmpty() && LocationAdd.getText().isEmpty() && CurrencyAdd.getText().isEmpty() && TransactionAdd.getText().isEmpty() && SpentAdd.getText().isEmpty()){
            Problem.setText("No information added");
            deleteTextAfterPause();
            CanBeAdded = false;
             TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Error adding info", "User didint add any info and trying to add it", Logic.GetFullDate());
            return;
        }
        else if(DateAdd.getText().isEmpty()){
            Problem.setText("No Date added");
            deleteTextAfterPause();
            CanBeAdded = false;
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Error adding info", "User didin't add date", Logic.GetFullDate());
            return;
        }
        else if(TypeAdd.getText().isEmpty()){
            Problem.setText("No Type added");
            deleteTextAfterPause();
            CanBeAdded = false;
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Error adding info", "User didin't add type", Logic.GetFullDate());
            return;
        }
         else if(LocationAdd.getText().isEmpty()){
            Problem.setText("No Location added");
            deleteTextAfterPause();
            CanBeAdded = false;
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Error adding info", "User didin't add location", Logic.GetFullDate());
            return;
        }
         else if(CurrencyAdd.getText().isEmpty()){
            Problem.setText("No Currency added");
            deleteTextAfterPause();
            CanBeAdded = false;
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Error adding info", "User didin't add currency", Logic.GetFullDate());
            return;
        }
         else if(TransactionAdd.getText().isEmpty()){
            Problem.setText("No Transaction method added");
            deleteTextAfterPause();
            CanBeAdded = false;
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Error adding info", "User didin't add transaction", Logic.GetFullDate());
            return;
        }
          else if(SpentAdd.getText().isEmpty()){
            Problem.setText("No Spent amount added");
            deleteTextAfterPause();
            CanBeAdded = false;
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Error adding info", "User didin't add spent", Logic.GetFullDate());
            return;
        }

        java.sql.Date sqlDate;
        try {
             sqlDate = java.sql.Date.valueOf(DateAdd.getText()); 
        } catch (Exception e) {
           Problem.setText("Date is incorrect. Use yyyy-mm-dd");
           CanBeAdded = false;
           TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Date error", "Date format incorrect", Logic.GetFullDate());
           return;
        }

          try {
             Double.parseDouble(SpentAdd.getText()); 
        } catch (Exception e) {
           Problem.setText("Letter were removed from Spent please press [Add] again to to add your purchase");
             SpentAdd.setText(SpentAdd.getText().replaceAll("[^a-zA-Z0-9.]", ""));
             SpentAdd.setText(SpentAdd.getText().replaceAll("[a-z]", ""));
             CanBeAdded = false;
             TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Spent error [letters]", "User tried adding [spent] with letters", Logic.GetFullDate());
           return;
        }

        // get key of the user
        SecretKey key = EncryptionBlock.decodeKey(CurrentUser.get(0).getKey());

        if(CanBeAdded){
            AddingInformationToTheDatabase.AddUserExpenses(EncryptionBlock.encrypt(DateAdd.getText(), key), EncryptionBlock.encrypt(TypeAdd.getText(), key), EncryptionBlock.encrypt(LocationAdd.getText(), key), EncryptionBlock.encrypt(CurrencyAdd.getText(), key), EncryptionBlock.encrypt(TransactionAdd.getText(), key), EncryptionBlock.encrypt(SpentAdd.getText(), key), EncryptionBlock.encrypt(CurrentUser.get(0).getDatabase(), key), EncryptionBlock.encrypt(CurrentUser.get(0).getSector(), key));
            Problem.setText("Added succesfully");
            deleteTextAfterPause();
            ClearAllTextFields();
            realoadTableData();
            TerminalLogic.insertInfoIntoDB(CurrentUser.get(0).getDatabase(), "Added", "User succesfully added new info", Logic.GetFullDate());
        }



    }

    public void getCurrentaName(){
      name.setText("Hello " + CurrentUser.get(0).getDatabase());
    }

    public void Button1press() throws IOException{
        enterNewScene(new ActionEvent(BUT1, null), CurrentUser.get(0).getName() + "'s Managment block","UserPageOne.fxml");
    }

    public void Button3press() throws IOException{
        enterNewScene(new ActionEvent(BUT3, null), CurrentUser.get(0).getName() + "'s Managment block","MessageBlock.fxml");
    }


    public void ClearAllTextFields(){
        DateAdd.clear();
        TypeAdd.clear();
        LocationAdd.clear();
        CurrencyAdd.clear();
        TransactionAdd.clear();
        SpentAdd.clear();
    }


    public void getCurrentDate(){
        DateAdd.setText(Logic.GetCurrentDate());
    }


    public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}



    public void deleteTextAfterPause(){
        int duration = 5;
        duration++;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> Problem.setText(""));
        pause.play();
    }

    public void creatingpdf() throws IOException, Exception{
        //get current uset in array
         ArrayList<UserInfoConvertPDF> user = new ArrayList<>();
        
        user = GetCurrentUsersInformation.ReadinfoForPDF();
        GenerateUserPDF.CreatePDF(user, CurrentUser);
        user.clear();
    }


}
