package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;
import de.hdm.itprojektss18Gruppe3.shared.report.KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport;

/**
 * Die Klasse für den Callback des ersten Reports: Alle Kontakte einer Eigenschaft und/oder Eigenschaftsausprägung.
 * 
 * @version 1.0 30 June 2018
 * @author Mert
 *
 */
public class AllKontaktEigenschaftenAndAuspraegungen extends HTMLResultPanel {

	/**
	 * Instanziierung des Proxy Objekts für den Report Generator
	 */
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	/**
	 * Konstruktor der Klasse
	 * 
	 * @param eigenschaft: In der GUI eingegebene Eigenschaft
	 * @param auspraegung: In der GUI eingegebene Eigenschaftsausprägung
	 */
	public AllKontaktEigenschaftenAndAuspraegungen(String nutzer, String eigenschaft, String auspraegung) {

		reportverwaltung.createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(nutzer, eigenschaft, auspraegung,
				new AllKontaktEigenschaftAndAuspraegungenCallback());
	}

	/**
	 * Nested Class für den AsyncCallback createKontakteMiteBestimmtenEigenschaftenUndAuspraegungenReport
	 *
	 */
	class AllKontaktEigenschaftAndAuspraegungenCallback
			implements AsyncCallback<KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Seite ist aufgetreten" + caught.getMessage());
		}

		@Override
		public void onSuccess(KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport result) {
			int resultSize = result.getRows().size();
			if(resultSize == 0){
				Window.alert("Es gibt keine Kontakte mit den eingegebenen Eigenschaften und Eigenschaftsausprägungen");
			}
			HTMLReportWriter hrw = new HTMLReportWriter();
			hrw.process(result);
			append(hrw.getReportText());
		}

	}

}
