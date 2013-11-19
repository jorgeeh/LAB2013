/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package laboratorio;

import BaseDeDatos.ClienteDB;
import BaseDeDatos.DataAccessException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class ClienteTest {
    Cliente nuevo = null;
    ClienteDB buscar = new ClienteDB();
    
    @Test
    public void buscarporDNITest() throws DataAccessException{
        nuevo = buscar.encontrarPorDNI("111");
        assertEquals(nuevo.getNombre(),"Martin Leiva");
    }
    
}
