package de.hdm.itprojektss18Gruppe3.client;

import java.io.Serializable;

import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

public class NutzerTeilhaberschaftKontaktWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Nutzer nutzer = null;
	private Teilhaberschaft teilhaberschaft = null;
	private Kontakt kontakt = null;
	
	public NutzerTeilhaberschaftKontaktWrapper(){
		
	}
	
	public NutzerTeilhaberschaftKontaktWrapper(Teilhaberschaft teilhaberschaft, Kontakt kontakt){
		this.teilhaberschaft = teilhaberschaft;
		this.kontakt = kontakt;
	}
	public NutzerTeilhaberschaftKontaktWrapper(Nutzer nutzer, Teilhaberschaft teilhaberschaft, Kontakt kontakt){
		this.nutzer = nutzer;
		this.teilhaberschaft = teilhaberschaft;
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

	public Kontakt getKontakt() {
		return kontakt;
	}

	public void setKontakt(Kontakt kontakt) {
		this.kontakt = kontakt;
	}

	public int getNutzerID(){
		return this.nutzer.getId();
	}
	
	public void setNutzerID(int nutzerID){
		this.nutzer.setId(nutzerID);
	}
	
	public String getNutzerMail(){
		return this.nutzer.getMail();
	}
	
	public void setNutzerMail(String mail){
		this.nutzer.setMail(mail);
	}
	
	public int getTeilhaberschaftID(){
		return this.teilhaberschaft.getId();
	}
	
	public void setTeilhaberschaftID(int teilhaberschaftID){
		this.teilhaberschaft.setId(teilhaberschaftID);
	}
	
	public int getTeilhaberschaftEigentuemerID(){
		return this.teilhaberschaft.getEigentuemerID();
	}
	
	public void setTeilhaberschaftEigentuemerID(int eigentuemerID){
		this.teilhaberschaft.setEigentuemerID(eigentuemerID);
	}
	
	public int getTeilhaberschaftTeilhaberID(){
		return this.teilhaberschaft.getTeilhabenderID();
	}
	
	public void setTeilhaberschaftTeilhabenderID(int teilhabenderID){
		this.teilhaberschaft.setTeilhabenderID(teilhabenderID);
	}
	
	public int getTeilhaberschaftKontaktID(){
		return this.teilhaberschaft.getKontaktID();
	}
	
	public void setTeilhaberschaftKontaktID(int kontaktID){
		this.teilhaberschaft.setKontaktID(kontaktID);
	}
	
	public int getKontaktID(){
		return this.kontakt.getId();
	}
	
	public void setKontaktID(int kontaktID){
		this.kontakt.setId(kontaktID);
	}
	
	public String getKontaktName(){
		return this.kontakt.getName();
	}
	
	public void setKontaktName(String kontaktname){
		this.kontakt.setName(kontaktname);
	}
}
