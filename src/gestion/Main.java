package gestion;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionPatients gestion = new GestionPatients();

        int choix;
        do {
            afficherMenu();
            choix = lireEntier(scanner);

            switch (choix) {
                case 1 -> ajouterPatient(scanner, gestion);
                case 2 -> afficherPatients(gestion.getPatients());
                case 3 -> rechercherPatient(scanner, gestion);
                case 4 -> afficherStatistiques(gestion);
                case 5 -> afficherPatients(gestion.getPatientsTriesParNom());
                case 6 -> modifierPatient(scanner, gestion);
                case 7 -> supprimerPatient(scanner, gestion);
                case 8 -> afficherMedecins(gestion);
                case 0 -> System.out.println("\n👋 Au revoir !");
                default -> System.out.println("⚠ Choix invalide.");
            }
        } while (choix != 0);

        scanner.close();
    }

    static void afficherMenu() {
        System.out.println("\n══════ MedManager  ══════");
        System.out.println("  1. ➕ Ajouter un patient");
        System.out.println("  2. 📋 Afficher tous les patients");
        System.out.println("  3. 🔍 Rechercher un patient");
        System.out.println("  4. 📊 Statistiques");
        System.out.println("  5. 🔤 Afficher patients triés");
        System.out.println("  6. ✏ Modifier un patient");
        System.out.println("  7. 🗑 Supprimer un patient");
        System.out.println("  8. 👨‍⚕️ Afficher les médecins");
        System.out.println("  0. Quitter");
        System.out.print("Votre choix : ");
    }

    static int lireEntier(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("⚠ Entrez un nombre : ");
            scanner.next();
        }
        int valeur = scanner.nextInt();
        scanner.nextLine();
        return valeur;
    }

    static int lireAnneeValide(Scanner scanner) {
        int annee;
        while (true) {
            System.out.print("Année de naissance : ");
            annee = lireEntier(scanner);

            int age = Year.now().getValue() - annee;
            if (age >= 0 && age <= 150) {
                return annee;
            }

            System.out.println("❌ Âge invalide (" + age + "). Réessayez (0 à 150).");
        }
    }

    static void afficherServices(GestionPatients gestion) {
        System.out.println("\n--- Services ---");
        List<ServiceHospitalier> services = gestion.getServices();

        for (int i = 0; i < services.size(); i++) {
            ServiceHospitalier s = services.get(i);
            System.out.printf("%d) %-15s (%d/%d)%n",
                    i + 1,
                    s.getNom(),
                    s.getNombreOccupes(),
                    s.getCapacite());
        }
    }

    static void ajouterPatient(Scanner scanner, GestionPatients gestion) {
        System.out.println("\n--- Nouveau Patient ---");

        System.out.print("Nom : ");
        String nom = scanner.nextLine().trim();

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine().trim();

        afficherServices(gestion);
        System.out.print("Choisir un service (numéro) : ");
        int numeroService = lireEntier(scanner);

        int annee = lireAnneeValide(scanner);

        try {
            boolean ajoute = gestion.ajouterPatient(nom, prenom, annee, numeroService);

            if (ajoute) {
                System.out.println("✅ Patient enregistré avec succès.");
            } else {
                System.out.println("⚠ Ajout impossible (service invalide ou complet).");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void afficherPatients(List<Patient> patients) {
        if (patients.isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return;
        }

        int wNum = 5, wNom = 16, wPrenom = 16, wAge = 7;

        System.out.println();
        System.out.println("┌" + "─".repeat(wNum) + "┬" + "─".repeat(wNom) + "┬" + "─".repeat(wPrenom) + "┬" + "─".repeat(wAge) + "┐");
        System.out.printf("│%3s │ %-14s │ %-14s │%5s │%n", "#", "Nom", "Prénom", "Âge");
        System.out.println("├" + "─".repeat(wNum) + "┼" + "─".repeat(wNom) + "┼" + "─".repeat(wPrenom) + "┼" + "─".repeat(wAge) + "┤");

        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            System.out.printf("│%3d │ %-14s │ %-14s │%5d │%n",
                    i + 1,
                    p.getNom(),
                    p.getPrenom(),
                    p.getAge());
        }

        System.out.println("└" + "─".repeat(wNum) + "┴" + "─".repeat(wNom) + "┴" + "─".repeat(wPrenom) + "┴" + "─".repeat(wAge) + "┘");
        System.out.println("Total : " + patients.size() + " patient(s)");
    }

    static void rechercherPatient(Scanner scanner, GestionPatients gestion) {
        if (gestion.getPatients().isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return;
        }

        System.out.print("\nRechercher (nom) : ");
        String recherche = scanner.nextLine().trim();

        List<Patient> resultat = gestion.rechercherParNom(recherche);

        if (resultat.isEmpty()) {
            System.out.println("Aucun résultat pour \"" + recherche + "\"");
            return;
        }

        for (Patient p : resultat) {
            System.out.println("→ " + p);
        }
    }

    static void afficherStatistiques(GestionPatients gestion) {
        if (gestion.getPatients().isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return;
        }

        System.out.println("\n--- Statistiques ---");
        System.out.println("Total patients : " + gestion.getPatients().size());
        System.out.printf("Âge moyen      : %.2f%n", gestion.getAgeMoyen());
        System.out.println("Plus jeune     : " + gestion.getAgeMin() + " ans");
        System.out.println("Plus vieux     : " + gestion.getAgeMax() + " ans");
    }

    static void modifierPatient(Scanner scanner, GestionPatients gestion) {
        if (gestion.getPatients().isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return;
        }

        System.out.println("\n--- Modifier un patient ---");
        afficherPatients(gestion.getPatients());

        System.out.print("Numéro du patient à modifier : ");
        int numero = lireEntier(scanner);

        if (numero < 1 || numero > gestion.getPatients().size()) {
            System.out.println("⚠ Numéro invalide.");
            return;
        }

        System.out.print("Nouveau nom : ");
        String nom = scanner.nextLine().trim();

        System.out.print("Nouveau prénom : ");
        String prenom = scanner.nextLine().trim();

        afficherServices(gestion);
        System.out.print("Nouveau service (numéro) : ");
        int numeroService = lireEntier(scanner);

        int annee = lireAnneeValide(scanner);

        try {
            boolean ok = gestion.modifierPatient(numero - 1, nom, prenom, annee, numeroService);

            if (ok) {
                System.out.println("✅ Patient modifié avec succès.");
            } else {
                System.out.println("⚠ Modification impossible.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void supprimerPatient(Scanner scanner, GestionPatients gestion) {
        if (gestion.getPatients().isEmpty()) {
            System.out.println("\nAucun patient enregistré.");
            return;
        }

        System.out.println("\n--- Supprimer un patient ---");
        afficherPatients(gestion.getPatients());

        System.out.print("Numéro du patient à supprimer : ");
        int numero = lireEntier(scanner);

        boolean supprime = gestion.supprimerPatient(numero - 1);

        if (supprime) {
            System.out.println("✅ Patient supprimé avec succès.");
        } else {
            System.out.println("⚠ Suppression impossible.");
        }
    }

    static void afficherMedecins(GestionPatients gestion) {
        System.out.println("\n--- Liste des médecins ---");

        if (gestion.getMedecins().isEmpty()) {
            System.out.println("Aucun médecin enregistré.");
            return;
        }

        for (Medecin m : gestion.getMedecins()) {
            System.out.println("→ " + m);
        }
    }
}