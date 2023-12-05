import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MotusTableCellRenderer extends DefaultTableCellRenderer {

    public MotusTableCellRenderer() {
        setOpaque(true); // Permet au fond d'être peint
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Appliquer des bordures
        if (row < table.getRowCount() - 1) {
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Bordure inférieure
        }
        if (column < table.getColumnCount() - 1) {
            setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY)); // Bordure droite
        }

        // Personnaliser la police

        Font originalFont = rendererComponent.getFont();
        Font newFont = new Font("Verdana", Font.BOLD, originalFont.getSize()); 
        rendererComponent.setFont(newFont);

         // Convertir le texte en majuscules
         if (value != null) {
            setText(value.toString().toUpperCase());
        }

        // Personnalisez d'autres propriétés d'apparence ici si nécessaire

        return rendererComponent;
    }
}
