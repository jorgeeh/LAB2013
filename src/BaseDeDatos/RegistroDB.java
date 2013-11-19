/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import errores.ErrorDeuda;
import errores.ErrorRegistroExistente;
import errores.ErrorRegistroInexistente;
import errores.ErrorStock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import laboratorio.Cliente;
import laboratorio.Pelicula;
import laboratorio.Registro;

/**
 *
 * @author Jorge_UltraLord
 */
public class RegistroDB {

    public java.sql.Date FechaHoySQL() {
        Calendar cal = Calendar.getInstance();
        java.sql.Date fechasql = new java.sql.Date(cal.getTime().getTime());
        return fechasql;
    }

    public java.sql.Date FechaHoyMasDias(int dias) {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) + dias);
        java.sql.Date fechasql = new java.sql.Date(cal.getTime().getTime());
        return fechasql;
    }

    public void borrarRegistro(String ndni, String codigo) throws DataAccessException, SQLException, ClassNotFoundException {
            PeliculasDB dbpelicula = new PeliculasDB();
            Pelicula pelicula = dbpelicula.encontrarPorCodigo(codigo);
        try {
            Registro existe = encontrarPorDNICOD(ndni, codigo);
            if (existe == null) {
                ErrorRegistroInexistente error = new ErrorRegistroInexistente();
                error.setVisible(true);
            }

            if(pelicula.getStock()>=0)    ///aqui se sube el stock al alquilar
            {
            pelicula.setStock(pelicula.getStock()+1);
            dbpelicula.actualizarPelicula(pelicula);
               
            }           
            
            CajaDB caja = new CajaDB();
            caja.actualizar(existe,this.FechaHoySQL());
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            String query = ("DELETE FROM registro WHERE dni='"+ndni+"' and codigopeli='"+codigo+"'");
            boolean result = smt.execute(query);

            

            smt.close();

        } catch (DataAccessException ex) {
            throw new DataAccessException("Error en RegistroDB.borrarCliente() " + ex);
        }


    }

    public Registro encontrarPorDNICOD(String ndni, String codigo) throws DataAccessException {
        try {
            //DEVUELVE NULO SI NO LO ENCONTRO
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            ResultSet result = smt.executeQuery("Select * from registro where dni='" + ndni + "' and codigopeli='" + codigo + "'");
            Registro registro = null;
            while (result.next()) {
                registro = new Registro();
                registro.setDni(result.getString("dni"));
                registro.setPelicula(result.getString("codigopeli"));
                registro.setFecha(result.getDate("fechasalida"));
                registro.setFechaEntrega(result.getDate("fechaentrega"));

            }
            result.close();
            smt.close();
            return registro;
        } catch (Exception e) {
            throw new DataAccessException("Error en RegistroDB.encontrarPorDNIyCOD" + e);
        }
    }

    public Collection recuperar() throws DataAccessException {
        try {
            //DEVUELVE NULO SI NO LO ENCONTRO
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            ResultSet result = smt.executeQuery("Select * from registro");
            Registro registro = null;
            ArrayList<Registro> lista = new ArrayList();

            while (result.next()) {
                registro = new Registro();
                registro.setDni(result.getString("dni"));
                registro.setPelicula(result.getString("codigopeli"));
                registro.setFecha(result.getDate("fechasalida"));
                registro.setFechaEntrega(result.getDate("fechaentrega"));
                lista.add(registro);
            }
            result.close();
            smt.close();
            return lista;
        } catch (Exception e) {
            throw new DataAccessException("Error en RegistroDB.encontrarPorDNIyCOD" + e);
        }
    }

    public void agregarRegistroDNICOD(String dni, String cod) throws DataAccessException {
        
            PeliculasDB dbpelicula = new PeliculasDB();
            Pelicula pelicula = dbpelicula.encontrarPorCodigo(cod);
        
        try {
            
            Registro existe = encontrarPorDNICOD(dni, cod);
            if (existe != null) {  //nulo si no existe
                ErrorRegistroExistente errorreg = new ErrorRegistroExistente();
                errorreg.setVisible(true);
                throw new DataAccessException("Registro ya existe");
            }

            Registro deudor = verificarDeuda(dni, this.FechaHoySQL());
            if (deudor != null) {  //al encontrar un deudor se pone distinto de nulo
                ErrorDeuda errordeuda = new ErrorDeuda();
                errordeuda.setVisible(true);
                throw new DataAccessException("Este es un deudor");
            }
            
            if (pelicula.getStock()==0){
                ErrorStock errorstock = new ErrorStock();
                errorstock.setVisible(true);
                return;
            }
            
            if(pelicula.getStock()>0)    ///aqui se baja el stock al alquilar
            {
            pelicula.setStock(pelicula.getStock()-1);
            dbpelicula.actualizarPelicula(pelicula);    
            }
            
            Connection con = BaseDeDatos.getInstance();
            PreparedStatement smt = con.prepareStatement("INSERT INTO registro(fechasalida, fechaentrega, codigopeli, dni) VALUES (?, ?, ?, ?)");
            smt.setDate(1, this.FechaHoySQL());
            smt.setDate(2, this.FechaHoyMasDias(5));
            smt.setString(3, cod);
            smt.setString(4, dni);
            smt.execute();
            
            CajaDB cajadb = new CajaDB();
            Registro existe2 = new Registro(dni,this.FechaHoySQL(),cod,this.FechaHoyMasDias(5));
            cajadb.nuevaCaja(existe2);

        } catch (Exception e) {
            throw new DataAccessException("Error en RegistroDB.agregarRegistro() " + e);
        }



    }

    
    
    public Registro encontrarPorCod(String cod) throws DataAccessException{
        
        try {
            //DEVUELVE NULO SI NO LO ENCONTRO
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            ResultSet result = smt.executeQuery("Select * from registro where codigopeli='" + cod + "'");
            Registro registro = null;
            while (result.next()) {
                registro = new Registro();
                registro.setDni(result.getString("dni"));
                registro.setPelicula(result.getString("codigopeli"));
                registro.setFecha(result.getDate("fechasalida"));
                registro.setFechaEntrega(result.getDate("fechaentrega"));

            }
            result.close();
            smt.close();
            return registro;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessException("Error en RegistroDB.encontrarPorCod" + e);
        }
    }
    
    
    
    public Registro encontrarPorDNI(String dni) throws DataAccessException{
        
        try {
            //DEVUELVE NULO SI NO LO ENCONTRO
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            ResultSet result = smt.executeQuery("Select * from registro where dni='" + dni + "'");
            Registro registro = null;
            while (result.next()) {
                registro = new Registro();
                registro.setDni(result.getString("dni"));
                registro.setPelicula(result.getString("codigopeli"));
                registro.setFecha(result.getDate("fechasalida"));
                registro.setFechaEntrega(result.getDate("fechaentrega"));

            }
            result.close();
            smt.close();
            return registro;
        } catch (Exception e) {
            throw new DataAccessException("Error en RegistroDB.encontrarPorDNI" + e);
        }
    }
    
    
    public Registro verificarDeuda(String dni, java.sql.Date fechadehoy) throws DataAccessException {
        try {
            Connection con = BaseDeDatos.getInstance();
            Statement smt = con.createStatement();
            String query = ("SELECT * from registro where dni='" + dni + "' and fechaentrega<'" + fechadehoy + "'");
            ResultSet result = smt.executeQuery(query);
            Registro registro = null;
            while (result.next()) {
                registro = new Registro(result.getString("dni"),result.getDate("fechasalida"),result.getString("codigopeli"),result.getDate("fechaentrega"));
                
            }
            result.close();
            smt.close();
            return registro;
        } catch (Exception e) {
            throw new DataAccessException("Error en RegistroDB.verificardeuda" + e);
        }

    }

    public void cargarDeudores() {

        //DEVUELVE NULO SI NO LO ENCONTRO
        try {

            ArrayList listaderegistros = (ArrayList) recuperar();
            Iterator it = listaderegistros.iterator();
            while (it.hasNext()) {
                Registro reg = (Registro) it;

                long diferencia = (this.FechaHoySQL().getTime() - reg.getFechaEntrega().getTime()) / (1000 * 60 * 60 * 24);
                double dias = Math.floor(diferencia);
                System.out.println("dias=" + dias);
                if ((int) dias > 0) {
                    System.out.println("dias=" + dias);
                    ClienteDB clientedb = new ClienteDB();
                    Cliente deudor = clientedb.encontrarPorDNI(reg.getDni());
                    clientedb.actualizarCliente(deudor);
                }

            }

        } catch (Exception e) {
        }



    }
}
