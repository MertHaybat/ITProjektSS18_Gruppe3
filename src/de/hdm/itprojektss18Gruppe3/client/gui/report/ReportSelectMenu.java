package de.hdm.itprojektss18Gruppe3.client.gui.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.AllKontakte;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.LoginInfo;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.LoginService;
import de.hdm.itprojektss18Gruppe3.shared.LoginServiceAsync;

public class ReportSelectMenu extends FlowPanel {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private LoginInfo loginInfo = null;
	private Button bt1 = new Button("Alle Kontakte");
	private Button bt2 = new Button("Alle Teilhaberschaften");
	private Button bt3 = new Button("Alle Eigenschaften");
	private Button logoutButton = new Button("Logout");
	private Anchor signInLink = new Anchor();
	private Anchor signOutLink = new Anchor("Sign Out");

	public ReportSelectMenu() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "KontaktmanagerReport.html", new LoginCallback());

		bt1.addClickHandler(new reportEinsClickHandler());
		bt1.setStylePrimaryName("reportButton");

		bt2.addClickHandler(new reportZweiClickHandler());
		bt2.setStylePrimaryName("reportButton");

		bt3.addClickHandler(new reportDreiClickHandler());
		bt3.setStylePrimaryName("reportButton");

		logoutButton.addClickHandler(new logoutClickHandler());
		logoutButton.setStylePrimaryName("reportButton");
		logoutButton.addStyleName("reportButtonFloat");

		this.add(bt1);
		this.add(bt2);
		this.add(bt3);
		this.add(logoutButton);
	}

	class reportEinsClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			AllKontakte allKontakteReport = new AllKontakte();
			RootPanel.get("contentReport").clear();
			RootPanel.get("contentReport").add(allKontakteReport);
		}

	}

	class reportZweiClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TeilnahmenReportForm teilnahmenReportForm = new TeilnahmenReportForm();
			RootPanel.get("contentReport").clear();
			RootPanel.get("contentReport").add(teilnahmenReportForm);
		}

	}

	class reportDreiClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			EigenschaftenReportForm eigenschaftenReportForm = new EigenschaftenReportForm();
			RootPanel.get("contentReport").clear();
			RootPanel.get("contentReport").add(eigenschaftenReportForm);
		}

	}

	class LoginCallback implements AsyncCallback<LoginInfo> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;
		}
	}

	public class logoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			signOutLink.setHref(loginInfo.getLogoutUrl());
			Window.open(signOutLink.getHref(), "_self", "");
		}
	}
}
