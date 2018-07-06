package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;

/**
 * Die Klasse für den Callback des ersten Reports: Alle eigenen Kontakte anzeigen.
 * 
 * @version 1.0 30 June 2018
 * @author Mert
 *
 */
public class AllKontakte extends HTMLResultPanel{

	/**
	 * Instanziierung des Proxy Objekts für den Report Generator
	 */
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();


	/**
	 * Konstruktor der Klasse AllKontakte
	 */
	public AllKontakte() {
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("email"));
		reportverwaltung.createAlleKontakteReport(nutzer, new CreateAlleKontakteReportCallback());
	}


	/**
	 * Nested Class für den AsyncCallback createAlleKontakteReport  
	 *
	 */
	class CreateAlleKontakteReportCallback implements AsyncCallback<AlleKontakteReport>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Seite" + caught.getMessage());
		}

		@Override
		public void onSuccess(AlleKontakteReport result) {
			int resultSize = result.getRows().size();
			if(resultSize == 0){
				Window.alert("Es gibt keine Kontakte");
			} else {
				HTMLReportWriter hrw = new HTMLReportWriter();
				hrw.process(result);
				append(hrw.getReportText());			
			}
		}

	}
}
