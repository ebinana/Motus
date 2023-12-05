import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Horloge {
    private Timer timer;
    private int minutes;
    private int secondes;
    private int tempsTotal; // Nouvel attribut pour le temps total
    private ActionListener actionListener;

    public Horloge() {
        minutes = 0;
        secondes = 0;
        tempsTotal = 0;

        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondes++;
                tempsTotal++; // Incrémentation du temps total à chaque seconde
                if (secondes == 60) {
                    secondes = 0;
                    minutes++;
                }
            }
        };

        timer = new Timer(1000, actionListener); // Mise à jour chaque seconde
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public String getTempsEcoule() {
        return String.format("%02d:%02d", minutes, secondes);
    }

    public int getTempsTotal() {
        return tempsTotal;
    }
}
