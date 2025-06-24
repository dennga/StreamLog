package de.lokal.streamlog;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



class StreamLogRepository {

    private static final String DATEINAME = "streamlog.txt";

     public List<StreamItem> loadItems() {
        List<StreamItem> geladeneItems = new ArrayList<>();
        // Der 'try-with-resources'-Block stellt sicher, dass der BufferedReader automatisch geschlossen wird.
        try (BufferedReader reader = new BufferedReader(new FileReader(DATEINAME))) {
            String zeile;
            // Lese jede Zeile der Datei, solange es Zeilen gibt
            while ((zeile = reader.readLine()) != null) {
                // Versuche, die gelesene Zeile in ein StreamItem-Objekt umzuwandeln (zu parsen).
                StreamItem item = parseZeileToStreamItem(zeile);
                // Wenn das Parsen erfolgreich war (also kein 'null' zurückgegeben wurde), füge das Item hinzu.
                if (item != null) {
                    geladeneItems.add(item);
                }
            }
        } catch (IOException e) {
            // Dies wird ausgelöst, wenn die Datei z.B. nicht gefunden wird.
            System.out.println("Info: StreamLog-Datei '" + DATEINAME + "' nicht gefunden oder lesbar. Eine neue wird erstellt, falls nötig.");
            // Hier werfen wir keine Exception, da eine leere Liste in diesem Fall ein gültiges Ergebnis ist.
        }
        return geladeneItems;
    }

    /**
     * Speichert die übergebene Liste von StreamItem-Objekten in die Textdatei.
     * Die bestehende Datei wird bei jedem Speichervorgang komplett überschrieben.
     *
     * @param items Die {@link java.util.List} von StreamItem-Objekten, die gespeichert werden sollen.
     */
    public void saveItems(List<StreamItem> items) {
        // 'new FileWriter(DATEINAME, false)' bedeutet, dass die Datei bei jedem Aufruf überschrieben wird (false für append=false).
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATEINAME, false))) {
            // Gehe durch jedes StreamItem in der Liste
            for (StreamItem item : items) {
                writer.write(item.toString()); // Schreibe das StreamItem als String in die Datei (nutzt StreamItem.toString())
                writer.newLine(); // Fügt einen Zeilenumbruch hinzu, damit jedes Item in einer neuen Zeile steht
            }
            // Eine Erfolgsmeldung für den Benutzer (optional, aber hilfreich)
            System.out.println("StreamLog-Daten erfolgreich gespeichert.");
        } catch (IOException e) {
            // Behandelt Fehler beim Schreiben in die Datei
            System.err.println("Fehler beim Speichern der StreamLog-Daten: " + e.getMessage());
        }
    }

    /**
     * Hilfsmethode (private), um eine einzelne Zeile aus der Textdatei in ein StreamItem-Objekt zu parsen.
     * Diese Methode ist 'private', da sie nur intern von dieser Klasse verwendet wird.
     *
     * @param zeile Die aus der Datei gelesene Textzeile, die geparst werden soll.
     * @return Ein {@link StreamItem}-Objekt, wenn das Parsen erfolgreich war;
     * ansonsten {@code null}, wenn die Zeile ungültig ist oder ein Fehler auftritt.
     */
    private StreamItem parseZeileToStreamItem(String zeile) {
        // Trenne die Zeile am Semikolon (';') in einzelne Teile
        String[] teile = zeile.split(";");

        // Ein StreamItem sollte genau 5 Teile haben: Titel;Typ;Bewertung;Erscheinungsdatum;GesehenAm
        if (teile.length == 5) {
            try {
                String titel = teile[0];
                String typ = teile[1];
                // Konvertiere die Bewertung von String zu int. Dies kann fehlschlagen (NumberFormatException).
                int bewertung = Integer.parseInt(teile[2]);
                String erscheinungsdatum = teile[3];
                String gesehenAm = teile[4];

                // Erstelle und gib ein neues StreamItem-Objekt zurück
                return new StreamItem(titel, typ, bewertung, erscheinungsdatum, gesehenAm);
            } catch (NumberFormatException e) {
                // Wenn die Bewertung keine gültige Zahl ist
                System.err.println("Fehler beim Parsen der Bewertung in Zeile: '" + zeile + "'. " + e.getMessage());
                return null; // Zeile konnte nicht korrekt geparst werden
            }
        } else {
            // Wenn die Zeile nicht die erwartete Anzahl von Semikolons/Teilen hat
            System.err.println("Ungültiges Datenformat in Zeile: '" + zeile + "'. Zeile übersprungen.");
            return null; // Zeile hat nicht die erwartete Anzahl von Teilen
        }
    }
}



