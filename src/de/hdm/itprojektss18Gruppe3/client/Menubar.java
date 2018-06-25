package de.hdm.itprojektss18Gruppe3.client;

import java.awt.Window;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ITProjektSS18Gruppe3.FindNutzerCallback;
import de.hdm.itprojektss18Gruppe3.client.ITProjektSS18Gruppe3.LoginCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView;
import de.hdm.itprojektss18Gruppe3.shared.LoginService;
import de.hdm.itprojektss18Gruppe3.shared.LoginServiceAsync;

public class Menubar extends MenuBar {

	private LoginInfo loginInfo = null;
	private Anchor signOutLink = new Anchor("Sign Out");

	private MenuBar menubar = new MenuBar();
	private MenuBar kontaktMenu = new MenuBar(true);
	private MenuBar kontaktlisteMenu = new MenuBar(true);
	private MenuBar teilhaberschaftMenu = new MenuBar(true);

	private MenuItem addKontakt = new MenuItem("Neuen Kontakt erstellen", new AllKontaktView.CreateKontaktCommand());
	private MenuItem deleteKontakt = new MenuItem("Kontakt löschen", new AllKontaktView.KontaktDeleteCommand());
	private MenuItem shareKontakt = new MenuItem("Kontakt teilen",
			new AllKontaktView.AddTeilhaberschaftKontaktCommand());
	private MenuItem addKontaktToKontaktliste = new MenuItem("Kontakt zur Kontaktliste hinzufügen",
			new AllKontaktView.AddKontaktToKontaktlisteCommand());
	private MenuItem addKontaktliste = new MenuItem("Neue Kontaktliste erstellen",
			new AllKontaktView.AddKontaktlisteCommand());
	private MenuItem addNewKontaktToKontaktliste = new MenuItem("Neuen Kontakt in dieser Kontaktliste erstellen",
			new AllKontaktView.AddNewKontaktToKontaktlisteCommand());
	private MenuItem deleteKontaktFromKontaktliste = new MenuItem("Kontakt aus dieser Kontaktliste löschen",
			new AllKontaktView.DeleteKontaktAusKontaktlisteCommand());
	private MenuItem deleteKontaktliste = new MenuItem("Kontaktliste löschen",
			new AllKontaktView.DeleteKontaktlisteCommand());
	private MenuItem shareKontaktliste = new MenuItem("Kontaktliste teilen",
			new AllKontaktView.AddTeilhaberschaftKontaktlisteCommand());
	private MenuItem manageTeilhaberschaften = new MenuItem("Teilhaberschaften verwalten",
			new AllKontaktView.TeilhaberschaftVerwaltenCommand());
	private MenuItem searchMenu = new MenuItem("Detailsuche", new ITProjektSS18Gruppe3.SuchenCommand());

	public Menubar() {
		RootPanel.get("menubar").clear();
		run();
	}

	public void run() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html", new LoginCallback());

		kontaktMenu.addItem(addKontakt);
		kontaktMenu.addItem(deleteKontakt);
		kontaktMenu.addItem(shareKontakt);
		kontaktMenu.addItem(addKontaktToKontaktliste);

		kontaktlisteMenu.addItem(addKontaktliste);
		kontaktlisteMenu.addItem(addNewKontaktToKontaktliste);
		kontaktlisteMenu.addItem(deleteKontaktFromKontaktliste);
		kontaktlisteMenu.addItem(deleteKontaktliste);
		kontaktlisteMenu.addItem(shareKontaktliste);

		teilhaberschaftMenu.addItem(manageTeilhaberschaften);

		menubar.setAutoOpen(true);
		menubar.setAnimationEnabled(true);
		menubar.setWidth("auto");
		menubar.setHeight("inherit");

		menubar.addItem("Übersicht", new Command() {

			@Override
			public void execute() {
				AllKontaktView akw = new AllKontaktView();
			}
		});
		menubar.addSeparator();
		menubar.addItem("Kontakt", kontaktMenu);
		menubar.addSeparator();
		menubar.addItem("Kontaktliste", kontaktlisteMenu);
		menubar.addSeparator();
		menubar.addItem("Teilhaberschaft", teilhaberschaftMenu);
		menubar.addSeparator();
		menubar.addItem(searchMenu);
		menubar.addSeparator();
		menubar.addItem("Ausloggen", new Command() {
			public void execute() {
				signOutLink.setHref(loginInfo.getLogoutUrl());
				com.google.gwt.user.client.Window.open(signOutLink.getHref(), "_self", "");
			}
		});

		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menubar);
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
}
