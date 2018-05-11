package de.hdm.itprojektss18Gruppe3.server;

import java.util.Date;
import java.util.Vector;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.itprojektss18Gruppe3.server.db.KontaktMapper;
import de.hdm.itprojektss18Gruppe3.server.db.KontaktlisteMapper;
import de.hdm.itprojektss18Gruppe3.server.db.NutzerMapper;
import de.hdm.itprojektss18Gruppe3.server.db.PersonMapper;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministration;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Person;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;


/**
 * Diese Klasse ist die Implementierungsklasse des Interface
 * KontaktmanagerAdministration.
 * Neben der ReportGeneratorImpl befindet sich hier die Applikationslogik.
 * 
 * 
 * @see KontaktmanagerAdministration
 * @see KontaktmanagerAdministrationAsync
 * @see RemoteServiceServlet
 * @version 1.30 11 May 2018
 * @author Thomas, Mert
 */

@SuppressWarnings("serial")
public class KontaktmanagerAdministrationImpl extends RemoteServiceServlet 
implements KontaktmanagerAdministration {
	
	/**
   	 * Referenz auf den KontaktlisteMapper, der Kontaktlisteobjekte mit der Datenbank
	 * abgleicht.
	 */
	private KontaktlisteMapper kontaktlisteMapper = null;
	
	/**
   	 * Referenz auf den KontaktMapper, der Kontaktobjekte mit der Datenbank
	 * abgleicht.
	 */
	private KontaktMapper kontaktMapper = null;
	
	/**
   	 * Referenz auf den NutzerMapper, der Nutzerobjekte mit der Datenbank
	 * abgleicht.
	 */
	private NutzerMapper nutzerMapper = null;
	
	/**
   	 * Referenz auf den PersonMapper, der Personobjekte mit der Datenbank
	 * abgleicht.
	 */
	private PersonMapper personMapper = null;
	
	/**
	 * Ein No-Argument-Konstruktor für die Client-seitige Erzeugung
	 * Ein No-Argument-Konstruktor f�r die Client-seitige Erzeugung
	 * von GWT.create
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public KontaktmanagerAdministrationImpl() throws IllegalArgumentException{
		
	}

	/**
	 * Initialsierungsmethode. Diese Methode muss f�r jede Instanz von
	 * KontaktmanagerAdministrationImpl aufgerufen werden.
	 * 
	 * @see ReportGeneratorImpl()
	 */
	@Override
	public void init() throws IllegalArgumentException {
		this.kontaktlisteMapper = KontaktlisteMapper.kontaktlisteMapper();
		this.kontaktMapper = KontaktMapper.kontaktMapper();		
//		this.nutzerMapper = NutzerMapper.nutzerMapper();
		this.personMapper = PersonMapper.personMapper();
	}

	/**
	 * Anlegen eines Nutzers.
	 */
	@Override
	public Nutzer createNutzer() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Anlegen eines Kontakts.
	 */
	@Override
	public Kontakt createKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Anlegen einer Kontaktliste.
	 */
	@Override
	public Kontaktliste createKontaktliste(String bezeichnung, int nutzerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Auslessen aller Kontaktlisten des übergebenen Nutzers.
	 */
	@Override
	public Vector<Kontaktliste> findAllKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Anlegen einer Teilhaberschaft.
	 */
	@Override
	public Teilhaberschaft createTeilhaberschaft(int kontaktlisteID, int kontaktID, int eigenschaftsauspraegungID,
			int teilhabenderID, int eigentuemerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Suchen von Teilhaberschaften eines bestimmten Teilhabenden.
	 */
	@Override
	public Teilhaberschaft findTeilhaberschaftByTeilhabenderID(int teilhabenderID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Suchen von Teilhaberschaften eines bestimmten Eigentuemers.
	 */
	@Override
	public Teilhaberschaft findTeilhaberschaftByEigentuemerID(int eigentuemerID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Auslesen aller Eigenschaften.
	 */
	@Override
	public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Auslesen aller Kontakte.
	 */
	@Override
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/** Überprüfen eines Nutzers nach der E-Mail Adresse
	 * 
	 *@param email; die GoogleMail Adresse des Nutzers
	 *@return Nutzer; Objekt der Klasse Nutzer wird zurückgegeben
	 */
	@Override
	public Nutzer checkEmail(String email) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Erstellen einer Eigenschaftsausprägung
	 * 
	 * @param wert; wert ist die Ausprägung einer Eigenschaft
	 * @param personID; personID definiert Nutzer/Kontakt dem die Eigenschaftsausprägung gehört
	 * @param status; status sagt aus, ob diese Eigenschaftsausprägung geteilt wurde oder nicht geteilt wurde
	 * @param eigenschaftID; ist die dazu gehörige Eigenschaft
	 * @return Eigenschaftsauspraegung; Zurückgegeben wird ein Objekt der Klasse Eigenschaftsauspraegung
	 */
	@Override
	public Eigenschaftsauspraegung createEigenschaftsauspraegung(String wert, int personID, int status,
			int eigenschaftID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Erstellen einer Eigenschaft
	 * 	
	 * @param bezeichnung; bezeichnung sagt aus was die Eigenschaft ist
	 * @return Eigenschaft; ein Eigenschaftsobjekt wird zurückgegeben
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft createEigenschaft(String bezeichnung) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Erstellen einer KontaktKontaktliste
	 * 
	 * @param kontaktID; ist die KontaktID die ausgewählt wurde
	 * @param kontaktlisteID; ist die KontaktlisteID die ausgewählt wurde
	 * @return KontaktKontaktliste Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public KontaktKontaktliste createKontaktKontaktliste(int kontaktID, int kontaktlisteID)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Alle Kontakte einer Kontaktliste anzeigen lassen
	 * 
	 * @param k; Objekt der Klasse Kontaktliste, hier wird die KontaktlisteID herausgenommen
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector <Kontakt> findAllKontakteByKontaktlisteID(Kontaktliste k) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Kontakte mit der Kontakt ID anzeigen lassen
	 * 
	 * @param k; Objekt der Klasse Kontakt, hier wird die KontaktID herausgenommen
	 * @return Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	public Kontakt findKontaktByID(Kontakt k) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Alle Eigenschaftsausprägung einer Person (Nutzer/Kontakt) anzeigen lassen
	 * 
	 * @param p; Objekt der Klasse Person, hier wird die PersonID herausgenommen
	 * @return Vector des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByPersonID(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Alle Eigenschaften einer Person anzeigen lassen
	 * 
	 * @param e; Objekt der Klasse Eigenschaft, hier wird die EigenschaftID herausgenommen
	 * @return Vector des Typs Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaft> findAllEigenschaftByEigenschaftIDFromPerson(Eigenschaft e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Alle Kontakte von einer Eigenschaftsausprägung anzeigen lassen. Dies ist die Suchfunktion.
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsausprägung
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontaktByEigenschaftsauspraegung(Eigenschaftsauspraegung e)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Alle Eigenschaftsausprägungen einer Eigenschaft anzeigen lassen
	 * 
	 * @param e; Objekt der Klasse Eigenschaft
	 * @return Vector des Typs Eigenschaftsausprägung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByEigenschaftID(Eigenschaft e)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Alle Eigenschaftsausprägung durch dessen Wert anzeigen lassen. Diese Methode ist Teil der Suchfunktion
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsausprägung
	 * @return Vector des Typs Eigenschaftsausprägung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByWert(Eigenschaftsauspraegung e)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Löschen einer Person (Nutzer/Kontakt)
	 * 
	 * @param p; Objekt der Klasse Person
	 * @return Objekt des Typs Person
	 * @throws IllegalArgumentException
	 */
	@Override
	public Person deletePerson(Person p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Löschen der Beziehung zwischen KontaktKontaktliste
	 * 
	 * @param k; Objekt der Klasse KontaktKontaktliste
	 * @return Objekt des Typs KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public KontaktKontaktliste deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Löschen einer Teilhaberschaft mit der PersonID
	 * 
	 * @param t; Objekt der Klasse Teilhaberschaft
	 * @return Objekt des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Teilhaberschaft deleteTeilhaberschaftByPersonID(Teilhaberschaft t) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Löschen einer Kontaktliste mit der NutzerID
	 * 
	 * @param k; Objekt der Klasse Kontaktliste
	 * @return Objekt des Typs Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public Kontaktliste deleteKontaktlisteByNutzerID(Kontaktliste k) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Löschen einer Eigenschaftsausprägung mit der PersonID
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsauspraegung
	 * @return Objekt des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaftsauspraegung deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung e)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
