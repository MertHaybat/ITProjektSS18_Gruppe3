package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse Teilhaberschaft erstellt eine Teilhaberschaft, welche über einen Kontakt, eine Kontaktliste,
 * eine Eigenschaftausprägung und/oder einen Nutzer erteilt werden kann.
 * Daher beherbergt die Klasse Teilhaberschaft  neben ihrer eigenen ID außerdem die Fremdschlüssel der Klassen 
 * Kontakt, Kontaktliste, Eigenschaftsausprägung und Nutzer.
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati
 *
 */
public class Teilhaberschaft {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID des Kontakts 
	 */
	private int kontaktID = 0;
	
	/**
	 * ID der Kontaktliste 
	 */
	private int kontaktlisteID = 0;

	/**
	 * ID der Eigenschaftsausprägung 
	 */
	private int eigenschaftsauspraegungID = 0;

	/**
	 * ID des Nutzers 
	 */
	private int nutzerID = 0;
	
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
	 * Auslesen der eigenschaftsauspraegungID
	 * @return eigenschaftsauspraegungID
	 */
	public int getEigenschaftsauspraegungID() {
		return eigenschaftsauspraegungID;
	}

	/**
	 * Setzen der eigenschaftsauspraegungID
	 * @param eigenschaftsauspraegungID
	 */
	public void setEigenschaftsauspraegungID(int eigenschaftsauspraegungID) {
		this.eigenschaftsauspraegungID = eigenschaftsauspraegungID;
	}

	/**
	 * Auslesen der NutzerID
	 * @return nutzerID
	 */
	public int getNutzerID() {
		return nutzerID;
	}

	/**
	 * Setzen der nutzerID
	 * @param nutzerID
	 */
	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}

	
	
}
