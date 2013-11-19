/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package laboratorio;

import BaseDeDatos.DataAccessException;
import BaseDeDatos.PeliculasDB;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class PeliculaTest {
    
    Pelicula nueva = null;
    PeliculasDB encontrar = new PeliculasDB();
    
    
    @Test
    public void buscarpoCodigoTest() throws DataAccessException{
        nueva = encontrar.encontrarPorCodigo("3");
        assertEquals(nueva.getTitulo(),"Superman");
    }
    
}
