package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteByTeilhaberschaftReport;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;

/**
 * Die Klasse für den Callback des ersten Reports: Alle Kontakte einer Teilhaberschaft.
 * 
 * @version 1.0 30 June 2018
 * @author Mert
 *
 */
public class AllKontaktTeilhaberschaften extends HTMLResultPanel{

	/**
	 * Instanziierung des Proxy Objekts für den Report Generator
	 */
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	/**
	 * Konstruktor der Klasse AllKontaktTeilhaberschaften
	 * 
	 * @param eigentuemer: In der GUI eigegebene Eigentümer einer Teilhaberschaft
	 * @param teilhabender: In der GUI eigegebene Teilhaber einer Teilhaberschaft
	 */
	public AllKontaktTeilhaberschaften(String eigentuemer, String teilhabender) {


		reportverwaltung.createAlleKontakteByTeilhaberschaftReport(eigentuemer, teilhabender,
				new CreateAlleKontakteByTeilhaberschaftCallback());

	}


	/**
	 * Nested Class für den AsyncCallback createAlleKontakteByTeilhaberschaftReport
	 */
	class CreateAlleKontakteByTeilhaberschaftCallback implements AsyncCallback<AlleKontakteByTeilhaberschaftReport> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(AlleKontakteByTeilhaberschaftReport result) {
			int resultSize = result.getRows().size();
			if(resultSize == 0){
				Window.alert("Es gibt keine Teilhaberschaften");
			}
			HTMLReportWriter hrw = new HTMLReportWriter();
			hrw.process(result);
			append(hrw.getReportText());
		}

	}

}
