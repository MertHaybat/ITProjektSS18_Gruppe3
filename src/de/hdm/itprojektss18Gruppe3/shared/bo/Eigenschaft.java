package de.hdm.itprojektss18Gruppe3.shared.bo;

/**
 * Die Klasse Eigenschaft steht in einer 1:n Beziehung zu Kontakt und Nutzer.
 * Die Klasse Eigenschaft steht in einer 1:n Beziehung zu Eigenschaftsausprägung.
 * Eigenschaft beschreibt ein Kontakt bzw. einen Nutzer. Diese wird dann später von der 
 * Eigenschaftsausprägung ergänzt bzw. beschrieben.
 * 
 * @version 1.0 04 May 2018
 * @author Giuseppe Galati, Mert Haybat
 *
 */

public class Eigenschaft extends BusinessObject {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Bezeichnung der Eigenschaft 
	 */
	private String bezeichnung = "";

	/**
	 * Auslesen der Bezeichnung
	 * @return bezeichnung
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * Setzen der Bezeichnung
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
}
