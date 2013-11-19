/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package laboratorio;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Martin
 */
public class PintarCelda extends DefaultTableCellRenderer {
    
private int columna_patron ;

    public PintarCelda(int Colpatron)
    {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column )
    {        
        Registro fech = new Registro();
        String fechapc;
        fechapc = fech.getFechaActual();
        setBackground(Color.white);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si la celda corresponde a una fecha del mismo dia, se cambia el color de fondo a rojo
        
        if( table.getValueAt(row,columna_patron).toString().compareTo(fechapc)==0 )
        {
            setBackground(Color.CYAN);
        }
        else{
            if (table.getValueAt(row,columna_patron).toString().compareTo(fechapc)<=0){
                setBackground(Color.RED);
            }
        }

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
 }
}
    
    
