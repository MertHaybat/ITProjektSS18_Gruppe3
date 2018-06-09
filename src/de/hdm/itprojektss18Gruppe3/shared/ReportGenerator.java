package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
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
	   *BankAdministrationImpltungImpl} notwendig. Bitte diese Methode direkt nach der
	   * Instantiierung aufrufen.
	   * 
	   * @throws IllegalArgumentException
	   */
	  public void init() throws IllegalArgumentException;
	  
	  /**
	   * Diese Methode sucht alle Kontakte und gibt diese zurück.
	   * @param
	   * @return Alle Kontakte
	   * @throws IllegalArgumentException
	   */
	  public abstract AlleKontakteReport createAlleKontakteReport(String lbEmail) 
			  throws IllegalArgumentException;
	  
	  /**
	   * Diese Methode sucht alle Kontakte anhand einer Teilhaberschaft und gibt diese zurück.
	   * @param
	   * @return Alle Kontakte einer bestimmten Teilhaberschaft
	   * @throws IllegalArgumentException
	   */
	  public abstract AlleKontakteByTeilhaberschaftReport createAlleKontakteByTeilhaberschaftReport(String a, String b)
				throws IllegalArgumentException;
	
	  /**
	   * Diese Methode sucht Kontakte mit übergebenen Eigenschaften und Ausprägungen.
	   * @param
	   * @return Kontakte, die bestimmte Eigenschaften und Eigenschaftsausprägungen besitzen
	   * @throws IllegalArgumentException
	   */
	  public abstract KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(
			String eig, String auspraegung) throws IllegalArgumentException;

	  public Vector<Nutzer> findNutzer() throws IllegalArgumentException;

	  public Nutzer findNutzerByMail(String email) throws IllegalArgumentException;

	  public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException;

	  public Eigenschaft findEigenschaftByBezeichnung(String bezeichnung) throws IllegalArgumentException;

}
