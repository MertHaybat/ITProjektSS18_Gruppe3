package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.client.gui.report.ReportSelectMenu;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.LoginService;
import de.hdm.itprojektss18Gruppe3.shared.LoginServiceAsync;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class KontaktmanagerReport implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private VerticalPanel vPanelBar = new VerticalPanel();
	private HorizontalPanel hPanelBar = new HorizontalPanel();
	private Anchor signInLink = new Anchor();
	private Anchor signOutLink = new Anchor("Sign Out");
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the Kontaktmanager Report application.");
	private Button logoutButton = new Button("Ausloggen");
	private Button loginButton = new Button("Login");
	private static ReportGeneratorAsync reportVerwaltung = ClientsideSettings.getReportGenerator();
	
	@Override
	public void onModuleLoad() {
		
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "KontaktmanagerReport.html", new LoginCallback());


	}
	/**
	 * Erstellen eines LoginCallbacks 
	 *
	 */
	class LoginCallback implements AsyncCallback<LoginInfo> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Login: " + caught.getMessage());
		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;
			if (loginInfo.isLoggedIn()) {
				reportVerwaltung.findNutzerByMail(loginInfo.getEmailAddress(), new FindNutzerCallback());

			} else {
				loadLogin();
			}
		}

	}
	/**
	 * CLickHandler für den LoginButton
	 *
	 */
	class loginButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			signInLink.setHref(loginInfo.getLoginUrl());
			Window.open(signInLink.getHref(), "_self", "");
		}

	}
	/**
	 * 
	 */
	private void loadLogin() {

		loginButton.addClickHandler(new loginButtonClickHandler());

		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
		RootPanel.get("contentReport").add(loginPanel);

	}
	/**
	 * Callback-Methode um Nutzer zu suchen
	 *
	 */
	class FindNutzerCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Login: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Nutzer result) {
			if (result != null) {
				Cookies.setCookie("email", result.getMail());
				Cookies.setCookie("id", result.getId() + "");
				loadReportgenerator();
			} else {
				Window.alert("Bitte registrieren Sie sich zu erst über den Kontaktmanager.");
				signOutLink.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutLink.getHref(), "_self", "");
			}
		}

	}
	/**
	 * ClickHandler für den logout
	 *
	 */
	public class logoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			signOutLink.setHref(loginInfo.getLogoutUrl());
			Window.open(signOutLink.getHref(), "_self", "");

		}
	}
	private void loadReportgenerator() {
//		vPanelBar.add(logoutButton);
//		Cookies.setCookie("logout", loginInfo.getLogoutUrl());
//		RootPanel.get("logout").add(logoutButton);

		ReportSelectMenu reportMenu = new ReportSelectMenu();
		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(reportMenu);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(reportMenu);
//		RootPanel.get("menubar").clear();
//		RootPanel.get("menubar").add(hPanelBar);
		
	
		signOutLink.setHref(loginInfo.getLogoutUrl());
	}
	
}
