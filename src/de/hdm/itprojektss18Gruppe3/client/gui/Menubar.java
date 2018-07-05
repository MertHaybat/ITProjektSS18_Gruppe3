package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.ITProjektSS18Gruppe3;
import de.hdm.itprojektss18Gruppe3.client.LoginInfo;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.ITProjektSS18Gruppe3.SuchenCommand;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.LoginService;
import de.hdm.itprojektss18Gruppe3.shared.LoginServiceAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/*
 * Klasse für die Menüleiste. Hier werden die Menüpunkte entsprechend dem geladen, 
 * was aufgrund der Aktion des Nutzers im Moment möglich ist. Auch ist hier die TextBox
 * für die schnelle Suche integriert
 */
public class Menubar extends MenuBar {

	private LoginInfo loginInfo = null;
	private Anchor signOutLink = new Anchor("Sign Out");
	private HorizontalPanel hp = new HorizontalPanel();
	private TextBox textBox = new TextBox();
	private Teilhaberschaft teilhaberschaft = null;

	private MenuBar menubar = new MenuBar();
	private MenuBar menubarRightSide = new MenuBar();
	private MenuBar addMenu = new MenuBar(true);
	private MenuBar alterMenu = new MenuBar(true);
	private MenuBar deleteMenu = new MenuBar(true);
	private MenuBar shareMenu = new MenuBar(true);
	private MenuBar teilhaberschaftMenu = new MenuBar(true);

	private Boolean ownKontakt = null;
	private Boolean kontaktlisteTeilhaberschaft = null;
	private Kontakt kontakt = null;
	private Kontaktliste kontaktliste = null;
	private ArrayList<Kontakt> allKontakteSelectedArrayList = null;
	private ArrayList<MenuItem> allMenuItems = new ArrayList<MenuItem>();
	private ArrayList<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> selectedTeilhaberschaftAuspraegung = null;

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private MenuItem addKontakt = new MenuItem("Neuen Kontakt erstellen", new AllKontaktView.CreateKontaktCommand());
	private MenuItem deleteKontakt = new MenuItem("Kontakt löschen", new AllKontaktView.KontaktDeleteCommand());
	private MenuItem shareKontakt = new MenuItem("Kontakt teilen",
			new ShareKontaktCommand());
	private MenuItem addKontaktEigenschaft = new MenuItem("Neue Eigenschaft für Kontakt", new KontaktForm.CreateEigenschaftAuspraegungCommand());
	private MenuItem alterKontakt = new MenuItem("Kontakt bearbeiten", new AlterKontaktCommand());
	private MenuItem addKontaktToKontaktliste = new MenuItem("Kontakt zur Kontaktliste hinzufügen",
			new AllKontaktView.AddKontaktToKontaktlisteCommand());
	private MenuItem addKontaktliste = new MenuItem("Neue Kontaktliste erstellen",
			new AllKontaktView.AddKontaktlisteCommand());
	private MenuItem addNewKontaktToKontaktliste = new MenuItem("Neuen Kontakt in dieser Kontaktliste erstellen",
			new AllKontaktView.AddNewKontaktToKontaktlisteCommand());
	private MenuItem deleteKontaktFromKontaktliste = new MenuItem("Kontakt aus dieser Kontaktliste löschen",
			new AllKontaktView.DeleteKontaktAusKontaktlisteCommand());
	private MenuItem renameKontaktliste = new MenuItem("Kontaktliste umbenennen",
			new AllKontaktView.RenameKontaktlisteCommand());
	private MenuItem deleteKontaktliste = new MenuItem("Kontaktliste löschen",
			new AllKontaktView.DeleteKontaktlisteCommand());
	private MenuItem shareKontaktliste = new MenuItem("Kontaktliste teilen",
			new AllKontaktView.AddTeilhaberschaftKontaktlisteCommand());
	private MenuItem manageTeilhaberschaften = new MenuItem("Teilhaberschaften",
			new AllKontaktView.TeilhaberschaftVerwaltenCommand());
	private MenuItem deleteTeilhaberschaften = new MenuItem("Teilhaberschaft löschen",
			new TeilhaberschaftVerwaltungView.DeleteTeilhaberschaft());
	private MenuItem searchMenu = new MenuItem("Detailsuche", new ITProjektSS18Gruppe3.SuchenCommand());

	/*
	 * Über verschiedene Konstruktoren, die jeweils verschiedene Parameter
	 * erwarten, kann so der jeweilige Command, der hinter einem MenüItem steckt,
	 * angepasst werden. Dadurch kann z.B. kein Kontakt gelöscht werden, wenn der Nutzer
	 * gerade eine Kontaktliste angeklickt hat.
	 */
	public Menubar() {
		RootPanel.get("menubar").clear();
		addMenuItemsToArray();

		run();
	}

	public Menubar(Kontakt k) {
		this.kontakt = k;
		addMenuItemsToArray();
		deleteKontakt.setEnabled(true);
		shareKontakt.setEnabled(true);
		addKontaktToKontaktliste.setEnabled(true);
		addKontaktEigenschaft.setEnabled(true);

		deleteKontakt.setScheduledCommand(new DeleteKontaktCommand());
		shareKontakt.setScheduledCommand(new ShareKontaktAndEigenschaftenCommand());
		addKontaktToKontaktliste.setScheduledCommand(new AddKontaktToKontaktlisteCommand());

		run();
	}

	public Menubar(Kontakt k, Teilhaberschaft t) {
		addMenuItemsToArray();
		this.kontakt = k;
		this.teilhaberschaft = t;
		deleteKontakt.setEnabled(false);
		deleteTeilhaberschaften.setEnabled(true);
		deleteTeilhaberschaften.setScheduledCommand(new KontaktForm.DeleteTeilhaberschaftCommand());


		shareKontakt.setEnabled(true);
		addKontaktToKontaktliste.setEnabled(true);
		addKontaktEigenschaft.setEnabled(true);

		deleteKontakt.setScheduledCommand(new DeleteKontaktCommand());
		addKontaktToKontaktliste.setScheduledCommand(new AddKontaktToKontaktlisteCommand());

		run();
	}

	public Menubar(Kontakt k, Boolean ownKontakt) {
		addMenuItemsToArray();
		this.kontakt = k;
		deleteTeilhaberschaften.setEnabled(true);
		deleteTeilhaberschaften.setScheduledCommand(new KontaktForm.DeleteTeilhaberschaftAuspraegungCommand());
		shareKontakt.setEnabled(true);
		deleteKontakt.setScheduledCommand(new DeleteKontaktCommand());
		shareKontakt.setScheduledCommand(new ShareKontaktCommand());
		run();
	}

	public Menubar(Kontaktliste kl, ArrayList<Kontakt> allKontakteSelectedArrayList) {
		addMenuItemsToArray();
		this.allKontakteSelectedArrayList = allKontakteSelectedArrayList;
		this.kontaktliste = kl;
		kontaktlisteTeilhaberschaft = AllKontaktView.getKontaktlisteTeilhaberschaft();
		shareKontakt.setScheduledCommand(new AllKontaktView.AddTeilhaberschaftKontaktCommand());
		if(!kontaktliste.getBezeichnung().equals("Empfangene Kontakte") && !kontaktliste.getBezeichnung().equals("Eigene Kontakte") &&
				kontaktlisteTeilhaberschaft == false ) {
			deleteKontaktliste.setEnabled(true);
			shareKontaktliste.setEnabled(true);
			addNewKontaktToKontaktliste.setEnabled(true);
			renameKontaktliste.setEnabled(true);
		}

		if(allKontakteSelectedArrayList.size() > 0 && kontaktlisteTeilhaberschaft == false) {
			deleteKontakt.setEnabled(true);
			shareKontakt.setEnabled(true);
			addKontaktToKontaktliste.setEnabled(true);
			deleteKontaktFromKontaktliste.setEnabled(true);
			alterKontakt.setEnabled(true);
		}
		
		if(kontaktlisteTeilhaberschaft == true && allKontakteSelectedArrayList.size() > 0) {
			deleteTeilhaberschaften.setEnabled(true);
			deleteTeilhaberschaften.setScheduledCommand(new AllKontaktView.TeilhaberschaftButtonCommand());
		} 
		
		if (kontaktlisteTeilhaberschaft == true && allKontakteSelectedArrayList.size() == 0) {
			deleteTeilhaberschaften.setEnabled(true);
			deleteTeilhaberschaften.setScheduledCommand(new AllKontaktView.DeleteTeilhaberschaftKontaktlisteCommand());
		}
		run();
	}

	public Menubar(MultiSelectionModel<NutzerTeilhaberschaftKontaktWrapper> ssmAuspraegung) {
		addMenuItemsToArray();
		deleteTeilhaberschaften.setEnabled(true);
		run();
	}

	public void addMenuItemsToArray() {
		allMenuItems.add(deleteKontakt);
		allMenuItems.add(shareKontakt);
		allMenuItems.add(alterKontakt);
		allMenuItems.add(addKontaktEigenschaft);
		allMenuItems.add(addKontaktToKontaktliste);
		allMenuItems.add(deleteKontaktFromKontaktliste);
		allMenuItems.add(deleteKontaktliste);
		allMenuItems.add(shareKontaktliste);
		allMenuItems.add(addNewKontaktToKontaktliste);
		allMenuItems.add(renameKontaktliste);
		allMenuItems.add(deleteTeilhaberschaften);

		for(MenuItem item : allMenuItems) {
			item.setEnabled(false);
		}
	}

	/*
	 * In der run Methode werden die MenuBars und MenuItems dann entsprechend zusammengeführt
	 * und in das RootPanel eingebunden.
	 */
	public void run() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html", new LoginCallback());

		addMenu.addItem(addKontakt);
		addMenu.addItem(addKontaktliste);
		addMenu.addItem(addKontaktEigenschaft);
		addMenu.addItem(addNewKontaktToKontaktliste);

		alterMenu.addItem(alterKontakt);
		alterMenu.addItem(addKontaktToKontaktliste);
		alterMenu.addItem(renameKontaktliste);

		shareMenu.addItem(shareKontakt);
		shareMenu.addItem(shareKontaktliste);

		deleteMenu.addItem(deleteKontakt);
		deleteMenu.addItem(deleteKontaktliste);
		deleteMenu.addItem(deleteKontaktFromKontaktliste);
		deleteMenu.addItem(deleteTeilhaberschaften);

		menubar.setAutoOpen(true);
		menubar.setAnimationEnabled(true);
		menubar.setWidth("auto");
		menubar.setHeight("inherit");

		menubar.addItem("Erstellen", addMenu).addStyleName("menuBarImage");
		menubar.addSeparator();
		menubar.addItem("Ändern", alterMenu).addStyleName("menuBarImage");
		menubar.addSeparator();
		menubar.addItem("Teilen", shareMenu).addStyleName("menuBarImage");
		menubar.addSeparator();
		menubar.addItem("Löschen", deleteMenu).addStyleName("menuBarImage");
		menubar.addSeparator();
		menubar.addItem(manageTeilhaberschaften);
		menubar.addSeparator();

		textBox.setStylePrimaryName("searchTextBox");
		textBox.setMaxLength(100);
		textBox.getElement().setPropertyString("placeholder", " Schnellsuche...");

		menubarRightSide.addSeparator();
		menubarRightSide.addItem(searchMenu);
		menubarRightSide.addSeparator();
		menubarRightSide.addItem("Ausloggen", new Command() {
			public void execute() {
				signOutLink.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutLink.getHref(), "_self", "");
			}
		});

		textBox.addKeyPressHandler(new TextBoxKeyPressHandler());

		for(MenuItem item : allMenuItems) {
			if(!item.isEnabled()) {
				item.setStylePrimaryName("disabledMenuBar");
			}
		}


		hp.add(menubar);
		hp.add(textBox);
		hp.add(menubarRightSide);

		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(hp);

	}

	class TextBoxKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			// TODO Auto-generated method stub

			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				DisclosurePanelSuche panelSuche = new DisclosurePanelSuche(textBox.getValue());
				RootPanel.get("content").clear();
				RootPanel.get("content").add(panelSuche);
				textBox.setText("");
			}
		}

	}

	class LoginCallback implements AsyncCallback<LoginInfo> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Vorgang konnte nicht abgeschlossen werden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;
		}
	}


	/*
	 * Die verschiedenen Commands, die den MenuItems übergeben werden, sind im Folgenden
	 * aufgeführt. Je nach Aktion werden die verschiedenen Klassen, die eine Aktion wie 
	 * z.B. das Löschen eines Kontaktes durchführen, aufgerufen.
	 */
	class DeleteKontaktCommand implements Command {

		@Override
		public void execute() {
			new DeleteKontaktDialogBox();
		}

	}

	public class DeleteKontaktDialogBox extends DialogBox {
		private DialogBox db = new DialogBox();
		private VerticalPanel vPanel = new VerticalPanel();
		private FlowPanel buttonPanel = new FlowPanel();
		private Label abfrage = new HTML("Soll der Kontakt " + kontakt.getName() + " wirklich gelöscht werden?<br><br>");
		private Button jaButton = new Button("Löschen");
		private Button neinButton = new Button("Abbrechen");

		public DeleteKontaktDialogBox() {
			db.setGlassEnabled(true);
			db.setAnimationEnabled(true);
			db.setAutoHideEnabled(true);
			db.setText("Kontakt löschen");
			jaButton.addClickHandler(new DeleteKontaktClickHandler());
			neinButton.addClickHandler(new AbortDeleteClickHandler());
			abfrage.setStylePrimaryName("centerTextDialogBox");
			vPanel.add(abfrage);
			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			buttonPanel.setStylePrimaryName("buttonPanelBox");
			vPanel.add(buttonPanel);
			db.add(vPanel);
			db.center();
		}

		public class AbortDeleteClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				db.hide();
			}

		}

		public class DeleteKontaktClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				kontaktmanagerVerwaltung.deleteKontaktByOwner(kontakt, new DeleteKontaktCallback());
			}
		}

		public class DeleteKontaktCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Der Vorgang konnte nicht abgeschlossen werden: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Der Kontakt wurde erfolgreich gelöscht.");
				db.hide();
				AllKontaktView allKontaktView = new AllKontaktView();
			}
		}

	}

	public class ShareKontaktCommand implements Command {

		@Override
		public void execute() {
			DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(kontakt);
			dialogbox.center();
		}

	}
	
	public class ShareKontaktAndEigenschaftenCommand implements Command {

		@Override
		public void execute() {
			TeilhaberschaftDialogBox dialogbox = new TeilhaberschaftDialogBox(kontakt);
			dialogbox.center();
		}

	}

	public class AddKontaktToKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			DialogBoxKontaktlisteHinzufuegen db = new DialogBoxKontaktlisteHinzufuegen(kontakt);
			db.center();
		}

	}

	public class AlterKontaktCommand implements Command {

		@Override
		public void execute() {
			KontaktForm kf = new KontaktForm(allKontakteSelectedArrayList.get(0));
		}
	}

}
