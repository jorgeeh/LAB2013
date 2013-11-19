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
import laboratorio.Caja;
import laboratorio.Pelicula;
import laboratorio.Registro;

/**
 *
 * @author Jorge_UltraLord
 */
public class CajaDB {
        
public Caja encontrarPorDNICOD(String ndni, String codigo) throws DataAccessException {
        try {
            //DEVUELVE NULO SI NO LO ENCONTRO
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            ResultSet result = smt.executeQuery("Select * from caja where dni='" + ndni + "' and codigo='" + codigo + "' and multasaldada=false");
            Caja registro = null;
            while (result.next()) {
                registro = new Caja();
                registro.setCodigo(result.getString("codigo"));
                registro.setDni(result.getString("dni"));
                registro.setFechaSalida(result.getDate("fechasalida"));
                registro.setFechaEntrega(result.getDate("fechaentrega"));
                registro.setFechaEntregaReal(result.getDate("fechaentregareal"));
                registro.setMulta(result.getDouble("multa"));
                registro.setPrecio(result.getDouble("precio"));
                registro.setMultasaldada(result.getBoolean("multasaldada"));
                
            }
            result.close();
            smt.close();
            return registro;
        } catch (Exception e) {
            throw new DataAccessException("Error en CajaDB..encontrarPorDNIyCOD" + e);
        }
    }

   public void nuevaCaja(Registro reg) throws DataAccessException {
        try {
            
            Caja existe = encontrarPorDNICOD(reg.getDni(), reg.getPelicula());
            if (existe != null) {  //nulo si no existe
                throw new DataAccessException("Caja ya existe");
            }

            Connection con = BaseDeDatos.getInstance();
            PreparedStatement smt = con.prepareStatement("INSERT INTO caja(fechasalida, fechaentrega, precio, multasaldada, dni, codigo) VALUES (?, ?, ?, ?, ?, ?)");
            
            smt.setDate(1,reg.getFecha());
            smt.setDate(2,reg.getFechaEntrega());
            smt.setDouble(3,this.obtenerprecio(reg.getPelicula()));
            smt.setBoolean(4, false);
            smt.setString(5, reg.getDni());
            smt.setString(6, reg.getPelicula());
            smt.execute();
            
        } catch (Exception e) {
            throw new DataAccessException("Error en CajaDB..agregarRegistro() " + e);
        }

    }

   
   public void actualizar (Registro reg, java.sql.Date fechaentregareal) throws DataAccessException {
       try {
            RegistroDB regdb = new RegistroDB();
            Connection con = BaseDeDatos.getInstance();
            PreparedStatement smt = con.prepareStatement("UPDATE caja SET fechaentregareal=?, multa=?, multasaldada=? WHERE dni='"+reg.getDni()+"' and codigo='"+reg.getPelicula()+"' and multasaldada=false");
            smt.setDate(1,regdb.FechaHoySQL());
            smt.setDouble(2,this.obtenerMulta(regdb.FechaHoySQL(),reg.getDni()));
            smt.setBoolean(3,true);
            smt.execute();                        
        } catch (Exception e) {
            throw new DataAccessException("Error en CajaDB.actualizar() "+e);
        }
    }
   
   public double obtenerprecio(String codigo) throws DataAccessException{
       PeliculasDB pelicula = new PeliculasDB();
       Pelicula peli = pelicula.encontrarPorCodigo(codigo);
       if(peli.getFormato().equals("DVD")){
       return 20.00;
       }
       if(peli.getFormato().equals("Blu-Ray")){
       return 30.00;
       }
       return 0.0;
   }
   
    public Double totalMultas() throws SQLException, ClassNotFoundException{
        Connection con = BaseDeDatos.getInstance();
        Statement smt = con.createStatement();
        ResultSet result = smt.executeQuery("SELECT multa FROM caja WHERE multasaldada = TRUE");
        Double acumulador=0.0;
        while (result.next())  {
            
        acumulador = acumulador + (result.getDouble("multa"));
        
        }
        return acumulador;
    }
   
   
   public Double totalFechas(String fecha) throws SQLException, ClassNotFoundException{
       Connection con = BaseDeDatos.getInstance();
       Statement smt = con.createStatement();
       ResultSet result = smt.executeQuery("SELECT precio FROM caja WHERE fechasalida ='"+fecha+"'");
       Double acumulador=0.0;
       while(result.next()){
           acumulador = acumulador + (result.getDouble("precio"));
       }
       return acumulador;
   }
    
   
   public double obtenerMulta(java.sql.Date hoy, String dni) throws DataAccessException{
             
       RegistroDB regdb = new RegistroDB();
       Registro deudor = regdb.verificarDeuda(dni, hoy);
       if(deudor !=null){
       return 20.00;
       }
       return 0.0;
             
   }
}