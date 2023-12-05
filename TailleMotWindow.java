import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TailleMotWindow {
    private static int selectedTailleMot = 7; // Taille par défaut

    public static int showDialog() {
        JFrame frame = new JFrame("Choisir la taille du mot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 100));

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel label = new JLabel("Choisir le nombre de caractères pour le mot à deviner : ");
        panel.add(label);

        // Créez un JComboBox avec des nombres de 7 à 13
        JComboBox<Integer> tailleMotSelector = new JComboBox<>();
        for (int i = 7; i <= 13; i++) {
            tailleMotSelector.addItem(i);
        }
        panel.add(tailleMotSelector);

        JButton okButton = new JButton("OK");
        panel.add(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedTailleMot = (int) tailleMotSelector.getSelectedItem();
                frame.dispose(); // Fermer la fenêtre une fois que la taille est sélectionnée
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null); // Centrez la fenêtre
        frame.setVisible(true);

        // Attendre que l'utilisateur sélectionne la taille
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100); // Attendre
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return selectedTailleMot;
    }
}
