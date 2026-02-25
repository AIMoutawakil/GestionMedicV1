package gestion;

public class Medecin extends Personne {
    private String specialite;

    public Medecin(String nom, String prenom, String specialite) {
        super(nom, prenom);
        this.specialite = specialite == null ? "" : specialite.trim();
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite == null ? "" : specialite.trim();
    }

    @Override
    public String toString() {
        return getNomComplet() + " - Spécialité: " + specialite;
    }
}