package gestion;

import java.util.ArrayList;
import java.util.List;

public class ServiceHospitalier {
    private String nom;
    private int capacite;
    private List<Patient> patients;

    public ServiceHospitalier(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
        this.patients = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getNombreOccupes() {
        return patients.size();
    }

    public boolean estComplet() {
        return patients.size() >= capacite;
    }

    public boolean ajouterPatient(Patient patient) {
        if (patient == null || estComplet()) {
            return false;
        }

        patients.add(patient);
        patient.setService(this);
        return true;
    }

    public boolean retirerPatient(Patient patient) {
        if (patient == null) {
            return false;
        }

        boolean retire = patients.remove(patient);
        if (retire) {
            patient.setService(null);
        }
        return retire;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    @Override
    public String toString() {
        return nom + " (" + getNombreOccupes() + "/" + capacite + ")";
    }
}