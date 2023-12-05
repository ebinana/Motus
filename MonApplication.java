import controllers.GameControls;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonApplication {
    private JFrame fenetre;
    private JTable tableau;
    private DefaultTableModel model;
    private GameControls gameControls;
    private JLabel timerDisplay;
    private Horloge horloge;
    private Timer updateTimeTimer; // Ajout d'un Timer pour mettre à jour le temps
    private int ligne = 0; // On initialise la ligne à 1 car la première ligne est déjà remplie
    

    public MonApplication(int selectedTailleMot) {
        int tailleMot = selectedTailleMot;

        SwingUtilities.invokeLater(() -> {
            fenetre = new JFrame("Motus");
            fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panelPrincipal = new JPanel();
            panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

            model = new DefaultTableModel(7, tailleMot);
            tableau = new JTable(model);
            tableau.setTableHeader(null);
            tableau.setRowHeight(30); // Ajuster la hauteur du tableau ici

            String[] mots = InitTable(tailleMot);
            String mot = mots[0];
            String mot_masque = mots[1];

            JPanel propositionPanel = new JPanel(new FlowLayout());
            propositionPanel.setBorder(BorderFactory.createTitledBorder("Votre proposition"));
            JLabel propositionLabel = new JLabel("");
            JTextField propositionField = new JTextField(20);
            propositionPanel.add(propositionLabel);
            propositionPanel.add(propositionField);

            propositionField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String essai = propositionField.getText();
                    if ((essai.charAt(0) == mot.charAt(0)) && (essai.length() == tailleMot)) {
                        ligne++;
                    }
                    while (true) {
                        if (essai.length() != tailleMot) {
                            JOptionPane.showMessageDialog(propositionPanel, "Veuillez proposer un mot avec " + tailleMot + " lettres");
                            propositionField.setText("");
                            break;
                        } else if (essai.charAt(0) != mot.charAt(0)) {
                            JOptionPane.showMessageDialog(propositionPanel, "Le mot que vous devez entrer doit commencer par la lettre " + mot.charAt(0));
                            propositionField.setText("");
                            break;
                        }else if (!essai.matches("[a-zA-Z]+")) {  // Si le mot ne contient pas que des lettres
                            JOptionPane.showMessageDialog(propositionPanel, "Veuillez entrer un mot composé uniquement de lettres");
                            propositionField.setText("");
                            break;
                        } else {
                            if (mot.equals(essai)) {
                                final String[] mot_masqueContainer = {mot_masque};
                                mot_masqueContainer[0] = updateTableModel(essai, mot, mot_masqueContainer[0], tailleMot, ligne);
                                JOptionPane.showMessageDialog(propositionPanel, "Félicitations, vous avez trouvé le mot ");
                                break;
                            } else if (ligne == 6) {
                                JOptionPane.showMessageDialog(propositionPanel, "Vous avez entré : " + essai);
                                propositionField.setText("");
                                final String[] mot_masqueContainer = {mot_masque};
                                mot_masqueContainer[0] = updateTableModel(essai, mot, mot_masqueContainer[0], tailleMot, ligne);
                                JOptionPane.showMessageDialog(propositionPanel, "Vous avez épuisé votre nombre d'essais, le mot était : " + mot);
                                break;
                            } else {
                                JOptionPane.showMessageDialog(propositionPanel, "Vous avez entré : " + essai);
                                propositionField.setText("");
                                final String[] mot_masqueContainer = {mot_masque};
                                mot_masqueContainer[0] = updateTableModel(essai, mot, mot_masqueContainer[0], tailleMot, ligne);
                                break;
                            }
                        }
                    }
                }
            });

            JPanel timerPanel = new JPanel(new FlowLayout());
            timerPanel.setBorder(BorderFactory.createTitledBorder("Temps passé"));
            timerDisplay = new JLabel("00:00");
            timerPanel.add(timerDisplay);

            JPanel boutonsPanel = new JPanel(new FlowLayout());
            JButton boutonRecommencer = new JButton("Recommencer");
            JButton boutonQuitter = new JButton("Quitter");
            JButton boutonPause = new JButton("Pause");

            tableau.setDefaultRenderer(Object.class, new MotusTableCellRenderer());

            boutonQuitter.addActionListener(e -> System.exit(0));
            boutonRecommencer.addActionListener(e -> {
                int choix = JOptionPane.showConfirmDialog(fenetre, "Voulez-vous vraiment recommencer ?", "Recommencer", JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) {
                    fenetre.dispose();
                    int nouvelleTailleMot = TailleMotWindow.showDialog();
                    new MonApplication(nouvelleTailleMot);
                    
                }
            });

            boutonPause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTimeTimer.stop();
                    System.out.println("Pause");
                }
            });

            boutonsPanel.add(boutonRecommencer);
            boutonsPanel.add(boutonQuitter);
            boutonsPanel.add(boutonPause);

            panelPrincipal.add(propositionPanel);
            panelPrincipal.add(timerPanel);
            panelPrincipal.add(new JScrollPane(tableau));
            panelPrincipal.add(boutonsPanel);

            fenetre.add(panelPrincipal);

            fenetre.setSize(400, 400);
            fenetre.setVisible(true);
        });

        gameControls = new GameControls();

        horloge = new Horloge();
        horloge.start();

        ActionListener updateTimeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerDisplay.setText(horloge.getTempsEcoule());
            }
        };

        updateTimeTimer = new Timer(1000, updateTimeListener);
        updateTimeTimer.start();
    }

    private String[] InitTable(int tailleMot) {
        String mot = gameControls.tirerMot(tailleMot);
        model.setColumnCount(tailleMot);

        String mot_masque = gameControls.masquerMot(mot, tailleMot);

        for (int i = 0; i < tailleMot; i++) {
            model.setValueAt(mot_masque.charAt(i), 0, i);
        }
        return new String[]{mot, mot_masque};
    }

    private String updateTableModel(String essai, String mot, String mot_masque, int tailleMot, int ligne) {

        String mot_masque_update = gameControls.comparer(mot, essai, mot_masque);

        for (int i = 0; i < tailleMot; i++) {
            model.setValueAt(mot_masque_update.charAt(i), ligne, i);
        }
        return mot_masque_update;
    }

    public static void main(String[] args) {
        int selectedTailleMot = TailleMotWindow.showDialog();  // Fenêtre de taille de mot 
        new MonApplication(selectedTailleMot);
    }
}