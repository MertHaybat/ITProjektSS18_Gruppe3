package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteByTeilhaberschaftReport;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport;

/**
 * 
 * 
 * @author thies
 */
@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService {


	  /**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
	   * {KontaktmanagerAdministrationImpl} notwendig. Bitte diese Methode direkt nach der
	   * Instantiierung aufrufen.
	   * 
	   * @throws IllegalArgumentException
	   */
	  public void init() throws IllegalArgumentException;
	  
	  /**
	   * Diese Methode sucht alle Kontakte und gibt diese zurück.
	   * 
	   * @param lbEmail;
	   * 			die Emailadresse des Nutzers zu dem alle Kontakte ausgegeben werden sollen.
	   * @return Alle Kontakte
	   * @throws IllegalArgumentException
	   */
	  public abstract AlleKontakteReport createAlleKontakteReport(Nutzer nutzer) 
			  throws IllegalArgumentException;
	  
	  /**
	   * Diese Methode sucht alle Kontakte anhand einer Teilhaberschaft und gibt diese zurück.
	   * 
	   * @param a, b;
	   * 			die Emailadressen der Nutzer, die miteinander Kontakte geteilt haben.
	   * 
	   * @return Alle Kontakte einer bestimmten Teilhaberschaft
	   * @throws IllegalArgumentException
	   */
	  public abstract AlleKontakteByTeilhaberschaftReport createAlleKontakteByTeilhaberschaftReport(String a, String b)
				throws IllegalArgumentException;
	
	  /**
	   * Diese Methode sucht alle Kontakte mit bestimmten Eigenschaften und Ausprägungen und gibt diese zurück 
	   * @param nutzer
	   * @param eig
	   * @param auspraegung
	   * @return Alle Kontakte einer bestimmten Eigenschaft und Ausprägung 
	   */
	  KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(
			String nutzer, String eig, String auspraegung);

	  /**
	   * Diese Methode sucht einen Nutzer  
	   * @return <Nutzer>
	   * @throws IllegalArgumentException
	   */
	  public Vector<Nutzer> findNutzer() throws IllegalArgumentException;

	  /**
	   * Diese Methode sucht einen Nutzer über seine Email 
	   * @param email
	   * @return Nutzer 
	   * @throws IllegalArgumentException
	   */
	  public Nutzer findNutzerByMail(String email) throws IllegalArgumentException;

	  /**
	   * Diese Methode sucht alle Eigenschaften und lädt sie in einen Vektor
	   * @return Vector<Eigenschaft>
	   * @throws IllegalArgumentException
	   */
	  public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException;

	  /**
	   * Diese Methode sucht eine Eigenschaft nach ihrer Bezeichnung
	   * @param bezeichnung
	   * @return Eigenschaft
	   * @throws IllegalArgumentException
	   */
	  public Eigenschaft findEigenschaftByBezeichnung(String bezeichnung) throws IllegalArgumentException;

	  /**
	   * Diese Methode sucht die Eigenschaften nach eines Kontakt-Objektes
	   * @param kontakt
	   * @return Vector<EigenschaftsAuspraegungWrapper>
	   * @throws IllegalArgumentException
	   */
	  public Vector<EigenschaftsAuspraegungWrapper> findEigenschaftWrapper(Kontakt kontakt) throws IllegalArgumentException;

	  /**
	   * Diese Methode sucht alle Kontakte und Teilhaberschaften anhand eines Nutzer-Objektes und
	   * lädt sie in einen Vektor 
	   * @param nutzer
	   * @return  Vector<NutzerTeilhaberschaftKontaktWrapper>
	   */
	  Vector<NutzerTeilhaberschaftKontaktWrapper> findAllKontakteAndTeilhaberschaftenByNutzer(Nutzer nutzer);

	  /**
	   * Diese Methode gibt den Nutzer einer Teilhaberschaft zurück anhand des Primärschlüssels des Teilhabenden
	   * @param teilhaberid
	   * @return Nutzer der bestimmten Teilhaberschaft (anhand der ID)
	   * @throws IllegalArgumentException
	   */
	  Nutzer nutzerTeilhaberschaft(int teilhaberid) throws IllegalArgumentException;

	Vector<Eigenschaftsauspraegung> findAllAuspraegungen(int nutzerID) throws IllegalArgumentException;

}
