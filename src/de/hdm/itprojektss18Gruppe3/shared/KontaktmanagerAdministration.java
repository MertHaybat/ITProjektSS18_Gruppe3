package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung von Kontakten.
 * 
 * @version 1.30 11 May 2018
 * @author Thomas, Mert
 */
@RemoteServiceRelativePath("kontaktmanager")
public interface KontaktmanagerAdministration extends RemoteService {
	
	/**
	 * Initialisierung des Objekts. Diese ist hauptsächlich
	 * zum No Argument Constructor der implementierenden Klasse notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
	/**
	 * Methode zum Anlegen eines neuen Nutzers.
	 * @return Nutzerobjekt
	 * @throws IllegalArgumentException
	 */
	public Nutzer createNutzer() throws IllegalArgumentException;
	
	/**
	 * Methode zum Anlegen eines neuen Kontakts.
	 * @param name
	 * @param erzeugungsdatum
	 * @param modifikationsdatum
	 * @param status
	 * @param nutzerID
	 * @return Kontaktobjekt
	 * @throws IllegalArgumentException
	 */
	public Kontakt createKontakt(String name, Date erzeugungsdatum,
			Date modifikationsdatum, int status, int nutzerID)
				throws IllegalArgumentException;
	
	/**
	 * Auslesen aller Kontakte.
	 * @return Vector mit allen Kontakten.
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException;
	
	/**
	 * Methode zum Anlegen eines neuen Kontaktlistenobjekts.
	 * @param bezeichnung
	 * @param nutzerID
	 * @return Kontaktlistenobjekt
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste createKontaktliste(String bezeichnung, int nutzerID) throws IllegalArgumentException;
	
	/**
	 * Auslesen aller Kontaktlistenobjekte eines Nutzers.
	 * @param nutzerID
	 * @return Vector mit allen Kontaktlisten des Nutzers
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontaktliste> findAllKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException;
	
	/**
	 * Methode zum Anlegen eines neuen Teilhaberschaftobjekts.
	 * @param kontaktlisteID
	 * @param kontaktID
	 * @param eigenschaftsauspraegungID
	 * @param teilhabenderID
	 * @param eigentuemerID
	 * @return Teilhaberschaftobjekt
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft createTeilhaberschaft(int kontaktlisteID, 
			int kontaktID, int eigenschaftsauspraegungID, int teilhabenderID,
			int eigentuemerID) throws IllegalArgumentException;
	
	/**
	 * Suchen eines Teilhaberschaftobjekts eines Teilhabenden.
	 * @param teilhabenderID
	 * @return Teilhaberschaftobjekt
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft findTeilhaberschaftByTeilhabenderID(int teilhabenderID)
			throws IllegalArgumentException;
	
	/**
	 * Suchen eines Teilhaberschaftobjekts eines Eigentümers.
	 * @param eigentuemerID
	 * @return Teilhaberschaftobjekt
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft findTeilhaberschaftByEigentuemerID(int eigentuemerID)
			throws IllegalArgumentException;
	
	/**
	 * Auslesen aller Eigenschaftobjekte.
	 * @return Vector mit allen Eigenschaftobjekten
	 * @throws IllegalArgumentException
	 */
	public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException;

}
