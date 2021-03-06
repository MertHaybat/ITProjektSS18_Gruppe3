package de.hdm.itprojektss18Gruppe3.client;

import java.io.Serializable;


import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;

/**
 * Wrapper Klasse für Eigenschaft und Eigenschaftsausprägung. Wird benötigt unteranderem für die CellTables
 * @author Mert
 *
 */
public class EigenschaftsAuspraegungWrapper implements Serializable {
	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Deklarieren von Eigenschaft und Eigenschaftsauspraegung
	 */
	private Eigenschaft eigenschaft = null;
	private Eigenschaftsauspraegung auspraegung = null;
	
	/**
	 * No Argument Konstruktor
	 */
	public EigenschaftsAuspraegungWrapper(){
	}
	
	/**
	 * 
	 * @param eigenschaft
	 */
	public EigenschaftsAuspraegungWrapper(Eigenschaft eigenschaft){
		this.eigenschaft = eigenschaft;
	}
	/**
	 * 
	 * @param auspraegung
	 */
	public EigenschaftsAuspraegungWrapper(Eigenschaftsauspraegung auspraegung){
		this.auspraegung = auspraegung;
	}
	/**
	 * 
	 * @param eigenschaft
	 * @param auspraegung
	 */
	public EigenschaftsAuspraegungWrapper(Eigenschaft eigenschaft, Eigenschaftsauspraegung auspraegung){
		this.eigenschaft = eigenschaft;
		this.auspraegung = auspraegung;
	}

	public Eigenschaft getEigenschaft() {
		return eigenschaft;
	}
	
	
	
	public void setEigenschaft(Eigenschaft eigenschaft) {
		this.eigenschaft = eigenschaft;
	}
	
	
	
	public Eigenschaftsauspraegung getAuspraegung() {
		return auspraegung;
	}
	
	public void setAuspraegung(Eigenschaftsauspraegung auspraegung) {
		this.auspraegung = auspraegung;
	}
	public Eigenschaftsauspraegung getEigenschaftsauspraegungObject(int auspraegungID){
		auspraegung.setId(auspraegungID);
		return auspraegung;
	}

	public String getBezeichnungEigenschaftValue(){
		return this.eigenschaft.getBezeichnung();
	}
	public void setBezeichnungEigenschaftValue(String bezeichnung){
		this.eigenschaft.setBezeichnung(bezeichnung);
	}
	public int getEigenschaftIdValue(){
		return this.eigenschaft.getId();
	}
	public void setEigenschaftIdValue(int eigenschaftID){
		this.eigenschaft.setId(eigenschaftID);
	}
	
	public int getPersonIdEigenschaftsauspraegungValue(){
		return this.auspraegung.getPersonID();
	}
	public void setPersonIdEigenschaftsauspraegungValue(int personID){
		this.auspraegung.setPersonID(personID);
	}
	public String getWertEigenschaftsauspraegungValue(){
		return this.auspraegung.getWert();
	}
	public void setWertEigenschaftsauspraegungValue(String wert){
		this.auspraegung.setWert(wert);
	}
	public int getIDEigenschaftsauspraegungValue(){
		return this.auspraegung.getId();
	}
	public void setIDEigenschaftsauspraegungValue(int eigenschaftsauspraegungID){
		this.auspraegung.setId(eigenschaftsauspraegungID);
	}
	public int getEigenschaftFKIdValue(){
		return this.auspraegung.getEigenschaftID();
	}
	public void setEigenschaftFKIdValue(int eigenschaftID){
		this.auspraegung.setEigenschaftID(eigenschaftID);
	}
	
	public int getStatusValue(){
		return this.auspraegung.getStatus();
	}
	
	public void setStatusValue(int status){
		this.auspraegung.setStatus(status);
	}
	public String toString(){
		return "<table><tr><td>"+this.eigenschaft.getBezeichnung()+":</td>"
				+ "<td>" +this.auspraegung.getWert() +"</td></tr></table>";
	
	
	}
	
}

