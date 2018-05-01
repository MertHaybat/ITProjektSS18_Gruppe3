package de.hdm.itprojektss18Gruppe3.shared.bo;

public class Eigenschaft extends BusinessObject {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	String bezeichnung = "";

	/**
	 * Konstruktor
	 */
	public Eigenschaft(){
		
	}
	
	public Eigenschaft(String bezeichnung){
		this.bezeichnung = bezeichnung;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
}
