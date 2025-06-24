package de.lokal.streamlog; // Stelle sicher, dass dein Package-Name korrekt ist

import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 
        boolean laeuft = true;           
        List<StreamItem> streamItems = new ArrayList<>(); 

        // Erstelle eine Instanz deines Repositories, um Daten zu laden und zu speichern.
        StreamLogRepository repository = new StreamLogRepository();   

        // Lade alle StreamItems, die bereits in der Datei gespeichert sind, in den Arbeitsspeicher.
        streamItems = repository.loadItems(); 
        System.out.println("Es wurden " + streamItems.size() + " Einträge geladen.");
                                            
        // Begrüßung des Benutzers
        System.out.println("--StreamLog--");

        // Hauptschleife mit Menü
        while (laeuft) {
            System.out.println("---"); // Trennlinie für bessere Lesbarkeit
            System.out.println("Menue: '1' zum Hinzufügen, '2' zum Anzeigen, '3' zum Suchen, '4' zum Beenden");
            System.out.print("Gib eine Zahl 1-4 ein und bestätige mit Enter: "); 

            String eingabe = sc.nextLine();

            if (eingabe.equals("1")) {
                fuegeStreamItemHinzu(sc, streamItems, repository); // Ruft die Methode zum Hinzufügen auf
            } else if (eingabe.equals("2")) {
                zeigeAlleStreamItems(streamItems); // Ruft die Methode zum Anzeigen auf
            } else if (eingabe.equals("3")) {
                sucheStreamItems(sc, streamItems); // Ruft die Methode zum Suchen auf
            } else if (eingabe.equals("4")) {
                laeuft = false; 
                System.out.println("StreamLog wird beendet. Bitte warten...");
            } else {
                System.out.println("Ungültige Eingabe! Bitte wähle eine Option von 1 bis 4.");  
            }
        } // Ende der while-Schleife
            
        // --- Daten speichern vor dem Beenden des Programms ---
        // Nachdem die Hauptschleife beendet wurde, speichern wir alle aktuellen StreamItems zurück in die Datei.
        repository.saveItems(streamItems);
        System.out.println("Alle Änderungen wurden gespeichert.");
        // --- Ende Speichern der Daten ---

        sc.close(); // Schließe den Scanner, um Ressourcen freizugeben.
        System.out.println("Auf Wiedersehen!"); 
    } // Ende der public static void main(String[] args) Methode

    /**
     * Ermöglicht dem Benutzer, einen neuen Film oder eine neue Serie hinzuzufügen.
     * Fragt nach Details, erstellt ein StreamItem und fügt es der Liste hinzu.
     * Speichert die aktualisierte Liste danach sofort in der Datei.
     *
     * @param scanner Der Scanner zum Lesen von Benutzereingaben.
     * @param streamItems Die Liste aller StreamItem-Objekte.
     * @param repository Das StreamLogRepository-Objekt zum Speichern der Daten.
     */
    private static void fuegeStreamItemHinzu(Scanner scanner, List<StreamItem> streamItems, StreamLogRepository repository) {
        System.out.println("\n--- Film/Serie hinzufügen ---");

        System.out.print("Titel: ");
        String titel = scanner.nextLine();

        // Duplikatsprüfung: Prüft, ob ein Eintrag mit diesem Titel bereits existiert (Groß-/Kleinschreibung ignorierend).
        for (StreamItem item : streamItems) {
            if (item.getTitel().equalsIgnoreCase(titel)) {
                System.out.println("Fehler: Ein Eintrag mit dem Titel '" + titel + "' existiert bereits!");
                return; // Beendet die Methode, wenn ein Duplikat gefunden wurde.
            }
        }

        System.out.print("Typ (Film/Serie): ");
        String typ = scanner.nextLine();
        // Hier könnte man später eine Validierung einbauen, dass nur "Film" oder "Serie" erlaubt ist.

        int bewertung = -1;
        while (bewertung < 1 || bewertung > 5) {
            System.out.print("Bewertung (1-5 Sterne): ");
            try {
                bewertung = Integer.parseInt(scanner.nextLine());
                if (bewertung < 1 || bewertung > 5) {
                    System.out.println("Ungültige Bewertung. Bitte gib eine Zahl zwischen 1 und 5 ein.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte gib eine Zahl ein.");
            }
        }

        System.out.print("Erscheinungsdatum (JJJJ-MM-TT, z.B. 2023-01-15): ");
        String erscheinungsdatum = scanner.nextLine();
        // Hier könnte man später eine Validierung für das Datumsformat einbauen.

        System.out.print("Gesehen am (JJJJ-MM-TT, z.B. 2024-06-24): ");
        String gesehenAm = scanner.nextLine();
        // Hier könnte man später eine Validierung für das Datumsformat einbauen.

        // Neues StreamItem-Objekt erstellen
        StreamItem neuesItem = new StreamItem(titel, typ, bewertung, erscheinungsdatum, gesehenAm);

        // Zum Arbeitsspeicher hinzufügen
        streamItems.add(neuesItem);

        // Die aktualisierte Liste sofort in der Datei speichern, um Datenverlust zu vermeiden.
        repository.saveItems(streamItems);

        System.out.println("'" + titel + "' erfolgreich zu StreamLog hinzugefügt und gespeichert.");
    } // Ende der private static void fuegeStreamItemHinzu-Methode

    /**
     * Zeigt alle vorhandenen StreamItem-Objekte aus der Liste an.
     * Wenn die Liste leer ist, wird eine entsprechende Nachricht ausgegeben.
     *
     * @param streamItems Die Liste aller StreamItem-Objekte, die angezeigt werden sollen.
     */
    private static void zeigeAlleStreamItems(List<StreamItem> streamItems) {
        System.out.println("\n--- Alle Filme/Serien in deinem StreamLog ---");

        if (streamItems.isEmpty()) {
            System.out.println("Dein StreamLog ist noch leer. Füge mit Option '1' neue Einträge hinzu!");
        } else {
            // Zählt die Einträge für eine übersichtlichere Ausgabe
            int counter = 1;
            for (StreamItem item : streamItems) {
                System.out.println("--- Eintrag " + counter + " ---");
                System.out.println("Titel:           " + item.getTitel());
                System.out.println("Typ:             " + item.getTyp());
                System.out.println("Bewertung:       " + item.getBewertung() + " von 5 Sternen");
                System.out.println("Erscheinungsdatum: " + item.getErscheinungsdatum());
                System.out.println("Gesehen am:      " + item.getGesehenAm());
                counter++;
            }
        }
        System.out.println("--- Ende der Liste ---");
    } // Ende der private static void zeigeAlleStreamItems-Methode

    /**
     * Sucht nach StreamItem-Objekten anhand eines Titels (oder Teils davon)
     * und zeigt die gefundenen Ergebnisse an. Die Suche ist nicht
     * Groß-/Kleinschreibung-sensitiv.
     *
     * @param scanner Der Scanner zum Lesen von Benutzereingaben.
     * @param streamItems Die Liste aller StreamItem-Objekte, in der gesucht werden soll.
     */
    private static void sucheStreamItems(Scanner scanner, List<StreamItem> streamItems) {
        System.out.println("\n--- Film/Serie suchen ---");

        if (streamItems.isEmpty()) {
            System.out.println("Dein StreamLog ist leer. Es gibt nichts zu suchen.");
            return; // Beende die Methode, wenn keine Einträge vorhanden sind.
        }

        System.out.print("Gib den Titel oder einen Teil des Titels ein, nach dem du suchen möchtest: ");
        String suchbegriff = scanner.nextLine().toLowerCase(); // Suchbegriff in Kleinbuchstaben umwandeln

        List<StreamItem> gefundeneItems = new ArrayList<>();
        for (StreamItem item : streamItems) {
            // Prüfe, ob der Titel des Items den Suchbegriff enthält (nicht Groß-/Kleinschreibung-sensitiv)
            if (item.getTitel().toLowerCase().contains(suchbegriff)) {
                gefundeneItems.add(item);
            }
        }

        if (gefundeneItems.isEmpty()) {
            System.out.println("Keine Einträge gefunden, die '" + suchbegriff + "' im Titel enthalten.");
        } else {
            System.out.println("\n--- Gefundene Einträge für '" + suchbegriff + "' ---");
            int counter = 1;
            for (StreamItem item : gefundeneItems) {
                System.out.println("--- Ergebnis " + counter + " ---");
                System.out.println("Titel:           " + item.getTitel());
                System.out.println("Typ:             " + item.getTyp());
                System.out.println("Bewertung:       " + item.getBewertung() + " von 5 Sternen");
                System.out.println("Erscheinungsdatum: " + item.getErscheinungsdatum());
                System.out.println("Gesehen am:      " + item.getGesehenAm());
                counter++;
            }
            System.out.println("--- Ende der Suchergebnisse ---");
        }
    } // Ende der private static void sucheStreamItems-Methode
} // Ende der public class Main