package FXMLControllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.mindrot.jbcrypt.BCrypt;

import DBControllers.AddingInformationToTheDatabase;
import DBControllers.CheckConnectioForDB;
import DBControllers.CreatingChemesInDB;
import DBControllers.ReadingInformatioFromDB;
import GenerallyUsedCode.EncryptionBlock;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.Logic;
import GenerallyUsedCode.TerminalLogic;
import ItemClases.Settings;
import ItemClases.UserSearch;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SignInController {

    
    @FXML
    MenuItem Menu1;

    @FXML
    MenuItem Menu2;

    @FXML
    TextField UserSignIn;

    @FXML
    TextField PasswordSignIn;

    @FXML
    Button SignIn;

    @FXML
    Hyperlink hyper;

    @FXML
    Label Information;

    @FXML
    Label DBstatus;

    @FXML
    TextField CategoryDislpay;

    @FXML
    ImageView iconFront;

      @FXML
    Button ShowPassword;

    @FXML 
    TextField password2;

    @FXML
    ImageView imageofeye;
    int buttonSTAGE = 0;



    static String Sector = ""; 



    public void initialize() throws Exception{
        // creating DB
        CreatingChemesInDB.DatabaseAllInOne();

        // display the database status
        changecolor();

        // hyper link handler
        HyperLinkHandler();

        loadPictures();

    }


    public void showPasswordButton(){
        Image Open = new Image("aImages/EyeOpen.png");
        Image Closed = new Image("aImages/EyeClosed.png");
        
        buttonSTAGE++;
        if(buttonSTAGE == 1){
            password2.setVisible(true);
            PasswordSignIn.setVisible(false);
            password2.setText(PasswordSignIn.getText());
            imageofeye.setImage(Closed);
        }
         if(buttonSTAGE == 2){
            password2.setVisible(false);
            PasswordSignIn.setVisible(true);
            PasswordSignIn.setText(password2.getText());
            imageofeye.setImage(Open);
        }
        
        
        if(buttonSTAGE == 2){
            buttonSTAGE = 0;
        }
        
    }

       public void loadPictures(){
        Image Open = new Image("aImages/EyeOpen.png");
        Image image = new Image("aImages/SignInIcon.png");
        iconFront.setImage(image);
        imageofeye.setImage(Open);
    }

   


    public void changecolor() throws Exception{
         DBstatus.setText(CheckConnectioForDB.CheckDBStatus());
        String status = CheckConnectioForDB.CheckDBStatus();
        if(status.equals("Online")){
            DBstatus.setTextFill(Color.GREEN);
            SignIn.setDisable(false);
        }
        if(status.equals("Offline")){
            DBstatus.setTextFill(Color.RED);
            SignIn.setDisable(true);
        }
        if(status.equals("Problem")){
            DBstatus.setTextFill(Color.YELLOW);
        }
         if(status.equals("Credential error")){
            DBstatus.setTextFill(Color.RED);
            SignIn.setDisable(true);
        }
          if(status.equals("No database")){
            DBstatus.setTextFill(Color.RED);
            SignIn.setDisable(true);
        }
    }


    public void CreateNewUser() throws Exception{

        // get user info
        
        ArrayList<UserSearch> searchUsers = ReadingInformatioFromDB.GetUserToCheckSignIn();
        ArrayList<UserSearch> DecryptedUserInfo = new ArrayList<>();

        if(!searchUsers.isEmpty()){

         for(int i = 0; i < searchUsers.size(); i++){
            int id = searchUsers.get(i).getId();
            String User = EncryptionBlock.decrypt(searchUsers.get(i).getUser(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));
            String Password = searchUsers.get(i).getPassword();
            String Database =  EncryptionBlock.decrypt(searchUsers.get(i).getData_base(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));
            String ProfileID =  EncryptionBlock.decrypt(searchUsers.get(i).getProfileID(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));
            String Sector =  EncryptionBlock.decrypt(searchUsers.get(i).getSector(), EncryptionBlock.decodeKey(searchUsers.get(i).getKey()));

            DecryptedUserInfo.add(new UserSearch(id, User,Password,Database,ProfileID,Sector, searchUsers.get(i).getKey()));
         }
        }

        if(!DecryptedUserInfo.isEmpty()){
         Boolean canBeAdded = true;

        for(int i = 0; i < DecryptedUserInfo.size(); i++){
            
            if(DecryptedUserInfo.get(i).getUser().equalsIgnoreCase(UserSignIn.getText())){
                canBeAdded = false;
            }
            
        }

        // find errors
         if(UserSignIn.getText().isEmpty() && PasswordSignIn.getText().isEmpty()){
            Information.setTextFill(Color.RED);
            Information.setText("No information added");
            deleteTextAfterPause();
            TerminalLogic.insertInfoIntoDB("Unknown", "Sign in error", "No information added", Logic.GetFullDate());
            return;
        }
         if(UserSignIn.getText().isEmpty()){
            Information.setTextFill(Color.RED);
             Information.setText("No user is added");
             deleteTextAfterPause();
             TerminalLogic.insertInfoIntoDB("Unknown", "Sign in error", "No username added", Logic.GetFullDate());
            return;
        }
         if(PasswordSignIn.getText().isEmpty() && password2.getText().isEmpty()){
            Information.setTextFill(Color.RED);
             Information.setText("No password is added");
             deleteTextAfterPause();
             TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "Sign in error", "No password added", Logic.GetFullDate());
            return;
        }
        if(Sector.isEmpty() || CategoryDislpay.getText().isEmpty()){
            Information.setTextFill(Color.RED);
            Information.setText("No sector selected");
            deleteTextAfterPause();
            TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "Sign in error", "No sector selected", Logic.GetFullDate());
            return;
        }


        boolean maxError = false;
        boolean minError = false;

        int max_lenght_of_username = 15;
        int min_lenght_of_username = 4;


        if(UserSignIn.getText().length() > max_lenght_of_username){
            Information.setTextFill(Color.RED);
             Information.setText("max name lenght is " + max_lenght_of_username);
             deleteTextAfterPause();
            maxError = true;
            TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "Sign in error lenght", "User entered to short name", Logic.GetFullDate());
            return;
            
        }
        if(UserSignIn.getText().length() < min_lenght_of_username){
            Information.setTextFill(Color.RED);
             Information.setText("min name lenght is "+ min_lenght_of_username);
             deleteTextAfterPause();
            minError = true;
            TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "Sign in error lenght", "User entered to long name", Logic.GetFullDate());
            return;
        }

          Boolean canContinue = null;
        ArrayList<Settings> settings = ReadingInformatioFromDB.getSettings();
        for(Settings s : settings){
            if(s.getSettingname().equalsIgnoreCase("Allow new registration")){
                canContinue = Boolean.parseBoolean(s.getSettingValue());
                
            }
        }
        
        if(canContinue){
            String password = "";
         if(buttonSTAGE == 1){
            password = password2.getText();
         }
         else{
            password = PasswordSignIn.getText();
         }

         boolean passOK = false;
         if(!PasswordSignIn.getText().isEmpty() || !password2.getText().isEmpty()){
            passOK = true;
         }
         

        if(canBeAdded && !Sector.isEmpty() && !maxError && !minError && !UserSignIn.getText().isEmpty() & passOK && !CategoryDislpay.getText().isEmpty() && !UserSignIn.getText().equalsIgnoreCase("admin")){
            // get key per user
            SecretKey key = EncryptionBlock.getKey();
            String stringKey = Base64.getEncoder().encodeToString(key.getEncoded());

            AddingInformationToTheDatabase.AddUserIntoDatabase(EncryptionBlock.encrypt(UserSignIn.getText(), key), BCrypt.hashpw(password, BCrypt.gensalt()), EncryptionBlock.encrypt(UserSignIn.getText() + Logic.getUsersDatabaseDigits(), key), EncryptionBlock.encrypt(Logic.getIconId(), key), EncryptionBlock.encrypt(Logic.GetCurrentDate(), key), EncryptionBlock.encrypt(Sector, key),  stringKey);
            TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "Sign in", "Created new account", Logic.GetFullDate());
            UserSignIn.clear();
            PasswordSignIn.clear();
            password2.clear();
            Sector = "";
            CategoryDislpay.setText("");
            Information.setTextFill(Color.GREEN);
            Information.setText("Added succsefully");
            
        }
    }
    else{
        Information.setText("Adding new user is not allowed");
        TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "sign in dis", "New user is trying to create new account den", Logic.GetFullDate());
    }

        if(UserSignIn.getText().equalsIgnoreCase("admin")){
            Information.setTextFill(Color.RED);
             Information.setText("Name CANNOT be [admin]");
            deleteTextAfterPause();
            TerminalLogic.insertInfoIntoDB("Unknown", "Sign in", "User tried creating admin acc", Logic.GetFullDate());
            return;
        }

         if(!canBeAdded){
            Information.setTextFill(Color.RED);
            Information.setText("User exits");
            deleteTextAfterPause();
            TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "Sign in error", "Someone tried create acc with existing acc", Logic.GetFullDate());
            return;
        }
    }
    
    }

    // get sector
    public void UserField(ActionEvent event){
        String user = "";
        MenuItem source = (MenuItem) event.getSource();
         user = source.getText();
         Sector = user;
         CategoryDislpay.setText(Sector);
         
    }
   

    // enter scene
    public void enterNewScene(ActionEvent event,String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}

    public void enterdbselector() throws Exception{
             Boolean canContinue = false;
        ArrayList<Settings> settings = ReadingInformatioFromDB.getSettings();
        if(!settings.isEmpty()){
        for(Settings s : settings){
            if(s.getSettingname().equalsIgnoreCase("Allow Database edit")){
                canContinue = Boolean.parseBoolean(s.getSettingValue());
                TerminalLogic.insertInfoIntoDB(UserSignIn.getText(), "Database dis", "Database cannot be modified dis", Logic.GetFullDate());
            }
        }
    }

        if(canContinue ){
        enterNewScene(new ActionEvent(SignIn, null),   "Database information","DatabaseCredentials.fxml");
        }
        else if(!DBstatus.getText().equalsIgnoreCase("Online")){
            enterNewScene(new ActionEvent(SignIn, null),   "Database information","DatabaseCredentials.fxml");
        }
        else{
            Information.setTextFill(Color.RED);
            Information.setText("Database is not editable");
            deleteTextAfterPause();
        }
    }

    // handle hyper link
   public void HyperLinkHandler(){
      hyper.setOnAction(event ->{
        try {
            enterNewScene(event, "Log In", "login.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
       });
   }



    // make text dissapear
    public void deleteTextAfterPause(){
        int duration = 5;
        duration++;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> Information.setText(""));
        pause.play();
    }

   

}
