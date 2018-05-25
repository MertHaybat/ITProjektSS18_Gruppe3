package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.itprojektss18Gruppe3.client.gui.MenuTree;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.LoginService;
import de.hdm.itprojektss18Gruppe3.shared.LoginServiceAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS18Gruppe3 implements EntryPoint {
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private VerticalPanel selectPanel = new VerticalPanel();
	private VerticalPanel vPanelBar = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the Kontaktmanager application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private Button zumReportGenerator = new Button("Zum Report Generator");
	private Button zumKontaktmanager = new Button("Zum Kontaktmanager");
	private Button loginButton = new Button("Login");
	private Button zurClientAuswahl = new Button("Zurück zur Client-Auswahl");

	@Override
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html", new LoginCallback());

	}

	private void loadLogin() {

		loginButton.addClickHandler(new loginButtonClickHandler());

		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
		RootPanel.get("content").add(loginPanel);

	}

	private void loadKontaktmanager() {
		zurClientAuswahl.addClickHandler(new ClientAuswahlClickHandler());
		vPanelBar.add(zurClientAuswahl);
		RootPanel.get("content").clear();
		RootPanel.get("leftmenutree").clear();
		RootPanel.get("menubar").add(vPanelBar);

		MenuTree menutree = new MenuTree();
		signOutLink.setHref(loginInfo.getLogoutUrl());
		menutree.addWidget(signOutLink);
	}

	class ClientAuswahlClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			signInLink.setHref(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html");
			Window.open(signInLink.getHref(), "_self", "");
		}

	}

	class zumReportClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			signInLink.setHref(GWT.getHostPageBaseURL() + "KontaktmanagerReport.html");
			Window.open(signInLink.getHref(), "_self", "");
		}

	}

	class zumKontaktmanagerClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			loadKontaktmanager();
		}

	}

	class loginButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			signInLink.setHref(loginInfo.getLoginUrl());
			Window.open(signInLink.getHref(), "_self", "");
		}

	}

	class LoginCallback implements AsyncCallback<LoginInfo> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Login: " + caught.getMessage());
		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;
			if (loginInfo.isLoggedIn()) {
				kontaktmanagerVerwaltung.checkEmail(loginInfo.getEmailAddress(), new FindNutzerCallback());
			
			} else {
				loadLogin();
			}
		}

	}

	class FindNutzerCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Login: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Nutzer result) {
			if (result != null) {
				RootPanel.get("content").clear();
				selectPanel.add(zumKontaktmanager);
				selectPanel.add(zumReportGenerator);
				zumKontaktmanager.addClickHandler(new zumKontaktmanagerClickHandler());
				zumReportGenerator.addClickHandler(new zumReportClickHandler());
				RootPanel.get("content").add(selectPanel);
				Cookies.setCookie("email", result.getMail());
				Cookies.setCookie("id", result.getId()+"");
			} else {
				CreateNutzerDialogBox dialogbox = new CreateNutzerDialogBox(loginInfo.getEmailAddress());
				dialogbox.center();


			}
		}

	}

	class CreateNutzerDialogBox extends DialogBox {
		private Label frage = new Label(
				"Sie haben noch keinen Nutzer auf diesem Kontaktmanager. Möchten Sie eins erstellen?");
		private Button ja = new Button("Ja");
		private Button nein = new Button("Nein");
		private String googleMail = "";
		private VerticalPanel vpanel = new VerticalPanel();

		public CreateNutzerDialogBox(String mail) {
			googleMail = mail;
			ja.addClickHandler(new CreateNutzerClickHandler());
			nein.addClickHandler(new DontCreateNutzerClickHandler());
			vpanel.add(frage);
			vpanel.add(ja);
			vpanel.add(nein);
			this.add(vpanel);

		}

		class CreateNutzerCallback implements AsyncCallback<Nutzer> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ihr User konnte nicht erstellt werden" + caught.getMessage());
			}

			@Override
			public void onSuccess(Nutzer result) {
				Window.alert("Ihr Nutzer wurde erfolgreich erstellt");
				Cookies.setCookie("email", result.getMail());
				Cookies.setCookie("id", result.getId()+"");
				loadKontaktmanager();
				hide();
			}

		}

		class CreateNutzerClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				kontaktmanagerVerwaltung.createNutzer(googleMail, new CreateNutzerCallback());

			}

		}

		class DontCreateNutzerClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				signOutLink.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutLink.getHref(), "_self", "");

			}

		}
	}
}
