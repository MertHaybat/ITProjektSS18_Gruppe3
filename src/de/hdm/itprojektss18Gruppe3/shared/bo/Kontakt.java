package de.hdm.itprojektss18Gruppe3.shared.bo;

import java.util.Date;

/**
 * Die Klasse Kontakt erstellt einen Kontakt, der anschließend einer Kontaktliste hinzugefügt werden kann.
 * Daher besitz die Klasse Kontakt eine n:m Beziehung zur Klasse Kontaktliste
 * Die Klasse Kontakt besitzt eine 1:n Beziehung zu Eigenschaft.
 * @version 1.0 06 May 2018
 * @author Giuseppe Galati
 *
 */
public class Kontakt extends BusinessObject{
	
	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name des Kontakts 
	 */
	private String name ="";
	
	/**
	 * Erzeugungsdatum des Kontakts 
	 */
	private Date erzeugungsdatum = null;
	
	/**
	 * Modifikationsdatum des Kontakts 
	 */
	private Date modifikationsdatum = null;
	
	/**
	 * Status des Kontakts 
	 */
	private int status = 0;
	
	/**
	 * GoogleMail-Adresse des Kontakts 
	 */
	private String googleMail = "";
	
	/**
	 * KontaktlisteID des Kontakts 
	 */
	private int kontaktlisteID = 0;
	
	/**
	 * NutzerID des Kontakts 
	 */
	private int nutzerID = 0;

	/**
	 * Auslesen des Kontaktnamens
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzen der Kontaktnamens
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Auslesen des Erzeugungsdatums
	 * @return erzeugungsdatum
	 */
	public Date getErzeugungsdatum() {
		return erzeugungsdatum;
	}

	/**
	 * Setzen der Erzeugungsdatums
	 * @param erzeugungsdatum
	 */
	public void setErzeugungsdatum(Date erzeugungsdatum) {
		this.erzeugungsdatum = erzeugungsdatum;
	}

	/**
	 * Auslesen des Modifikationsdatums
	 * @return modifikationsdatum
	 */
	public Date getModifikationsdatum() {
		return modifikationsdatum;
	}

	/**
	 * Setzen der Modifikationsdatums
	 * @param modifikationsdatum
	 */
	public void setModifikationsdatum(Date modifikationsdatum) {
		this.modifikationsdatum = modifikationsdatum;
	}
	
	/**
	 * Auslesen des Status
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setzen der Status
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Auslesen der GoogleMail-Adresse
	 * @return googleMail
	 */
	public String getGoogleMail() {
		return googleMail;
	}

	/**
	 * Setzen der GoogleMail-Adresse
	 * @param googleMail
	 */
	public void setGoogleMail(String googleMail) {
		this.googleMail = googleMail;
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
	 * Auslesen der NutzerID
	 * @return nutzerID
	 */
	public int getNutzerID() {
		return nutzerID;
	}

	/**
	 * Setzen der NutzerID
	 * @param nutzerID
	 */
	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}
}
