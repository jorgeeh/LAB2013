/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jorge_UltraLord
 */
public class BaseDeDatos {
    protected static Connection instance = null;
    
    
    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        
        if (instance == null) {
            String url = "jdbc:postgresql://localhost:5432/laboratorio";  
            String driver = "org.postgresql.Driver";
            Class.forName(driver);
            instance = DriverManager.getConnection(url, "postgres", "1234");
        }        
        return instance;
    }        
        
    }
