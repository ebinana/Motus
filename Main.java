import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int getTailleMot() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez la taille du mot souhaitée (un entier) : ");
        int tailleMot = scanner.nextInt();
        scanner.close();
        return tailleMot;
    }

    public static String tirerMot(int tailleMot) {
        String DatabaseWord = "Database.txt"; // Remplacez par le nom de votre fichier
    
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

    public static void main(String[] args) {
        int tailleMot = getTailleMot();

        if (tailleMot < 6 || tailleMot > 17) {
            System.out.println("La taille du mot est hors champ pour jouer à Motus");
            return;
        }

        String mot = tirerMot(tailleMot);
        if (mot != null) {
            System.out.println("Mot de taille sélectionné : " + mot);

            // On va afficher dir connue la première lettre du mot une autre choisie aléatoirement

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
        }
    }
}
