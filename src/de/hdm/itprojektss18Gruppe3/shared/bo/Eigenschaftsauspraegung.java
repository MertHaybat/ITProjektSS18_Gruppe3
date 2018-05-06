package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse Eigenschaftsauspraegung steht in einer n:m Beziehung zu Eigenschaft.
 * Eigenschaftsauspraegung beschreibt die Eigenschaften eines Kontakts bzw. eines Nutzers. 
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati
 *
 */

public class Eigenschaftsauspraegung {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * Wert der Eigenschaftsausprägung 
	 */
	private String wert = "";
	
	/**
	 * ID des Eigentümers 
	 */
	private int eigentuemerID = 0;

	/**
	 * Auslesen des Werts
	 * @return Wert
	 */
	public String getWert() {
		return wert;
	}

	/**
	 * Setzen des Statuses
	 * @param wert
	 */
	public void setWert(String wert) {
		this.wert = wert;
	}

	/**
	 * Auslesen der EigentümerID
	 * @return eigentuemerID
	 */
	public int getEigentuemerID() {
		return eigentuemerID;
	}

	/**
	 * Setzen der EigentümerID
	 * @param eigentuemerID
	 */
	public void setEigentuemerID(int eigentuemerID) {
		this.eigentuemerID = eigentuemerID;
	}
	
}
