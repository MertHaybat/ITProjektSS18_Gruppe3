package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Person;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung von Kontakten.
 * 
 * @version 1.30 11 May 2018
 * @author Thomas, Mert, giuseppeggalati
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
	 * @param mail; ist die Google E-Mail Adresse des Nutzers
	 * @return Nutzerobjekt
	 * @throws IllegalArgumentException
	 */
	public Nutzer createNutzer(String mail) throws IllegalArgumentException;
	
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
	public Kontakt createKontakt(String name, int status, int nutzerID) throws IllegalArgumentException;
	
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
	public Kontaktliste createKontaktliste(String bezeichnung, int nutzerID, int status) throws IllegalArgumentException;
	
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
	 * Suchen von Teilhaberschaften eines Teilhabenden.
	 * @param teilhabenderID
	 * @return Vector des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	
	/**
	 * Suchen von Teilhaberschaften eines Eigentümers.
	 * @param eigentuemerID
	 * @return Vector des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	Vector<Teilhaberschaft> findTeilhaberschaftByEigentuemerID(int eigentuemerID);
	
	/**
	 * Auslesen aller Eigenschaftobjekte.
	 * @return Vector mit allen Eigenschaftobjekten
	 * @throws IllegalArgumentException
	 */
	public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException;

	/**
	 * In der checkNutzer Methode wird die GoogleMail des Nutzers überprüft.
	 * Nutzer Objekt wird ausgegeben wenn in der Datenbank die E-Mail Adresse 
	 * bereits existiert.
	 * 
	 * @param email ist die GoogleMail des Nutzers
	 * @return Nutzer Objekt
	 * @throws IllegalArgumentException
	 */
	public Nutzer checkEmail(String email) throws IllegalArgumentException;
	
	/**
	 * CreateEigenschaftsauspraegung erstellt eine Eigenschaftsausprägung.
	 * 
	 * @param wert; wert ist die Ausprägung einer Eigenschaft
	 * @param personID; personID definiert Nutzer/Kontakt dem die Eigenschaftsausprägung gehört
	 * @param status; status sagt aus, ob diese Eigenschaftsausprägung geteilt wurde oder nicht geteilt wurde
	 * @param eigenschaft; ist die dazu gehörige Eigenschaft
	 * @return Eigenschaftsauspraegung; Zurückgegeben wird ein Objekt der Klasse Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	public Eigenschaftsauspraegung createEigenschaftsauspraegung(String wert, int personID, int status, String eigenschaft) throws IllegalArgumentException;
	
	/**
	 * CreateEigenschaft erstellt eine Eigenschaft in der Datenbank.
	 * In der Methode wird erst kontrolliert, ob es eine Eigenschaft mit derselben Bezeichnung bereits gibt.
	 * Sollte dies der Fall sein wird nur die Eigenschaftsausprägung gesetzt.
	 * 
	 * @param bezeichnung; bezeichnung sagt aus was die Eigenschaft ist
	 * @return Eigenschaft; ein Eigenschaftsobjekt wird zurückgegeben
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft createEigenschaft(String bezeichnung) throws IllegalArgumentException;
	
	/**
	 * Erstellung einer Beziehung zwischen Kontakt und Kontaktliste für die Zwischentabelle
	 * KontaktKontaktliste in der Datenbank.
	 * 
	 * @param kontaktID; ist die KontaktID die ausgewählt wurde
	 * @param kontaktlisteID; ist die KontaktlisteID die ausgewählt wurde
	 * @return KontaktKontaktliste Objekt
	 * @throws IllegalArgumentException
	 */
	public KontaktKontaktliste createKontaktKontaktliste(int kontaktID, int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * Alle Kontakte einer Kontaktliste anzeigen lassen
	 * 
	 * @param k; Objekt der Klasse Kontaktliste, hier wird die KontaktlisteID herausgenommen
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	public Vector <Kontakt> findAllKontakteByKontaktlisteID(Kontaktliste k) throws IllegalArgumentException;
	
	/**
	 * Kontakte mit der Kontakt ID anzeigen lassen
	 * 
	 * @param k; Objekt der Klasse Kontakt, hier wird die KontaktID herausgenommen
	 * @return Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	public Kontakt findKontaktByID(int kontaktID) throws IllegalArgumentException;
	
	/**
	 * Alle Eigenschaftsausprägung einer Person (Nutzer/Kontakt) anzeigen lassen
	 * 
	 * @param p; Objekt der Klasse Person, hier wird die PersonID herausgenommen
	 * @return Vector des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByPersonID(Person p) throws IllegalArgumentException;
	
	/**
	 * Alle Eigenschaften anhand einer EigenschaftID anzeigen lassen
	 * 
	 * @param e; Objekt der Klasse Eigenschaft, hier wird die EigenschaftID herausgenommen
	 * @return Vector des Typs Eigenschaft
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft findEigenschaftByEigenschaftIDFromPerson(Eigenschaftsauspraegung e) throws IllegalArgumentException;
	
	/**
	 * Alle Eigenschaftsausprägungen einer Eigenschaft anzeigen lassen
	 * 
	 * @param e; Objekt der Klasse Eigenschaft
	 * @return Vector des Typs Eigenschaftsausprägung
	 * @throws IllegalArgumentException
	 */
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByEigenschaftID(Eigenschaft e) throws IllegalArgumentException;
	
	/**
	 * Alle Eigenschaftsausprägung durch dessen Wert anzeigen lassen. Diese Methode ist Teil der Suchfunktion
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsausprägung
	 * @return Vector des Typs Eigenschaftsausprägung
	 * @throws IllegalArgumentException
	 */
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByWertAndEigenschaft(Eigenschaftsauspraegung e, Eigenschaft eigenschaft) throws IllegalArgumentException;
	
	/**
	 * Löschen eines Kontakts mit der NutzerID
	 * 
	 * @param k; Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktByOwner(Kontakt k) throws IllegalArgumentException;
	
	/**
	 * Löschen der Beziehung zwischen KontaktKontaktliste mit der KontaktlisteID
	 * 
	 * @param k; Objekt der Klasse KontaktKontaktliste
	 * @return Objekt des Typs KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktKontaktlisteByKontaktlisteID(KontaktKontaktliste k) throws IllegalArgumentException;
	
	/**
	 * Auslesen von Teilhabern in Teilhaberschaften
	 * 
	 * @param t; Objekt der Klasse Teilhaberschaft
	 * @return Objekt des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public Vector <Teilhaberschaft> findAllTeilhaberschaftenByTeilhabenderID(int teilhaberschaftID) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Kontaktliste mit der NutzerID
	 * 
	 * @param k; Objekt der Klasse Kontaktliste
	 * @return Objekt des Typs Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktlisteByNutzerID(Kontaktliste k) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Eigenschaftsausprägung mit der PersonID
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsauspraegung
	 * @return Objekt des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	public void deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung e) throws IllegalArgumentException;
	
	/**
	 * Anzeigen einer Eigenschaft anhand der Bezeichnung
	 * 
	 * @param e; Bezeichnung von Eigenschaft
	 * @return Objekt der Klasse Eigenschaft
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft findEigenschaftByBezeichnung(String e) throws IllegalArgumentException;

	/**
	 * Anzeigen einer Eigenschaft anhand der ID
	 * 
	 * @param e; Objekt der Klasse Eigenschaft
	 * @return Objekt der Klasse Eigenschaft
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft findEigenschaftByEigenschaftID(int eigenschaftID) throws IllegalArgumentException;
	
	/**
	 * Löschen einer KontaktKontaktliste mit der KontaktID
	 * 
	 * @param k; Objekt der Klasse KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktKontaktlisteByKontaktID(KontaktKontaktliste k) throws IllegalArgumentException;
	
	/**
	 * 
	 * 
	 * @param e Objekt der Klasse Eigenschaft
	 * @return Vector der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontakteByEigenschaftUndEigenschaftsauspraegungen(Eigenschaft e, Eigenschaftsauspraegung auspraegung) throws IllegalArgumentException;

	/**
	 * 
	 * @param t Objekt der Klasse Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByID(Teilhaberschaft t) throws IllegalArgumentException;

	/**
	 * 
	 * @param eig - Objekt der Klasse Eigenschaft
	 * @throws IllegalArgumentException
	 */
	public void saveEigenschaft(Eigenschaft eig) throws IllegalArgumentException;

	/**
	 * 
	 * @param aus - Objekt der Klasse Eigenschaftsausprägung
	 * @throws IllegalArgumentException
	 */
	public void saveEigenschaftsauspraegung(Eigenschaftsauspraegung aus) throws IllegalArgumentException;

	/**
	 * 
	 * @param k Objekt der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	public void saveKontakt(Kontakt k) throws IllegalArgumentException;

	/**
	 * 
	 * @param kliste - Objekt der Klasse Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public void saveKontaktliste(Kontaktliste kliste) throws IllegalArgumentException;

	/**
	 * 
	 * @param n - Objekt der Klasse Nutzer
	 * @throws IllegalArgumentException
	 */
	public void saveNutzer(Nutzer n) throws IllegalArgumentException;

	/**
	 * 
	 * @param t - Objekt der Klasse Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void saveTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;

	/**
	 * Löschen aller Teilhaberschaften eines Nutzers
	 * 
	 * @param n - Objekt der Klasse Nutzer
	 * @throws IllegalArgumentException
	 */
	public void deleteAllTeilhaberschaftByOwner(Nutzer n) throws IllegalArgumentException;

	/**
	 * Löschen aller Eigenschaftsauspraegungen einer Person
	 * 
	 * @param p - Objekt der Klasse Person
	 * @throws IllegalArgumentException
	 */
	public void deleteAllEigenschaftsauspraegungByNutzer(Nutzer n) throws IllegalArgumentException;
	
	/**
	 * Löschen aller KontaktKontaktliste Beziehungen in der Zwischentabelle.
	 * 
	 * @param nutzerID - Übergabeparameter des Fremdschlüssels in der Kontaktliste 
	 * @throws IllegalArgumentException
	 */
	public void deleteAllKontaktKontaktlisteByOwner(Nutzer n) throws IllegalArgumentException;
	
	/**
	 * Löschen aller Kontakt eines Nutzers
	 * 
	 * @param n - Nutzer Objekt, der Besitzer eines Kontakts
	 * @throws IllegalArgumentException
	 */
	public void deleteAllKontaktByOwner(Nutzer n) throws IllegalArgumentException;

	/**
	 * Löschen aller Kontaktlisten eines Nutzers
	 * 
	 * @param n - Nutzer Objekt, der Besitzer einer Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public void deleteAllKontaktlisteByOwner(Nutzer n) throws IllegalArgumentException;
	
	/**
	 * Löschen eines Nutzers
	 * 
	 * @param n - Nutzer Objekt, der Besitzer eines Nutzers
	 * @throws IllegalArgumentException
	 */
	public void deleteNutzer(Nutzer n) throws IllegalArgumentException;
	
	/**
	 * Teilhaberschaften ausgehend von einem Kontakt werden gelöscht
	 * 
	 * @param k - Kontakt Objekt, für die Fremdschlüssel in der Tabelle Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByKontakt(Kontakt k) throws IllegalArgumentException;
	
	/**
	 * Eigenschaftsauspraegungungen eines Kontakts werden gelöscht
	 * 
	 * @param k - Kontakt Objekt, für die Fremdschlüssel in der Tabelle Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	public void deleteEigenschaftsauspraegungByKontakt(Kontakt k) throws IllegalArgumentException;

	/**
	 * Löschen aller KontaktKontaktliste Beziehungen in der zusammengesetzten Tabelle
	 * 
	 * @param k - Kontakt Objekt, für die Fremdschlüssel in der Tabelle KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktKontaktlisteByKontakt(Kontakt k) throws IllegalArgumentException;
	
	/**
	 * Anzeigen aller Kontakte gefiltert nach der nutzerID
	 * 
	 * @param nutzerID - ist der Übergabeparameter für die ID des Nutzers, der angemeldet ist
	 * @return Vector<Kontakt> - Alle Kontakte die der Nutzer erstellt hat
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontaktByNutzerID(int nutzerID) throws IllegalArgumentException;
	
	/**
	 * Anzeigen aller Kontakte, die ein Nutzer nach einer Teilhaberschaft erhalten hat
	 * 
	 * @param teilhabenderID - ist der Übergabeparameter für die ID des Nutzers, der angemeldet ist
	 * @return Vector<Kontakt> - Alle Kontakte die der Nutzer geteilt bekommen hat
	 */
	public Vector<Kontakt> findAllKontakteByTeilhabenderID(int teilhabenderID);

	/**
	 * Löschen einer Eigenschaftsausprägung nach der Id
	 * 
	 * @param e - Objekt der Klasse Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	public void deleteEigenschaftsauspraegungById(Eigenschaftsauspraegung e) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Teilhaberschaft nach der EigenschaftsausprägungID
	 * 
	 * @param t - Objekt der Klasse Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByEigenschaftsauspraegungID(Teilhaberschaft t) throws IllegalArgumentException;

	/**
	 * Löschen einer Teilhaberschaft nach der ID
	 * 
	 * @param t - Objekt der Klasse Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftById(Teilhaberschaft t) throws IllegalArgumentException;
	
	/**
	 * Anzeigen aller Kontakte die mit dem angemeldeten Nutzer geteilt wurden.
	 * 
	 * @param teilhabenderID - ID des teilhabendenen Nutzers
	 * @param eigentuemerID - ID des Eigentümers 
	 * @return Vector<Kontakt> - Alle Kontakte ausgehend von einer Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontaktByTeilhaberschaften(int teilhabenderID, int eigentuemerID) throws IllegalArgumentException;

	/**
	 * Nutzer mit der anhand der ID finden
	 * 
	 * @param nutzerID - ID des angemeldeten Nutzers
	 * @return Nutzer Objekt
	 * @throws IllegalArgumentException
	 */
	public Nutzer findNutzerByID(int nutzerID) throws IllegalArgumentException;

	/**
	 * Löschen einer Kontaktliste anhand der Kontaktliste ID
	 * 
	 * @param k - Objekt der Klasse Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktlisteByID(Kontaktliste k) throws IllegalArgumentException;
	
	/**
	 * Löschen einer KontaktKontaktliste Beziehung. Wird verwendet wenn ein Kontakt aus einer Kontaktliste gelöscht werden soll.
	 * 
	 * @param kk
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktKontaktlisteByID(KontaktKontaktliste kk) throws IllegalArgumentException;

	/**
	 * Alle Nutzer Anzeigen
	 * 
	 * @return Vector<Nutzer> - Vector des Typs Nutzer, welches alle Nutzer zurück gibt
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;
	
	/**
	 * Alle Eigenschaften und Eigenschaftsauspraegungen eines Kontakts ausgeben
	 * 
	 * @param kontakt - Kontakt der übergeben wird, um die Eigenschaften und Ausprägungen zu finden
	 * @return Vector<EigenschaftsAuspraegungWrapper> Alle Eigenschaften und Eigenschaftsauspraegungen gebündelt in einem Wrapper Vector 
	 * @throws IllegalArgumentException
	 */
	public Vector<EigenschaftsAuspraegungWrapper> findEigenschaftHybrid(Kontakt kontakt) throws IllegalArgumentException;
	
	/**
	 * Löschen eines Kontaktes mit der Kontakt ID
	 * @param k - Der Übergebene Kontakt, der gelöscht wird
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktByID(Kontakt k) throws IllegalArgumentException;
	
	
	public void deleteKontaktKontaktliste(KontaktKontaktliste kon) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Teilhaberschaft anhand der Kontaktliste ID
	 * 
	 * @param t; Objekt der Klasse Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByKontaktlisteID(Teilhaberschaft t) throws IllegalArgumentException;
	
	/**
	 * Methode zur Ausgabe aller Kontaktlisten die mit einem bestimmten Nutzer geteilt wurden
	 * @param teilhabenderID - Übergabeparameter der Klasse Teilhaberschaft
	 * @return Vector des Typs Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontaktliste> findAllKontaktlisteByTeilhaberschaft(int teilhabenderID) throws IllegalArgumentException;

	/**
	 * Ausgeben einer Kontaktliste über deren Primärschlüssel
	 * @param kontaktlisteID - ID der Kontaktliste
	 * @return Objekt der Klasse Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste findKontaktlisteByID(int kontaktlisteID) throws IllegalArgumentException;

	/**
	 * Methode um einen Kontakt suchen zu können
	 * @param: t
	 * 			Übergabeparameter der Klasse Kontakt
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findKontaktByName(Kontakt k) throws IllegalArgumentException;
	
	/**
	 * Suchen und ausgeben der eigenen Kontakte, die Teile der übergebenen Parameter enthalten
	 * 
	 * @param nutzer - Objekt der Klasse Nutzer
	 * @param eigenschaftsauspraegung - Objekt der Klasse Eigenschaftsauspraegung
	 * @param eigenschaft - Objekt der Klasse Eigenschaft
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findEigeneKontakteBySuche(Nutzer nutzer, Eigenschaftsauspraegung eigenschaftsauspraegung, String eigenschaft) throws IllegalArgumentException;
	
	/**
	 * Suchen und ausgeben der Kontakte, die mit einem geteilt wurden und Teile der übergebenen Parameter enthalten
	 * 
	 * @param nutzer - Objekt der Klasse Nutzer
	 * @param eigenschaftsauspraegung - Objekt der Klasse Eigenschaftsauspraegung
	 * @param eigenschaft - Objekt der Klasse Eigenschaft
	 * @return Vector<Kontakt> Alle Kontakte, die mit dem suchenden Nutzer geteilt wurden
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findTeilhaberschaftKontakteBySuche(Nutzer nutzer, Eigenschaftsauspraegung eigenschaftsauspraegung, String eigenschaft) throws IllegalArgumentException;
	

	/**
	 * Suchen und ausgeben der selbst erstellten Kontakte und der Kontakte, 
	 * die mit einem geteilt wurden und Teile der übergebenen Parameter enthalten
	 * @param nutzer - Objekt der Klasse Nutzer
	 * @param eigenschaftsauspraegung - Objekt der Klasse Eigenschaftsauspraegung
	 * @param eigenschaft - Objekt der Klasse Eigenschaft
	 * @return Vector<Kontakt> Alle Kontakte, die die entsprechenden Eigenschaften und
	 * 							Ausprägungen haben
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findTeilhaberUndEigeneKontakteBySuche(Nutzer nutzer, Eigenschaftsauspraegung eigenschaftsauspraegung, String eigenschaft) throws IllegalArgumentException;

	/**
	 * Methode zum Ausgeben aller Kontakte über den Primärschlüssel des Eigentümers
	 * @param eigentuemerID - ID des Eigentümers
	 * @return Vector <Kontakt> Alle Kontakte des Eigentümers
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontakteByEigentuemerID(int eigentuemerID) throws IllegalArgumentException;
	
	/**
	 * Methode um eine Eigenschaftsausprägung über ihren Primärschlüssel zu finden
	 * @param: eigenschaftsauspraegungID
	 * @return Eigenschaftsauspraegung			
	 * @throws IllegalArgumentException
	 */
	public Eigenschaftsauspraegung findEigenschaftsauspraegungById(int eigenschaftsauspraegungID) throws IllegalArgumentException;

	/**
	 * Methode zum Ausgeben eines Kontakts über die Primärschlüssel des Teilhabenden
	 * @param teilhabenderID - ID des Teilhabers
	 * @return Vector<Kontakt> Alle Kontakte des Teilhabenden
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findKontakteByTeilhabenderID(int teilhabenderID) throws IllegalArgumentException;
	
	/**
	 * Ausgeben von Kontaktlisten über die den Primärschlüssel des Teilhabenden
	 * @param teilhabenderID
	 * @return Vector<Kontaktliste> Alle Kontaktlisten des Teilhabers
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontaktliste> findKontaktlisteByTeilhabenderID(int teilhabenderID) throws IllegalArgumentException;
	
	/**
	 * Ausgeben von Kontaktlisten über den Primärschlüssel des Nutzers
	 * @param bezeichnung
	 * @param nutzerid
	 * @return Kontaktliste des Nutzers
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste findKontaktlistByName(String bezeichnung, int nutzerid) throws IllegalArgumentException;
	
	/**
	 * Ausgeben von Nutzern über den Primärschlüssel des Kontakts
	 * @param kontaktID
	 * @return Vector <Nutzer> Nutzer des Kontakts
	 * @throws IllegalArgumentException
	 */
	public Vector<Nutzer> findNutzerByKontaktID(int kontaktID) throws IllegalArgumentException;
	
	/**
	 * Ausgeben von Teilhaberschaft über Primärschlüssel des Kontakts
	 * @param kontaktID
	 * @return Vector<Teilhaberschaft> Teilhaberschaft(-en) des Kontakts
	 * @throws IllegalArgumentException
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktID(int kontaktID) throws IllegalArgumentException;
	
	/**
	 * Ausgeben bei Verwaltung Teilhaberschaften, die Kontakte 
	 * @param teilhabenderID
	 * @return Vector <NutzerTeilhaberschaftKontaktWrapper>
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findNutzerTeilhaberschaftKontaktWrapperByTeilhaberschaft(int teilhabenderID) throws IllegalArgumentException;
	
	/**
	 * Ausgabe bei Verwaltung der Teilhaberschaften, die Eigenschaften und Ausprägungen der Kontakte 
	 * @param teilhabenderID
	 * @return Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> Alle Eigenschaften Ausprägungen der Kontakte in der Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> findAuspraegungTeilhaberschaftKontaktWrapperByTeilhaberschaft(int teilhabenderID) throws IllegalArgumentException;

	/**
	 * Ausgabe bei Verwaltung der Teilhaberschaften, die Kontaktliste mit den Kontakten der Kontaktliste 
	 * @param teilhabenderID
	 * @return Vector<NutzerTeilhaberschaftKontaktlisteWrapper>, die Kontaktlisten, mit den Kontakten, der Liste
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftKontaktlisteWrapper> findNutzerTeilhaberschaftKontaktlisteWrapper(int teilhabenderID) throws IllegalArgumentException;

	/**
	 * Ausgabe der Ausprägungen der Kontakte der Teilhaberschaft 
	 * @param teilhabenderID
	 * @return <Kontakt> findTeilhabendeKontakteAuspraegungen, die Ausprägung der empfangenen Kontakte 
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findTeilhabendeKontakteAuspraegungen(int teilhabenderID) throws IllegalArgumentException;

	/**
	 * Löschen der Teilhaberschaft 
	 * @param t
	 * @throws IllegalArgumentException
	 */
	public void deleteTeilhaberschaftByTeilhaberschaft(Teilhaberschaft t) throws IllegalArgumentException;

	/**
	 * Ausgabe der Eigenschaftsausprägung und der Kontakte bei empfangengen Kontakten 
	 * @param teilhabenderID
	 * @return Vector<NutzerTeilhaberschaftKontaktWrapper>, Eigenschaftsausprägungen der empfangenen Kontakte 
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findEigenschaftsauspraegungAndKontaktByTeilhaberschaft(
			int teilhabenderID) throws IllegalArgumentException;

	/**
	 * Ausgabe der Eigenschaften, der Ausprägungen des jeweiligen Kontaktes 
	 * @param nutzerID
	 * @param kontaktID
	 * @return Vector<EigenschaftsAuspraegungWrapper>, Eigenschaft und Ausprägung des Kontaktes
	 * @throws IllegalArgumentException
	 */
	public Vector<EigenschaftsAuspraegungWrapper> findEigenschaftAndAuspraegungByKontakt(int nutzerID, int kontaktID)
			throws IllegalArgumentException;

	/**
	 * Ausgabe der Teilhaberschaft nach Primärschlüssel des Nutzers und der Ausprägung
	 * @param nutzerid
	 * @param auspraegungid
	 * @return Vector<Teilhaberschaft>, Teilhaberschaft 
	 * @throws IllegalArgumentException
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByAuspraegungIDAndNutzerID(int nutzerid, int auspraegungid)
			throws IllegalArgumentException;

	/**
	 * Ausgabe der Teilhaberschaft nach Primärschlüssel des Kontaktes und des Nutzers
	 * @param nutzerid
	 * @param kontaktid
	 * @return Vector<Teilhaberschaft>, Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByKontaktAndTeilhaber(int nutzerid, int kontaktid)
			throws IllegalArgumentException;

	/**
	 * Ausgabe der Teilhaberschaft, nach Primärschlüssel des Nutzers und der Vector<Eigenschaftsauspraegung> auspraegung
	 * @param nutzerid
	 * @param auspraegung
	 * @return Teilhaberschaft
	 */
	public String findTeilhaberschaftString(int nutzerid, Vector<Eigenschaftsauspraegung> auspraegung) throws IllegalArgumentException;

	/**
	 * Ausgabe der Kontakte und Teilhaberschaften nach Nutzerobjekt 
	 * @param nutzer
	 * @return Vector<NutzerTeilhaberschaftKontaktWrapper>, Kontakte und Teilhaberschaften
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findAllKontakteAndTeilhaberschaftenByNutzer(Nutzer nutzer) throws IllegalArgumentException;

	/**
	 * Ausgabe der Kontakte und Teilhaberschaft nach Primärschlüssel der Eigentümer und des Teilhabender 
	 * @param eigentuemerID
	 * @param teilhabenderID
	 * @return Vector<Kontakt>, Kontakt und Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findKontaktTeilhaberschaftByEigentuemerAndTeilhaber(int eigentuemerID, int teilhabenderID)
			throws IllegalArgumentException;

	/**
	 * Ausgabe der Kontaktliste in einer Teilhaberschaft als Eigentümer, nach Primärschlüssel Eigentümer 
	 * @param eigentuemerID
	 * @return Vector<NutzerTeilhaberschaftKontaktlisteWrapper>, Kontaktliste 
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftKontaktlisteWrapper> findKontaktlisteTeilhaberschaftEigentuemer(int eigentuemerID)
			throws IllegalArgumentException;

	/**
	 * Ausgabe der Kontakte in einer Teilhaberschaft als Eigentümer, nach Primärschlüssel Eigentümer
	 * @param eigentuemerID
	 * @return Vector<NutzerTeilhaberschaftKontaktWrapper>, Kontakte 
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findKontaktTeilhaberschaftEigentuemer(int eigentuemerID)
			throws IllegalArgumentException;

	/**
	 * Ausgabe der Ausprägungen in einer Teilhaberschaft als Eigentümer, nach Primärschlüssel des Eigentümers
	 * @param eigentuemerID
	 * @return Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>, Ausprägung
	 * @throws IllegalArgumentException
	 */
	public Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> findAuspraegungTeilhaberschaftEigentuemer(
			int eigentuemerID) throws IllegalArgumentException;

	/**
	 * Ausgabe der Kontakte der Eigenschaften und der Ausprägungen, anhand der Objekte Nutzer, Eigenschaft und 
	 * Eigenschaftsauspraegung
	 * @param nutzer
	 * @param eigenschaft
	 * @param auspraegung
	 * @return Vector<Kontakt>, Kontakte, Eigenschaften und Eigenschaftsausprägungen
	 * @throws IllegalArgumentException
	 */
	public Vector<Kontakt> findAllKontakteEigenschaftAuspraegung(Nutzer nutzer, Eigenschaft eigenschaft,
			Eigenschaftsauspraegung auspraegung) throws IllegalArgumentException;

	public Vector<Teilhaberschaft> findAllTeilhaberschaftByKontaktliste(int kontaktlisteID) throws IllegalArgumentException;

	public Vector<Teilhaberschaft> findAllTeilhaberschaftByAuspraegung(int auspraegungID) throws IllegalArgumentException;

	public Vector<EigenschaftsAuspraegungWrapper> findAllEigenschaftsauspraegungWrapper(Nutzer nutzer, Kontakt kontakt) throws IllegalArgumentException;

	public Vector<Eigenschaftsauspraegung> findAllGeteiltAndEigeneAuspraegungen(int nutzerID) throws IllegalArgumentException; 

}
