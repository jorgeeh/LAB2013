/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio;

import java.sql.Date;


public class Caja {
    private String dni;
    private String codigo;
     
    private java.sql.Date fechaSalida;
    private java.sql.Date  fechaEntrega;
    private java.sql.Date  fechaEntregaReal;
    
    private Double precio;
    private Double multa;
    
    private Boolean multasaldada;
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaEntregaReal() {
        return fechaEntregaReal;
    }

    public void setFechaEntregaReal(Date fechaEntregaReal) {
        this.fechaEntregaReal = fechaEntregaReal;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Boolean getMultasaldada() {
        return multasaldada;
    }

    public void setMultasaldada(Boolean multasaldada) {
        this.multasaldada = multasaldada;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

   
}
