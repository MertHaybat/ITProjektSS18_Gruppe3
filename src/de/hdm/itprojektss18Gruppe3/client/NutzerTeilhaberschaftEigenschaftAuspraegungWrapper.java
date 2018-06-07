package de.hdm.itprojektss18Gruppe3.client;

import java.io.Serializable;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

public class NutzerTeilhaberschaftEigenschaftAuspraegungWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Nutzer nutzer = null;
	private Teilhaberschaft teilhaberschaft = null;
	private Eigenschaft eigenschaft = null;
	private Eigenschaftsauspraegung eigenschaftsauspraegung = null;
	private Kontakt kontakt = null;


	public NutzerTeilhaberschaftEigenschaftAuspraegungWrapper() {

	}

	public NutzerTeilhaberschaftEigenschaftAuspraegungWrapper(Nutzer nutzer, Teilhaberschaft teilhaberschaft,
			Eigenschaft eigenschaft, Eigenschaftsauspraegung eigenschaftsauspraegung, Kontakt kontakt) {
		this.nutzer = nutzer;
		this.teilhaberschaft = teilhaberschaft;
		this.eigenschaft = eigenschaft;
		this.eigenschaftsauspraegung = eigenschaftsauspraegung;
		this.kontakt = kontakt;
	}
	public Kontakt getKontakt() {
		return kontakt;
	}
	
	public void setKontakt(Kontakt kontakt) {
		this.kontakt = kontakt;
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

	public Eigenschaft getEigenschaft() {
		return eigenschaft;
	}

	public void setEigenschaft(Eigenschaft eigenschaft) {
		this.eigenschaft = eigenschaft;
	}

	public Eigenschaftsauspraegung getEigenschaftsauspraegung() {
		return eigenschaftsauspraegung;
	}

	public void setEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung) {
		this.eigenschaftsauspraegung = eigenschaftsauspraegung;
	}

}
