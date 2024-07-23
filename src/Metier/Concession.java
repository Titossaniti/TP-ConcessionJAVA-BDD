package Metier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Concession {
    private String raisonSocial;

    private List<Voiture> voitures;

    // Constructeur
    public Concession() {
        // 1iere maniére d'initialiser l'attribut liste
        this.voitures = new ArrayList<Voiture>();
    }

    public Concession(String rs) {
        // 2ieme maniére d'initialiser l'attribut liste
        this.setVoitures(new ArrayList<Voiture>());
        this.setRaisonSocial(rs);
    }

    // Accesseur
    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public List<Voiture> getVoitures() {
        return voitures;
    }

    public void setVoitures(List<Voiture> voitures) {
        this.voitures = voitures;
    }

    // Comportement
    public void AjoutVoiture(Voiture voiture) {
        this.getVoitures().add(voiture);
    }

    public void SuppressionVoiture(Voiture voiture) {
        this.getVoitures().remove(voiture);
    }

    public HashSet<String> ListeMarques(){
        HashSet<String> marques = new HashSet<String>();
        for(Voiture voiture : this.getVoitures()) {
            if(!marques.contains(voiture.getMarque()))
            {
                marques.add(voiture.getMarque());
            }
        }
        return marques;
    }

    @Override public String toString() {
        return this.getRaisonSocial();
    }
}
