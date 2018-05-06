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
public class Kontakt {
	
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
	private Date erzeuguungsdatum = null;
	
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
	 * EigenschaftsID des Kontakts 
	 */
	private int eigenschaftID = 0;

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
	 * Auslesen des Erzeuguungsdatums
	 * @return erzeuguungsdatum
	 */
	public Date getErzeuguungsdatum() {
		return erzeuguungsdatum;
	}

	/**
	 * Setzen der Erzeuguungsdatums
	 * @param erzeuguungsdatum
	 */
	public void setErzeuguungsdatum(Date erzeuguungsdatum) {
		this.erzeuguungsdatum = erzeuguungsdatum;
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

	/**
	 * Auslesen der EigenschaftID
	 * @return eigenschaftID
	 */
	public int getEigenschaftID() {
		return eigenschaftID;
	}

	/**
	 * Setzen der EigenschaftID
	 * @param eigenschaftID
	 */
	public void setEigenschaftID(int eigenschaftID) {
		this.eigenschaftID = eigenschaftID;
	}
}
