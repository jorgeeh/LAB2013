/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio;

import java.text.SimpleDateFormat;

public class Registro {
   private String dni;;
   private java.sql.Date fecha;
   private String pelicula;
   private java.sql.Date fechaEntrega;

    public Registro(String dni, java.sql.Date fecha, String pelicula, java.sql.Date fechaEntrega) {
        this.dni = dni;
        this.fecha = fecha;
        this.pelicula = pelicula;
        this.fechaEntrega = fechaEntrega;
    }

    
public Registro() {
        this.dni = null;
        this.fecha = null;
        this.pelicula = null;
        this.fechaEntrega = null;
    }
    

   
   
   
   
   
   public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public java.sql.Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(java.sql.Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }
   
    public String getFechaActual() {
    java.util.Date ahora = new java.util.Date();
    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
    return formateador.format(ahora);
}
   
}
