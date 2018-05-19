package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;
import de.hdm.itprojektss18Gruppe3.shared.report.KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport;

public class AllKontaktEigenschaftenAndAuspraegungen extends HTMLResultPanel{
	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();
	
	Eigenschaft eigenschaft = new Eigenschaft();
	Eigenschaftsauspraegung auspraeung = new Eigenschaftsauspraegung();
	
	public AllKontaktEigenschaftenAndAuspraegungen(){
		eigenschaft.setId(1);
		auspraeung.setWert("*er*");
		
		reportverwaltung.createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(eigenschaft, auspraeung, new AllKontaktEigenschaftAndAuspraegungenCallback());
	}
	
	class AllKontaktEigenschaftAndAuspraegungenCallback implements AsyncCallback<KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Seite ist aufgetreten" + caught.getMessage());
		}

		@Override
		public void onSuccess(KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport result) {
			HTMLReportWriter hrw = new HTMLReportWriter();
			hrw.process(result);
			append(hrw.getReportText());
		}
		
	}

}
