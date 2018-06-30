package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.GWT;

import de.hdm.itprojektss18Gruppe3.shared.CommonSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministration;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.ReportGenerator;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;


/**
 * Klasse für die Clientseitingen Einstellungen des Projekts. Die Proxy Objekte
 * werden hier instanziiert.
 * 
 * @version 1.0 30 June 2018
 * @author Mert
 *
 */
public class ClientsideSettings extends CommonSettings {

	/**
	 * Deklarieren der Instanzvariablen der Proxy Objekte
	 */
	private static KontaktmanagerAdministrationAsync kontaktVerwaltung = null;
	private static ReportGeneratorAsync reportGenerator = null;

	/**
	 * Methode zum Instanziieren des Proxy Objektes
	 * 
	 * @return kontaktVerwaltung: Instanzvariable des Proxy Objekts
	 */
	public static KontaktmanagerAdministrationAsync getKontaktVerwaltung() {
		if (kontaktVerwaltung == null) {
			kontaktVerwaltung = GWT.create(KontaktmanagerAdministration.class);
		}
		return kontaktVerwaltung;
	}

	/**
	 * Methode zum Instanziieren des Proxy Objektes
	 * 
	 * @return kontaktVerwaltung: Instanzvariable des Proxy Objekts
	 */
	public static ReportGeneratorAsync getReportGenerator() {

		if (reportGenerator == null) {
			reportGenerator = GWT.create(ReportGenerator.class);

		}
		return reportGenerator;
	}

}
