package de.hdm.itprojektss18Gruppe3.server;

import java.util.Date;
import java.util.Vector;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18Gruppe3.server.db.EigenschaftMapper;
import de.hdm.itprojektss18Gruppe3.server.db.EigenschaftsauspraegungMapper;
import de.hdm.itprojektss18Gruppe3.server.db.KontaktKontaktlisteMapper;
import de.hdm.itprojektss18Gruppe3.server.db.KontaktMapper;
import de.hdm.itprojektss18Gruppe3.server.db.KontaktlisteMapper;
import de.hdm.itprojektss18Gruppe3.server.db.NutzerMapper;
import de.hdm.itprojektss18Gruppe3.server.db.PersonMapper;
import de.hdm.itprojektss18Gruppe3.server.db.TeilhaberschaftMapper;
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
	@SuppressWarnings("unused")
	private PersonMapper personMapper = null;
	
	/**
   	 * Referenz auf den EigenschaftsauspraegungMapper, der Eigenschaftsauspraegungobjekte mit der Datenbank
	 * abgleicht.
	 */
	private EigenschaftsauspraegungMapper eigenschaftsauspraegungMapper = null;
	
	/**
   	 * Referenz auf den EigenschaftMapper, der Eigenschaftobjekte mit der Datenbank
	 * abgleicht.
	 */
	private EigenschaftMapper eigenschaftMapper = null;
	
	/**
   	 * Referenz auf den TeilhaberschaftMapper, der Teilhaberschaftobjekte mit der Datenbank
	 * abgleicht.
	 */
	private TeilhaberschaftMapper teilhaberschaftMapper = null;	
	
	/**
   	 * Referenz auf den KontaktKontaktlisteMapper, der KontaktKontaktlisteobjekte mit der Datenbank
	 * abgleicht.
	 */
	private KontaktKontaktlisteMapper kontaktKontaktlisteMapper = null;
	
	/**
	 * Ein No-Argument-Konstruktor für die Client-seitige Erzeugung
	 * von GWT.create
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public KontaktmanagerAdministrationImpl() throws IllegalArgumentException{
		
	}

	/**
	 * Initialsierungsmethode. Diese Methode muss für jede Instanz von
	 * KontaktmanagerAdministrationImpl aufgerufen werden.
	 * 
	 * @see ReportGeneratorImpl()
	 */
	@Override
	public void init() throws IllegalArgumentException {
		this.kontaktlisteMapper = KontaktlisteMapper.kontaktlisteMapper();
		this.kontaktMapper = KontaktMapper.kontaktMapper();		
		this.nutzerMapper = NutzerMapper.nutzerMapper();
		this.personMapper = PersonMapper.personMapper();
		this.eigenschaftMapper = EigenschaftMapper.eigenschaftMapper();
		this.eigenschaftsauspraegungMapper = EigenschaftsauspraegungMapper.eigenschaftsauspraegungMapper();
		this.teilhaberschaftMapper = TeilhaberschaftMapper.teilhaberschaftMapper();
		this.kontaktKontaktlisteMapper = KontaktKontaktlisteMapper.kontaktkontaktlisteMapper();
		
	}

	/**
	 * Anlegen eines Nutzers.
	 * @param mail; ist die Google E-Mail Adresse des Nutzers
	 */
	@Override
	public Nutzer createNutzer(String mail) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setMail(mail);
		return this.nutzerMapper.createNutzer(nutzer);
	}

	/**
	 * Anlegen eines Kontakts.
	 */
	@Override
	public Kontakt createKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID)
			throws IllegalArgumentException {
		
		Kontakt kontakt = new Kontakt();
		kontakt.setName(name);
		kontakt.setErzeugungsdatum(erzeugungsdatum);
		kontakt.setModifikationsdatum(modifikationsdatum);
		kontakt.setStatus(status);
		kontakt.setNutzerID(nutzerID);
		
		return this.kontaktMapper.createKontakt(kontakt);
	}

	/**
	 * Anlegen einer Kontaktliste.
	 */
	@Override
	public Kontaktliste createKontaktliste(String bezeichnung, int nutzerID) throws IllegalArgumentException {
		
		Kontaktliste kontaktliste = new Kontaktliste();
		
		kontaktliste.setBezeichnung(bezeichnung);
		kontaktliste.setNutzerID(nutzerID);
		
		return this.kontaktlisteMapper.createKontaktliste(kontaktliste);
	}

	/**
	 * Auslessen aller Kontaktlisten des übergebenen Nutzers.
	 */
	@Override
	public Vector<Kontaktliste> findAllKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException {
		return this.kontaktlisteMapper.findAllKontaktlisteByNutzerID(nutzerID);
	}

	/**
	 * Anlegen einer Teilhaberschaft.
	 */
	@Override
	public Teilhaberschaft createTeilhaberschaft(int kontaktlisteID, int kontaktID, int eigenschaftsauspraegungID,
			int teilhabenderID, int eigentuemerID) throws IllegalArgumentException {
		
		Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
		
		teilhaberschaft.setEigenschaftsauspraegungID(eigenschaftsauspraegungID);
		teilhaberschaft.setEigentuemerID(eigentuemerID);
		teilhaberschaft.setKontaktID(kontaktID);
		teilhaberschaft.setKontaktlisteID(kontaktlisteID);
		teilhaberschaft.setTeilhabenderID(teilhabenderID);
		
		return this.teilhaberschaftMapper.createTeilhaberschaft(teilhaberschaft);
	}

	/**
	 * Suchen von Teilhaberschaften eines bestimmten Teilhabenden.
	 */
	@Override
	public Vector<Teilhaberschaft>findTeilhaberschaftByTeilhabenderID(int teilhabenderID) throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByTeilhabenderID(teilhabenderID);
	}

	/**
	 * Suchen von Teilhaberschaften eines bestimmten Eigentuemers.
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByEigentuemerID(int eigentuemerID) throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByEigentuemerID(eigentuemerID);
	}

	/**
	 * Auslesen aller Eigenschaften.
	 */
	@Override
	public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException {
		return this.eigenschaftMapper.findAllEigenschaften();
	}

	/**
	 * Auslesen aller Kontakte.
	 */
	@Override
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException {
		return this.kontaktMapper.findAllKontakt();
	}

	/** Überprüfen eines Nutzers nach der E-Mail Adresse
	 * 
	 *@param email; die GoogleMail Adresse des Nutzers
	 *@return nutzer; Objekt der Klasse Nutzer wird zurückgegeben
	 */
	@Override
	public Nutzer checkEmail(String email) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer = this.nutzerMapper.findNutzerByEmail(email);
		
		if (nutzer == null){
			return null;
		} else {
			return nutzer;
		}	
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
			String eigenschaft) throws IllegalArgumentException {
		
		Eigenschaft eigenschaftNeu = createEigenschaft(eigenschaft);
		
		Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
		
		eigenschaftsauspraegung.setWert(wert);
		eigenschaftsauspraegung.setPersonID(personID);
		eigenschaftsauspraegung.setStatus(status);
		eigenschaftsauspraegung.setEigenschaftID(eigenschaftNeu.getId());
		
		Kontakt k = findKontaktByID(personID);
		
		saveKontakt(k);
		
		return this.eigenschaftsauspraegungMapper.createEigenschaftsauspraegung(eigenschaftsauspraegung);
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
		
		Eigenschaft eigenschaftNeu = new Eigenschaft();

		eigenschaftNeu.setBezeichnung(bezeichnung);

		Eigenschaft eigenschaft = findEigenschaftByBezeichnung(eigenschaftNeu);

		if (eigenschaft.getBezeichnung() == "") {
			return this.eigenschaftMapper.createEigenschaft(eigenschaftNeu);
		} else {
			return eigenschaft;
		}

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
		
		KontaktKontaktliste kliste = new KontaktKontaktliste();
		
		kliste.setKontaktID(kontaktID);
		kliste.setKontaktlisteID(kontaktlisteID);
		
		return this.kontaktKontaktlisteMapper.createKontaktKontaktliste(kliste);
	}

	/**
	 * Alle Kontakte einer Kontaktliste anzeigen lassen
	 * 
	 * @param k Objekt der Klasse Kontaktliste, hier wird die KontaktlisteID herausgenommen
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector <Kontakt> findAllKontakteByKontaktlisteID(Kontaktliste k) throws IllegalArgumentException {
		return this.kontaktMapper.findAllKontakteByKontaktlisteID(k.getId());
	}
	/**
	 * Kontakte mit der Kontakt ID anzeigen lassen
	 * 
	 * @param k; Objekt der Klasse Kontakt, hier wird die KontaktID herausgenommen
	 * @return Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	public Kontakt findKontaktByID(int kontaktID) throws IllegalArgumentException {
		return this.kontaktMapper.findKontaktByKontaktID(kontaktID);
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
		return this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungByPersonID(p.getId());
	}

	/**
	 * Alle Eigenschaften einer Person anzeigen lassen
	 * 
	 * @param e; Objekt der Klasse Eigenschaft, hier wird die EigenschaftID herausgenommen
	 * @return Vector des Typs Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaft> findAllEigenschaftByEigenschaftIDFromPerson(Eigenschaftsauspraegung e) throws IllegalArgumentException {
		//TODO
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
		//TODO
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
		return this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungenByEigenschaftID(e.getId());
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
		return this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungByWert(e.getWert());
	}
	
	/**
	 * Löschen aller Teilhaberschaften eines Nutzers
	 * 
	 * @param n : Objekt der Klasse Nutzer
	 * @throws IllegalArgumentException
	 */
	public void deleteAllTeilhaberschaftByOwner(Nutzer n) throws IllegalArgumentException {
		// Teilhaberschaft löschen
		Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
		teilhaberschaft.setEigentuemerID(n.getId());
		this.teilhaberschaftMapper.deleteTeilhaberschaftByNutzerID(teilhaberschaft);
	}
	
	/**
	 * Löschen aller Eigenschaftsauspraegungen einer Person
	 * 
	 * @param p - Objekt der Klasse Person
	 * @throws IllegalArgumentException
	 */
	public void deleteAllEigenschaftsauspraegungByNutzer(Nutzer n) throws IllegalArgumentException {
		
		deleteAllTeilhaberschaftByOwner(n);
		
		Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
		eigenschaftsauspraegung.setPersonID(n.getId());
		this.eigenschaftsauspraegungMapper.deleteEigenschaftsauspraegungByPersonID(eigenschaftsauspraegung);
	}
	
	/**
	 * Löschen aller KontaktKontaktliste Beziehungen in der Zwischentabelle.
	 * 
	 * @param nutzerID - Übergabeparameter des Fremdschlüssels in der Kontaktliste 
	 * @throws IllegalArgumentException
	 */
	public void deleteAllKontaktKontaktlisteByOwner(Nutzer n) throws IllegalArgumentException {
		Vector<Kontaktliste> vectorNutzer = findAllKontaktlisteByNutzerID(n.getId());
		for (Kontaktliste kontaktliste : vectorNutzer) {
			KontaktKontaktliste kliste = new KontaktKontaktliste();
			kliste.setKontaktlisteID(kontaktliste.getId());
			this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktlisteID(kliste);
		}
	}
	
	/**
	 * Löschen aller Kontakt eines Nutzers
	 * 
	 * @param n - Nutzer Objekt, der Besitzer eines Kontakts
	 * @throws IllegalArgumentException
	 */
	public void deleteAllKontaktByOwner(Nutzer n) throws IllegalArgumentException {
		
		deleteAllEigenschaftsauspraegungByNutzer(n);
		
		deleteAllKontaktKontaktlisteByOwner(n);
		
		Kontakt k = new Kontakt();
		k.setNutzerID(n.getId());
		this.kontaktMapper.deleteKontaktByNutzerID(k);
	}
	
	/**
	 * Löschen aller Kontaktlisten eines Nutzers
	 * 
	 * @param n - Nutzer Objekt, der Besitzer einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public void deleteAllKontaktlisteByOwner(Nutzer n) throws IllegalArgumentException {
		
		deleteAllKontaktByOwner(n);
		
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setNutzerID(n.getId());
		this.kontaktlisteMapper.deleteKontaktlisteByNutzerID(kontaktliste);

	}
	
	/**
	 * Löschen eines Nutzers
	 * 
	 * @param n - Nutzer Objekt, der Besitzer eines Nutzers
	 * @throws IllegalArgumentException
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {
		
		deleteAllKontaktlisteByOwner(n);
		
		Nutzer nutzer = new Nutzer();
		nutzer.setId(n.getId());
		this.nutzerMapper.deleteNutzer(nutzer);
	}
	
	/**
	 * Löschen eines Kontakts mit der NutzerID
	 * 
	 * @param k; Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktByOwner(Kontakt k) throws IllegalArgumentException {
	
		deleteEigenschaftsauspraegungByKontakt(k);
	
		deleteKontaktKontaktlisteByKontakt(k);
		
		this.kontaktMapper.deleteKontakt(k);	
	}
	
	@Override
	public void deleteTeilhaberschaftByKontakt(Kontakt k) throws IllegalArgumentException {
		Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
		teilhaberschaft.setKontaktID(k.getId());
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktID(teilhaberschaft);
	}
	
	
	@Override
	public void deleteEigenschaftsauspraegungByKontakt(Kontakt k) throws IllegalArgumentException {
		deleteTeilhaberschaftByKontakt(k);
		
		Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
		eigenschaftsauspraegung.setPersonID(k.getId());
		this.eigenschaftsauspraegungMapper.deleteEigenschaftsauspraegungByPersonID(eigenschaftsauspraegung);
	}
	
	@Override
	public void deleteKontaktKontaktlisteByKontakt(Kontakt k) throws IllegalArgumentException {
		KontaktKontaktliste kliste = new KontaktKontaktliste();
		kliste.setKontaktID(k.getId());
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktID(kliste);
	}

	/**
	 * Löschen der Beziehung zwischen KontaktKontaktliste
	 * 
	 * @param k; Objekt der Klasse KontaktKontaktliste
	 * @return Objekt des Typs KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByKontaktlisteID(KontaktKontaktliste k) throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktlisteID(k);		
	}

	/**
	 * Löschen einer Teilhaberschaft mit der PersonID
	 * 
	 * @param t; Objekt der Klasse Teilhaberschaft
	 * @return Objekt des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftByPersonID(Teilhaberschaft t) throws IllegalArgumentException {
		// TODO Auto-generated method stub		
	}
	
	/**
	 * Löschen einer Kontaktliste mit der NutzerID
	 * 
	 * @param k; Objekt der Klasse Kontaktliste
	 * @return Objekt des Typs Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktlisteByNutzerID(Kontaktliste k) throws IllegalArgumentException {
		
		KontaktKontaktliste kliste = new KontaktKontaktliste();
		
		kliste.setKontaktlisteID(k.getId());
		
		deleteKontaktKontaktlisteByKontaktlisteID(kliste);
		
		this.kontaktlisteMapper.deleteKontaktlisteByNutzerID(k);
	}
	
	/**
	 * Löschen einer KontaktKontaktliste mit der KontaktID
	 * 
	 * @param k; Objekt der Klasse KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByKontaktID(KontaktKontaktliste k) throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktID(k);
	}
	
	/**
	 * Löschen einer Eigenschaftsausprägung mit der PersonID
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsauspraegung
	 * @return Objekt des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung e)
			throws IllegalArgumentException {
		this.eigenschaftsauspraegungMapper.deleteEigenschaftsauspraegungByPersonID(e);
	}

	/**
	 * Anzeigen einer Eigenschaft anhand der Bezeichnung
	 * 
	 * @param e; Objekt der Klasse Eigenschaft
	 * @return Objekt der Klasse Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft findEigenschaftByBezeichnung(Eigenschaft e) throws IllegalArgumentException {
		return this.eigenschaftMapper.findEigenschaftByBezeichnung(e.getBezeichnung());
	}
	
	/**
	 * Anzeigen einer Eigenschaft anhand der ID
	 * 
	 * @param e; Objekt der Klasse Eigenschaft
	 * @return Objekt der Klasse Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft findEigenschaftByEigenschaftID(Eigenschaft e) throws IllegalArgumentException {
		return this.eigenschaftMapper.findEigenschaftByEigenschaftID(e.getId());
	}

	
	
	@Override
	public Vector <Kontakt>findAllKontakteByEigenschaftUndEigenschaftsauspraegungen(Eigenschaft e, Eigenschaftsauspraegung auspraegung)
			throws IllegalArgumentException {
		//TODO
		return null;
		
	}
	/**
	 * @param t - Übergabeparameter der ID von Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByID (Teilhaberschaft t) throws IllegalArgumentException{
		this.teilhaberschaftMapper.deleteTeilhaberschaftByID(t);
	}

	
	/**
	 * 
	 * @param eig - Übergabeparameter der Eigenschaft, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	public void saveEigenschaft(Eigenschaft eig) throws IllegalArgumentException {
			eigenschaftMapper.updateEigenschaft(eig);
	}
	/**
	 * 
	 * @param aus - Übergabeparameter der Eigenschaftsausprägung, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	
	public void saveEigenschaftsauspraegung(Eigenschaftsauspraegung aus) throws IllegalArgumentException{
			
			Kontakt k = findKontaktByID(aus.getPersonID());
		
			k.setModifikationsdatum(new Date());
			
			saveKontakt(k);
			
			eigenschaftsauspraegungMapper.updateEigenschaftsauspraegung(aus);
		}
	
	/**
	 * 
	 * @param k - Übergabeparameter von Kontakt, der geändert werden soll
	 * @throws IllegalArgumentException
	 */
	public void saveKontakt(Kontakt k) throws IllegalArgumentException{
			k.setModifikationsdatum(new Date());
			kontaktMapper.updateKontakt(k);
	}
	
	/**
	 * 
	 * @param kliste - Übergabeparameter von Kontaktliste, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	public void saveKontaktliste(Kontaktliste kliste) throws IllegalArgumentException{
			kontaktlisteMapper.updateKontaktliste(kliste);
	}
	
	/**
	 * 
	 * @param n - Übergabeparameter von Nutzer, der geändert werden soll
	 * @throws IllegalArgumentException
	 */
	public void saveNutzer(Nutzer n) throws IllegalArgumentException{
		nutzerMapper.updateNutzer(n);
	}
	
	/**
	 * 
	 * @param p - Übergabeparamater von Person, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	public void savePerson(Person p) throws IllegalArgumentException{
			personMapper.updatePerson(p);
	}
	
	/**
	 * 
	 * @param t - Übergabeparameter von Teilhaberschaft, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	public void saveTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException{
			teilhaberschaftMapper.updateTeilhaberschaft(t);
	}
}
