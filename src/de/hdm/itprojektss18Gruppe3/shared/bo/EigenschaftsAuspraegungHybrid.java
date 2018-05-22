package de.hdm.itprojektss18Gruppe3.shared.bo;

import java.io.Serializable;

public class EigenschaftsAuspraegungHybrid implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String auspraegung = "";
	private String eigenschaft = "";
	
	private int auspraegungid = 0;
	private int eigenschaftid = 0;
	
	public EigenschaftsAuspraegungHybrid(){
		
	}
	
	public EigenschaftsAuspraegungHybrid(Eigenschaft eigenschaft, Eigenschaftsauspraegung auspraegung){
		
		this.auspraegung = auspraegung.getWert();
		this.eigenschaft = eigenschaft.getBezeichnung();
		this.auspraegungid = auspraegung.getId();
		this.eigenschaftid = auspraegung.getEigenschaftID();
		
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
		return auspraegungid;
	}

	public void setAuspraegungid(int auspraegungid) {
		this.auspraegungid = auspraegungid;
	}

	public int getEigenschaftid() {
		return eigenschaftid;
	}

	public void setEigenschaftid(int eigenschaftid) {
		this.eigenschaftid = eigenschaftid;
	}
	
	

}
