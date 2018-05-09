package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse Kontaktliste erstellt eine Liste, welcher beliebig viele Kontakte hinzugefügt und wieder entfernt werden können.
 * Daher besitz die Klasse Kontaktliste eine n:m Beziehung zur Klasse Kontakt
 * Kontaktliste beherbergt neben ihrer eigenen ID außerdem den Fremdschlüssel der Klasse Kontakt.
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati
 *
 */
public class Kontaktliste extends BusinessObject{

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fremdschlüsselbeziehung zum Nutzer 
	 */
	private int nutzerID = 0;
	
	/**
	 * Bezeichnung der Kontaktliste 
	 */
	private String bezeichnung = "";

	/**
	 * Auslesen der Fremdschlüsselbeziehung zum Nutzer 
	 * @return nutzerID
	 */
	public int getNutzerID() {
		return nutzerID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zum Nutzer 
	 * @param nutzerID
	 */
	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}

	/**
	 * Auslesen der Bezeichnung der Kontaktliste
	 * @return bezeichnung
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * Setzen der Bezeichnung der Kontaktliste
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
	
}
