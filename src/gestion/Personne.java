package gestion;

public class Personne {
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.nom = nom == null ? "" : nom.trim();
        this.prenom = prenom == null ? "" : prenom.trim();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom == null ? "" : nom.trim();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom == null ? "" : prenom.trim();
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }
}