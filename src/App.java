import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Metier.Concession;
import Metier.Voiture;

public class App {

    private Concession concession;

    public App() {
        creerConcession();
        Menu();
    }

    public Concession getConcession() {
        return concession;
    }

    public void setConcession(Concession concession) {
        this.concession = concession;
    }

    public void execution() {
        boolean continuer = true;
        do {
            continuer = action(continuer);
        }
        while(continuer);
    }

    public int choix() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        int choix = 0;
        try {
            choix = Integer.parseInt(bufferRead.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return choix;
    }

    public boolean action(boolean sortir) {
        Menu();
        int choix = choix();
        switch(choix) {
            case 1:
                afficherListeVoiture();
                break;
            case 2:
                System.out.println("Ajouter une voiture à la concession (entrer marque, puis model)");
                Voiture voiture = this.creerVoiture();
                this.getConcession().AjoutVoiture(voiture);
                break;
            case 3:
                System.out.println("Supprimer une voiture à la concession (entrer index)");
                SupprimerVoitureConcession();
                break;
            case 4:
                System.out.println("Sortir de la boucle");
                sortir = SortirProgram(sortir);
                break;
        }
        return sortir;
    }

    public static boolean SortirProgram(boolean sortir) {
        sortir = false;
        return sortir;
    }

    public void Menu() {
        System.out.println("1 - Afficher les voitures de la concession");
        System.out.println("2 - Ajouter une voiture à la concession");
        System.out.println("3 - Supprimer une voiture de la concession");
        System.out.println("4 - Sortir de l'application");
    }

    public String demanderInfo() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String info = "";
        try {
            info = bufferRead.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return info;
    }

    public Voiture creerVoiture() {
        Voiture voiture = new Voiture(demanderInfo(),demanderInfo());
        return voiture;
    }

    public void creerConcession() {
        System.out.println("Quel est le nom de la concession ?");
        String info = this.demanderInfo();
        this.setConcession(new Concession(info));
    }

    public void afficherListeVoiture() {
        for(int i=0; i<this.getConcession().getVoitures().size(); i++) {
            System.out.println(i + " " + this.concession.getVoitures().get(i));
        }
    }

    public void SupprimerVoitureConcession() {
        this.afficherListeVoiture();
        int choix = choix();
        this.concession.getVoitures().remove(choix);
    }
}
