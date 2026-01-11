import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class startprogram extends Application {


    @Override
    public void start(Stage stage) {
        try { 
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            // puts the icon on the program
            Image icon = new Image(getClass().getResourceAsStream("aImages/PorgramIcon.png"));
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.setTitle("Log in");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
