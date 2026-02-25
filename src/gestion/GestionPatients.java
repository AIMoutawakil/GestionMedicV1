package gestion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestionPatients {
    private List<Patient> patients;
    private List<ServiceHospitalier> services;
    private List<Medecin> medecins;

    public GestionPatients() {
        patients = new ArrayList<>();
        services = new ArrayList<>();
        medecins = new ArrayList<>();
        initialiserServicesParDefaut();
        initialiserMedecinsParDefaut();
    }

    private void initialiserServicesParDefaut() {
        ajouterService("Urgences", 3);
        ajouterService("Cardio", 2);
        ajouterService("Pédiatrie", 2);
        ajouterService("Chirurgie", 2);
    }

    private void initialiserMedecinsParDefaut() {
        medecins.add(new Medecin("Amrani", "Sara", "Urgences"));
        medecins.add(new Medecin("Bennani", "Youssef", "Cardiologie"));
    }

    public void ajouterService(String nom, int capacite) {
        services.add(new ServiceHospitalier(nom, capacite));
    }

    public List<ServiceHospitalier> getServices() {
        return services;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Medecin> getMedecins() {
        return medecins;
    }

    public boolean ajouterPatient(String nom, String prenom, int anneeNaissance, int numeroService) {
        if (numeroService < 1 || numeroService > services.size()) {
            return false;
        }

        ServiceHospitalier service = services.get(numeroService - 1);
        if (service.estComplet()) {
            return false;
        }

        Patient patient = new Patient(nom, prenom, anneeNaissance);

        boolean ajouteDansService = service.ajouterPatient(patient);
        if (!ajouteDansService) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    public boolean modifierPatient(int index, String nom, String prenom, int anneeNaissance, int nouveauNumeroService) {
        if (index < 0 || index >= patients.size()) {
            return false;
        }

        if (nouveauNumeroService < 1 || nouveauNumeroService > services.size()) {
            return false;
        }

        Patient patient = patients.get(index);
        ServiceHospitalier ancienService = patient.getService();
        ServiceHospitalier nouveauService = services.get(nouveauNumeroService - 1);

        if (ancienService != nouveauService && nouveauService.estComplet()) {
            return false;
        }

        patient.setNom(nom);
        patient.setPrenom(prenom);
        patient.setAnneeNaissance(anneeNaissance);

        if (ancienService != nouveauService) {
            if (ancienService != null) {
                ancienService.retirerPatient(patient);
            }
            nouveauService.ajouterPatient(patient);
        }

        return true;
    }

    public boolean supprimerPatient(int index) {
        if (index < 0 || index >= patients.size()) {
            return false;
        }

        Patient patient = patients.get(index);
        ServiceHospitalier service = patient.getService();

        if (service != null) {
            service.retirerPatient(patient);
        }

        patients.remove(index);
        return true;
    }

    public List<Patient> rechercherParNom(String recherche) {
        List<Patient> resultat = new ArrayList<>();

        for (Patient p : patients) {
            if (p.getNom().toLowerCase().contains(recherche.toLowerCase())) {
                resultat.add(p);
            }
        }

        return resultat;
    }

    public double getAgeMoyen() {
        if (patients.isEmpty()) {
            return 0;
        }

        int somme = 0;
        for (Patient p : patients) {
            somme += p.getAge();
        }

        return (double) somme / patients.size();
    }

    public int getAgeMin() {
        if (patients.isEmpty()) {
            return 0;
        }

        int min = patients.get(0).getAge();
        for (Patient p : patients) {
            if (p.getAge() < min) {
                min = p.getAge();
            }
        }

        return min;
    }

    public int getAgeMax() {
        if (patients.isEmpty()) {
            return 0;
        }

        int max = patients.get(0).getAge();
        for (Patient p : patients) {
            if (p.getAge() > max) {
                max = p.getAge();
            }
        }

        return max;
    }

    public List<Patient> getPatientsTriesParNom() {
        List<Patient> copie = new ArrayList<>(patients);
        copie.sort(Comparator.comparing(Patient::getNom, String.CASE_INSENSITIVE_ORDER));
        return copie;
    }
}