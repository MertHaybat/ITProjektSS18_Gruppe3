package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse Eigenschaftsauspraegung ist eine zusammengesetzte Tabelle aus Eigenschaft und Eigenschaftsauspraegung.
 * Hier befinden sich neben der eigenen ID der Klasse, die Fremdschlüssel der eben erwähnten Tabellen sowie der Klasse Kontakt. 
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati
 *
 */
public class EigenschaftEigenschaftsauspraegung {
	
	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID der Eigenschaft 
	 */
	private int eigenschaftID = 0;
	
	/**
	 * ID der Eigenschaftsausprägung 
	 */
	private int eigenschaftsauspraegungID = 0;
	
	
	/**
	 * ID des Kontakts 
	 */
	private int kontaktID = 0;


	/**
	 * Auslesen der eigenschaftID
	 * @return eigenschaftID
	 */
	public int getEigenschaftID() {
		return eigenschaftID;
	}

	/**
	 * Setzen der eigenschaftID
	 * @param eigenschaftID
	 */
	public void setEigenschaftID(int eigenschaftID) {
		this.eigenschaftID = eigenschaftID;
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
	 * Auslesen der kontaktID
	 * @return kontaktID
	 */
	public int getKontaktID() {
		return kontaktID;
	}

	/**
	 * Setzen der kontaktID
	 * @param kontaktID
	 */
	public void setKontaktID(int kontaktID) {
		this.kontaktID = kontaktID;
	}
}
