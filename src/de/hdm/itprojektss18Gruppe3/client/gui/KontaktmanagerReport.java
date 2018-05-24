package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.client.gui.report.ReportSelectMenu;

public class KontaktmanagerReport extends HTMLResultPanel implements EntryPoint {

	private Button zurClientAuswahl = new Button("Zurück zur Client-Auswahl");
	private VerticalPanel vPanelBar = new VerticalPanel();
	private Anchor signInLink = new Anchor();

	@Override
	public void onModuleLoad() {
		vPanelBar.add(zurClientAuswahl);

		ReportSelectMenu reportMenu = new ReportSelectMenu();
		RootPanel.get("leftmenutree").add(reportMenu);
		RootPanel.get("menubar").add(vPanelBar);
		
		zurClientAuswahl.addClickHandler(new ClientAuswahlClickHandler());

	}

	class ClientAuswahlClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			signInLink.setHref(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html");
			Window.open(signInLink.getHref(), "_self", "");
		}

	}
}
