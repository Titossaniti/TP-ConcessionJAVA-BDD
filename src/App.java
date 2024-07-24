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
        this.dbManager = new DatabaseManager();
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
        } while (continuer);
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
        Scanner scanner = new Scanner(System.in);
        Menu();
        int choix = choix();
        switch (choix) {
            case 1:
                afficherListeVoiture();
                break;
            case 2:
                System.out.println("Ajouter une voiture à la concession (entrer marque, puis model)");
                Voiture voiture = this.creerVoiture();
                this.getConcession().AjoutVoiture(voiture);
                break;
            case 3:
                System.out.print("Entrez la marque de la voiture à supprimer: ");
                String delMarque = scanner.nextLine();
                supprimerVoitureConcession(delMarque);
                break;
            case 4:
                System.out.print("Entrez la marque de la voiture à modifier: ");
                String oldMarque = scanner.nextLine();
                System.out.print("Entrez le nouveau nom de marque: ");
                String newMarque = scanner.nextLine();
                System.out.print("Entrez le nouveau model de la voiture: ");
                String newModel = scanner.nextLine();
                modifierVoiture(oldMarque, newMarque, newModel);
                break;
            case 5:
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
        System.out.println("4 - Modifier une voiture de la concession");
        System.out.println("5 - Sortir de l'application");
    }

    public String demanderInfo() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String info = "";
        try {
            info = bufferRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
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

    public void supprimerVoitureConcession(String marque) {
        try {
            dbManager.deleteVehicule(marque);
            System.out.println("Voiture supprimée avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la voiture.");
        }
    }

    public void modifierVoiture(String oldMarque, String newMarque, String newModel) {
        try {
            dbManager.updateVehicule(oldMarque, newMarque, newModel);
            System.out.println("Voiture modifiée avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de la voiture.");
        }
    }
}
