package FXMLControllers;

import java.io.IOException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import DBControllers.CheckConnectioForDB;
import DBControllers.ReadingInformatioFromDB;
import GenerallyUsedCode.EncryptionBlock;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.Logic;
import GenerallyUsedCode.TerminalLogic;
import ItemClases.GetCurrentUser;
import ItemClases.Settings;
import ItemClases.UserSearch;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class LogInController {


    @FXML
    TextField UserLogin;

    @FXML 
    TextField PasswordLogIn;

    @FXML 
    Button LogInButton;

    @FXML
    Label Information;

    @FXML
    Hyperlink hyper;

    @FXML
    Label DBstatus;

    @FXML
    ImageView iconFront;

     @FXML
    Button ShowPassword;

    @FXML 
    TextField password2;

    @FXML
    ImageView imageofeye;
    int buttonSTAGE = 0;

    @FXML
    AnchorPane pane;

    @FXML
    Label time;

    int times = 1;
    int canfail = 3;
    int multiplyer = 1;

    boolean adminlocked = false;
    int admintried = 0;

    private Timeline timeline;

    public void initialize() throws Exception{
       changecolor();

       // hyper link handler
        HyperLinkHandler();
     
        loadPictures();
        
        Catchenter();
    }

    


    public void showPasswordButton(){
        Image Open = new Image("aImages/EyeOpen.png");
        Image Closed = new Image("aImages/EyeClosed.png");
        
        buttonSTAGE++;
       
        if(buttonSTAGE == 1){
            password2.setVisible(true);
            PasswordLogIn.setVisible(false);
            password2.setText(PasswordLogIn.getText());
            imageofeye.setImage(Closed);
        }
         if(buttonSTAGE == 2){
            password2.setVisible(false);
            PasswordLogIn.setVisible(true);
            PasswordLogIn.setText(password2.getText());
            imageofeye.setImage(Open);
        }
        
        
        if(buttonSTAGE == 2){
            buttonSTAGE = 0;
        }
        
    }

    public void Catchenter(){
        pane.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ENTER)
            try {
                CheckIfCredentialsAreRight();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
            if(event.getCode() == KeyCode.DELETE && event.isShiftDown()){
                Platform.exit();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



    }

    public void loadPictures(){
        Image image = new Image("aImages/LogInIcon.png");
        iconFront.setImage(image);
        Image image3 = new Image("aImages/EyeOpen.png");
        imageofeye.setImage(image3);
    }

    public void changecolor() throws Exception{
         DBstatus.setText(CheckConnectioForDB.CheckDBStatus());
        String status = CheckConnectioForDB.CheckDBStatus();
        if(status.equals("Online")){
            DBstatus.setTextFill(Color.GREEN);
            LogInButton.setDisable(false);
        }
        if(status.equals("Offline")){
            DBstatus.setTextFill(Color.RED);
            LogInButton.setDisable(true);
        }
        if(status.equals("Problem")){
            DBstatus.setTextFill(Color.YELLOW);
        }
         if(status.equals("Credential error")){
            DBstatus.setTextFill(Color.RED);
            LogInButton.setDisable(true);
        }
         if(status.equals("No database")){
            DBstatus.setTextFill(Color.RED);
            LogInButton.setDisable(true);
        }
    }

    public void CheckIfCredentialsAreRight() throws Exception{

        ArrayList<UserSearch> searchUsers = ReadingInformatioFromDB.GetUserToCheckSignIn();
        ArrayList<UserSearch> DecryptedUserInfo = new ArrayList<>();

        if(!searchUsers.isEmpty()){
        for(int i = 0; i < searchUsers.size(); i++){
            int id = searchUsers.get(i).getId();
            String User = EncryptionBlock.decrypt(searchUsers.get(i).getUser(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));
            String Password = searchUsers.get(i).getPassword();
            String database = EncryptionBlock.decrypt(searchUsers.get(i).getData_base(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));
            String ProfileID =  EncryptionBlock.decrypt(searchUsers.get(i).getProfileID(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));
            String Sector =  EncryptionBlock.decrypt(searchUsers.get(i).getSector(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));

            DecryptedUserInfo.add(new UserSearch(id, User, Password,database,ProfileID,Sector, searchUsers.get(i).getKey()));
         }
        }

        if(!DecryptedUserInfo.isEmpty()){
        if(UserLogin.getText().isEmpty() && PasswordLogIn.getText().isEmpty()){
            Information.setText("No informations is added");
            deleteTextAfterPause();
            TerminalLogic.insertInfoIntoDB("Unknown", "Login Error", "User added no info", Logic.GetFullDate());
            return;
         }
         if(UserLogin.getText().isEmpty()){
            Information.setText("No Username added");
            deleteTextAfterPause();
            TerminalLogic.insertInfoIntoDB("Unknown", "Login Error", "User didin't add username", Logic.GetFullDate());
            return;
         }
          if(PasswordLogIn.getText().isEmpty() && password2.getText().isEmpty()){
            Information.setText("No Password added");
            deleteTextAfterPause();
            TerminalLogic.insertInfoIntoDB(UserLogin.getText(), "Login Error", "User didin't add password", Logic.GetFullDate());
            return;
         }

         boolean userFound = false;
         String name = "";
         String database = "";
         String Sector = "";
         String key = "";
         String profilePic = "";
         String Ainfo = "";

          Boolean canContinue = null;
        ArrayList<Settings> settings = ReadingInformatioFromDB.getSettings();
        for(Settings s : settings){
            if(s.getSettingname().equalsIgnoreCase("Allow user connect")){
                canContinue = Boolean.parseBoolean(s.getSettingValue());
                
            }
        }

     
         //check if user exists and password and name are correct


         String password = "";
         if(buttonSTAGE == 1){
            password = password2.getText();
         }
         else{
            password = PasswordLogIn.getText();
         }
         
        
         for(int i = 0; i < DecryptedUserInfo.size(); i++){
            
            if(canContinue && UserLogin.getText().equalsIgnoreCase(DecryptedUserInfo.get(i).getUser()) && BCrypt.checkpw(password, DecryptedUserInfo.get(i).getPassword())){
            
                name = DecryptedUserInfo.get(i).getUser();
                database = DecryptedUserInfo.get(i).getData_base();
                Sector = DecryptedUserInfo.get(i).getSector();
                key = DecryptedUserInfo.get(i).getKey();
                profilePic = DecryptedUserInfo.get(i).getProfileID();
                userFound = true;
            }
            if(!canContinue && !UserLogin.getText().equalsIgnoreCase("admin")){
                Information.setText("Currently log in is disabled");
                TerminalLogic.insertInfoIntoDB(UserLogin.getText(), "Login dis", "Exsisting user was denied log in", Logic.GetFullDate());
                return;
            }
       
            if(DecryptedUserInfo.get(i).getUser().equalsIgnoreCase("admin")){
                Ainfo = DecryptedUserInfo.get(i).getPassword();
                name = DecryptedUserInfo.get(i).getUser();
                database = DecryptedUserInfo.get(i).getData_base();
                Sector = DecryptedUserInfo.get(i).getSector();
                key = DecryptedUserInfo.get(i).getKey();
                profilePic = DecryptedUserInfo.get(i).getProfileID();
            }

         }

         boolean isadmin = false;
         boolean passwordMatches = false;
         if(buttonSTAGE == 1){
             passwordMatches = BCrypt.checkpw(password2.getText().trim(), Ainfo);
         }
         else{
             passwordMatches = BCrypt.checkpw(PasswordLogIn.getText().trim(), Ainfo);
         }
         

         if(UserLogin.getText().equalsIgnoreCase("admin")){
            isadmin = true;
         }

         if(isadmin && passwordMatches && !adminlocked){
            enterNewScene(new ActionEvent(LogInButton, null), "Admin's Managment block", "AdminBlockOne.fxml");
             Logic.AddCurrentUsersInfoToTxtFile(name, database,Sector,key,profilePic);
         }

         else{
            admintried++;
            if(admintried >= 2){
                adminlocked = true;
                Information.setText("Admin block is [BLOCKED]");
                TerminalLogic.insertInfoIntoDB("Admin", "Failed to login", "Someone tried to enter admin failed", Logic.GetFullDate());
            }
            
         }
         
         DecryptedUserInfo.clear();
         
         if(userFound && !password.isEmpty() && !UserLogin.getText().isEmpty() && !UserLogin.getText().equalsIgnoreCase("admin")){
            Logic.AddCurrentUsersInfoToTxtFile(name, database,Sector,key,profilePic);
            Information.setText("Something went wrong");

            // get current user
            String username = "";
            ArrayList<GetCurrentUser> userinformation = new ArrayList<>();
            userinformation = Logic.ReadCurrentUsersInfoToTxtFile();
            username = userinformation.get(0).getName();
            // enter user block one
           enterNewScene(new ActionEvent(LogInButton, null), username + "'s Managment block","UserPageOne.fxml");
           TerminalLogic.insertInfoIntoDB(UserLogin.getText(), "Login", "Exsisting user logged in", Logic.GetFullDate());
         }

         else{
            if(canContinue && !UserLogin.getText().equalsIgnoreCase("admin")){
            Information.setText("Username or password is incorrect " +  canfail + "/" + times);
            TerminalLogic.insertInfoIntoDB(UserLogin.getText(), "Login Error", "Someone trying to connect (failed)", Logic.GetFullDate());
                
                if(times == canfail){
                    LogInButton.setDisable(true);
                    hyper.setDisable(true);
                    Information.setText("Bloked please try again later");
                    times++;

                    timer();
        
                }
                times++;
                
            }
            deleteTextAfterPause();
           
            
            return;
            
         }
        }
    }

    public void timer(){

        lockFromLeaving("lock");
        int wait = 5 * multiplyer +1;
        time.setText(String.valueOf(wait));
      
  timeline = new Timeline(
    new KeyFrame(Duration.seconds(1), e -> {
        int currentTime = Integer.parseInt(time.getText());
        if (currentTime > 0) {
            ShowPassword.setDisable(true);
            password2.clear();
            PasswordLogIn.clear();
            UserLogin.clear();
            time.setVisible(true);
            hyper.setDisable(true);
            Information.setText("");
            time.setText(String.valueOf(currentTime - 1));
        } else {  
            time.setVisible(false);
            hyper.setDisable(false);
            ShowPassword.setDisable(false);
            LogInButton.setDisable(false);
            timeline.stop();
            times = 1;
            lockFromLeaving("unlock");
        }
    })
    
);

    multiplyer = multiplyer +2;
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    }

    public void lockFromLeaving(String lock){
        if(lock.equalsIgnoreCase("lock")){
        Stage stage = (Stage) time.getScene().getWindow();
        stage.setOnCloseRequest(event -> {
    event.consume();
});
        }
        else{
            Stage stage = (Stage) time.getScene().getWindow();
        stage.setOnCloseRequest(event -> {
});
        }
    }


    public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}

    // handle hyper link
   public void HyperLinkHandler(){
      hyper.setOnAction(event ->{
        try {
            enterNewScene(event, "Sign In", "SignIn.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
       });
   }




    public void deleteTextAfterPause(){
        int duration = 5;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> Information.setText(""));
        pause.play();
    }

}
