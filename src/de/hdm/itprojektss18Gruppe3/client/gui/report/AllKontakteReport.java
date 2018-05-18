package de.hdm.itprojektss18Gruppe3.client.gui.report;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;

public class AllKontakteReport extends HTMLResultPanel{
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	public AllKontakteReport() {
		reportverwaltung.createAlleKontakteReport(new AsyncCallback<AlleKontakteReport>() {

			@Override
			public void onSuccess(AlleKontakteReport result) {
				HTMLReportWriter hrw = new HTMLReportWriter();
				hrw.process(result);
				append(hrw.getReportText());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
}
