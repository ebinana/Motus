import controllers.GameControls;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class copymonapp {
    private JFrame fenetre;
    private JTable tableau;
    private JComboBox<Integer> tailleMotSelector;
    private int tailleMot = 9;
    private DefaultTableModel model;
    //private String motEx = "parlement";
    private GameControls gameControls; // Ajoutez une instance de GameControls

    public copymonapp() {
        SwingUtilities.invokeLater(() -> {
            fenetre = new JFrame("Ma Fenêtre Swing");
            fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Créez un modèle de tableau par défaut avec 7 colonnes et la taille de mot par défaut
            model = new DefaultTableModel(7, tailleMot);
            tableau = new JTable(model);
            tableau.setDefaultRenderer(Object.class, new MatrixTableCellRenderer());

            JPanel panelPrincipal = new JPanel(new BorderLayout());

            // Créer le panneau inférieur
            JPanel bottomPanel = new JPanel(new FlowLayout());

            // Créer un JLabel pour afficher "Temps restant"
            JLabel timerLabel = new JLabel("Temps restant: ");
            bottomPanel.add(timerLabel);

            // Créer un JLabel pour afficher le chronomètre (vous devrez implémenter la logique du chronomètre)
            JLabel timerDisplay = new JLabel("0:00"); // Vous devrez mettre à jour ceci en temps réel
            bottomPanel.add(timerDisplay);

            // Créer un JLabel pour afficher "Votre proposition"
            JLabel propositionLabel = new JLabel("Votre proposition: ");
            bottomPanel.add(propositionLabel);

            // Créer un champ de texte pour entrer la proposition
            JTextField propositionField = new JTextField(20); // Vous pouvez ajuster la largeur en fonction de vos préférences
            bottomPanel.add(propositionField);

            panelPrincipal.add(bottomPanel, BorderLayout.SOUTH);

            // Créer le panneau supérieur
            JPanel topPanel = new JPanel(new BorderLayout());

            // Créez le ComboBox pour sélectionner la taille du mot
            tailleMotSelector = new JComboBox<>();
            for (int i = 7; i <= 13; i++) {
                tailleMotSelector.addItem(i);
            }
            tailleMotSelector.setSelectedItem(tailleMot); // Sélectionnez la taille par défaut

            JPanel boutonsPanel = new JPanel(new FlowLayout());
            JButton boutonCommencer = new JButton("Commencer");
            JButton boutonQuitter = new JButton("Quitter");
            JButton boutonPause = new JButton("Pause");

            // Ajout d'un écouteur d'événements au ComboBox pour mettre à jour la taille du mot
            tailleMotSelector.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Ne changez pas la taille du mot immédiatement, attendez la validation
                }
            });

            boutonCommencer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    updateTableModel(tailleMot); // Mettre à jour le modèle du tableau avec le mot tiré
                }
            });

            // Ajout d'un écouteur d'événements au ComboBox pour mettre à jour la taille du mot
            tailleMotSelector.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tailleMot = (int) tailleMotSelector.getSelectedItem();
                    gameControls.updateTaille(tailleMot); // Mettez à jour la taille dans GameControls
                    updateTableModel(); // Mettre à jour le modèle du tableau
                }
            });

            // Ajout d'écouteurs d'événements aux boutons
            //boutonCommencer.addActionListener(e -> System.out.println("Commencer"));
            boutonQuitter.addActionListener(e -> System.exit(0));
            boutonPause.addActionListener(e -> System.out.println("Pause"));

            boutonsPanel.add(tailleMotSelector);
            boutonsPanel.add(boutonCommencer);
            boutonsPanel.add(boutonQuitter);
            boutonsPanel.add(boutonPause);

            topPanel.add(new JScrollPane(tableau), BorderLayout.CENTER);
            topPanel.add(boutonsPanel, BorderLayout.SOUTH);

            panelPrincipal.add(topPanel, BorderLayout.CENTER);

            fenetre.add(panelPrincipal);

            fenetre.setSize(400, 400);
            fenetre.setVisible(true);
        });

        gameControls = new GameControls(); // Initialisez une instance de GameControls
    }

    public void updateTableModel() {
        String mot = gameControls.tirerMot(tailleMot); // Tirer un nouveau mot en fonction de la taille
        model.setColumnCount(tailleMot); // Mettre à jour le nombre de colonnes du tableau

        // Remplir la première ligne du tableau avec les lettres du nouveau mot masqué 

        String mot_masque = gameControls.masquerMot(mot,tailleMot);

        for (int i = 0; i < tailleMot; i++) {
            model.setValueAt(mot_masque.charAt(i), 0, i);
        }
    }

    public static void main(String[] args) {
        new copymonapp();
    }
}