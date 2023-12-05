package controllers;

import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControls {
    int ligne = 5;
    int colonne = 7;
    private int tailleMot; // Ajoutez un attribut pour stocker la taille du mot

    public void updateTaille(int nouvelleTaille) {
        this.tailleMot = nouvelleTaille;
    }

    public static String tirerMot(int tailleMot) {
        String DatabaseWord = "Dico.txt"; // Remplacez par le nom de votre fichier
    
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader(DatabaseWord));
            Random random = new Random();
            String ligne = null;
            String mot = null;
    
            while (mot == null) {
                // Lire une ligne au hasard
                long nombreDeLignes = lecteur.lines().count();
                long ligneAleatoire = random.nextInt((int) nombreDeLignes);
    
                // Réinitialiser le lecteur
                lecteur.close();
                lecteur = new BufferedReader(new FileReader(DatabaseWord));
    
                // Lire la ligne sélectionnée
                for (int i = 0; i < ligneAleatoire; i++) {
                    ligne = lecteur.readLine();
                }
    
                if (ligne != null) {
                    // Extraire le premier mot de la ligne
                    String[] mots = ligne.split(" ");
                    if (mots.length > 0) {
                        mot = mots[0];
                        if (mot.length() != tailleMot) {
                            mot = null;
                        }
                    }
                }
            }
    
            lecteur.close(); // Fermez le lecteur après utilisation
            return mot;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String masquerMot(String mot, int tailleMot) {
        if (mot != null) {
            char[] mot_char = mot.toCharArray();
            Random random = new Random();
            int lettre_choisie = random.nextInt(tailleMot) + 1;
    
            char[] motModifie = new char[mot.length()];
    
            // Initialisation du char avec des '_'
            for (int i = 0; i < mot.length(); i++) {
                motModifie[i] = '_';
            }
    
            motModifie[0] = mot_char[0];
            motModifie[lettre_choisie - 1] = mot_char[lettre_choisie - 1];
    
            // Afficher le mot modifié
            for (char lettre : motModifie) {
                System.out.print(lettre + " ");
            }
            String motMasqueString = new String(motModifie);
    
            return motMasqueString;
        } else {
            return null;
        }
    }
    

    public void start() {
        // initialise le mots à trouver
        String DatabaseWord = "Dico.txt";
        int tailleutilisateur = inputTaille();
        String mots = getfromDico(DatabaseWord, tailleutilisateur);

        String mot = afficheinit(mots);
        System.out.println(mot);
        int taille = mots.length();

        // partie jeu
        int essaie = 7;
        String motresultat = "";
        for (int i = 0; i <= taille; i++) { // créer un mot de taille étoiles
            motresultat = motresultat + '*';
        }

        for (int i = 1; i < essaie; i++) {
            String mot4 = motinput(taille);
            motresultat = comparer(mots, mot4, motresultat);
            System.out.println(motresultat);
            findejeu(mots, mot4);
        }
    }

    private String motinput(int taille) {
        String mot4 = "";
        while (true) {
            System.out.println("entrer un mot de taille: " + taille);
            Scanner scanner = new Scanner(System.in);
            mot4 = scanner.next();
            if (mot4.length() == taille) {
                break;
            }
        }
        return mot4;
    }

    private int inputTaille() {
        System.out.println("entrer la taille du mot");
        boolean a = true;
        int taille = 0;
        while (a) {
            Scanner scannertaille = new Scanner(System.in);
            String taillestring = scannertaille.next();
            taille = Integer.parseInt(taillestring);
            if (taille > 6 && taille < 17) {
                a = false;
                break;
            } else {
                System.out.println("La taille du mot est hors champ pour jouer à Motus");
            }
        }
        return taille;
    }

    public String comparer(String mot, String essai, String mot_masque) {
        String motcomp = "";
        // pour avoir la meme taille
        for (int i = 1; i <= mot.length(); i++) {
            if (essai.substring(i - 1, i).equals(mot.substring(i - 1, i))) {
                motcomp = motcomp + mot.substring(i - 1, i);
                
            } else {
                motcomp = motcomp + mot_masque.substring(i - 1, i);
            }
        }
        return motcomp;
    }

    private String afficheinit(String motsinit) {
        String motsdebut = "";
        motsdebut = motsdebut + motsinit.substring(0, 1);
        int taille = motsinit.length();
        Random rand = new Random();
        int nombreAlea = rand.nextInt(taille - 1) + 2;
        for (int i = 1; i < motsinit.length(); i++) {
            if (i == nombreAlea) {
                motsdebut = motsdebut + motsinit.substring(i, i + 1);
            } else {
                motsdebut = motsdebut + '*';
            }
        }
        return motsdebut;
    }

    private String getfromDico(String Nomdudico, int tailleMot) {
        String DatabaseWord = Nomdudico;
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader(DatabaseWord));
            Random random = new Random();
            String ligne = null;
            String mot = null;

            while (mot == null) {
                // Lire une ligne au hasard
                long nombreDeLignes = lecteur.lines().count();
                long ligneAleatoire = random.nextInt((int) nombreDeLignes);

                // Réinitialiser le lecteur
                lecteur.close();
                lecteur = new BufferedReader(new FileReader(DatabaseWord));

                // Lire la ligne sélectionnée
                for (int i = 0; i < ligneAleatoire; i++) {
                    ligne = lecteur.readLine();
                }

                if (ligne != null) {
                    // Extraire le premier mot de la ligne
                    String[] mots = ligne.split(" ");
                    if (mots.length > 0) {
                        mot = mots[0];
                        if (mot.length() != tailleMot) {
                            mot = null;
                        }
                    }
                }
            }
            return mot;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void findejeu(String Mot, String mot2) {
        int taille = Mot.length();
        int a = 0;
        String chara = "";
        for (int i = 0; i < taille; i++) {
            chara = Mot.substring(i, i + 1);
            if (chara.equals(mot2.substring(i, i + 1))) {
            } else {
                a = 1;
            }
        }
        if (a == 0) {
            System.out.println("Vous avez gagné");
        }
    }

    public boolean verifierMot(String essai, String mot, JTextField propositionField, JPanel propositionPanel){
        if (essai.length() != tailleMot){
                        JOptionPane.showMessageDialog(propositionPanel,"Veuillez proposez un mot avec " + tailleMot + " lettres");
                        return false;
                    }
        else if (essai.charAt(0) != mot.charAt(0)){
                JOptionPane.showMessageDialog(propositionPanel, " Le mot que vous devez entré soit commencer par la lettre " + mot.charAt(0) );
                propositionField.setText(""); // Efface le champ de texte après l'entrée
                return false;
            }
        else {
                JOptionPane.showMessageDialog(propositionPanel, "Vous avez entré : " + essai);
                propositionField.setText(""); // Efface le champ de texte après l'entrée
                return true;
            }

    }
}
