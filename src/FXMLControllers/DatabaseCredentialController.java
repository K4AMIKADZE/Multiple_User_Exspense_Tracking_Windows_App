package FXMLControllers;

import java.io.IOException;
import java.util.Base64;

import javax.crypto.SecretKey;

import DBControllers.CheckConnectioForDB;
import GenerallyUsedCode.EncryptionBlock;
import GenerallyUsedCode.HandleSwitchingScenes;
import GenerallyUsedCode.Logic;
import GenerallyUsedCode.TerminalLogic;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DatabaseCredentialController {

    @FXML
    TextField url;

    @FXML
    PasswordField name;

    @FXML
    PasswordField password;

    @FXML
    Button add;

    @FXML 
    Label problem;

    @FXML
    ImageView logimage;

    @FXML
    Label DBstatus;

    int timespressed = 0;

    public void initialize() throws Exception {
        LogoutButtonImage();
        changecolor();
    }


     public void changecolor() throws Exception{
         DBstatus.setText(CheckConnectioForDB.CheckDBStatus());
        String status = CheckConnectioForDB.CheckDBStatus();
        if(status.equals("Online")){
            DBstatus.setTextFill(Color.GREEN);
            
        }
        if(status.equals("Offline")){
            DBstatus.setTextFill(Color.RED);
            
        }
        if(status.equals("Problem")){
            DBstatus.setTextFill(Color.YELLOW);
        }
         if(status.equals("Credential error")){
            DBstatus.setTextFill(Color.RED);
            
        }
          if(status.equals("No database")){
            DBstatus.setTextFill(Color.RED);
            
        }
    }

     public void LogoutButtonImage(){
      Image image = new Image("aImages/LogOut.png");
      logimage.setImage(image);
    }


    public void AddInfoToFile() throws Exception{
        if(!url.getText().isEmpty() && !name.getText().isEmpty()){ // check if user is not trying to enter DB without password ententionally
        timespressed++;
        }
        boolean seemsOK = true;

        if(url.getText().isEmpty() && name.getText().isEmpty() ){ // && password.getText().isEmpty()
            problem.setTextFill(Color.RED);
            problem.setText("No information added");
            seemsOK = false;
            deleteTextAfterPause();
             if(DBstatus.getText().equalsIgnoreCase("Online")){
            TerminalLogic.insertInfoIntoDB("Unknown", "Database", "User didin't add any info", Logic.GetFullDate());
             }
            return;
        }
        if(url.getText().isEmpty()){
            problem.setTextFill(Color.RED);
            problem.setText("No url added");
            seemsOK = false;
            deleteTextAfterPause();
            if(DBstatus.getText().equalsIgnoreCase("Online")){
            TerminalLogic.insertInfoIntoDB("Unknown", "Database", "Didin't add DB url", Logic.GetFullDate());
            }
            return;
        }
         if(name.getText().isEmpty()){
            problem.setTextFill(Color.RED);
            problem.setText("No name added");
            seemsOK = false;
            deleteTextAfterPause();
            if(DBstatus.getText().equalsIgnoreCase("Online")){
                TerminalLogic.insertInfoIntoDB("Unknown", "Database", "Didin't add DB root name", Logic.GetFullDate());
            }
            
            return;
        }
         if(password.getText().isEmpty()){
            problem.setTextFill(Color.RED);
            problem.setText("No password added (UNSAFE) Are you sure you want to continue ");
            add.setText("Yes Add");
            if(DBstatus.getText().equalsIgnoreCase("Online")){
                TerminalLogic.insertInfoIntoDB("Unknown", "Database", "Connected to DB no pass (UNSAFE)", Logic.GetFullDate());
            }
            
            
        }
        else{
            timespressed = 2;
        }


        if(seemsOK && timespressed ==2){
        problem.setTextFill(Color.GREEN);
        SecretKey key = EncryptionBlock.getKey();
        String stringKey = Base64.getEncoder().encodeToString(key.getEncoded());
        String urls = EncryptionBlock.encrypt(url.getText(), key);
        String names = EncryptionBlock.encrypt(name.getText(), key);
        String pass = EncryptionBlock.encrypt(password.getText(), key);
        Logic.WriteDatabaseInformation(urls, names, pass, stringKey);
        changecolor();
        problem.setText("Information added");
        deleteTextAfterPause();
        if(DBstatus.getText().equalsIgnoreCase("Online")){
        TerminalLogic.insertInfoIntoDB("Unknown", "Database", "User changed DB info", Logic.GetFullDate());
        }
        url.clear();
        name.clear();
        if (!password.getText().isEmpty()) {
            password.clear();
        }
        add.setText("Add database");
        timespressed = 0; // reset are you sure promt
        
        }
    }

    public void exit() throws IOException{
      enterNewScene(new ActionEvent(add, null), "Log in","login.fxml");
     }

            public void enterNewScene(ActionEvent event, String titleOfTheNewScene, String ScenePath) throws IOException {
        HandleSwitchingScenes handlestuff = new HandleSwitchingScenes();
        handlestuff.changeScene(event, titleOfTheNewScene, ScenePath);
}


     // make text dissapear
    public void deleteTextAfterPause(){
        int duration = 5;
        duration++;
        PauseTransition pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> problem.setText(""));
        pause.play();
    }


}
