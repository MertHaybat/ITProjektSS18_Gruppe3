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
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.DisclosurePanelSuche.FindKontaktByNameCallback;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Person;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Diese Klasse ist die Implementierungsklasse des Interface
 * KontaktmanagerAdministration. Neben der ReportGeneratorImpl befindet sich
 * hier die Applikationslogik.
 * 
 * 
 * @see KontaktmanagerAdministration
 * @see KontaktmanagerAdministrationAsync
 * @see RemoteServiceServlet
 * @version 1.30 11 May 2018
 * @author Thomas, Mert
 */

@SuppressWarnings("serial")
public class KontaktmanagerAdministrationImpl extends RemoteServiceServlet implements KontaktmanagerAdministration {

	/**
	 * Referenz auf den KontaktlisteMapper, der Kontaktlisteobjekte mit der
	 * Datenbank abgleicht.
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
	 * Referenz auf den EigenschaftsauspraegungMapper, der
	 * Eigenschaftsauspraegungobjekte mit der Datenbank abgleicht.
	 */
	private EigenschaftsauspraegungMapper eigenschaftsauspraegungMapper = null;

	/**
	 * Referenz auf den EigenschaftMapper, der Eigenschaftobjekte mit der
	 * Datenbank abgleicht.
	 */
	private EigenschaftMapper eigenschaftMapper = null;

	/**
	 * Referenz auf den TeilhaberschaftMapper, der Teilhaberschaftobjekte mit
	 * der Datenbank abgleicht.
	 */
	private TeilhaberschaftMapper teilhaberschaftMapper = null;

	/**
	 * Referenz auf den KontaktKontaktlisteMapper, der
	 * KontaktKontaktlisteobjekte mit der Datenbank abgleicht.
	 */
	private KontaktKontaktlisteMapper kontaktKontaktlisteMapper = null;

	/**
	 * Ein No-Argument-Konstruktor für die Client-seitige Erzeugung von
	 * GWT.create
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public KontaktmanagerAdministrationImpl() throws IllegalArgumentException {

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
	 * Anlegen eines Nutzers
	 * 
	 * @param mail;
	 *            ist die Google E-Mail Adresse des Nutzers
	 * @return Nutzer; Zurückgegeben wird ein Objekt der Klasse Nutzer
	 */
	@Override
	public Nutzer createNutzer(String mail) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setMail(mail);
		return this.nutzerMapper.createNutzer(nutzer);
	}

	/**
	 * Anlegen eines Kontakts
	 * 
	 * @param name;
	 *            ist der Name des Kontakts
	 * @param erzeugungsdatum;
	 *            zeigt den Zeitpunkt der Erstellung des Kontakts an
	 * @param modifikationsdatum;
	 *            zeigt an, wann der Kontakt zuletzt modifiziert wurde
	 * @param status;
	 *            status sagt aus, ob der Kontakt geteilt oder nicht geteilt
	 *            wurde
	 * @param nutzerID;
	 *            Übergabeparameter des Fremdschlüssels für den Kontakt
	 * @return Kontakt; Zurückgegeben wird ein Objekt der Klasse Kontakt
	 */
	@Override
	public Kontakt createKontakt(String name, int status, int nutzerID) throws IllegalArgumentException {

		Kontakt kontakt = new Kontakt();
		kontakt.setName(name);
		kontakt.setErzeugungsdatum(new Date());
		kontakt.setModifikationsdatum(new Date());
		kontakt.setStatus(status);
		kontakt.setNutzerID(nutzerID);

		return this.kontaktMapper.createKontakt(kontakt);
	}

	/**
	 * Anlegen einer Kontaktliste
	 * 
	 * @param bezeichnung;
	 *            bezeichnung sagt aus, wie die Kontaktliste benannt wurde
	 * @param nutzerID;
	 *				Übergabeparameter des Fremdschlüssels in der Kontaktliste
	 * @param status: 
	 * 				Übergibt den Status der Kontaktliste, ob geteilt oder nicht 	 
	 * @return Kontaktliste; Zurückgegeben wird ein Objekt der Klasse
	 *         Kontaktliste
	 */
	@Override
	public Kontaktliste createKontaktliste(String bezeichnung, int nutzerID, int status) throws IllegalArgumentException {
		
		Kontaktliste kliste = findKontaktlistByName(bezeichnung, nutzerID);
		
		if (kliste.getId() == 0){
			Kontaktliste kontaktliste = new Kontaktliste();
			
			kontaktliste.setBezeichnung(bezeichnung);
			kontaktliste.setNutzerID(nutzerID);
			kontaktliste.setStatus(status);
			
			return this.kontaktlisteMapper.createKontaktliste(kontaktliste);
			
		}
		return null;
	}

	/**
	 * Alle Kontaktlisten eines Nutzers anzeigen lassen
	 * 
	 * @param nutzerID;
	 *            Primärschlüssel des Nutzers, mithilfe dessen die verbundenen
	 *            Kontaktlisten des Nutzers angezeigt gesucht werden können.
	 * @return Vector des Typs Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontaktliste> findAllKontaktlisteByNutzerID(int nutzerID) throws IllegalArgumentException {
		return this.kontaktlisteMapper.findAllKontaktlisteByNutzerID(nutzerID);
	}

	/**
	 * Anlegen einer Teilhaberschaft
	 * 
	 * @param kontaktlisteID;
	 *            definiert Kontaktliste, die der Teilhaberschaft angehört
	 * @param kontaktID;
	 *            definiert den Kontakt, der der Teilhaberschaft angehört
	 * @param eigenschaftsauspraegungID;
	 *            definiert die Eigenschaftsausprägung, die geteilt wird
	 * @param teilhabenderID;
	 *            definiert den Teilhabenden an der Teilhaberschaft
	 * @param eigentuemerID;
	 *            definiert den Eigentümer der Teilhaberschaft
	 * @return Teilhaberschaftsobjekt; Zurückgegeben wird ein Objekt der Klasse
	 *         Teilhaberschaft
	 * @throws IllegalArgumentException
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
		if(teilhabenderID == eigentuemerID){
			return teilhaberschaft;
		}

		if (kontaktID != 0) {
			Kontakt k = findKontaktByID(kontaktID);
			k.setStatus(1);
			saveKontakt(k);
		}

		if (eigenschaftsauspraegungID != 0) {
			Eigenschaftsauspraegung e = findEigenschaftsauspraegungById(eigenschaftsauspraegungID);
			e.setStatus(1);
			saveEigenschaftsauspraegung(e);
		}
		if (kontaktlisteID != 0) {
			Kontaktliste k = findKontaktlisteByID(kontaktlisteID);
			k.setStatus(1);
			saveKontaktliste(k);
			return this.teilhaberschaftMapper.createTeilhaberschaft(teilhaberschaft);
		}

		if (eigenschaftsauspraegungID == 0 && kontaktID != 0) {
			Vector<Teilhaberschaft> vectorTeilhaberschaft = this.teilhaberschaftMapper
					.findTeilhaberschaftByKontaktAndAuspraegung(teilhaberschaft);
			for (Teilhaberschaft teilhaberschaft2 : vectorTeilhaberschaft) {
				if (teilhaberschaft2.getEigenschaftsauspraegungID() != 0) {
					deleteTeilhaberschaftById(teilhaberschaft2);

				}

			}

		}
		if (eigenschaftsauspraegungID != 0 & kontaktID != 0){
			Vector<Teilhaberschaft> t = this.teilhaberschaftMapper.findTeilhaberschaftByKontaktAndTeilhaber(kontaktID, teilhabenderID);
			if(t.size()!=0){
				return null;
			}
		}
		Vector<Teilhaberschaft> teilhaberschaften = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		if(teilhaberschaften.size() == 0){
			this.teilhaberschaftMapper.createTeilhaberschaft(teilhaberschaft);
		} else {
			for (Teilhaberschaft teilhaberschaft2 : teilhaberschaften) {
				if (teilhaberschaft2.getKontaktlisteID() == kontaktlisteID
						&& teilhaberschaft2.getEigenschaftsauspraegungID() == eigenschaftsauspraegungID
						&& teilhaberschaft2.getKontaktID() == kontaktID){
					return null;
				}
				
			}
		}
//		for (Teilhaberschaft teilhaberschaft2 : teilhaberschaften) {
//			if (teilhaberschaft2.getKontaktlisteID() != kontaktlisteID
//					&& teilhaberschaft2.getEigenschaftsauspraegungID() != eigenschaftsauspraegungID
//					&& teilhaberschaft2.getKontaktID() != kontaktID) {
//				return this.teilhaberschaftMapper.createTeilhaberschaft(teilhaberschaft);
//
//			} else {
//				return null;
//			}
//
//		}
		return this.teilhaberschaftMapper.createTeilhaberschaft(teilhaberschaft);
	}

	/**
	 * Auslesen von Teilhaberschaften eines Eigentümers
	 * 
	 * @param eigentuemerID;
	 *            eigentuemerID definiert den Nutzer, der die Teilhaberschaft
	 *            erstellt hat
	 * @return Vector des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByEigentuemerID(int eigentuemerID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByEigentuemerID(eigentuemerID);
	}

	/**
	 * Auslesen aller Eigenschaften
	 * 
	 * @return Vector des Typs Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException {
		return this.eigenschaftMapper.findAllEigenschaften();
	}

	/**
	 * Auslesen aller Kontakte
	 * 
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontakte() throws IllegalArgumentException {
		return this.kontaktMapper.findAllKontakt();
	}

	/**
	 * Überprüfen eines Nutzers nach der E-Mail Adresse
	 * 
	 * @param email;
	 *            die GoogleMail Adresse des Nutzers
	 * @return nutzer; Objekt der Klasse Nutzer wird zurückgegeben
	 */
	@Override
	public Nutzer checkEmail(String email) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer = this.nutzerMapper.findNutzerByEmail(email);

		if (nutzer.getId() == 0) {
			return null;
		} else {
			return nutzer;
		}
	}

	/**
	 * Erstellen einer Eigenschaftsausprägung
	 * 
	 * @param wert;
	 *            wert ist die Ausprägung einer Eigenschaft
	 * @param personID;
	 *            personID definiert Nutzer/Kontakt dem die
	 *            Eigenschaftsausprägung gehört
	 * @param status;
	 *            status sagt aus, ob diese Eigenschaftsausprägung geteilt wurde
	 *            oder nicht geteilt wurde
	 * @param eigenschaftID;
	 *            ist die dazu gehörige Eigenschaft
	 * @return Eigenschaftsauspraegung; Zurückgegeben wird ein Objekt der Klasse
	 *         Eigenschaftsauspraegung
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
	 * @param bezeichnung;
	 *            bezeichnung sagt aus was die Eigenschaft ist
	 * @return Eigenschaft; ein Eigenschaftsobjekt wird zurückgegeben
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft createEigenschaft(String bezeichnung) throws IllegalArgumentException {

		Eigenschaft eigenschaftNeu = new Eigenschaft();

		eigenschaftNeu.setBezeichnung(bezeichnung);

		Eigenschaft eigenschaft = findEigenschaftByBezeichnung(bezeichnung);

		if (eigenschaft.getBezeichnung() == "") {
			return this.eigenschaftMapper.createEigenschaft(eigenschaftNeu);
		} else {
			return eigenschaft;
		}

	}

	/**
	 * Erstellen einer KontaktKontaktliste
	 * 
	 * @param kontaktID;
	 *            ist die KontaktID die ausgewählt wurde
	 * @param kontaktlisteID;
	 *            ist die KontaktlisteID die ausgewählt wurde
	 * @return KontaktKontaktliste Objekt
	 * @throws IllegalArgumentException
	 */
	@Override
	public KontaktKontaktliste createKontaktKontaktliste(int kontaktID, int kontaktlisteID)
			throws IllegalArgumentException {
		int kontaktCount = 0;
		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setId(kontaktlisteID);

		Vector<Kontakt> kontakteVector = findAllKontakteByKontaktlisteID(kontaktliste);

		for (Kontakt kontakt : kontakteVector) {
			if (kontaktID == kontakt.getId()) {
				kontaktCount++;
			}

		}
		if (kontaktCount == 0) {
			KontaktKontaktliste kliste = new KontaktKontaktliste();
			kliste.setKontaktlisteID(kontaktlisteID);
			kliste.setKontaktID(kontaktID);
			return this.kontaktKontaktlisteMapper.createKontaktKontaktliste(kliste);
		} else {
			return null;
		}

	}

	/**
	 * Alle Kontakte einer Kontaktliste anzeigen lassen
	 * 
	 * @param k
	 *            Objekt der Klasse Kontaktliste, hier wird die KontaktlisteID
	 *            herausgenommen
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontakteByKontaktlisteID(Kontaktliste k) throws IllegalArgumentException {
		return this.kontaktMapper.findAllKontakteByKontaktlisteID(k.getId());
	}

	/**
	 * Kontakte mit der Kontakt ID anzeigen lassen
	 * 
	 * @param k;
	 *            Objekt der Klasse Kontakt, hier wird die KontaktID
	 *            herausgenommen
	 * @return Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Kontakt findKontaktByID(int kontaktID) throws IllegalArgumentException {
		return this.kontaktMapper.findKontaktByKontaktID(kontaktID);
	}

	/**
	 * Alle Eigenschaftsausprägung einer Person (Nutzer/Kontakt) anzeigen lassen
	 * 
	 * @param p;
	 *            Objekt der Klasse Person, hier wird die PersonID
	 *            herausgenommen
	 * @return Vector des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByPersonID(Person p)
			throws IllegalArgumentException {
		return this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungByPersonID(p.getId());
	}

	/**
	 * Alle Eigenschaften einer Person anzeigen lassen
	 * 
	 * @param e;
	 *            Objekt der Klasse Eigenschaft, hier wird die EigenschaftID
	 *            herausgenommen
	 * @return Vector des Typs Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft findEigenschaftByEigenschaftIDFromPerson(Eigenschaftsauspraegung e)
			throws IllegalArgumentException {

		Eigenschaft eigenschaft = findEigenschaftByEigenschaftID(e.getEigenschaftID());

		return eigenschaft;
	}

	/**
	 * Alle Kontakte von einer Eigenschaftsausprägung anzeigen lassen. Dies ist
	 * die Suchfunktion in einer einzelnen Kontaktliste.
	 * 
	 * @param e;
	 *            Objekt der Klasse Eigenschaftsausprägung
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontaktByEigenschaftsauspraegung(Eigenschaftsauspraegung e)
			throws IllegalArgumentException {
		// TODO
		return null;
	}

	/**
	 * Alle Eigenschaftsausprägungen einer Eigenschaft anzeigen lassen
	 * 
	 * @param e;
	 *            Objekt der Klasse Eigenschaft
	 * @return Vector des Typs Eigenschaftsausprägung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByEigenschaftID(Eigenschaft e)
			throws IllegalArgumentException {
		return this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungenByEigenschaftID(e.getId());
	}

	/**
	 * Alle Eigenschaftsausprägung durch dessen Wert anzeigen lassen. Diese
	 * Methode ist Teil der Suchfunktion
	 * 
	 * @param e;
	 *            Objekt der Klasse Eigenschaftsausprägung
	 * @return Vector des Typs Eigenschaftsausprägung
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByWertAndEigenschaft(Eigenschaftsauspraegung e,
			Eigenschaft eigenschaft) throws IllegalArgumentException {
		Vector<Eigenschaftsauspraegung> auspraegungen = new Vector<Eigenschaftsauspraegung>();

		if(e.getWert().equals("")){
			auspraegungen = findAllEigenschaftsauspraegungByEigenschaftID(eigenschaft);
			
		} else {
		
		char a = e.getWert().charAt(0);
		String b = "*";
		char c = b.charAt(0);
		char d = e.getWert().charAt(e.getWert().length() - 1);

		if (eigenschaft != null){
			
			e.setEigenschaftID(eigenschaft.getId());
		}

		if (a == c) {
			e.setWert(e.getWert().replace("*", "%"));
			auspraegungen = this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungByWert(e);
		} else if (c == d) {
			e.setWert(e.getWert().replace("*", "%"));
			auspraegungen = this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungByWert(e);
		} else {
			auspraegungen = this.eigenschaftsauspraegungMapper.findAllEigenschaftsauspraegungByWert(e);
		}
		}
		return auspraegungen;
	}

	/**
	 * Löschen aller Teilhaberschaften eines Nutzers
	 * 
	 * @param n
	 *            : Objekt der Klasse Nutzer
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteAllTeilhaberschaftByOwner(Nutzer n) throws IllegalArgumentException {
		// Teilhaberschaft löschen
		Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
		teilhaberschaft.setEigentuemerID(n.getId());
		this.teilhaberschaftMapper.deleteTeilhaberschaftByNutzerID(teilhaberschaft);
	}

	/**
	 * Löschen aller Eigenschaftsauspraegungen einer Person
	 * 
	 * @param p
	 *            - Objekt der Klasse Person
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteAllEigenschaftsauspraegungByNutzer(Nutzer n) throws IllegalArgumentException {

		deleteAllTeilhaberschaftByOwner(n);

		Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
		eigenschaftsauspraegung.setPersonID(n.getId());
		this.eigenschaftsauspraegungMapper.deleteEigenschaftsauspraegungByPersonID(eigenschaftsauspraegung);
	}

	/**
	 * Löschen aller KontaktKontaktliste Beziehungen in der Zwischentabelle.
	 * 
	 * @param nutzerID
	 *            - Übergabeparameter des Fremdschlüssels in der Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
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
	 * @param n
	 *            - Nutzer Objekt, der Besitzer eines Kontakts
	 * @throws IllegalArgumentException
	 */
	@Override
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
	 * @param n
	 *            - Nutzer Objekt, der Besitzer einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteAllKontaktlisteByOwner(Nutzer n) throws IllegalArgumentException {

		deleteAllKontaktByOwner(n);

		Kontaktliste kontaktliste = new Kontaktliste();
		kontaktliste.setNutzerID(n.getId());
		this.kontaktlisteMapper.deleteKontaktlisteByNutzerID(kontaktliste);

	}

	/**
	 * Löschen eines Nutzers
	 * 
	 * @param n
	 *            - Nutzer Objekt, der Besitzer eines Nutzers
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException {

		deleteAllKontaktlisteByOwner(n);

		Nutzer nutzer = new Nutzer();
		nutzer.setId(n.getId());
		this.nutzerMapper.deleteNutzer(nutzer);
	}

	/**
	 * Löschen eines Kontakts mit der NutzerID
	 * 
	 * @param k;
	 *            Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktByOwner(Kontakt k) throws IllegalArgumentException {

		deleteEigenschaftsauspraegungByKontakt(k);

		deleteKontaktKontaktlisteByKontakt(k);

		this.kontaktMapper.deleteKontakt(k);
	}

	/**
	 * Löschen einer Teilhaberschaft über den Kontakt
	 * @param k;
	 * 			Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftByKontakt(Kontakt k) throws IllegalArgumentException {
		Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
		teilhaberschaft.setKontaktID(k.getId());
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktID(teilhaberschaft);
	}
	
	/**
	 * Löschen einer Eigenschaftsausprägung über den Kontakt
	 * @param k;
	 * 			Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteEigenschaftsauspraegungByKontakt(Kontakt k) throws IllegalArgumentException {
		deleteTeilhaberschaftByKontakt(k);

		Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
		eigenschaftsauspraegung.setPersonID(k.getId());
		this.eigenschaftsauspraegungMapper.deleteEigenschaftsauspraegungByPersonID(eigenschaftsauspraegung);
	}

	/**
	 * Löschen einer KontaktKontaktliste über den Kontakt
	 * @param k;
	 * 			Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByKontakt(Kontakt k) throws IllegalArgumentException {
		KontaktKontaktliste kliste = new KontaktKontaktliste();
		kliste.setKontaktID(k.getId());
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktID(kliste);
	}

	/**
	 * Löschen der Beziehung zwischen KontaktKontaktliste
	 * 
	 * @param k;
	 *            Objekt der Klasse KontaktKontaktliste
	 * @return Objekt des Typs KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByKontaktlisteID(KontaktKontaktliste k) throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktlisteID(k);
	}

	/**
	 * Auslesen von Teilhabern in Teilhaberschaften
	 * 
	 * @param t;
	 *            Objekt der Klasse Teilhaberschaft
	 * @return Objekt des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Teilhaberschaft> findAllTeilhaberschaftenByTeilhabenderID(int teilhabenderID)
			throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByTeilhabenderID(teilhabenderID);
	}

	/**
	 * Löschen einer Kontaktliste mit der NutzerID
	 * 
	 * @param k;
	 *            Objekt der Klasse Kontaktliste
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
	 * @param k;
	 *            Objekt der Klasse KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByKontaktID(KontaktKontaktliste k) throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktID(k);
	}

	/**
	 * Löschen einer Eigenschaftsausprägung mit der PersonID
	 * 
	 * @param e;
	 *            Objekt der Klasse Eigenschaftsauspraegung
	 * @return Objekt des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung e) throws IllegalArgumentException {
		this.eigenschaftsauspraegungMapper.deleteEigenschaftsauspraegungByPersonID(e);
	}

	/**
	 * Anzeigen einer Eigenschaft anhand der Bezeichnung
	 * 
	 * @param e;
	 * @return Objekt der Klasse Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft findEigenschaftByBezeichnung(String e) throws IllegalArgumentException {
		return this.eigenschaftMapper.findEigenschaftByBezeichnung(e);
	}

	/**
	 * Anzeigen einer Eigenschaft anhand der ID
	 * 
	 * @param e;
	 * @return Objekt der Klasse Eigenschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaft findEigenschaftByEigenschaftID(int eigenschaftID) throws IllegalArgumentException {
		return this.eigenschaftMapper.findEigenschaftByEigenschaftID(eigenschaftID);
	}

	/**
	 * Auslesen aller Kontakte mit bestimmten Eigenschaften und
	 * Eigenschaftsauspraegungen
	 * 
	 * @param e;
	 *            Objekt der Klasse Eigenschaft
	 * @param auspraegung;
	 *            Objekt der Klasse Eigenschaftsauspraegung
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontakteByEigenschaftUndEigenschaftsauspraegungen(Eigenschaft e,
			Eigenschaftsauspraegung auspraegung) throws IllegalArgumentException {
		Vector<Kontakt> allContact = new Vector<Kontakt>();
		Vector<Eigenschaftsauspraegung> auspraegungen = new Vector<Eigenschaftsauspraegung>();

		if (auspraegung.getWert().equals("")) {

			auspraegungen = findAllEigenschaftsauspraegungByEigenschaftID(e);
			
		} else {

			auspraegungen = findAllEigenschaftsauspraegungByWertAndEigenschaft(auspraegung, e);

		}

		for (Eigenschaftsauspraegung eigenschaftsauspraegung : auspraegungen) {
			allContact.add(findKontaktByID(eigenschaftsauspraegung.getPersonID()));
		}

		return allContact;

	}

	/**
	 * Löschen der Teilhaberschaft über die ID
	 * @param t
	 *            - Übergabeparameter der ID von Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftByID(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaftByID(t);
	}

	/**
	 * Methode zur Bearbeitung einer Eigenschaft
	 * @param eig
	 *            - Übergabeparameter der Eigenschaft, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void saveEigenschaft(Eigenschaft eig) throws IllegalArgumentException {
		eigenschaftMapper.updateEigenschaft(eig);
	}

	/**
	 * Methode zur Bearbeitung einer Eigenschaftsausprägung
	 * @param aus
	 *            - Übergabeparameter der Eigenschaftsausprägung, die geändert
	 *            werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void saveEigenschaftsauspraegung(Eigenschaftsauspraegung aus) throws IllegalArgumentException {

		Kontakt k = findKontaktByID(aus.getPersonID());

		k.setModifikationsdatum(new Date());
		saveKontakt(k);

		eigenschaftsauspraegungMapper.updateEigenschaftsauspraegung(aus);
	}

	/**
	 * Methode zur Bearbeitung eines Kontaktes
	 * @param k
	 *            - Übergabeparameter von Kontakt, der geändert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void saveKontakt(Kontakt k) throws IllegalArgumentException {
		k.setModifikationsdatum(new Date());
		kontaktMapper.updateKontakt(k);
	}

	/**
	 * Methode zur Bearbeitung einer Kontaktliste 
	 * @param kliste
	 *            - Übergabeparameter von Kontaktliste, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	public void saveKontaktliste(Kontaktliste kliste) throws IllegalArgumentException {
		kontaktlisteMapper.updateKontaktliste(kliste);
	}

	/**
	 * Methode zur Bearbeitung eines Nutzers 
	 * @param n
	 *            - Übergabeparameter von Nutzer, der geändert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void saveNutzer(Nutzer n) throws IllegalArgumentException {
		nutzerMapper.updateNutzer(n);
	}

	/**
	 * Methode zur Bearbeitung einer Person 
	 * @param p
	 *            - Übergabeparamater von Person, die geändert werden soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void savePerson(Person p) throws IllegalArgumentException {
		personMapper.updatePerson(p);
	}

	/**
	 * Methode zur Bearbeitung einer Teilhaberschaft
	 * @param t
	 *            - Übergabeparameter von Teilhaberschaft, die geändert werden
	 *            soll
	 * @throws IllegalArgumentException
	 */
	@Override
	public void saveTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException {
		teilhaberschaftMapper.updateTeilhaberschaft(t);
	}

	/**
	 * Wurde noch nicht getestet.. Kommentare folgen!
	 *
	 */
	@Override
	public Vector<Kontakt> suchFunktion(Nutzer nutzer, Eigenschaft eigenschaft, Eigenschaftsauspraegung auspraegung)
			throws IllegalArgumentException {
		// TODO
		// Suchfunktion für Eigenschaftsauspraegung in allen Kontakten

		// Nach dem Wert in der Tabelle Eigenschaftsauspraegung schauen, Wird
		// gefiltert nach Wert und EigenschaftID
		Vector<Eigenschaftsauspraegung> auspraegungen = findAllEigenschaftsauspraegungByWertAndEigenschaft(auspraegung,
				eigenschaft);

		// In Teilhaberschaft nach den geteilten Auspraegungen suchen. Wenn es
		// nicht geteilte Ausprägungen gibt, nicht anzeigen.

		Vector<Teilhaberschaft> teilhaberschaftenKontakte = findAllTeilhaberschaftenByTeilhabenderID(nutzer.getId());
		Vector<Kontakt> teilhabendeKontakte = new Vector<Kontakt>();
		for (int i = 0; i < teilhaberschaftenKontakte.size(); i++) {
			teilhabendeKontakte.add(findKontaktByID(teilhaberschaftenKontakte.elementAt(i).getKontaktID()));
		}

		// In Teilhaberschaft nach den geteilten Kontaktlisten suchen.
		Kontaktliste k = new Kontaktliste();
		Vector<Kontakt> teilhabendeKontaktlistenKontakte = new Vector<Kontakt>();
		Vector<Kontakt> allKontaktlisteKontakte = new Vector<Kontakt>();
		for (int o = 0; o < teilhaberschaftenKontakte.size(); o++) {
			k.setId(teilhaberschaftenKontakte.elementAt(o).getKontaktlisteID());
			allKontaktlisteKontakte = findAllKontakteByKontaktlisteID(k);
			teilhabendeKontaktlistenKontakte.addAll(allKontaktlisteKontakte);
		}

		// In den Kontakten nach der NutzerID filtern (alle Kontakte von mir)
		Vector<Kontakt> alleKontakte = findAllKontaktByNutzerID(nutzer.getId());
		Vector<Kontakt> alleKontakteVonTeilnahmen = findAllKontakteByTeilhabenderID(nutzer.getId());
		Vector<Kontakt> allContacts = new Vector<Kontakt>();

		allContacts.addAll(alleKontakteVonTeilnahmen);
		allContacts.addAll(alleKontakte);
		allContacts.addAll(teilhabendeKontakte);
		allContacts.addAll(teilhabendeKontaktlistenKontakte);

		Vector<Kontakt> allFilteredContacts = new Vector<Kontakt>();

		for (int p = 0; p < auspraegungen.size(); p++) {
			for (int x = 0; x < allContacts.size(); x++) {

				int personID = auspraegungen.elementAt(p).getPersonID();

				if (personID == allContacts.elementAt(x).getId()) {
					allFilteredContacts.add(allContacts.elementAt(x));
				}
			}
		}

		return allFilteredContacts;
	}

	/**
	 * Methode um alle Kontakte eines Nutzers zurückgeben
	 * @param: nutzerID
	 * 			Übergabeparameter der ID des Nutzers
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontaktByNutzerID(int nutzerID) throws IllegalArgumentException {
		return this.kontaktMapper.findAllKontaktByNutzerID(nutzerID);
	}

	/**
	 * Methode um alle Kontakte aus einer Teilhebrschaft zurückgeben
	 * @param: teilhabenderID
	 * 			Übergabeparameter der ID des Teilhabenden
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findAllKontakteByTeilhabenderID(int teilhabenderID) throws IllegalArgumentException {
		// TODO
		Vector<Teilhaberschaft> teilhaberschaftVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Kontakt> kontakteVector = new Vector<Kontakt>();
		Vector<Teilhaberschaft> filteredTeilhaberschaftenVector = new Vector<Teilhaberschaft>();
		
		for (Teilhaberschaft teilhaberschaft : teilhaberschaftVector) {
			if(teilhaberschaft.getKontaktID() != 0 && teilhaberschaft.getEigenschaftsauspraegungID() == 0){
				filteredTeilhaberschaftenVector.add(teilhaberschaft);
			}
		}
		

		for (Teilhaberschaft teilhaberschaft : filteredTeilhaberschaftenVector) {
			kontakteVector.add(findKontaktByID(teilhaberschaft.getKontaktID()));

		}

		return kontakteVector;
	}

	/**
	 * Methode zum Löschen einer Teilhaberschaft über den Primärschlüssel id
	 * 
	 * @param t
	 *            - Übergabeparameter von Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftById(Teilhaberschaft t) throws IllegalArgumentException {

		Teilhaberschaft teil = new Teilhaberschaft();
		teil.setId(t.getId());
		this.teilhaberschaftMapper.deleteTeilhaberschaftByID(teil);

	}

	/**
	 * Methode zum Löschen einer Teilhaberschaft über den Fremdschlüsse
	 * eigenschaftsauspraegungid
	 * 
	 * @param t
	 *            - Übergabeparameter von Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteTeilhaberschaftByEigenschaftsauspraegungID(Teilhaberschaft t) throws IllegalArgumentException {
		this.teilhaberschaftMapper.deleteTeilhaberschaftByEigenschaftsauspraegungID(t);
	}

	/**
	 * Methode zum Löschen einer Eigenschaftauspraegung über den Primärschlüssel
	 * id
	 * 
	 * @param e
	 *            - Übergabeparameter von Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteEigenschaftsauspraegungById(Eigenschaftsauspraegung e) throws IllegalArgumentException {
		Teilhaberschaft t = new Teilhaberschaft();
		t.setEigenschaftsauspraegungID(e.getId());

		deleteTeilhaberschaftByEigenschaftsauspraegungID(t);

		this.eigenschaftsauspraegungMapper.deleteEigenschaftsauspraegung(e);
	}

	
	@Override
	public Vector<Kontakt> findAllKontaktByTeilhaberschaften(int teilhabenderID, int eigentuemerID)
			throws IllegalArgumentException {

		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		for(int i = 0; i<teilhabenderVector.size(); i++){
			if(teilhabenderVector.elementAt(i).getKontaktID() == 0){
				teilhabenderVector.removeElementAt(i);
			}
		}
		Vector<Teilhaberschaft> eigentuemerVector = findTeilhaberschaftByEigentuemerID(eigentuemerID);
		for(int o = 0; o<eigentuemerVector.size(); o++){
			if(eigentuemerVector.elementAt(o).getKontaktID() == 0){
				eigentuemerVector.removeElementAt(o);
			}
		}
		Vector<Teilhaberschaft> filteredTeilhaberschaften = new Vector<Teilhaberschaft>();

		for (int i = 0; i < teilhabenderVector.size(); i++) {
			for (int x = 0; x < teilhabenderVector.size(); x++) {
				if (teilhabenderVector.elementAt(i).getId() == eigentuemerVector.elementAt(x).getId()) {
					filteredTeilhaberschaften.add(teilhabenderVector.elementAt(i));
				}
			}
		}

		Vector<Kontakt> kontakte = new Vector<Kontakt>();

		for (Teilhaberschaft teilhaberschaft : filteredTeilhaberschaften) {
			kontakte.add(findKontaktByID(teilhaberschaft.getKontaktID()));
		}
	
		
		return kontakte;
	}

	/**
	 * Methode um alle Nutzer über den Primärschlüssel zurückgeben
	 * @param: nutzerID
	 * 			Übergabeparameter der ID des Nutzers
	 * @return Ein Objekt der Klasse Nutzer wird zurükgegeben 
	 * @throws IllegalArgumentException
	 */
	@Override
	public Nutzer findNutzerByID(int nutzerID) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByID(nutzerID);
	}

	/**
	 * Methode zum Löschen einer Kontaktliste über den Primärschlüssel id
	 * 
	 * @param k
	 *            - Übergabeparameter von Kontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktlisteByID(Kontaktliste k) throws IllegalArgumentException {
		KontaktKontaktliste kontaktliste = new KontaktKontaktliste();
		Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
		kontaktliste.setKontaktlisteID(k.getId());
		teilhaberschaft.setKontaktlisteID(k.getId());
		deleteKontaktKontaktlisteByKontaktlisteID(kontaktliste);
		deleteTeilhaberschaftByKontaktlisteID(teilhaberschaft);
		this.kontaktlisteMapper.deleteKontaktliste(k);
	}

	/**
	 * Methode zum Löschen einer KontaktKontaktliste über dem Primärschlüssel id
	 * 
	 * @param kk
	 *            - Übergabeparamter von KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktlisteByID(KontaktKontaktliste kk) throws IllegalArgumentException {
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktlisteID(kk);
	}

	/**
	 * Methode um alle Nutzer zurückzugeben
	 * @return Vector des Typs Nutzer
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException {
		return this.nutzerMapper.findAllNutzer();
	}

	@Override
	public Vector<EigenschaftsAuspraegungWrapper> findEigenschaftHybrid(Person person) throws IllegalArgumentException {
		Vector<Eigenschaftsauspraegung> auspraegung = findAllEigenschaftsauspraegungByPersonID(person);

		Vector<Eigenschaft> eigenschaft = new Vector<Eigenschaft>();

		for (Eigenschaftsauspraegung eigenschaftsauspraegung : auspraegung) {
			eigenschaft.add(findEigenschaftByEigenschaftID(eigenschaftsauspraegung.getEigenschaftID()));
		}

		Vector<EigenschaftsAuspraegungWrapper> eigenschaftAuspraegung = new Vector<EigenschaftsAuspraegungWrapper>();

		Vector<Eigenschaftsauspraegung> auspraegungVector = new Vector<Eigenschaftsauspraegung>();

		if (auspraegung.isEmpty()) {
			createEigenschaftsauspraegung(" ", person.getId(), 0, "Vorname");
			createEigenschaftsauspraegung(" ", person.getId(), 0, "Nachname");
			createEigenschaftsauspraegung(" ", person.getId(), 0, "Geburtsdatum");
			createEigenschaftsauspraegung(" ", person.getId(), 0, "Telefonnummer");
			createEigenschaftsauspraegung(" ", person.getId(), 0, "E-Mail");
			auspraegungVector = findAllEigenschaftsauspraegungByPersonID(person);
			for (int t = 1; t < 6; t++) {
				eigenschaft.add(findEigenschaftByEigenschaftID(t));
			}
			for (int p = 0; p < eigenschaft.size(); p++) {
				for (int z = 0; z < eigenschaft.size(); z++) {
					if (eigenschaft.elementAt(p).getId() == auspraegungVector.elementAt(z).getEigenschaftID()) {
						eigenschaftAuspraegung.add(new EigenschaftsAuspraegungWrapper(eigenschaft.elementAt(p),
								auspraegungVector.elementAt(z)));
					}

				}

			}
		}
		for (Eigenschaftsauspraegung eigenschaftsauspraegung : auspraegung) {
			for (Eigenschaft eigenschaftvergleich : eigenschaft) {
				if (eigenschaftsauspraegung.getEigenschaftID() == eigenschaftvergleich.getId()) {
					eigenschaftAuspraegung
							.add(new EigenschaftsAuspraegungWrapper(eigenschaftvergleich, eigenschaftsauspraegung));
					break;
				}
			}
		}

		return eigenschaftAuspraegung;
	}

	/**
	 * Methode um einen Kontakt über seine ID zu löschen
	 * @param: k
	 * 			Übergabeparameter der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktByID(Kontakt k) throws IllegalArgumentException {
		deleteKontaktKontaktlisteByKontakt(k);
		deleteEigenschaftsauspraegungByKontakt(k);
		deleteTeilhaberschaftByKontakt(k);
		this.kontaktMapper.deleteKontakt(k);
	}

	/**
	 * Methode um alle Nutzer über die Mail zurückgeben
	 * @param: mail
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Nutzer> findAllNutzerByEmail(String mail) throws IllegalArgumentException {

		return null;
	}
	
	/**
	 * Methode um KontaktKontaktliste zu löschen
	 * @param: kon
	 * 			Übergabeparameter der Klasse KontaktKontaktliste 
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteKontaktKontaktliste(KontaktKontaktliste kon) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		this.kontaktKontaktlisteMapper.deleteKontaktKontaktlisteByKontaktKontaktliste(kon);
	}

	/**
	 * Methode um alle Eigenschaften eines Kontaktes zurückzugeben
	 * @param: k
	 * 			Übergabeparameter der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Eigenschaft> findAllEigenschaftByKontakt(Kontakt k) throws IllegalArgumentException {

		return null;
	}

	/**
	 * Methode um eine Teilhaberschaft einer Kontaktliste zu löschen 
	 * @param: t
	 * 			Übergabeparameter der Klasse Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByKontaktlisteID(Teilhaberschaft t) throws IllegalArgumentException {
		
		this.teilhaberschaftMapper.deleteTeilhaberschaftByKontaktlisteID(t);

	}

	@Override
	public Vector<Kontaktliste> findAllKontaktlisteByTeilhaberschaft(int teilhabenderID)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Vector<Teilhaberschaft> teilhaberschaftVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Kontaktliste> kontaktlisteVector = new Vector<Kontaktliste>();

		for (Teilhaberschaft teilhaberschaft : teilhaberschaftVector) {
			kontaktlisteVector.add(findKontaktlisteByID(teilhaberschaft.getKontaktlisteID()));
		}

		return kontaktlisteVector;

	}

	// @Override
	// public Vector<Kontaktliste> findAllKontaktlisteByTeilhaberschaftID(int
	// teilhabenderID)
	// throws IllegalArgumentException {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public Kontaktliste findKontaktlisteByID(int kontaktlisteID) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.kontaktlisteMapper.findKontaktlisteByID(kontaktlisteID);
	}

	/**
	 * Methode um einen Kontakt suchen zu können
	 * @param: t
	 * 			Übergabeparameter der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Kontakt> findKontaktByName(Kontakt k) throws IllegalArgumentException {

		char a = k.getName().charAt(0);
		String b = "*";
		char c = b.charAt(0);
		char d = k.getName().charAt(k.getName().length() - 1);

		Vector<Kontakt> kontaktVector = new Vector<Kontakt>();
		

		if (a == c) {
			k.setName(k.getName().replace("*", "%"));
			kontaktVector = this.kontaktMapper.findKontaktByNameUndNutzerID(k);
		} else if (c == d) {
			k.setName(k.getName().replace("*", "%"));
			kontaktVector = this.kontaktMapper.findKontaktByNameUndNutzerID(k);
		} else {
			kontaktVector = this.kontaktMapper.findKontaktByNameUndNutzerID(k);
		}

		return kontaktVector;
	}

	@Override
	public Vector<Kontakt> findEigeneKontakteBySuche(Nutzer nutzer, Eigenschaftsauspraegung eigenschaftsauspraegung, String eigenschaft) throws IllegalArgumentException {
		Eigenschaft eigenschaftAusDB = findEigenschaftByBezeichnung(eigenschaft);
			Vector<Eigenschaftsauspraegung> allAuspraegungen = findAllEigenschaftsauspraegungByWertAndEigenschaft(eigenschaftsauspraegung, eigenschaftAusDB);
			Vector<Kontakt> allKontakteByNutzer = findAllKontaktByNutzerID(nutzer.getId());
			Vector<Kontakt> filteredKontakte = new Vector<Kontakt>();
			for(int i = 0; i<allAuspraegungen.size(); i++){
				for(int o = 0; o<allKontakteByNutzer.size(); o++){
					if(allAuspraegungen.elementAt(i).getPersonID() == allKontakteByNutzer.elementAt(o).getId()){
						filteredKontakte.add(allKontakteByNutzer.elementAt(o));
					}
				}
			}
			return filteredKontakte;
	}

	@Override
	public Vector<Kontakt> findTeilhaberschaftKontakteBySuche(Nutzer nutzer,
		Eigenschaftsauspraegung eigenschaftsauspraegung, String eigenschaft) throws IllegalArgumentException {
		Eigenschaft eigenschaftAusDB = findEigenschaftByBezeichnung(eigenschaft);
		Vector<Eigenschaftsauspraegung> allAuspraegungen = findAllEigenschaftsauspraegungByWertAndEigenschaft(eigenschaftsauspraegung, eigenschaftAusDB);
		Vector<Teilhaberschaft> teilhaberschaftVector = findAllTeilhaberschaftenByTeilhabenderID(nutzer.getId());

		Vector<Kontakt> kontakteByTeilhaberschaft = new Vector<Kontakt>();
		Vector<Kontakt> filteredKontakte = new Vector<Kontakt>();
		
		for (Teilhaberschaft teilhaberschaft : teilhaberschaftVector) {
			kontakteByTeilhaberschaft.add(findKontaktByID(teilhaberschaft.getKontaktID()));
		}
		for(int i = 0; i<allAuspraegungen.size(); i++){
			for(int o = 0; o<kontakteByTeilhaberschaft.size(); o++){
				if(allAuspraegungen.elementAt(i).getPersonID() == kontakteByTeilhaberschaft.elementAt(o).getId()){
					filteredKontakte.add(kontakteByTeilhaberschaft.elementAt(o));
				}
			}
		}
		
		
		return filteredKontakte;
	}

	@Override
	public Vector<Kontakt> findTeilhaberUndEigeneKontakteBySuche(Nutzer nutzer,
			Eigenschaftsauspraegung eigenschaftsauspraegung, String eigenschaft) throws IllegalArgumentException {
		Vector<Kontakt> eigeneKontakte = findEigeneKontakteBySuche(nutzer, eigenschaftsauspraegung, eigenschaft);
		Vector<Kontakt> teilhabendeKontakte = findTeilhaberschaftKontakteBySuche(nutzer, eigenschaftsauspraegung, eigenschaft);
		
		Vector<Kontakt> alleKontakte = new Vector<Kontakt>();
		alleKontakte.addAll(eigeneKontakte);
		alleKontakte.addAll(teilhabendeKontakte);
		
		return alleKontakte;
	}

	@Override
	public Vector<Kontakt> findAllKontakteByEigentuemerID(int eigentuemerID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> eigentuemerVector = findTeilhaberschaftByEigentuemerID(eigentuemerID);
		Vector<Kontakt> eigentumKontakt = new Vector<Kontakt>();
		for(int i = 0; i<eigentuemerVector.size(); i++){
			if(eigentuemerVector.elementAt(i).getKontaktID() == 0){
				eigentuemerVector.removeElementAt(i);
			}
		}
		
		for (Teilhaberschaft teilhaberschaft : eigentuemerVector) {
			eigentumKontakt.add(findKontaktByID(teilhaberschaft.getKontaktID()));
		}
		
		return eigentumKontakt;
	}

	/**
	 * Methode um eine Eigenschaftsausprägung über ihren Primärschlüssel zu finden
	 * @param: eigenschaftsauspraegungID
	 * @return Eigenschaftsauspraegung			
	 * @throws IllegalArgumentException
	 */
	@Override
	public Eigenschaftsauspraegung findEigenschaftsauspraegungById(int eigenschaftsauspraegungID)
			throws IllegalArgumentException {
		return this.eigenschaftsauspraegungMapper.findEigenschaftsauspraegungByID(eigenschaftsauspraegungID);
	}

	@Override
	public Vector<Kontakt> findKontakteByTeilhabenderID(int teilhabenderID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Teilhaberschaft> kontaktTeilhabenderVector = new Vector<Teilhaberschaft>();
		Vector<Kontakt> kontakteVector = new Vector<Kontakt>();
		for (Teilhaberschaft teilhaberschaft : teilhabenderVector) {
			if(teilhaberschaft.getKontaktID() != 0 && teilhaberschaft.getEigenschaftsauspraegungID() == 0){
				kontaktTeilhabenderVector.add(teilhaberschaft);
			}
		}
		

		for (Teilhaberschaft teilhabender : kontaktTeilhabenderVector) {
			kontakteVector.add(findKontaktByID(teilhabender.getKontaktID()));
		}  
		
		
		
		return kontakteVector;
	}

	@Override
	public Vector<Kontaktliste> findKontaktlisteByTeilhabenderID(int teilhabenderID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Teilhaberschaft> kontaktlisteTeilhabenderVector = new Vector<Teilhaberschaft>();
		Vector<Kontaktliste> kontaktlisteVector = new Vector<Kontaktliste>();
		Kontaktliste kontaktliste = new Kontaktliste();

		for (Teilhaberschaft teilhaberschaft : teilhabenderVector) {
			if (teilhaberschaft.getKontaktlisteID() != 0) {
				kontaktlisteTeilhabenderVector.add(teilhaberschaft);
			}
		}

		for (Teilhaberschaft teilhabender : kontaktlisteTeilhabenderVector) {
			kontaktlisteVector.add(findKontaktlisteByID(teilhabender.getKontaktlisteID()));

		}

		return kontaktlisteVector;
	}

	/**
	 * Methode um eine Kontaktliste zu finden
	 * @param bezeichnung, nutzerid
	 * 		Übergabeparameter sind die Bezeichnung und die ID des Nutzers
	 * @return kontaktliste 
	 * @throws IllegalArgumentException
	 */
	@Override
	public Kontaktliste findKontaktlistByName(String bezeichnung, int nutzerid) throws IllegalArgumentException {
		return this.kontaktlisteMapper.findKontaktlisteByBezeichnung(bezeichnung, nutzerid);
	}
	
	/**
	 * Methode um Nutzer zu finden über die kontaktID
	 * @param kontaktID
	 * @return kontaktVector (Vector mit allen Nutzern des Kontaktes)
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Nutzer> findNutzerByKontaktID(int kontaktID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhaberschaftVector = findTeilhaberschaftByKontaktID(kontaktID);
		Vector<Nutzer> kontaktVector = new Vector<Nutzer>();
		for (Teilhaberschaft teilhaberschaft : teilhaberschaftVector) {
			kontaktVector.add(findNutzerByID(teilhaberschaft.getTeilhabenderID()));
		}
		return kontaktVector;
	}

	/**
	 * Methode um Teilhaberschaft zu finden über die kontaktID
	 * @param kontaktID
	 * @return Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktID(int kontaktID) throws IllegalArgumentException {
		return this.teilhaberschaftMapper.findTeilhaberschaftByKontaktID(kontaktID);
	}

	
	@Override
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findNutzerTeilhaberschaftKontaktWrapperByTeilhaberschaft(
			int teilhabenderID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Teilhaberschaft> kontaktTeilhabenderVector = new Vector<Teilhaberschaft>();
		Vector<Kontakt> kontakteVector = new Vector<Kontakt>();
		Vector<NutzerTeilhaberschaftKontaktWrapper> wrapperVector = new Vector<NutzerTeilhaberschaftKontaktWrapper>();

		for (Teilhaberschaft teilhaberschaft : teilhabenderVector) {
			if (teilhaberschaft.getKontaktID() != 0 && teilhaberschaft.getEigenschaftsauspraegungID() == 0) {
				kontaktTeilhabenderVector.add(teilhaberschaft);
			}
		}
		for (Teilhaberschaft teilhabender : kontaktTeilhabenderVector) {
			wrapperVector.add(new NutzerTeilhaberschaftKontaktWrapper(findNutzerByID(teilhabender.getEigentuemerID()),
					teilhabender, findKontaktByID(teilhabender.getKontaktID())));
		}

		return wrapperVector;
	}

	@Override
	public Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> findAuspraegungTeilhaberschaftKontaktWrapperByTeilhaberschaft(
			int teilhabenderID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Teilhaberschaft> auspraegungTeilhabenderVector = new Vector<Teilhaberschaft>();
		Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> wrapperVector = new Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>();
		
		for (Teilhaberschaft teilhaberschaft : teilhabenderVector) {
			if(teilhaberschaft.getEigenschaftsauspraegungID() != 0 && teilhaberschaft.getKontaktID() != 0){
				
				auspraegungTeilhabenderVector.add(teilhaberschaft); 
			}
		}
		
		for (Teilhaberschaft teilhabender : auspraegungTeilhabenderVector) {
			Eigenschaftsauspraegung e = findEigenschaftsauspraegungById(teilhabender.getEigenschaftsauspraegungID());
			wrapperVector.add(new NutzerTeilhaberschaftEigenschaftAuspraegungWrapper(findNutzerByID(teilhabender.getEigentuemerID()),
					teilhabender, findEigenschaftByEigenschaftIDFromPerson(e), findEigenschaftsauspraegungById(e.getId()), findKontaktByID(e.getPersonID())));
		}

		
		return wrapperVector;
	}

	@Override
	public Vector<NutzerTeilhaberschaftKontaktlisteWrapper> findNutzerTeilhaberschaftKontaktlisteWrapper(
			int teilhabenderID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Teilhaberschaft> kontaktlisteTeilhabenderVector = new Vector<Teilhaberschaft>();
		Vector<NutzerTeilhaberschaftKontaktlisteWrapper> wrapperVector = new Vector<NutzerTeilhaberschaftKontaktlisteWrapper>();

		for (Teilhaberschaft teilhaberschaft : teilhabenderVector) {
			if(teilhaberschaft.getKontaktlisteID() != 0){
				kontaktlisteTeilhabenderVector.add(teilhaberschaft); 
			}
		}
		
		for (Teilhaberschaft teilhabender : kontaktlisteTeilhabenderVector) {
			wrapperVector.add(new NutzerTeilhaberschaftKontaktlisteWrapper(findNutzerByID(teilhabender.getEigentuemerID()),
					teilhabender, findKontaktlisteByID(teilhabender.getKontaktlisteID())));
		
	}
		return wrapperVector;
	}
	@Override
	public Vector<Kontakt> findTeilhabendeKontakteAuspraegungen(int teilhabenderID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Teilhaberschaft> kontaktTeilhabenderVector = new Vector<Teilhaberschaft>();
		Vector<Kontakt> kontakteVector = new Vector<Kontakt>();
		for (Teilhaberschaft teilhaberschaft : teilhabenderVector) {
			if((teilhaberschaft.getKontaktID() != 0 && teilhaberschaft.getEigenschaftsauspraegungID() == 0) || 
					(teilhaberschaft.getKontaktID() != 0 && teilhaberschaft.getEigenschaftsauspraegungID() != 0)){
				kontaktTeilhabenderVector.add(teilhaberschaft);
			}
		}
		

		for (Teilhaberschaft teilhabender : kontaktTeilhabenderVector) {
			kontakteVector.add(findKontaktByID(teilhabender.getKontaktID()));
		}  
		
		
		
		return kontakteVector;
	}

	
	@Override
	public void deleteTeilhaberschaftByTeilhaberschaft (Teilhaberschaft t) throws IllegalArgumentException{
		this.teilhaberschaftMapper.deleteTeilhaberschaftByTeilhabender(t);
	}
	
	@Override
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findEigenschaftsauspraegungAndKontaktByTeilhaberschaft(
			int teilhabenderID) throws IllegalArgumentException {
		Vector<Teilhaberschaft> teilhabenderVector = findAllTeilhaberschaftenByTeilhabenderID(teilhabenderID);
		Vector<Teilhaberschaft> kontaktTeilhabenderVector = new Vector<Teilhaberschaft>();
		Vector<Kontakt> kontakteVector = new Vector<Kontakt>();
		Vector<NutzerTeilhaberschaftKontaktWrapper> wrapperVector = new Vector<NutzerTeilhaberschaftKontaktWrapper>();
		Vector<NutzerTeilhaberschaftKontaktWrapper> wrapperFilteredVector = new Vector<NutzerTeilhaberschaftKontaktWrapper>();
		int kontaktID = 0;

		for (Teilhaberschaft teilhaberschaft : teilhabenderVector) {
			if (teilhaberschaft.getKontaktID() != 0) {
				kontaktTeilhabenderVector.add(teilhaberschaft);
			}
		}
		for (Teilhaberschaft teilhabender : kontaktTeilhabenderVector) {
			if(teilhabender.getKontaktID() != kontaktID){
				wrapperVector.add(new NutzerTeilhaberschaftKontaktWrapper(findNutzerByID(teilhabender.getEigentuemerID()),
						teilhabender, findKontaktByID(teilhabender.getKontaktID())));
			}
			kontaktID = teilhabender.getKontaktID();
		
		}
				

		return wrapperVector;
	}
	
	@Override
	public Vector<EigenschaftsAuspraegungWrapper> findEigenschaftAndAuspraegungByKontakt (int nutzerID, int kontaktID) throws IllegalArgumentException{
		Kontakt k = new Kontakt();
		k.setId(kontaktID);
		Vector<Teilhaberschaft> teilhaberschaftVector = this.teilhaberschaftMapper.findTeilhaberschaftByKontaktAndTeilhaber(kontaktID, nutzerID);
		
		Vector<EigenschaftsAuspraegungWrapper> wrapperVector = new Vector<EigenschaftsAuspraegungWrapper>();
		Vector<Eigenschaft> eigenschaftVector = findAllEigenschaften();
		Vector<EigenschaftsAuspraegungWrapper> wrapperVectorFiltered = new Vector<EigenschaftsAuspraegungWrapper>();
		
		if(teilhaberschaftVector.size() != 0){
			for (Teilhaberschaft teilhaberschaft : teilhaberschaftVector) {
				
				if(teilhaberschaft.getEigenschaftsauspraegungID() != 0){
					wrapperVector.add(new EigenschaftsAuspraegungWrapper(findEigenschaftsauspraegungById(teilhaberschaft.getEigenschaftsauspraegungID())));
				} else if(teilhaberschaft.getKontaktID() != 0 && teilhaberschaft.getEigenschaftsauspraegungID() == 0){
					wrapperVectorFiltered = findEigenschaftHybrid(k);
				}
			}
		
			for (EigenschaftsAuspraegungWrapper eigenschaftsauspraegung : wrapperVector) {
				for (Eigenschaft eigenschaftvergleich : eigenschaftVector) {
					if (eigenschaftsauspraegung.getAuspraegung().getEigenschaftID() == eigenschaftvergleich.getId()) {
						wrapperVectorFiltered
								.add(new EigenschaftsAuspraegungWrapper(eigenschaftvergleich, eigenschaftsauspraegung.getAuspraegung()));
						break;
					}
				}
			}
			
			return wrapperVectorFiltered;
		} else {
			
			wrapperVectorFiltered = findEigenschaftHybrid(k);
			
		}
		
		
		return wrapperVectorFiltered;
	}
	
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktAndTeilhaber(int nutzerid, int kontaktid) throws IllegalArgumentException{
		return this.teilhaberschaftMapper.findTeilhaberschaftByKontaktAndTeilhaber(nutzerid, kontaktid);
	}
	
	@Override
	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungIDAndNutzerID(int nutzerid, int auspraegungid) throws IllegalArgumentException{
		return this.teilhaberschaftMapper.findTeilhaberschaftByAuspraegungIDAndNutzerID(auspraegungid, nutzerid);
		
	}
	
	@Override
	public String findTeilhaberschaftString(int nutzerid, Vector<Eigenschaftsauspraegung> auspraegung) throws IllegalArgumentException{
		Vector<Teilhaberschaft> teilhaberschaftVector = new Vector<Teilhaberschaft>();
		
		for (Eigenschaftsauspraegung eigenschaftsauspraegung : auspraegung) {
			teilhaberschaftVector.addAll(findTeilhaberschaftByAuspraegungIDAndNutzerID(nutzerid, eigenschaftsauspraegung.getId()));
		}
		
		if(teilhaberschaftVector.size() == 0){
			return "keineteilhaberschaft";
		} else {
			return "teilhaberschaft";
			
		}
		
	}

	@Override
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findAllKontakteAndTeilhaberschaftenByNutzer(Nutzer nutzer)
			throws IllegalArgumentException {
		Vector<Kontakt> teilhaberschaftKontakte = findAllKontakteByTeilhabenderID(nutzer.getId());
		Vector<Kontakt> eigeneKontakte = findAllKontaktByNutzerID(nutzer.getId());

		Vector<Teilhaberschaft> eigentuemerteilhaberschaft = findTeilhaberschaftByEigentuemerID(nutzer.getId());
		Vector<Teilhaberschaft> teilhaberschaft = findAllTeilhaberschaftenByTeilhabenderID(nutzer.getId());
		Vector<NutzerTeilhaberschaftKontaktWrapper> wrapperVector = new Vector<NutzerTeilhaberschaftKontaktWrapper>();

		for (Kontakt kontakt : teilhaberschaftKontakte) {
			for (Teilhaberschaft teilhaberschaftForeach : teilhaberschaft) {
				if (teilhaberschaftForeach.getKontaktID() == kontakt.getId()) {
					wrapperVector.add(new NutzerTeilhaberschaftKontaktWrapper(findNutzerByID(teilhaberschaftForeach.getEigentuemerID()), teilhaberschaftForeach, kontakt));

				}
			}
		}
		for (Kontakt kontakte : eigeneKontakte) {
			for (Teilhaberschaft teilhaberschafteigen : eigentuemerteilhaberschaft) {

				if (kontakte.getId() == teilhaberschafteigen.getKontaktID()) {
					wrapperVector.add(new NutzerTeilhaberschaftKontaktWrapper(findNutzerByID(teilhaberschafteigen.getTeilhabenderID()), teilhaberschafteigen, kontakte));
				}

			}

		}
		for (Kontakt kontakteigen : eigeneKontakte) {
			if(kontakteigen.getStatus() == 0){
				wrapperVector.add(new NutzerTeilhaberschaftKontaktWrapper(null, null, kontakteigen));
			}
		}

		return wrapperVector;

	}
	
}

	// @Override
	// public Vector<Kontaktliste> findAllKontaktlisteByTeilhaberschaftID(int
	// teilhabenderID)
	// throws IllegalArgumentException {
	// // TODO Auto-generated method stub
	// return null;
	// }
	
