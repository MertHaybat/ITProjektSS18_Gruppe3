package de.hdm.itprojektss18Gruppe3.client;

import java.io.Serializable;

import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * 
 * @author ersinbarut
 * Wrapperklasse um in der GUI Teilhaberschaften zu verwalten/anzuzeigen (in dem Fall Nutzer und Kontaktliste)
 */
public class NutzerTeilhaberschaftKontaktlisteWrapper implements Serializable {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Deklarierung der Attribute 
	 */
	private Nutzer nutzer = null;
	private Teilhaberschaft teilhaberschaft = null;
	private Kontaktliste kontaktliste = null;
	
	public NutzerTeilhaberschaftKontaktlisteWrapper(){
		
	}
	
	public NutzerTeilhaberschaftKontaktlisteWrapper(Nutzer nutzer, Teilhaberschaft teilhaberschaft, Kontaktliste kontaktliste){
		this.nutzer = nutzer;
		this.teilhaberschaft = teilhaberschaft;
		this.kontaktliste = kontaktliste;
	}

	public Nutzer getNutzer() {
		return nutzer;
	}

	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}

	public Teilhaberschaft getTeilhaberschaft() {
		return teilhaberschaft;
	}

	public void setTeilhaberschaft(Teilhaberschaft teilhaberschaft) {
		this.teilhaberschaft = teilhaberschaft;
	}

	public Kontaktliste getKontaktliste() {
		return kontaktliste;
	}

	public void setKontaktliste(Kontaktliste kontaktliste) {
		this.kontaktliste = kontaktliste;
	}

}
