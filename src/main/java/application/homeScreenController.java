package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class homeScreenController implements Initializable {

    eventos evento = new eventos();
    String query;
    Connection c;
    @FXML Button btnatualizar;
    @FXML Button btnpessoal;
    @FXML Button btnselecionar;
    @FXML TextField txtbuscar;
    @FXML ListView listpublica;
    public static String nomeIdeia;
    public static int count = 0;
        
  
    private static List<String> ideias = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comandosHome();
    }    
    
    private void iniciarListview(){
        try {
            c = (Connection) connAzureDB.connect();
            query = "SELECT TITULO_IDEIA FROM TB_IDEIAS WHERE STATUS_IDEIA = 'PUBLICA';";
            Statement stmt =  c.createStatement();          
            ResultSet rs = stmt.executeQuery(query);  
            
            while(rs.next())
            {
                ideias.add(rs.getString("TITULO_IDEIA")); 
            }
          
            ObservableList<String> ids = FXCollections.observableArrayList(ideias);       
            listpublica.setItems(ids);
            c.close();
      
        } catch (SQLException ex) {
            Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void comandosHome(){
        
        iniciarListview();
        
        listpublica.getSelectionModel().getSelectedItem();
        listpublica.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {        
            @Override        
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {       
               
                nomeIdeia = newValue;
             
            }
        });
        
        btnpessoal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                count = count +1;
                try {  
                    Parent nextPage = FXMLLoader.load(getClass().getResource("/fxml/personalPageScreen.fxml"));                                                 
                    Scene nextPageScene = new Scene(nextPage);                                                  
                    Stage stage = new Stage();   
                    stage.setTitle("THINK - PÁGINA PESSOAL");
                    stage.setResizable(false);
                    stage.setScene(nextPageScene);
                    stage.showAndWait();
                 }                   
                 catch (IOException ex) {                                                                        
                    Logger.getLogger(initialScreenController.class.getName()).log(Level.SEVERE, null, ex);                                                      
                 }
            }
        });
        
        btnselecionar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if ("".equals(nomeIdeia) || nomeIdeia == null){
                    
                    Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                           
                    alert.setHeaderText(null);                                                         
                    alert.initStyle(StageStyle.UTILITY);                                                                        
                    alert.setResizable(false);                                                               
                    alert.setTitle("***AVISO***");                                           
                    alert.setContentText("Selecione pelo menos uma ideia publica!!");                                                      
                    Optional <ButtonType> action = alert.showAndWait();
                }
                
                else{          
                  
                    try {  
                    
                        Parent nextPage = FXMLLoader.load(getClass().getResource("/fxml/publicScreen.fxml"));                                                 
                        Scene nextPageScene = new Scene(nextPage);                                                  
                        Stage stage = new Stage();   
                        stage.setTitle("THINK - IDEIA PÚBLICA");
                        stage.setResizable(false);
                        stage.setScene(nextPageScene);
                        stage.showAndWait();
                     }                   
                     catch (IOException ex) {                                                                        
                        Logger.getLogger(initialScreenController.class.getName()).log(Level.SEVERE, null, ex);                                                      
                     }          
                }
            }
        });
        
        btnatualizar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent event){
                ideias.removeAll(ideias);
                iniciarListview();
            }
        });
    }
}