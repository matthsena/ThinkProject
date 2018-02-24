package application;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.StageStyle;

public class personalPageScreenController implements Initializable {

    eventos evento = new eventos();
    Connection c;
    private static String query;
    private static List<String> ideias = new ArrayList<>();
        
    private static List<String> comentarios = new ArrayList<>();

    private static String statusrbnt;
    private static String statusdb;
    public static String titulo;
    
    @FXML Button btnnova;
    @FXML Button btnapagar;
    @FXML Button btnsalvar;
    @FXML TextField txttitulo;
    @FXML TextArea txtIdeia;
    @FXML ListView listIdeias;
    @FXML ListView listComentarios;
    @FXML RadioButton rbtnpublica;
    @FXML RadioButton rbtnprivada;
    @FXML ToggleGroup status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        comandos();
    }   
    private void limpar(){
      
        txttitulo.setText("");
        txtIdeia.setText("");        
        comentarios.removeAll(comentarios);
        ObservableList<String> ids = FXCollections.observableArrayList(comentarios);
        listComentarios.setItems(ids);      
        ideias.removeAll(ideias);       
        ObservableList<String> ids1 = FXCollections.observableArrayList(ideias);
        listIdeias.setItems(ids1);
    }
    
    private void iniciarListview(){
        
        
        rbtnpublica.setSelected(false);
        rbtnprivada.setSelected(false);
        try {
            c = (Connection) connAzureDB.connect();
            query = "SELECT * FROM TB_IDEIAS WHERE USUARIO_IDEIA = '"+initialScreenController.user+"';";
            Statement stmt =  c.createStatement();          
            ResultSet rs = stmt.executeQuery(query);  
            

            while(rs.next())
            {
                
               ideias.add(rs.getString("TITULO_IDEIA"));
               
            }
            ObservableList<String> ids = FXCollections.observableArrayList(ideias);
            listIdeias.setItems(ids);
            
            c.close();
      
        } catch (SQLException ex) {
            Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void comandos(){
       limpar();
       iniciarListview();
        
        status.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
   
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1)
   
            {
                RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle();
                if("Pública".equals(chk.getText())){
                    statusrbnt = "PUBLICA";
                }
                else if ("Privada".equals(chk.getText())){
                    statusrbnt = "PRIVADA";
                }
            }
        });

        listIdeias.getSelectionModel().getSelectedItem();
        listIdeias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {        
           
            @Override        
            
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {       
             

                comentarios.removeAll(comentarios);
         
                query = "SELECT TEXTO_IDEIA, STATUS_IDEIA FROM TB_IDEIAS WHERE TITULO_IDEIA = '"+newValue+"' AND USUARIO_IDEIA = '"+initialScreenController.user+"';";
                
                try {
                    c = (Connection) connAzureDB.connect();
                    Statement stmt =  c.createStatement();          
                    ResultSet rs = stmt.executeQuery(query);  
                    
                    while(rs.next())
                    {
                        txtIdeia.setText(rs.getString("TEXTO_IDEIA"));
                        txttitulo.setText(newValue);
                        statusdb = rs.getString("STATUS_IDEIA");
                    }
                    if("PUBLICA".equals(statusdb)){
                        rbtnpublica.setSelected(true);
                    }
                    else
                        if ("PRIVADA".equals(statusdb)){
                            rbtnprivada.setSelected(true);
                        }
                                    
                    query = "SELECT COMENTARIO FROM TB_COMENTARIOS WHERE TITULO_IDEIA = '"+newValue+"';";
                    try {
                    c = (Connection) connAzureDB.connect();
                    stmt =  c.createStatement();          
                    rs = stmt.executeQuery(query);  
                    
                    while(rs.next())
                    {
                        comentarios.add(rs.getString("COMENTARIO"));
                    }
                   
                    ObservableList<String> ids = FXCollections.observableArrayList(comentarios);
                    listComentarios.setItems(ids);

                    c.close();

                } catch (SQLException ex) {
                    Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                } catch (SQLException ex) {
                    Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        listComentarios.getSelectionModel().getSelectedItem();
        listComentarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {        
           
            @Override        
            
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {       
             
                try {
                    query = "SELECT COMENTARIO FROM TB_COMENTARIOS WHERE TITULO_IDEIA = '"+txttitulo.getText()+"';";
                    
                    
                    c = (Connection) connAzureDB.connect();
                    Statement stmt =  c.createStatement();
                    ResultSet rs2 = stmt.executeQuery(query);  
                    
                    if(rs2.next())
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("COMENTÁRIO");
                        alert.setContentText(newValue);
                    
                        alert.showAndWait();
                    }
                    c.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                        
            }
        });
        
        btnsalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (statusrbnt == null || "".equals(txttitulo.getText()) || "".equals(txtIdeia.getText())){
                    Alert alert = new Alert(Alert.AlertType.WARNING);                                                                                           
                    alert.setHeaderText(null);                                                         
                    alert.initStyle(StageStyle.UTILITY);                                                                        
                    alert.setResizable(false);                                                               
                    alert.setTitle("***AVISO***");                                           
                    alert.setContentText("Falha ao Salvar sua ideia, existem campos vazios!!!");                                                      
                    Optional <ButtonType> action = alert.showAndWait();
                }
                else{
                    
                    try {
                        
                        query = "SELECT TITULO_IDEIA FROM TB_IDEIAS WHERE TITULO_IDEIA = '"+txttitulo.getText()+"' AND USUARIO_IDEIA = '"+initialScreenController.user+"';";
                        try {
                            c = (Connection) connAzureDB.connect();
                            Statement stmt =  c.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            
                            if(rs.next()){
                                titulo = rs.getString("TITULO_IDEIA");
                                evento.AlterarIdeias(titulo, txtIdeia.getText(), statusrbnt);
                            }
                            else{                                
                                evento.InserirIdeia(txttitulo.getText(), statusrbnt, txtIdeia.getText(), initialScreenController.user);
                            }
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            
                        }  
                        c.close();
                        limpar();
                        iniciarListview();
                    } catch (SQLException ex) {
                        Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
               
                    }
                }
            }
        });
        
        btnapagar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                try {
                    query = "SELECT TITULO_IDEIA FROM TB_IDEIAS WHERE TITULO_IDEIA = '"+txttitulo.getText()+"' AND USUARIO_IDEIA = '"+initialScreenController.user+"';";
                    
                    try {
                        c = (Connection) connAzureDB.connect();
                        Statement stmt =  c.createStatement();             
                        ResultSet rs = stmt.executeQuery(query);
                        
                        if(rs.next()){
                            
                            evento.ApagarComentarios(txttitulo.getText());
                            evento.ApagarIdeia(txttitulo.getText());
                            txttitulo.requestFocus();
                        }
                        else{   
                            limpar();
                            iniciarListview();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                    c.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(personalPageScreenController.class.getName()).log(Level.SEVERE, null, ex);   
                }
                
                limpar();
                iniciarListview();
                
                txttitulo.setText("");
                txtIdeia.setText("");
            }
        });
        
        btnnova.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               limpar();
               iniciarListview();
           }
        });
        
        
        
    }
}