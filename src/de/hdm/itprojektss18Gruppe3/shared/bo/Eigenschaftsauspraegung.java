package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse Eigenschaftsauspraegung steht in einer n:m Beziehung zu Eigenschaft.
 * Eigenschaftsauspraegung beschreibt die Eigenschaften eines Kontakts bzw. eines Nutzers. 
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati, Mert Haybat
 *
 */

public class Eigenschaftsauspraegung extends BusinessObject{

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * Wert der Eigenschaftsausprägung 
	 */
	private String wert = "";
	
	/**
	 * Fremdschlüsselbeziehung zur Person der Eigenschaftsausprägung
	 */
	private int personID = 0;

	/**
	 * Fremdschlüsselbeziehung zur Person der Eigenschaftsausprägung
	 */
	private int status = 0;
	
	/**
	 * Fremdschlüsselbeziehung zur eigenschaft der Eigenschaftsausprägung
	 */
	private int eigenschaftID = 0;
	
	
	/**
	 * Auslesen des Werts
	 * @return Wert
	 */
	public String getWert() {
		return wert;
	}

	/**
	 * Setzen des Werts
	 * @param wert
	 */
	public void setWert(String wert) {
		this.wert = wert;
	}
	
	/**
	 * Auslesen der Fremdschlüsselbeziehung zum Eigentümer der Eigenschaftsausprägung
	 * @return personID
	 */
	public int getPersonID() {
		return personID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zum Eigentümer der Eigenschaftsausprägung
	 * @param personID
	 */
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	
	/**
	 * Auslesen des Status der Eigenschaftsausprägung
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setzen des Status der Eigenschaftsausprägung
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Auslesen der Fremdschlüsselbeziehung zur Eigenschaft der Eigenschaftsausprägung
	 * @return eigenschaftID
	 */
	public int getEigenschaftID() {
		return eigenschaftID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zur Eigenschaft der Eigenschaftsausprägung
	 * @param eigenschaftID
	 */
	public void setEigenschaftID(int eigenschaftID) {
		this.eigenschaftID = eigenschaftID;
	}

	
	
}
