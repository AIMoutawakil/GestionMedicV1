package gestion;

import java.time.Year;

public class Patient extends Personne {
    private int anneeNaissance;
    private ServiceHospitalier service;

    public Patient(String nom, String prenom, int anneeNaissance) {
        super(nom, prenom);
        setAnneeNaissance(anneeNaissance);
    }

    public int getAnneeNaissance() {
        return anneeNaissance;
    }

    public void setAnneeNaissance(int anneeNaissance) {
        int age = Year.now().getValue() - anneeNaissance;
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Âge invalide : " + age);
        }
        this.anneeNaissance = anneeNaissance;
    }

    public int getAge() {
        return Year.now().getValue() - anneeNaissance;
    }

    public ServiceHospitalier getService() {
        return service;
    }

    public void setService(ServiceHospitalier service) {
        this.service = service;
    }

    @Override
    public String toString() {
        String nomService = (service == null) ? "Aucun" : service.getNom();
        return getNomComplet() + " (" + getAge() + " ans) - Service: " + nomService;
    }
}