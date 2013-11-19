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
import laboratorio.Pelicula;

/**
 *
 * @author Jorge_UltraLord
 */
public class PeliculasDB {
    
    public void actualizarPelicula(Pelicula nueva) throws DataAccessException {
    try {
            Pelicula existe = encontrarPorCodigo(nueva.getCodigo());
            if (existe==null) {
                throw new DataAccessException("Pelicula no existe");
            }
            Connection con = BaseDeDatos.getInstance();
           
            String query = ("UPDATE peliculas SET codigo='"+nueva.getCodigo()+"', titulo='"+nueva.getTitulo()+"', director='"+nueva.getDirector()+"', formato='"+nueva.getFormato()+"', genero='"+nueva.getGenero()+"', stock='"+nueva.getStock()+"'WHERE codigo='"+nueva.getCodigo()+"'");
            PreparedStatement smt = con.prepareStatement(query);
            smt.execute();  
           
        } catch (Exception e) {
            throw new DataAccessException("Error en PeliculaDB.actualizarPelicula() "+e);
        }
    }
    
    public void agregarPelicula(Pelicula nueva) throws DataAccessException{ 
        try {
            Pelicula existe = encontrarPorCodigo(nueva.getCodigo());
            if (existe!=null) {
                throw new DataAccessException("Pelicula ya existe");
            }
            Connection con = BaseDeDatos.getInstance();
           

            PreparedStatement smt = con.prepareStatement("INSERT INTO peliculas( codigo, titulo, director, formato, genero, stock) VALUES (?, ?, ?, ?, ?, ?)");
            smt.setString(1,nueva.getCodigo());
            smt.setString(2,nueva.getTitulo());
            smt.setString(3,nueva.getDirector());
            smt.setString(4,nueva.getFormato());
            smt.setString(5,nueva.getGenero());
            smt.setInt(6,nueva.getStock());
            smt.execute();            
        } catch (Exception e) {
            throw new DataAccessException("Error en PelicukaDB.agregarPelicula() "+e);
        }
    }
           
    public Pelicula encontrarPorCodigo(String codigo) throws DataAccessException {
         try {
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            ResultSet result = smt.executeQuery("Select * from peliculas where codigo='"+codigo+"'");            
            Pelicula pelicula = null;
            while (result.next())  {
                pelicula = new Pelicula(result.getString("titulo"),result.getString("codigo"),result.getString("genero"),result.getString("director"),result.getInt("stock"),result.getString("formato"));
                
            }
            result.close();
            smt.close();
            return pelicula;
        } catch (Exception e) {
            throw new DataAccessException("Error en PeliculaDB.encontrarPorCodigo"+e);
        }
    }
    
    public void borrarPelicula(String codigo) throws DataAccessException, SQLException, ClassNotFoundException{
        try {
            Pelicula existe = encontrarPorCodigo(codigo);
            if(existe==null){
                throw new DataAccessException("Pelicula no existe");
            }
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            String query = ("DELETE FROM peliculas WHERE codigo='"+codigo+"'");
            boolean result = smt.execute(query);
            smt.close();
            smt.close();
        } catch (DataAccessException ex) {
            throw new DataAccessException("Error en PeliculaDB.borrarPelicula() "+ex);       
        }
        
        
    }
}
