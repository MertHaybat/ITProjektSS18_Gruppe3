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
public class Teilhaberschaft extends BusinessObject{

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fremdschlüsselbeziehung zum Kontakt  
	 */
	private int kontaktID = 0;
	
	/**
	 * Fremdschlüsselbeziehung zur Kontaktliste
	 */
	private int kontaktlisteID = 0;

	/**
	 * Fremdschlüsselbeziehung zur Eigenschaftsausprägung 
	 */
	private int eigenschaftsauspraegungID = 0;

	/**
	 * Fremdschlüsselbeziehung zum Nutzer
	 */
	private int nutzerID = 0;
	
	/**
	 * Auslesen der Fremdschlüsselbeziehung zum Kontakt 
	 * @return kontaktID
	 */
	public int getKontaktID() {
		return kontaktID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zum Kontakt 
	 * @param kontaktID
	 */
	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}

	/**
	 * Auslesen der Fremdschlüsselbeziehung zur Kontaktliste
	 * @return kontaktlisteID
	 */
	public int getKontaktlisteID() {
		return kontaktlisteID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zur Kontaktliste
	 * @param kontaktlisteID
	 */
	public void setKontaktlisteID(int kontaktlisteID) {
		this.kontaktlisteID = kontaktlisteID;
	}

	/**
	 * Auslesen Fremdschlüsselbeziehung zur Eigenschaftsausprägung
	 * @return eigenschaftsauspraegungID
	 */
	public int getEigenschaftsauspraegungID() {
		return eigenschaftsauspraegungID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zur Eigenschaftsausprägung
	 * @param eigenschaftsauspraegungID
	 */
	public void setEigenschaftsauspraegungID(int eigenschaftsauspraegungID) {
		this.eigenschaftsauspraegungID = eigenschaftsauspraegungID;
	}

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

	
	
}
