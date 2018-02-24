package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class publicScreenController implements Initializable {

    @FXML Button btncomentar;
    @FXML TextField txttitulo;
    @FXML TextArea txtideia;
    @FXML TextArea txtcomentario;
    String query;
    Connection c;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            c = (Connection) connAzureDB.connect();
            query = "SELECT * FROM TB_IDEIAS WHERE TITULO_IDEIA = '"+homeScreenController.nomeIdeia+"';";
            Statement stmt =  c.createStatement();          
            ResultSet rs = stmt.executeQuery(query);  
            txttitulo.setText(homeScreenController.nomeIdeia);

            while(rs.next())
            {
               txtideia.setText(rs.getString("TEXTO_IDEIA"));
            }
            c.close();
      
        } catch (SQLException ex) {
            Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btncomentar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if ("".equals(txtcomentario.getText())){
                  
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setResizable(false);
                    alert.setTitle("***AVISO***");
                    alert.setContentText("Seu comentário deve ter algum conteúdo!");
                }
                else{
                    try {
                        c = (Connection) connAzureDB.connect();
                    } catch (SQLException ex) {
                        Logger.getLogger(publicScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        query = "INSERT INTO TB_COMENTARIOS (COMENTARIO, TITULO_IDEIA) VALUES ('"+txtcomentario.getText()+"', '"+homeScreenController.nomeIdeia+"');";
                        Statement stmt =  c.createStatement();
                        stmt.executeUpdate(query);
                        c.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setResizable(false);
                        alert.setTitle("***AVISO***");
                        alert.setContentText("Sucesso ao enviar seu comentário|");                              
                        Optional <ButtonType> action = alert.showAndWait();
                        txtcomentario.setText("");
                        btncomentar.requestFocus();
                    } catch (SQLException ex) {
                        Logger.getLogger(publicScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }    
}