package de.hdm.itprojektss18Gruppe3.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;

import de.hdm.itprojektss18Gruppe3.shared.CommonSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministration;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.ReportGenerator;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;

public class ClientsideSettings extends CommonSettings{

	private static KontaktmanagerAdministrationAsync kontaktVerwaltung = null;
	
	private static ReportGeneratorAsync reportGenerator = null;
	  /**
	   * Name des Client-seitigen Loggers.
	   */
	  private static final String LOGGER_NAME = "Kontaktmanager Web Client";
	  
	  /**
	   * Instanz des Client-seitigen Loggers.
	   */
	  private static final Logger log = Logger.getLogger(LOGGER_NAME);
	
	
	public static KontaktmanagerAdministrationAsync getKontaktVerwaltung(){
		if (kontaktVerwaltung == null){
			kontaktVerwaltung = GWT.create(KontaktmanagerAdministration.class);
		}
		return kontaktVerwaltung;
	}
	
	public static ReportGeneratorAsync getReportGenerator() {

		if (reportGenerator == null) {
			reportGenerator = GWT.create(ReportGenerator.class);

			
		}
		return reportGenerator;
	}
	
	  public static Logger getLogger() {
		    return log;
		  }
}
