package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse Eigenschaftsauspraegung ist eine zusammengesetzte Tabelle aus Eigenschaft und Eigenschaftsauspraegung.
 * Hier befinden sich neben der eigenen ID der Klasse, die Fremdschlüssel der eben erwähnten Tabellen sowie der Klasse Kontakt. 
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati
 *
 */
public class EigenschaftEigenschaftsauspraegung extends BusinessObject {
	
	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fremdschlüsselbeziehung zur Eigenschaft 
	 */
	private int eigenschaftID = 0;
	
	/**
	 * Fremdschlüsselbeziehung zur Eigenschaftsausprägung 
	 */
	private int eigenschaftsauspraegungID = 0;
	
	
	/**
	 * Fremdschlüsselbeziehung zum Kontakt 
	 */
	private int kontaktID = 0;


	/**
	 * Auslesen der Fremdschlüsselbeziehung zur Eigenschaft

	 * @return eigenschaftID
	 */
	public int getEigenschaftID() {
		return eigenschaftID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zur Eigenschaft 

	 * @param eigenschaftID
	 */
	public void setEigenschaftID(int eigenschaftID) {
		this.eigenschaftID = eigenschaftID;
	}

	/**
	 * Auslesen der Fremdschlüsselbeziehung zur Eigenschaftsausprägung
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
	 * Auslesen der Fremdschlüsselbeziehung zur Eigenschaft 
	 * @return kontaktID
	 */
	public int getKontaktID() {
		return kontaktID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zur Eigenschaft 
	 * @param kontaktID
	 */
	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}
}
