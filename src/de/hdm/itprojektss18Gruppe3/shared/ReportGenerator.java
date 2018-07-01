package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
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
	
	  KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(
			String nutzer, String eig, String auspraegung);

	  public Vector<Nutzer> findNutzer() throws IllegalArgumentException;

	  public Nutzer findNutzerByMail(String email) throws IllegalArgumentException;

	  public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException;

	  public Eigenschaft findEigenschaftByBezeichnung(String bezeichnung) throws IllegalArgumentException;

	  public Vector<EigenschaftsAuspraegungWrapper> findEigenschaftWrapper(Kontakt kontakt) throws IllegalArgumentException;

	  Vector<NutzerTeilhaberschaftKontaktWrapper> findAllKontakteAndTeilhaberschaftenByNutzer(Nutzer nutzer);

	  Nutzer nutzerTeilhaberschaft(int teilhaberid) throws IllegalArgumentException;

}
