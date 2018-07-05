package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.CreateEigenschaftsauspraegungDialogBox.createEigenschaftsauspraegungCallback;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Die Klasse "Kontaktformular" beinhaltet einige festgelegten Textboxen, die
 * mit Eigenschaften bezeichnet sind und durch deren Eintrag erhalten die
 * Eigenschaften ihre jeweiligen Eigenschaftsausprägungen. Des Weiteren lässt
 * die TextArea einen Hinweis-Text zu für einen neuen Kontakt. Damit ermöglicht
 * die Klasse "Kontaktformular" das erstellen von einem Objekt "Kontakt" mit den
 * dazugehörigen Eigenschaften und Eigenschaftsausprägungen.
 * 
 * @version 1.0 18 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktForm extends MainFrame {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private Eigenschaftsauspraegung auspraegung = new Eigenschaftsauspraegung();
	private Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
	private static Kontakt k = null;
	private static Kontaktliste kontaktliste = null;
	private static Teilhaberschaft kontaktTeilhaberschaft = null;

	private Image shareImageIndicator = new Image();

	private FlexTable flextable = new FlexTable();
	private VerticalPanel vPanel = new VerticalPanel();
	private VerticalPanel vPanel2 = new VerticalPanel();
	private VerticalPanel vPanel3 = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel kontaktLabelkontaktName = new HorizontalPanel();
	private HorizontalPanel tableButtonPanel = new HorizontalPanel();

	private HTML headline = new HTML("Kontaktansicht");
	private Label modifikationsdatum = new Label("Modifikationsdatum: ");
	private Label erstellungsdatum = new Label("Erstellungsdatum: ");
	private Label kontaktNameLabel = new Label("Nickname: ");
	private TextBox kontaktNameBox = new TextBox();

	private Vector<Eigenschaftsauspraegung> auspraegungVector = new Vector<Eigenschaftsauspraegung>();
	private DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy 'um' HH:mm");

	private NoSelectionModel<EigenschaftsAuspraegungWrapper> selection = new NoSelectionModel<EigenschaftsAuspraegungWrapper>();
	private CellTableAuspraegungWrapper celltable = new CellTableAuspraegungWrapper(selection);
	private ClickableTextCell clickEigenschaft = new ClickableTextCell();
	private EditTextCell editEigenschaft = new EditTextCell();
	private static SingleSelectionModel<EigenschaftsAuspraegungWrapper> ssm = new SingleSelectionModel<EigenschaftsAuspraegungWrapper>();
	private Vector<Teilhaberschaft> teilhaberschaftVector = new Vector<Teilhaberschaft>();
	private Vector<EigenschaftsAuspraegungWrapper> resultWrapperVector = new Vector<EigenschaftsAuspraegungWrapper>();
	private String eigenschaft = "";
	private static Boolean ownKontakt = null;
	private static Boolean kontaktteilhaberschaftBoolean = null;

	private CellTableAuspraegungWrapper.WertEigenschaftColumn wertEigenschaftColumn = celltable.new WertEigenschaftColumn(
			clickEigenschaft);

	private CellTableAuspraegungWrapper.WertAuspraegungColumn wertAuspraegungColumn = celltable.new WertAuspraegungColumn(
			editEigenschaft) {
		@Override
		public void onBrowserEvent(Context context, Element elem, EigenschaftsAuspraegungWrapper object,
				NativeEvent event) {
			super.onBrowserEvent(context, elem, object, event);

			//			if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
			setFieldUpdater(new WertAuspraegungFieldUpdater());
			//			}
			if (object.getWertEigenschaftsauspraegungValue() == "") {
				kontaktmanagerVerwaltung.deleteEigenschaftsauspraegungById(
						object.getEigenschaftsauspraegungObject(object.getIDEigenschaftsauspraegungValue()),
						new DeleteEigenschaftsauspraegung());
			}

		}
	};

	public KontaktForm() {
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));
		kontaktmanagerVerwaltung.createKontakt("Neuer Kontakt", 0, nutzer.getId(), new CreateKontaktCallback());
		RootPanel.get("content").clear();
		RootPanel.get("content").add(this);
		//		super.onLoad();
	}

	public KontaktForm(Kontakt kontakt) {
		k = kontakt;
		kontaktNameBox.setValue(kontakt.getName());
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));

		kontaktmanagerVerwaltung.findEigenschaftAndAuspraegungByKontakt(nutzer.getId(), kontakt.getId(),
				new AllAuspraegungenCallback());

		modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(kontakt.getModifikationsdatum()));
		erstellungsdatum.setText("Erstellt am: " + dtf.format(kontakt.getErzeugungsdatum()));
		vPanel2.add(modifikationsdatum);
		vPanel2.add(erstellungsdatum);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(this);
		run();
		//		super.onLoad();
	}

	public void run() {

		celltable.setWidth("130%");
		kontaktNameBox.addKeyPressHandler(new KontaktTextBoxKeyPressHandler());
		headline.setStylePrimaryName("h3");
		kontaktNameLabel.setStylePrimaryName("kontaktFormText");
		kontaktNameBox.setStylePrimaryName("kontaktFormTextBox");
		kontaktLabelkontaktName.add(kontaktNameLabel);
		kontaktLabelkontaktName.add(kontaktNameBox);

		shareImageIndicator.setStylePrimaryName("shareImageIndicator");
		kontaktLabelkontaktName.add(shareImageIndicator);

		vPanel.add(headline);
		vPanel.add(new HTML("<br><br>"));
		vPanel.add(kontaktLabelkontaktName);
		vPanel.add(new HTML("<br><br>"));
		vPanel.add(celltable);
		vPanel.add(new HTML("<br>"));		
		vPanel.setStylePrimaryName("kontaktFormPanel");
		vPanel2.setStylePrimaryName("kontaktFormPanel");
		vPanel3.setStylePrimaryName("kontaktFormPanel");

		this.add(vPanel);
		this.add(vPanel3);
		this.add(vPanel2);


		RootPanel.get("content").add(vPanel);
		RootPanel.get("content").add(vPanel3);
		RootPanel.get("content").add(vPanel2);

	}


	public class TeilhaberschaftKontaktCallback implements AsyncCallback<Vector<Teilhaberschaft>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Vorgang konnte nicht abgeschlossen werden: " + caught.getMessage());

		}

		@Override
		public void onSuccess(Vector<Teilhaberschaft> result) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktteilhaberschaftBoolean = false;

			for (Teilhaberschaft teilhaberschaft : result) {
				if(teilhaberschaft.getKontaktID() == k.getId() && teilhaberschaft.getEigenschaftsauspraegungID() == 0 ||
						nutzer.getId() != k.getNutzerID()){
					kontaktteilhaberschaftBoolean = true;
					kontaktTeilhaberschaft = teilhaberschaft;
					break;
				}
			}
			if(kontaktteilhaberschaftBoolean == true) { 
				shareImageIndicator.setUrl("/images/received_share.png");
				Menubar mb = new Menubar(k, kontaktTeilhaberschaft); 
			} else if(ownKontakt == false) {
				Menubar mb = new Menubar(k, ownKontakt); 
				shareImageIndicator.setUrl("/images/received_share.png");
			} else {
				Menubar mb = new Menubar(k); 
				if(k.getStatus() == 0) {
					shareImageIndicator.setUrl("/images/singleperson.svg");
				} else if (k.getStatus() == 1){
					shareImageIndicator.setUrl("/images/group.svg");
				}
			}
		}
	}

	public static class DeleteTeilhaberschaftCommand implements Command {

		@Override
		public void execute() {
			kontaktmanagerVerwaltung.deleteTeilhaberschaftById(kontaktTeilhaberschaft, new DeleteTeilhaberschaftCallback());
		}
	}

	public static class DeleteTeilhaberschaftCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Vorgang konnte nicht abgeschlossen werden: " + caught.getMessage());

		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Teilhaberschaft gelöscht");
			CustomTreeModel ctm = new CustomTreeModel();
			AllKontaktView akw = new AllKontaktView();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);

		}


	}

	public class WertAuspraegungFieldUpdater implements FieldUpdater<EigenschaftsAuspraegungWrapper, String> {

		@Override
		public void update(int index, EigenschaftsAuspraegungWrapper object, String value) {

			selection.getLastSelectedObject().setWertEigenschaftsauspraegungValue(value);
			selection.getLastSelectedObject()
			.setIDEigenschaftsauspraegungValue(object.getIDEigenschaftsauspraegungValue());
			auspraegung.setWert(object.getWertEigenschaftsauspraegungValue());
			auspraegung.setId(object.getIDEigenschaftsauspraegungValue());
			auspraegung.setPersonID(object.getPersonIdEigenschaftsauspraegungValue());
			String input = object.getEigenschaft().getBezeichnung();
			String inputAuspraegung = value;
			if (input.equals("PLZ") || input.equals("Telefonnummer")) {
				if (!inputAuspraegung.matches("[0-9]*")) {
					Window.alert("Für diese Eigenschaft müssen Sie Zahlen als Ausprägung eingeben!");
					KontaktForm kontaktform = new KontaktForm(k);
				} else {
					kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());

				}
			} else if (input.equals("Geburtsdatum")) {
				if (!inputAuspraegung.matches("[0-9][0-9].[0-9][0-9].[0-9][0-9][0-9][0-9]")) {
					Window.alert("Bitte beachten Sie das Format: TT.MM.YYYY");
					KontaktForm kontaktform = new KontaktForm(k);
				} else {
					kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
				}
			} else {
				kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
			}

		}

	}

	public class KontaktHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			kontaktmanagerVerwaltung.createKontakt(k.getName(), 0, nutzer.getId(), new KontaktHinzufuegenCallback());
		}

	}

	public class KontaktHinzufuegenCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontakt result) {
			Window.alert("Der geteilte Kontakte wurde zu Ihren Kontakten hinzugefügt.");
		}

	}

	public class DeleteEigenschaftsauspraegung implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			KontaktForm kontaktform = new KontaktForm(k);
		}

	}

	public class KontaktTextBoxKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				k.setName(kontaktNameBox.getValue());
				kontaktmanagerVerwaltung.saveKontakt(k, new UpdateKontaktCallback());

			}
		}

	}

	public class ZurueckZuKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktlistView allKontaktlistView = new KontaktlistView();
		}
	}

	public class ZurueckZuKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("content").clear();
			AllKontaktView allKontaktView = new AllKontaktView();
		}
	}

	public class DeleteTeilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			kontaktmanagerVerwaltung.deleteTeilhaberschaftByID(teilhaberschaft, new DeleteTeilhaberschaftCallBack());
		}

		class DeleteTeilhaberschaftCallBack implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Löschen der Teilhaberschaft war nicht erfolgreich: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Teilhaberschaft erfolgreich gelöscht");
				AllKontaktView akw = new AllKontaktView();
			}

		}

	}

	public static class CreateEigenschaftAuspraegungCommand implements Command {

		@Override
		public void execute() {
			CreateEigenschaftsauspraegungDialogBox dialogbox = new CreateEigenschaftsauspraegungDialogBox(k,
					kontaktliste);
			dialogbox.center();
		}

	}

	public class UpdateKontaktCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kontakt konnte nicht abgespeichert werden" + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			Label gespeichert = new Label("Änderungen gespeichert");
			vPanel3.add(gespeichert);
			Timer timer = new Timer() {
				@Override
				public void run() {
					vPanel3.clear();
				}
			};
			timer.schedule(3000);
		}

	}

	public class UpdateAuspraegungCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Speichern der Eigenschaftsauspraegung" + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			kontaktmanagerVerwaltung.findKontaktByID(auspraegung.getPersonID(), new FindKontaktCallback());

			vPanel3.add(new Label("Änderungen gespeichert"));
			Timer timer = new Timer() {
				@Override
				public void run() {
					vPanel3.clear();
				}
			};
			timer.schedule(3000);

		}

		public class FindKontaktCallback implements AsyncCallback<Kontakt> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Laden: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Kontakt result) {
				modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(result.getModifikationsdatum()));
			}

		}

	}

	public class CreateEigenschaftAuspraegungCallback implements AsyncCallback<Eigenschaftsauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Speichern der Eigenschaftsauspraegung" + caught.getMessage());
		}

		@Override
		public void onSuccess(Eigenschaftsauspraegung result) {
		}

	}

	public class AllAuspraegungenCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden des Kontaktes" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungWrapper> result) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			final int resultSize = 0;
			Vector<Eigenschaftsauspraegung> auspraegungVector = new Vector<Eigenschaftsauspraegung>();
			for (EigenschaftsAuspraegungWrapper eigenschaftsauspraegung : result) {
				auspraegungVector.add(eigenschaftsauspraegung.getAuspraegung());
			}

			kontaktmanagerVerwaltung.findTeilhaberschaftString(nutzer.getId(), auspraegungVector,
					new AuspraegungTeilhaberschaftCallback());

			celltable.setRowData(0, result);
			celltable.setRowCount(result.size(), true);
			for (EigenschaftsAuspraegungWrapper eigenschaftsAuspraegungWrapper : result) {
				Eigenschaftsauspraegung e = new Eigenschaftsauspraegung();
				e.setId(eigenschaftsAuspraegungWrapper.getEigenschaftIdValue());
				auspraegungVector.add(e);
			}
		}

	}

	public class AuspraegungTeilhaberschaftCallback implements AsyncCallback<String> {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(String result) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			ownKontakt = null;
			if (result.equals("teilhaberschaft")) {
				wertEigenschaftColumn = celltable.new WertEigenschaftColumn(clickEigenschaft);
				wertAuspraegungColumn = celltable.new WertAuspraegungColumn(clickEigenschaft);
				celltable.addColumn(wertEigenschaftColumn, "");
				celltable.addColumn(wertAuspraegungColumn, "");
				kontaktNameBox.setEnabled(false);
				celltable.setSelectionModel(ssm);
				ownKontakt = false;
			} else if (nutzer.getId() != k.getNutzerID()) {
				celltable.addColumn(wertEigenschaftColumn, "Eigenschaft");
				celltable.setColumnWidth(wertEigenschaftColumn, 40, Unit.EM);
				celltable.addColumn(wertAuspraegungColumn, "Wert");
				celltable.setColumnWidth(wertAuspraegungColumn, 55, Unit.EM);
				flextable.setWidget(15, 0, tableButtonPanel);
				ownKontakt = false;
			} else {
				celltable.addColumn(wertEigenschaftColumn, "Eigenschaft");
				celltable.setColumnWidth(wertEigenschaftColumn, 40, Unit.EM);
				celltable.addColumn(wertAuspraegungColumn, "Wert");
				celltable.setColumnWidth(wertAuspraegungColumn, 55, Unit.EM);
				flextable.setWidget(15, 0, tableButtonPanel);
				ownKontakt = true;
			}
			kontaktmanagerVerwaltung.findTeilhaberschaftByKontaktAndTeilhaber(nutzer.getId(), k.getId(), new TeilhaberschaftKontaktCallback());
		}

	}

	public static class DeleteTeilhaberschaftAuspraegungCommand implements Command {

		@Override
		public void execute() {

			if(ssm.getSelectedSet().size() > 0) {

				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				Teilhaberschaft t = new Teilhaberschaft();
				t.setKontaktID(ssm.getSelectedObject().getPersonIdEigenschaftsauspraegungValue());
				t.setEigenschaftsauspraegungID(ssm.getSelectedObject().getAuspraegung().getId());
				t.setTeilhabenderID(nutzer.getId());
				kontaktmanagerVerwaltung.deleteTeilhaberschaftByTeilhaberschaft(t,
						new DeleteTeilhaberschaftAuspraegungCallback());
			} else {
				Window.alert("Wählen Sie zunächst die Eigenschaft aus, für welche die Teilhaberschaft"
						+ " gelöscht werden soll");
			}
		}
	}

	public static class DeleteTeilhaberschaftAuspraegungCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen der Teilhaberschaft: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			Kontaktliste kl = new Kontaktliste();
			kl.setBezeichnung("Empfangene Kontakte");
			CustomTreeModel ctm = new CustomTreeModel();
			AllKontaktView akw = new AllKontaktView();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);
		}

	}

	public class CreateKontaktCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Erstellen des Kontakts" + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontakt result) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			kontaktmanagerVerwaltung.findEigenschaftAndAuspraegungByKontakt(nutzer.getId(), result.getId(),
					new AllAuspraegungenCallback());
			kontaktNameBox.setValue(result.getName());
			k = result;
		}

	}
}
