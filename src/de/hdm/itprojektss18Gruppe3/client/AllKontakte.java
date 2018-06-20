package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;

public class AllKontakte extends HTMLResultPanel{
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	public AllKontakte() {
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("email"));
		reportverwaltung.createAlleKontakteReport(nutzer, new CreateAlleKontakteReportCallback());
	}
	
	
	
	class CreateAlleKontakteReportCallback implements AsyncCallback<AlleKontakteReport>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Seite" + caught.getMessage());
		}

		@Override
		public void onSuccess(AlleKontakteReport result) {
			HTMLReportWriter hrw = new HTMLReportWriter();
			hrw.process(result);
			append(hrw.getReportText());			
		}
		
	}
}
