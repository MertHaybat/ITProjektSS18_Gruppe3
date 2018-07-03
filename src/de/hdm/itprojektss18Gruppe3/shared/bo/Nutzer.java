package de.hdm.itprojektss18Gruppe3.shared.bo;


/**
 * Nutzer ist die Subklasse von Person. Nutzer erbt von Person
 * somit ist dies eine is_a Beziehung
 *  
 * @version 1.0 09 May 2018
 * @author Mert
 *
 */
public class Nutzer extends Person{

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * E-Mail Adresse des Nutzers
	 */
	private String mail = "";

	/**
	 * Auslesen der E-Mail Adresse des Nutzers
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setzen der E-Mail Adresse des Nutzers
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String toString(){
		return "<table><tr><td>"+this.getMail()+"</td></tr></table>";
	
	
	}
}
