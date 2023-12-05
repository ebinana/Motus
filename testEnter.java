import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class testEnter {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crée une nouvelle fenêtre
            JFrame frame = new JFrame("Entrée de chaîne de caractères");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            // Crée un panneau principal avec gestion de la disposition
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Crée un champ de texte pour l'entrée de la chaîne de caractères
            JTextField textField = new JTextField(20);

            // Crée un label pour indiquer l'objectif
            JLabel label = new JLabel("Entrez une chaîne de caractères et appuyez sur Entrée :");

            // Ajoute un action listener au champ de texte
            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = textField.getText();
                    JOptionPane.showMessageDialog(frame, "Vous avez entré : " + input);
                    textField.setText(""); // Efface le champ de texte après l'entrée
                }
            });

            // Ajoute les composants au panneau principal
            panel.add(label);
            panel.add(textField);

            // Ajoute le panneau principal à la fenêtre
            frame.add(panel);

            // Centre la fenêtre sur l'écran
            frame.setLocationRelativeTo(null);

            // Rend la fenêtre visible
            frame.setVisible(true);
        });
    }
}
