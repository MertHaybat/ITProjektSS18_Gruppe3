package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteByTeilhaberschaftReport;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;

public class AllKontaktTeilhaberschaften extends HTMLResultPanel{
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();
	
	public AllKontaktTeilhaberschaften(String lbNutzerEigentuemer, String lbNutzerTeilhabender) {
	
		reportverwaltung.createAlleKontakteByTeilhaberschaftReport(lbNutzerEigentuemer, lbNutzerTeilhabender,
				new CreateAlleKontakteByTeilhaberschaftCallback());
		
	}

	
	
	class CreateAlleKontakteByTeilhaberschaftCallback implements AsyncCallback<AlleKontakteByTeilhaberschaftReport> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Seite ist aufgetreten" + caught.getMessage());
		}

		@Override
		public void onSuccess(AlleKontakteByTeilhaberschaftReport result) {
			int resultSize = result.getRows().size();
			if(resultSize == 1){
				Window.alert("Es gibt keine Teilhaberschaften");
			}
			HTMLReportWriter hrw = new HTMLReportWriter();
			hrw.process(result);
			append(hrw.getReportText());
		}

	}

}
