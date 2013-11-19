/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio;


public class Cliente {

    private String nombre;
    private String domicilio;
    private String telefono;
    private String dni;
    

    public Cliente(String nombre, String domicilio, String telefono, String dni) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
