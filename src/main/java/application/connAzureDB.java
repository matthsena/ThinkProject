package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class connAzureDB {    
    private static String hostName = "thinkbanco.database.windows.net";
    private static String dbName = "banco_think";
    private static String user = "matthsena";
    private static String password = "thinkbanco123@#--";
    private static String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
    private static Connection connection = null;
    
    public static Connection connect() throws SQLException{    
    
        try{
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
          
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        connection = DriverManager.getConnection(url);       
        return connection;
        
    }
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        
        if(connection != null && !connection.isClosed())
            return connection;
     
        connect();
        return connection;

    }
    
}
