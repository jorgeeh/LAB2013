/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import laboratorio.Cliente;

/**
 *
 * @author Jorge_UltraLord
 */
public class ClienteDB {
    
    public void actualizarCliente(Cliente nuevo) throws DataAccessException {
    try {
            Cliente existe = encontrarPorDNI(nuevo.getDni());
            if (existe==null) {
                throw new DataAccessException("Cliente no existe");
            }
            Connection con = BaseDeDatos.getInstance();
           
            String query = ("UPDATE clientes SET nombre=?,telefono=?,domicilio=? WHERE dni='"+nuevo.getDni()+"'");
            PreparedStatement smt = con.prepareStatement(query);
            smt.setString(1,nuevo.getNombre());
            smt.setString(2,nuevo.getTelefono());
            smt.setString(3, nuevo.getDomicilio());
            smt.execute();  
           
        } catch (Exception e) {
            throw new DataAccessException("Error en ClienteDB.actualizarCliente() "+e);
        }
    }
    
    public void agregarCliente(Cliente nuevo) throws DataAccessException{
        try {
            Cliente existe = encontrarPorDNI(nuevo.getDni());
            if (existe!=null) {
                throw new DataAccessException("Cliente ya existe");
            }
            Connection con = BaseDeDatos.getInstance();
            PreparedStatement smt = con.prepareStatement("Insert into clientes (nombre,dni,telefono,domicilio) values (?,?,?,?)");
            smt.setString(1,nuevo.getNombre());
            smt.setString(2,nuevo.getDni());
            smt.setString(3,nuevo.getTelefono());
            smt.setString(4, nuevo.getDomicilio());
 
            smt.execute();            
        } catch (Exception e) {
            throw new DataAccessException("Error en ClienteDB.agregarCliente() "+e);
        }
    }
           
    public Cliente encontrarPorDNI(String ndni) throws DataAccessException {
         try {
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            ResultSet result = smt.executeQuery("Select * from clientes where dni='"+ndni+"'");            
            Cliente cliente = null;
            while (result.next())  {
                cliente = new Cliente(result.getString("nombre"),result.getString("domicilio"),result.getString("telefono"),ndni);
                
            }
            result.close();
            smt.close();
            return cliente;
        } catch (Exception e) {
            throw new DataAccessException("Error en ClienteDB.encontrarPorDNI"+e);
        }
    }
    
    
    
    public void borrarCliente(String ndni) throws DataAccessException, SQLException, ClassNotFoundException{
        try {
            Cliente existe = encontrarPorDNI(ndni);
            if(existe==null){
                throw new DataAccessException("Cliente no existe");
            }
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            String query = ("DELETE FROM clientes WHERE dni='"+ndni+"'");
            boolean result = smt.execute(query);
            smt.close();
        } catch (DataAccessException ex) {
            throw new DataAccessException("Error en ClienteDB.borrarCliente() "+ex);       
        }
        
        
    }
    
}

