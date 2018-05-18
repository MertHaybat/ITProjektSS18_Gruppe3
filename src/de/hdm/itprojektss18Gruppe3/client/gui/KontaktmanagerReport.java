package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.report.AllKontakteReport;
import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.HTMLReportWriter;

public class KontaktmanagerReport extends HTMLResultPanel implements EntryPoint {

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();
	
	
	@Override
	public void onModuleLoad() {
		RootPanel.get("content").clear();
		RootPanel.get("content").add(new AllKontakteReport());
	}

}
