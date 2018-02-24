package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class eventos {
    private static Connection c;
    private static String query;
    
    
    
    public void Abrir(ActionEvent event, String caminho, String titulo){
        try {                         
                     
            Parent nextPage = FXMLLoader.load(getClass().getResource(caminho)); 
            Scene nextPageScene = new Scene(nextPage);         
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  
            stage.setTitle(titulo);
            stage.setScene(nextPageScene);  
            stage.show(); 
        }   
                    
        catch (IOException ex) {           
            Logger.getLogger(initialScreenController.class.getName()).log(Level.SEVERE, null, ex);                                                      
        }
    }
    

    public void CamposVazios(){
        
        Alert alert = new Alert(Alert.AlertType.WARNING);  
        alert.setHeaderText(null);          
        alert.initStyle(StageStyle.UTILITY);     
        alert.setResizable(false);     
        alert.setTitle("***AVISO***");   
        alert.setContentText("Nenhum dos campos podem estar vazios!!!");
        Optional <ButtonType> action = alert.showAndWait();
    }
    
    public void InserirIdeia(String titulo, String status, String texto, String usuario){
        try{    

            c = (Connection) connAzureDB.connect();                                                        
            query = "INSERT INTO TB_IDEIAS (TITULO_IDEIA, STATUS_IDEIA, TEXTO_IDEIA, USUARIO_IDEIA) VALUES ('"+titulo+"', '"+status+"', '"+texto+"', '"+usuario+"');";                          
            Statement stmt =  c.createStatement();
            stmt.executeUpdate(query);  
            c.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                              
            alert.setHeaderText(null);                                                               
            alert.initStyle(StageStyle.UTILITY);                                                                        
            alert.setResizable(false);                                                        
            alert.setTitle("***AVISO***");                                                      
            alert.setContentText("Sucesso ao salvar sua ideia");                                                     
            Optional <ButtonType> action = alert.showAndWait();                              
        }                 
     
        catch(SQLException se){	                                      
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                           
            alert.setHeaderText(null);                                                         
            alert.initStyle(StageStyle.UTILITY);                                                                        
            alert.setResizable(false);                                                               
            alert.setTitle("***AVISO***");                                           
            alert.setContentText("Falha ao Salvar sua ideia, esse nome já foi utilizado por alguém!!!");                                                      
            Optional <ButtonType> action = alert.showAndWait(); 	                           
        }      
    }
    
    public void ApagarIdeia(String titulo){
        try{    

            c = (Connection) connAzureDB.connect();                                                        
            query = "DELETE FROM TB_IDEIAS WHERE TITULO_IDEIA = '"+titulo+"';";                          
            Statement stmt =  c.createStatement();
            stmt.executeUpdate(query);  
            c.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                              
            alert.setHeaderText(null);                                                               
            alert.initStyle(StageStyle.UTILITY);                                                                        
            alert.setResizable(false);                                                        
            alert.setTitle("***AVISO***");                                                      
            alert.setContentText("Sucesso ao apagar sua ideia");                                                     
            Optional <ButtonType> action = alert.showAndWait();                              
        }                 
     
        catch(SQLException se){	                                      
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                           
            alert.setHeaderText(null);                                                         
            alert.initStyle(StageStyle.UTILITY);                                                                        
            alert.setResizable(false);                                                               
            alert.setTitle("***AVISO***");                                           
            alert.setContentText("Falha ao apagar sua ideia !!!");                                                      
            Optional <ButtonType> action = alert.showAndWait(); 	                           
        } 
        
        
        
        
    }
    public void ApagarComentarios(String titulo){
        try{    

            c = (Connection) connAzureDB.connect();                                                        
            query = "DELETE FROM TB_COMENTARIOS WHERE TITULO_IDEIA = '"+titulo+"';";                          
            Statement stmt =  c.createStatement();
            stmt.executeUpdate(query);  
            c.close();                             
        }                 
     
        catch(SQLException se){	                  
            System.out.println(se);
        } 
    }
    
    public void AlterarIdeias(String titulo, String texto, String status){
        
        try{    
            
            c = (Connection) connAzureDB.connect();                                                        
            query = "UPDATE TB_IDEIAS SET TEXTO_IDEIA = '"+texto+"', STATUS_IDEIA = '"+status+"' WHERE TITULO_IDEIA = '"+titulo+"';";                          
            Statement stmt =  c.createStatement();
            stmt.executeUpdate(query);  
            c.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);                                                                                              
            alert.setHeaderText(null);                                                               
            alert.initStyle(StageStyle.UTILITY);                                                                        
            alert.setResizable(false);                                                        
            alert.setTitle("***AVISO***");                                                      
            alert.setContentText("Sucesso ao atualizar sua ideia");                                                     
            Optional <ButtonType> action = alert.showAndWait();                              
        }                 
     
        catch(SQLException se){	                                      
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                           
            alert.setHeaderText(null);                                                         
            alert.initStyle(StageStyle.UTILITY);                                                                        
            alert.setResizable(false);                                                               
            alert.setTitle("***AVISO***");                                           
            alert.setContentText("Falha ao atualizar sua ideia !!!");                                                      
            Optional <ButtonType> action = alert.showAndWait(); 
            
        }
        
    }
}
