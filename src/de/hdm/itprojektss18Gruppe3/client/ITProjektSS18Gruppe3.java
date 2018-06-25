package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView;
import de.hdm.itprojektss18Gruppe3.client.gui.CustomTreeModel;
import de.hdm.itprojektss18Gruppe3.client.gui.DisclosurePanelSuche;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.LoginService;
import de.hdm.itprojektss18Gruppe3.shared.LoginServiceAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS18Gruppe3 implements EntryPoint {
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label welcomeMessage = new Label("Willkommen beim Kontaktmanager");
	private Label loginMessage = new Label("Bitte loggen Sie sich mit Ihrem" + " Google Account ein");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private TextBox textBox = new TextBox();
	private Button suchenButton = new Button("Detailsuche");
	private Button logoutButton = new Button("Ausloggen");
	private Button loginButton = new Button("LOGIN");

	@Override
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html", new LoginCallback());

	}

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
	}

	private void loadKontaktmanager() {
		textBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					DisclosurePanelSuche panelSuche = new DisclosurePanelSuche(textBox.getValue());
					RootPanel.get("content").clear();
					RootPanel.get("content").add(panelSuche);
					textBox.setText("");
				}
			}
		});
		HorizontalPanel flowpanel = new HorizontalPanel();
		// Cookies.setCookie("logout", loginInfo.getLogoutUrl());
		textBox.setStylePrimaryName("searchTextBox");
		textBox.setMaxLength(100);
		textBox.getElement().setPropertyString("placeholder", " Schnellsuche...");
		flowpanel.add(textBox);
		flowpanel.setStylePrimaryName("logoutBarContainer");
		RootPanel.get("logout").add(flowpanel);// hPanelBar.add(logoutButton);
		RootPanel.get("leftmenutree").clear();
		AllKontaktView kontaktView = new AllKontaktView();

		// AUFRUF DES BAUMS
		CustomTreeModel ctm = new CustomTreeModel();
		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(ctm);

		signOutLink.setHref(loginInfo.getLogoutUrl());

		Menubar mb = new Menubar();
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
				Cookies.setCookie("email", result.getMail());
				Cookies.setCookie("id", result.getId() + "");
				loadKontaktmanager();
			} else {
				CreateNutzerDialogBox dialogbox = new CreateNutzerDialogBox(loginInfo.getEmailAddress());
				dialogbox.center();

			}
		}

	}

	class CreateNutzerDialogBox extends DialogBox {
		private Label frage = new Label(
				"Sie haben noch keinen Nutzer auf diesem Kontaktmanager. Möchten Sie einen neuen Nutzer anlegen?");
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
				Window.alert("Ihr Nutzer wurde erfolgreich angelegt");
				Cookies.setCookie("email", result.getMail());
				Cookies.setCookie("id", result.getId() + "");
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

	public static class SuchenCommand implements Command {

		@Override
		public void execute() {
			DisclosurePanelSuche panelSuche = new DisclosurePanelSuche();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(panelSuche);
		}
	}

}
