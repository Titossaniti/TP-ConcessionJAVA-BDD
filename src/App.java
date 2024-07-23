import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import Metier.Concession;
import Metier.Voiture;

public class App {

    private Concession concession;
    private DatabaseManager dbManager;
    public App() {

        try {
            this.dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/concession?characterEncoding=utf8", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }

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
                supprimerVoitureConcession();
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez la marque de la voiture: ");
        String marque = scanner.nextLine();
        System.out.print("Entrez le modèle de la voiture: ");
        String modele = scanner.nextLine();
        Voiture voiture = new Voiture(marque, modele);
        try {
            dbManager.addVehicule(voiture);
            System.out.println("Voiture ajoutée avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la voiture.");
        }
        return voiture;
    }

    public void creerConcession() {
        System.out.println("Quel est le nom de la concession ?");
        String info = this.demanderInfo();
        this.setConcession(new Concession(info));
    }

    public void afficherListeVoiture() {
        List<Voiture> voitures = dbManager.getVehicule();
        for (Voiture voiture : voitures) {
            System.out.println(voiture);
        }
    }

    public void supprimerVoitureConcession(String marque) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez la marque de la voiture à supprimer: ");
        scanner.nextLine();
        try {
            dbManager.deleteVehicule(marque);
            System.out.println("Voiture supprimée avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la voiture.");
        }
    }
}