package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse KontaktlisteKontakt ist eine zusammengesetzte Tabelle aus Kontakt und Kontaktliste.
 * Hier befinden sich neben der eigenen ID der Klasse, die Fremdschlüssel der eben erwähnten Tabelle. 
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati
 *
 */
public class KontaktKontaktliste {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID der Kontaktliste 
	 */
	private int kontaktlisteID = 0;
	
	/**
	 * ID des Kontakts 
	 */
	private int kontaktID = 0;

	/**
	 * Auslesen der KontaktlisteID
	 * @return kontaktlisteID
	 */
	public int getKontaktlisteID() {
		return kontaktlisteID;
	}

	/**
	 * Setzen der KontaktlisteID
	 * @param kontaktlisteID
	 */
	public void setKontaktlisteID(int kontaktlisteID) {
		this.kontaktlisteID = kontaktlisteID;
	}

	/**
	 * Auslesen der KontaktID
	 * @return kontaktID
	 */
	public int getKontaktID() {
		return kontaktID;
	}

	/**
	 * Setzen der KontaktID
	 * @param kontaktID
	 */
	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}
	
	
}
