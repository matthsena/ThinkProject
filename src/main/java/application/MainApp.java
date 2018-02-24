package application;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/initialScreen.fxml"));
        stage.setResizable(false);
        stage.setTitle("THINK | Bem-Vindo");
        Scene scene = new Scene(root);    
        stage.setScene(scene);
        stage.show();
        
    }
    
    public static void main(String[] args) {

        launch(args);
    }
}