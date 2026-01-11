package FXMLControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.crypto.SecretKey;

import DBControllers.AddingInformationToTheDatabase;
import DBControllers.RemovingItemsFromDB;
import GenerallyUsedCode.AdminBlockToGetAllUserInfo;
import GenerallyUsedCode.EncryptionBlock;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.Logic;
import GenerallyUsedCode.ProfilePicLogic;
import GenerallyUsedCode.TerminalLogic;
import ItemClases.AdminTableView;
import ItemClases.GetCurrentUser;
import ItemClases.MessageUsers;
import ItemClases.UserAndKey;
import ItemClases.UserInfo;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.beans.value.ChangeListener;

public class MessageController {

    @FXML
    TextField user;

     @FXML
    TextArea message;

    @FXML
    Button send;

    @FXML
    Label problem;

     @FXML
    Label problem2;

    @FXML
    Label count;

    @FXML
    TextField search;

    @FXML
    Button but1;

    @FXML
    Button but3;

    @FXML
    Label namelabel;

    @FXML
    Button logbutton;

    @FXML
    ImageView logimage;

    @FXML
    ImageView UserImage;

    @FXML
    Button But4;



    // table
    @FXML
    TableView<AdminTableView> table;

    @FXML
    TableColumn<AdminTableView, String> usercolumn;

    ObservableList<AdminTableView> info = FXCollections.observableArrayList();

     @FXML
    TableView<MessageUsers> table2;

    @FXML
    TableColumn<MessageUsers, Integer> id;
    @FXML
    TableColumn<MessageUsers, String> sender;
    @FXML
    TableColumn<MessageUsers, String> message2;
    @FXML
    TableColumn<MessageUsers, String> arrived;

    ObservableList<MessageUsers> info2 = FXCollections.observableArrayList();
    FilteredList<MessageUsers> filteredInfo;
    

    // all user info
    ArrayList<UserInfo> userinfo = new ArrayList<>();
    // unique users keys
    ArrayList<UserAndKey> userkey = new ArrayList<>();

    // get the key for later
    String userEncryptingKey = "";
    // get current user 
    ArrayList<GetCurrentUser> GetCurrentSender = new ArrayList<>();


    // runs when window starts
    public void initialize() throws Exception {

        GetCurrentSender.clear();
        userinfo.clear();
        info2.clear();
        info.clear();
        userkey.clear();
        
        GetCurrentSender = Logic.ReadCurrentUsersInfoToTxtFile();
        userinfo = AdminBlockToGetAllUserInfo.DecryptAllInfo();
        info2 = AdminBlockToGetAllUserInfo.getinfoForMessageTable2(GetCurrentSender.get(0).getKey());
        
        // all unique users
        getUserAndKeys();
        //put the info into (info) array to put into table view
        GetUsersToTableView();
        // puts info into table view
        addInfoIntoFirstTableView();
        //get item by selecting it
        getSelectedItem();
        //count message size
        trackTheMessageSize();
        //add info into second tableview
        putInfoIntoTABLEview2();
        // change name according to user
        changenameaccordingtouser();
        // load profile pic
        LoadProfilePicture();


        if(GetCurrentSender.get(0).getDatabase().equalsIgnoreCase("admin1597")){
            But4.setVisible(true);
            
            
        }
        else{
        
         But4.setVisible(false);
        
            
        }

       
        

        
        LogoutButtonImage();
    }

    public void getTheItemsToremove() throws Exception{
        MessageUsers item = table2.getSelectionModel().getSelectedItem();
        if(item != null){
            removeMessage(item);
        }
        else{
            TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message error", "User didin't select message to remove", Logic.GetFullDate());
            problem2.setText("Nothing is selected could not remove please select item to remove");
            deleteTextAfterPause();
        }
    }

    public void removeMessage(MessageUsers item) throws Exception{
        RemovingItemsFromDB.RemoveUsersMessage(item.getId());
        info2.clear();
        info2 = AdminBlockToGetAllUserInfo.getinfoForMessageTable2(GetCurrentSender.get(0).getKey());
        TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message", "User removed message", Logic.GetFullDate());
        problem2.setText("Item Removed succesfully");
        putInfoIntoTABLEview2();
        deleteTextAfterPause();
    }

    public void UpdateMessages() throws Exception{
        info2.clear();
        info2 = AdminBlockToGetAllUserInfo.getinfoForMessageTable2(GetCurrentSender.get(0).getKey());
        putInfoIntoTABLEview2();
        TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message", "User updated messages", Logic.GetFullDate());
        problem2.setText("Items Updated");
        deleteTextAfterPause();
    }

    public void upd2(){
         info2.clear();
        info2 = AdminBlockToGetAllUserInfo.getinfoForMessageTable2(GetCurrentSender.get(0).getKey());
        putInfoIntoTABLEview2();
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

    public void putInfoIntoTABLEview2(){
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        sender.setCellValueFactory(new PropertyValueFactory<>("sender"));
        message2.setCellValueFactory(new PropertyValueFactory<>("message"));
        arrived.setCellValueFactory(new PropertyValueFactory<>("date"));
         
        filteredInfo = new FilteredList<>(info2, p -> true);

         table2.setItems(filteredInfo);



         search.textProperty().addListener((obs, oldValue, newValue) -> {

         filteredInfo.setPredicate(user -> {

            if (newValue == null || newValue.isEmpty()) {

                return true;

            }

            String lowerCase = newValue.toLowerCase();

            return user.getDate().toLowerCase().contains(lowerCase) ||

                   String.valueOf(user.getId()).toLowerCase().contains(lowerCase) ||

                   user.getMessage().toLowerCase().contains(lowerCase) ||

                   user.getSender().toLowerCase().contains(lowerCase);

        });

    });

    //startRefreshTimeline();

    }

    private void refreshTable() {
    try {
        upd2();
        
       // table.refresh();       
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void startRefreshTimeline() {
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(30), e -> refreshTable())
    );
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
}

    public void SendMessage() throws Exception{
        boolean canBeSend = true;

        if(user.getText().isEmpty() && message.getText().isEmpty()){
            problem.setText("No information is added please add some");
            canBeSend = false;
            TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message Error", "User added no info", Logic.GetFullDate());
            return;
        }
        if(user.getText().isEmpty()){
            problem.setText("Please select the user by clicking it on the table");
            canBeSend = false;
            TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message Error", "User didin't select receiver", Logic.GetFullDate());
            return;
        }
        if(message.getText().isEmpty()){
            problem.setText("Please enter message");
            canBeSend = false;
            TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message Error", "User didin't add message", Logic.GetFullDate());
            return;
        }

        if(message.getText().length() >100){
             problem.setText("Message to long");
            canBeSend = false;
            TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message Error", "User's message is to long", Logic.GetFullDate());
            return;
        }

        if(canBeSend){
        SecretKey key = EncryptionBlock.decodeKey(userEncryptingKey);
        AddingInformationToTheDatabase.insertMessage(EncryptionBlock.encrypt(GetCurrentSender.get(0).getDatabase(), key), EncryptionBlock.encrypt(user.getText(), key), EncryptionBlock.encrypt(message.getText(), key), EncryptionBlock.encrypt(Logic.GetFullDate(), key));
        problem.setText("Send succesfully");
        TerminalLogic.insertInfoIntoDB(GetCurrentSender.get(0).getDatabase(), "Message", GetCurrentSender.get(0).getDatabase() + " Send message to " + user.getText(), Logic.GetFullDate());
        message.clear();
        
        }
    }
    public void trackTheMessageSize(){
        message.textProperty().addListener((s,ss,sss) ->{
            int lengh = sss.length();
            count.setText(lengh + "/100");

        });
    }

    public void getSelectedItem(){

        table.getSelectionModel().selectedItemProperty().addListener((obs,oldval,newval) ->{
            String selecteduser = newval.getName();
            user.setText(selecteduser);
            getuserkey(selecteduser);
        });

    }

    public void getuserkey(String name){
        for(UserAndKey s : userkey){
            if(s.getName().equals(name)){
                userEncryptingKey = s.getKey();
                break;
            }
        }
        
    }

    public void addInfoIntoFirstTableView(){
        usercolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        info.sort(Comparator.comparing(AdminTableView::getName));

         table.setItems(info);
    }

    public void GetUsersToTableView(){
        info.clear();
        for(UserAndKey s : userkey){
            info.add(new AdminTableView(s.getName()));
            
        }
    }
    // get unique names etc
    public void getUserAndKeys(){

        for(UserInfo s : userinfo){

            String database = s.getUser_Database();
            String key = s.getAge();
            boolean found = false;

            for(UserAndKey ss : userkey){
                if(ss.getName().equals(database) ){
                    found = true;
                }
            }
            // if something not working accordint to removing the current user from list here needs to be changed
            if(!found && !database.equals(GetCurrentSender.get(0).getDatabase())){
            userkey.add(new UserAndKey(database, key));
            }
        }



    }



    public void enterhome() throws IOException{
        if(GetCurrentSender.get(0).getDatabase().equalsIgnoreCase("admin1597")){
            enterNewScene(new ActionEvent(but1, null),  "admin's Managment block", "AdminBlockOne.fxml");
        }
        else{
        enterNewScene(new ActionEvent(but1, null), GetCurrentSender.get(0).getDatabase() + "'s Managment block", "UserPageOne.fxml");
        }
    }

      public void enteradd() throws IOException{
        if(GetCurrentSender.get(0).getDatabase().equalsIgnoreCase("admin1597")){
            enterNewScene(new ActionEvent(but3, null),  "admin's Managment block", "AdminBlockTwo.fxml");
            
            
        }
        else{
        enterNewScene(new ActionEvent(but3, null), GetCurrentSender.get(0).getDatabase() + "'s Managment block", "UserPageTwo.fxml");
         
        
            
        }
    }

    public void changenameaccordingtouser(){
        if(GetCurrentSender.get(0).getDatabase().equalsIgnoreCase("admin1597")){
            namelabel.setText("Welcome back admin");
            but3.setText("Charts");
        }
        else{
            namelabel.setText("Hello " + GetCurrentSender.get(0).getDatabase());
        }
    }



    // extra info for cosmetics and scene switching

       public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
    }

    public void enterterminal() throws IOException{
        enterNewScene(new ActionEvent(but3, null),  "admin's Managment block", "AdminBlockThree.fxml");
    }



    public void deleteTextAfterPause(){
        int duration = 5;
        duration++;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> problem.setText(""));
        pause.setOnFinished(event -> problem2.setText(""));
        pause.play();
    }


}
