package de.hdm.itprojektss18Gruppe3.shared.bo;

import java.io.Serializable;

public class EigenschaftsAuspraegungHybrid implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String auspraegung = "";
	private String eigenschaft = "";
	
	private int statusAuspraegung = 0;
	
	private int auspraegungID = 0;
	private int eigenschaftID = 0;
	
	public EigenschaftsAuspraegungHybrid(){
		
	}
	
	public EigenschaftsAuspraegungHybrid(Eigenschaft eigenschaft, Eigenschaftsauspraegung auspraegung){
		
		this.auspraegung = auspraegung.getWert();
		this.eigenschaft = eigenschaft.getBezeichnung();
		this.auspraegungID = auspraegung.getId();
		this.eigenschaftID = auspraegung.getEigenschaftID();
		this.statusAuspraegung = auspraegung.getStatus();
		
	}

	public int getStatusAuspraegung() {
		return statusAuspraegung;
	}

	public void setStatusAuspraegung(int statusAuspraegung) {
		this.statusAuspraegung = statusAuspraegung;
	}

	public String getAuspraegung() {
		return auspraegung;
	}

	public void setAuspraegung(String auspraegung) {
		this.auspraegung = auspraegung;
	}

	public String getEigenschaft() {
		return eigenschaft;
	}

	public void setEigenschaft(String eigenschaft) {
		this.eigenschaft = eigenschaft;
	}

	public int getAuspraegungid() {
		return auspraegungID;
	}

	public void setAuspraegungid(int auspraegungID) {
		this.auspraegungID = auspraegungID;
	}

	public int getEigenschaftid() {
		return eigenschaftID;
	}

	public void setEigenschaftid(int eigenschaftID) {
		this.eigenschaftID = eigenschaftID;
	}
	
	

}
