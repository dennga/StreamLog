package de.lokal.streamlog;

/*
 * Die StreamItem-Klasse ist wie ein Formular oder eine Vorlage. 
 * Jedes Mal, wenn du einen Film oder eine Serie hinzufügst, erstellst du eine neue "Ausfüllung" dieses Formulars 
 * das ist dann ein Objekt der Klasse StreamItem.
 */


// öffentliche Klasse mit privaten Attributen/Eigenschaften
public class StreamItem {
    private String titel;
    private String typ;
    private int bewertung;
    private String erscheinungsdatum;
    private String gesehenAm;

    //param eine Art Kommentar in JavaDocs beschreibt die Parameter (Eingabewerte)
    /** 
     * 
     * @param titel Der Titel des Films oder der Serie.
     * @param typ Der Typ des Eintrags also ob Serie oder Film.
     * @param bewertung Die Bewertung des Benutzers (1-5 Sterne).
     * @param erscheinungsdatum Das Erscheinungsdatum des Films/Serie.
     * @param gesehenAm Das Datum, an dem der Film/Serie geschaut wurde.
     * 
     */

    // Konstruktor(Bauplan) - initialisiert ein neues StreamItem-Objekt mit den übergebenen Werten und stellt so sicher, dass jedes Objekt sofort einen definierten Zustand hat.   
    public StreamItem(String titel, String typ, int bewertung, String erscheinungsdatum, String gesehenAm) {
        this.titel = titel;
        this.typ = typ;
        this.bewertung = bewertung;
        this.erscheinungsdatum = erscheinungsdatum;
        this.gesehenAm = gesehenAm;

    }

    // -- Getter-Methoden --
    // Diese Methode erlaubt es anderen Klassen, auf die privaten Attribute zuzugreifen

    public String getTitel() {
        return titel;
    }

    public String getTyp() {
        return typ;
    }

    public int getBewertung() {
        return bewertung;
    }

    public String getErscheinungsdatum() {
        return erscheinungsdatum;
    }

    public String getGesehenAm() {
        return gesehenAm;
    }

    // --Setter-Methoden--
    // Sie erlauben es, die Werte der Attribute nach der Erstellung des Objekts zu ändern.

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }

    public void setErscheinungsdatum(String erscheinungsdatum) {
        this.erscheinungsdatum = erscheinungsdatum;
    }

    public void setGesehenAm(String gesehenAm) {
        this.gesehenAm = gesehenAm;
    }

     /**
     * Überschreibt die Standard toString()-Methode, um eine lesbare String-Repräsentation
     * des StreamItem-Objekts zurückzugeben.
     *
     *
     * @return Eine String-Repräsentation des StreamItem-Objekts.
     */
    @Override
    public String toString() {
        return titel + ";" + typ + ";" + bewertung + ";" + erscheinungsdatum + ";" + gesehenAm;
    }

}
