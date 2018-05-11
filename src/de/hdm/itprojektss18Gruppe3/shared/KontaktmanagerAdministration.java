package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Person;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Synchrone Schnittstelle f�r eine RPC-f�hige Klasse zur Verwaltung von Kontakten.
 * 
 * @author Thomas, Mert
 */
@RemoteServiceRelativePath("kontaktmanager")
public interface KontaktmanagerAdministration extends RemoteService {
	
	/**
	 * Initialisierung des Objekts. Diese ist haupts�chlich
	 * zum No Argument Constructor der implementierenden Klasse notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
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
	 * @param eigenschaftID; ist die dazu gehörige Eigenschaft
	 * @return Eigenschaftsauspraegung; Zurückgegeben wird ein Objekt der Klasse Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	public Eigenschaftsauspraegung createEigenschaftsauspraegung(String wert, int personID, int status, int eigenschaftID) throws IllegalArgumentException;
	
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
	public Kontakt findKontaktByID(Kontakt k) throws IllegalArgumentException;
	
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
	public Vector <Eigenschaft> findAllEigenschaftByEigenschaftIDFromPerson(Eigenschaft e) throws IllegalArgumentException;
	
	/**
	 * Alle Kontakte von einer Eigenschaftsausprägung anzeigen lassen. Dies ist die Suchfunktion.
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsausprägung
	 * @return Vector des Typs Kontakt
	 * @throws IllegalArgumentException
	 */
	public Vector <Kontakt> findAllKontaktByEigenschaftsauspraegung(Eigenschaftsauspraegung e) throws IllegalArgumentException;
	
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
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByWert(Eigenschaftsauspraegung e) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Person (Nutzer/Kontakt)
	 * 
	 * @param p; Objekt der Klasse Person
	 * @return Objekt des Typs Person
	 * @throws IllegalArgumentException
	 */
	public Person deletePerson(Person p) throws IllegalArgumentException;
	
	/**
	 * Löschen der Beziehung zwischen KontaktKontaktliste
	 * 
	 * @param k; Objekt der Klasse KontaktKontaktliste
	 * @return Objekt des Typs KontaktKontaktliste
	 * @throws IllegalArgumentException
	 */
	public KontaktKontaktliste deleteKontaktKontaktliste(KontaktKontaktliste k) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Teilhaberschaft mit der PersonID
	 * 
	 * @param t; Objekt der Klasse Teilhaberschaft
	 * @return Objekt des Typs Teilhaberschaft
	 * @throws IllegalArgumentException
	 */
	public Teilhaberschaft deleteTeilhaberschaftByPersonID(Teilhaberschaft t) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Kontaktliste mit der NutzerID
	 * 
	 * @param k; Objekt der Klasse Kontaktliste
	 * @return Objekt des Typs Kontaktliste
	 * @throws IllegalArgumentException
	 */
	public Kontaktliste deleteKontaktlisteByNutzerID(Kontaktliste k) throws IllegalArgumentException;
	
	/**
	 * Löschen einer Eigenschaftsausprägung mit der PersonID
	 * 
	 * @param e; Objekt der Klasse Eigenschaftsauspraegung
	 * @return Objekt des Typs Eigenschaftsauspraegung
	 * @throws IllegalArgumentException
	 */
	public Eigenschaftsauspraegung deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung e) throws IllegalArgumentException;
	
	
	
	
	
}
