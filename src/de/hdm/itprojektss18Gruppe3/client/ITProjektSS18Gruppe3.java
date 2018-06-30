package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView;
import de.hdm.itprojektss18Gruppe3.client.gui.CustomTreeModel;
import de.hdm.itprojektss18Gruppe3.client.gui.DisclosurePanelSuche;
import de.hdm.itprojektss18Gruppe3.client.gui.Footer;
import de.hdm.itprojektss18Gruppe3.client.gui.Menubar;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.LoginService;
import de.hdm.itprojektss18Gruppe3.shared.LoginServiceAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/**
 * EntryPoint-Klasse des Moduls: itprojektss18gruppe3
 * 
 * Info: Da die GUI-Klassen nur eine Instanziierung benötigen für das Ausführen auf der Oberfläche
 * werden die Objekte als unused markiert. Um dies zu umgehen und evtl. missverständnisse zu vermeiden
 * werden "unused-warnings" ausgeblendet.
 * 
 * @version 1.10 30 June 2018
 * @author Mert
 *
 */
@SuppressWarnings("unused")
public class ITProjektSS18Gruppe3 implements EntryPoint {
	
	/**
	 * Deklarierung der Klasse LoginInfo für die Google API
	 */
	private LoginInfo loginInfo = null;
	
	/**
	 * Instanziierung der GUI Objekten: Panels, Label, Anchor und Button
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	
	private Label welcomeMessage = new Label("Willkommen beim Kontaktmanager");
	private Label loginMessage = new Label("Bitte loggen Sie sich mit Ihrem" + " Google Account ein");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	private Button loginButton = new Button("LOGIN");
	
	/**
	 * Instanziierung des Proxys
	 */
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	/**
	 * onModuleLoad des Moduls: itprojektss18gruppe3
	 * Ist die main-Methode in einem GWT-Projekt
	 */
	@Override
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html", new LoginCallback());

	}

	/**
	 * Die loadLogin() Methode wird verwendet für das Anzeigen der API
	 */
	private void loadLogin() {

		loginButton.addClickHandler(new loginButtonClickHandler());
		loginButton.setStylePrimaryName("loginButton");
		welcomeMessage.setStylePrimaryName("landingPageWelcomeMessage");
		loginPanel.setStylePrimaryName("loginPanel");
		loginMessage.setStylePrimaryName("landingPageLoginMessage");
		loginPanel.add(welcomeMessage);
		loginPanel.add(loginMessage);
		loginPanel.add(loginButton);
		RootPanel.get("content").add(loginPanel);
		Footer footer = new Footer();
	}

	/**
	 * Die loadKontaktmanager() Methode wird aufgerufen wenn der User bereits existiert. 
	 * Der User wird weitergeleitet auf den Kontaktmanager
	 */
	private void loadKontaktmanager() {
		Footer footer = new Footer();
		AllKontaktView kontaktView = new AllKontaktView();

		// AUFRUF DES BAUMS
		CustomTreeModel ctm = new CustomTreeModel();
		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(ctm);

		signOutLink.setHref(loginInfo.getLogoutUrl());

		Menubar mb = new Menubar();
	}

	/**
	 * Nested Class für den Login Button
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
	 * Nested Class für den Login Callback
	 * In der onSuccess wird überprüft, ob der User eingeloggt. Wenn er eingeloggt ist, wird mit der checkEmail
	 * überprüft ob die E-Mail Adresse bereits in der Datenbank existiert. 
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
				kontaktmanagerVerwaltung.checkEmail(loginInfo.getEmailAddress(), new FindNutzerCallback());

			} else {
				loadLogin();
			}
		}

	}

	/**
	 * Nested Class für den AsyncCallback checkEmail
	 * Wenn der User bereits existiert werden zwei Cookies erstellt und der Kontaktmanager geladen.
	 * Wenn nicht wird eine DialogBox geöffnet, für die Abfrage, ob der User sich registrieren will.
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
				Cookies.setCookie("signout", loginInfo.getLogoutUrl());
				loadKontaktmanager();
			} else {
				CreateNutzerDialogBox dialogbox = new CreateNutzerDialogBox(loginInfo.getEmailAddress());
				dialogbox.center();

			}
		}

	}
	
	/**
	 * Nested Class einer DialogBox für die Nutzer Erstellung Abfrage.
	 * @author Mert
	 *
	 */
	class CreateNutzerDialogBox extends DialogBox {
		/**
		 * Instanziierung der GUI-Elemente 
		 */
		private Label frage = new Label(
				"Sie haben noch keinen Nutzer auf diesem Kontaktmanager. Möchten Sie einen neuen Nutzer anlegen?");
		private Button ja = new Button("Ja");
		private Button nein = new Button("Nein");
		private VerticalPanel vpanel = new VerticalPanel();
		/**
		 * Instanziierung der googleMail. Diese speichert später die übergebene gmail Adresse.
		 */
		private String googleMail = "";

		/**
		 * Konstruktor der aufgerufen wird.
		 * @param mail: Email Adresse des angemeldeten Nutzers.
		 */
		public CreateNutzerDialogBox(String mail) {
			googleMail = mail;
			ja.addClickHandler(new CreateNutzerClickHandler());
			nein.addClickHandler(new DontCreateNutzerClickHandler());
			vpanel.add(frage);
			vpanel.add(ja);
			vpanel.add(nein);
			this.add(vpanel);

		}

		/**
		 * Nested Class in der DialogBox. Wenn Nutzer einen User erstellen möchte, gibt es diesen Callback Aufruf.
		 *
		 */
		class CreateNutzerCallback implements AsyncCallback<Nutzer> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ihr User konnte nicht erstellt werden" + caught.getMessage());
			}

			@Override
			public void onSuccess(Nutzer result) {
				Window.alert("Ihr Nutzer wurde erfolgreich angelegt");
				Cookies.setCookie("signout", loginInfo.getLogoutUrl());
				Cookies.setCookie("email", result.getMail());
				Cookies.setCookie("id", result.getId() + "");
				hide();
				loadKontaktmanager();
			}

		}
		/**
		 * Nested Class in der DialogBox. Wenn Nutzer einen User erstellen möchte, gibt es diesen ClickHandler Aufruf.
		 *
		 */
		class CreateNutzerClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				kontaktmanagerVerwaltung.createNutzer(googleMail, new CreateNutzerCallback());
	
			}

		}

		/**
		 * Nested Class in der DialogBox. Wenn Nutzer keinen User erstellen möchte, gibt es diesen ClickHandler Aufruf.
		 * Der User wird dann auf die Startseite weitergeleitet.
		 *
		 */
		class DontCreateNutzerClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				signOutLink.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutLink.getHref(), "_self", "");

			}

		}
		
	}
	public static class SuchenCommand implements Command {

		@Override
		public void execute() {
			DisclosurePanelSuche panelSuche = new DisclosurePanelSuche();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(panelSuche);
		}
	}

}
