/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio;

import java.util.ArrayList;

/**
 *
 * @author Jorge_UltraLord
 */
public class Pelicula {
    
    private String titulo;
    private String codigo;
    private String genero;
    private String formato;
    private String director;
    private Double precio;
    private Integer stock;

    public Pelicula(String titulo, String codigo , String genero, String director, Integer stock,String formato) {
        this.titulo = titulo;
        this.codigo = codigo;
        this.genero = genero;
        this.formato = formato;
        this.director = director;
        this.stock = stock;
    }

    public double precioFormato(){
        if(formato.equals("DVD")){
        return 15.00;
        }
        if(formato.equals("Blu-Ray")){
        return 30.00;
        }
        
        return 5.0; //valor de retorno para que el compilador no de error
    }
    
    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
