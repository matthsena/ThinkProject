package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class initialScreenController implements Initializable {
            
    eventos evento = new eventos();
    private static Connection c;
    private static String query;
    public static String user;
    @FXML private Button btnregistrar;
    @FXML private Button btnentrar;
    @FXML private Button btnvoltar;
    @FXML private TextField txtuserR;
    @FXML private TextField txtemailR;
    @FXML private PasswordField txtsenhaR;
    @FXML private PasswordField txtsenhaE;
    @FXML private TextField txtuserE;
    @FXML private Button btnconta;
    @FXML private AnchorPane anchorPaneEntrar;  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comandos();
    }     
    
    private void limparCampos(){
        
        txtemailR.setText("");                            
        txtsenhaE.setText("");                                                 
        txtsenhaR.setText("");                                             
        txtuserE.setText("");                                              
        txtuserR.setText("");
    }
    private void comandos(){
        
        final TranslateTransition acaoAparecer = new TranslateTransition(new Duration(350), anchorPaneEntrar); 
        final TranslateTransition acaoEsconder = new TranslateTransition(new Duration(350), anchorPaneEntrar);                 
        acaoEsconder.setToX(0); 
       
        btnregistrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               if("".equals(txtuserR.getText()) || "".equals(txtsenhaR.getText()) || "".equals(txtemailR.getText())){                                 
                   evento.CamposVazios();
               }
               else {
                    try{    
                        c = (Connection) connAzureDB.connect();                                                        
                        query = "INSERT INTO TB_USUARIO (USUARIO, SENHA, EMAIL) VALUES ('"+txtuserR.getText()+"','"+txtsenhaR.getText()+"','"+txtemailR.getText()+"');";                          
                        Statement stmt =  c.createStatement();
                        stmt.executeUpdate(query);  
                        c.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                              
                        alert.setHeaderText(null);                                                               
                        alert.initStyle(StageStyle.UTILITY);                                                                        
                        alert.setResizable(false);                                                        
                        alert.setTitle("***AVISO***");                                                      
                        alert.setContentText("Sucesso ao cadastrar  se");                                                     
                        Optional <ButtonType> action = alert.showAndWait();  
                        if(anchorPaneEntrar.getTranslateX()== 0){          
                            acaoAparecer.setToX(-(anchorPaneEntrar.getWidth()));
                            acaoAparecer.play();   
                        } 
                        
                    }    
                    catch(SQLException se){	                                      
                        Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                           
                        alert.setHeaderText(null);                                                         
                        alert.initStyle(StageStyle.UTILITY);                                                                        
                        alert.setResizable(false);                                                               
                        alert.setTitle("***AVISO***");                                           
                        alert.setContentText("Falha ao registrar, usuário já existente!!!");                                                      
                        Optional <ButtonType> action = alert.showAndWait(); 	                           
                    }      
               }
               limparCampos();
            }
        });
        
        btnentrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
                try {                  
                    c = (Connection) connAzureDB.connect();                                   
                    query = "SELECT USUARIO, SENHA FROM TB_USUARIO WHERE USUARIO = '"+txtuserE.getText()+"' AND SENHA = '"+txtsenhaE.getText()+"';";                          
                    Statement stmt =  c.createStatement();          
                    ResultSet rs = stmt.executeQuery(query);  
                    
                    if (rs.next()) {
                        user = txtuserE.getText();
                        evento.Abrir(event, "/fxml/homeScreen.fxml", "THINK - HOME");    
                        
                    }
                    else {		
                        Alert alert = new Alert(Alert.AlertType.WARNING);                                         
                        alert.setHeaderText(null);                     
                        alert.initStyle(StageStyle.UTILITY);                                
                        alert.setResizable(false);                    
                        alert.setTitle("***AVISO***");                     
                        alert.setContentText("Falha ao entrar, verifique se seu usuário e senha estão corretos!!!");                      
                        Optional <ButtonType> action = alert.showAndWait(); 
                        limparCampos();    
                    }			        	                                                      
                }        
                catch(SQLException se){		                     
                     
                    JOptionPane.showMessageDialog(null,"Erro"+se);
                    limparCampos();
                }                      
            }   
        });
        
        btnconta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if(anchorPaneEntrar.getTranslateX()== 0){          
                    acaoAparecer.setToX(-(anchorPaneEntrar.getWidth()));
                    acaoAparecer.play();   
                } 
            }
        });
       
        btnvoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              acaoEsconder.play();          
            }
        });
        
        
    }
}