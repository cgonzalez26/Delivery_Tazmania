package Controlador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ColorearFilas extends DefaultTableCellRenderer {

    private final int columna_patron;

    public ColorearFilas(int Colpatron) {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean Selected, boolean hasFocus, int row, int col) {
        if (table.getRowCount() != 0) {
            if (row < table.getRowCount()) {
                switch (columna_patron) {
                    case 7: {
                        float fila = Float.parseFloat(table.getValueAt(row, columna_patron).toString());
                        if (fila <= 10) {
                            setForeground(Color.RED);
                        } else {
                            setForeground(Color.BLACK);
                        }
                        break;
                    }
                    case 2: {
                        float fila = Float.parseFloat(table.getValueAt(row, columna_patron).toString());
                        if (fila < 0) {
                            setForeground(Color.RED);
                        } else if (fila == 0) {
                            setForeground(Color.BLUE);
                        } else {
                            setForeground(Color.BLACK);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
            row++;
        }
        super.getTableCellRendererComponent(table, value, Selected, hasFocus, row, col);
        return this;
    }
}
