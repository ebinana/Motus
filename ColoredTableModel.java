import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.Arrays;

public class ColoredTableModel extends DefaultTableModel {
    private Color[][] cellColors;

    public ColoredTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
        cellColors = new Color[rowCount][columnCount];
    }

    public Color getCellColor(int row, int column) {
        return cellColors[row][column];
    }

    public void setCellColor(int row, int column, Color color) {
        cellColors[row][column] = color;
        fireTableCellUpdated(row, column);
    }

    public void resetCellColors() {
        for (int i = 0; i < getRowCount(); i++) {
            Arrays.fill(cellColors[i], null);
        }
        fireTableDataChanged();
    }

    
}
