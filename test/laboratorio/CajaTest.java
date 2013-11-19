/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package laboratorio;

import BaseDeDatos.CajaDB;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class CajaTest {
    
    CajaDB caja = new CajaDB();
    
    @Test
    public void totalMultaTest() throws SQLException, ClassNotFoundException{
        assertTrue(caja.totalMultas() == 20.0);
    }
    @Test
    public void totalFechasTest() throws SQLException, ClassNotFoundException{
        assertTrue(caja.totalFechas("2013-11-10")== 20.0);
    }
}
