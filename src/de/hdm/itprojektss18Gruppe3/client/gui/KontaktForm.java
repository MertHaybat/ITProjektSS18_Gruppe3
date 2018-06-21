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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox.AbortDeleteClickHandler;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox.DeleteKontaktCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox.DeleteKontaktClickHandler;
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
	private Kontakt k = null;
	private Kontaktliste kontaktliste = null;

	private Button deleteContact = null;
	private Button addAuspraegung = new Button("+");
	private Button zurueckZuAllKontaktView = new Button("Alle Kontakte");
	private Button deleteTeilhaberschaftButton = new Button("Teilhaberschaft löschen");

	private FlexTable flextable = new FlexTable();
	private VerticalPanel vPanel = new VerticalPanel();
	private VerticalPanel vPanel2 = new VerticalPanel();
	private VerticalPanel vPanel3 = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel kontaktLabelkontaktName = new HorizontalPanel();
	private HorizontalPanel tableButtonPanel = new HorizontalPanel();	
	
	private Label modifikationsdatum = new Label("Modifikationsdatum: ");
	private Label erstellungsdatum = new Label("Erstellungsdatum: ");
	private Label kontaktNameLabel = new Label("Kontaktname: ");
	private TextBox kontaktNameBox = new TextBox();

	private Vector<Eigenschaftsauspraegung> auspraegungVector = new Vector<Eigenschaftsauspraegung>();
	private DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MMMM.yyyy");

	private NoSelectionModel<EigenschaftsAuspraegungWrapper> selection = new NoSelectionModel<EigenschaftsAuspraegungWrapper>();
	private CellTableAuspraegungWrapper celltable = new CellTableAuspraegungWrapper(selection);
	private ClickableTextCell clickEigenschaft = new ClickableTextCell();
	private EditTextCell editEigenschaft = new EditTextCell();
	private SingleSelectionModel<EigenschaftsAuspraegungWrapper> ssm = new SingleSelectionModel<EigenschaftsAuspraegungWrapper>();
	private Vector<Teilhaberschaft> teilhaberschaftVector = new Vector<Teilhaberschaft>();
	private Vector<EigenschaftsAuspraegungWrapper> resultWrapperVector = new Vector<EigenschaftsAuspraegungWrapper>(); 
	
	private CellTableAuspraegungWrapper.IconColumn iconColumn = celltable.new IconColumn();

	private CellTableAuspraegungWrapper.WertEigenschaftColumn wertEigenschaftColumn = celltable.new WertEigenschaftColumn(
			editEigenschaft) {
		@Override
		public void onBrowserEvent(Context context, Element elem, EigenschaftsAuspraegungWrapper object,
				NativeEvent event) {
			super.onBrowserEvent(context, elem, object, event);

			if (event.getKeyCode() == KeyCodes.KEY_ENTER) {

				if (object.getBezeichnungEigenschaftValue() == "") {
					kontaktmanagerVerwaltung.deleteEigenschaftsauspraegungById(
							object.getEigenschaftsauspraegungObject(object.getIDEigenschaftsauspraegungValue()),
							new DeleteEigenschaftsauspraegung());
				}

			}
			super.onBrowserEvent(context, elem, object, event);

		}

	};

	private CellTableAuspraegungWrapper.WertAuspraegungColumn wertAuspraegungColumn = celltable.new WertAuspraegungColumn(
			editEigenschaft) {
		@Override
		public void onBrowserEvent(Context context, Element elem, EigenschaftsAuspraegungWrapper object,
				NativeEvent event) {
			super.onBrowserEvent(context, elem, object, event);

			if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
				setFieldUpdater(new WertAuspraegungFieldUpdater());
			}
			if (object.getWertEigenschaftsauspraegungValue() == "") {
				kontaktmanagerVerwaltung.deleteEigenschaftsauspraegungById(
						object.getEigenschaftsauspraegungObject(object.getIDEigenschaftsauspraegungValue()),
						new DeleteEigenschaftsauspraegung());
			}

		}
	};

	public KontaktForm() {
		deleteContact = new Button("Abbrechen");
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));
		kontaktmanagerVerwaltung.createKontakt("Neuer Kontakt", 0, nutzer.getId(), new CreateKontaktCallback());
		deleteContact.addClickHandler(new DeleteChangesClickHandler());
		super.onLoad();
	}

	public KontaktForm(Kontakt kontakt) {
		this.k = kontakt;
		deleteContact = new Button("Löschen");
		hPanel.add(zurueckZuAllKontaktView);
		zurueckZuAllKontaktView.addClickHandler(new ZurueckZuKontaktClickHandler());
		kontaktNameBox.setValue(kontakt.getName());
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));
		
		kontaktmanagerVerwaltung.findEigenschaftAndAuspraegungByKontakt(nutzer.getId(), kontakt.getId(), new AllAuspraegungenCallback());

		
		modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(kontakt.getModifikationsdatum()));
		erstellungsdatum.setText("Erstellt am: " + dtf.format(kontakt.getErzeugungsdatum()));
		deleteContact.addClickHandler(new DeleteChangesClickHandler());
		vPanel2.add(modifikationsdatum);
		vPanel2.add(erstellungsdatum);
		super.onLoad();

	}

//	public KontaktForm(Kontakt kontakt, Kontaktliste kontaktliste) {
//		this.k = kontakt;
//		this.kontaktliste = kontaktliste;
//		deleteContact = new Button("Löschen");
//		zurueckZuAllKontaktView.addClickHandler(new ZurueckZuKontaktClickHandler());
//		kontaktNameBox.setValue(kontakt.getName());
//		Nutzer nutzer = new Nutzer();
//		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
//		kontaktmanagerVerwaltung.findEigenschaftAndAuspraegungByKontakt(nutzer.getId(), kontakt.getId(), new AllAuspraegungenCallback());
//		modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(kontakt.getModifikationsdatum()));
//		erstellungsdatum.setText("Erstellt am: " + dtf.format(kontakt.getErzeugungsdatum()));
//		deleteContact.addClickHandler(new DeleteChangesClickHandler());
//		vPanel2.add(modifikationsdatum);
//		vPanel2.add(erstellungsdatum);
//		super.onLoad();
//	}
//
//	public KontaktForm(Kontakt kontakt, Teilhaberschaft teilhaberschaft) {
//		this.k = kontakt;
//		this.teilhaberschaft = teilhaberschaft;
//		deleteContact = new Button("Löschen");
//		hPanel.add(zurueckZuAllKontaktView);
//		zurueckZuAllKontaktView.addClickHandler(new ZurueckZuKontaktClickHandler());
//		kontaktNameBox.setValue(kontakt.getName());
//		Nutzer nutzer = new Nutzer();
//		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
//		nutzer.setMail(Cookies.getCookie("mail"));
//		
//		kontaktmanagerVerwaltung.findEigenschaftAndAuspraegungByKontakt(nutzer.getId(), kontakt.getId(), new AllAuspraegungenCallback());
//		modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(kontakt.getModifikationsdatum()));
//		erstellungsdatum.setText("Erstellt am: " + dtf.format(kontakt.getErzeugungsdatum()));
//
//		Button kontaktHinzufuegen = new Button("Eigenen Kontakten Hinzufügen");
//		kontaktHinzufuegen.addClickHandler(new KontaktHinzufuegenClickHandler());
//		vPanel3.add(kontaktHinzufuegen);
//		deleteContact.addClickHandler(new DeleteTeilhaberschaftClickHandler());
//		vPanel2.add(modifikationsdatum);
//		vPanel2.add(erstellungsdatum);
//		super.onLoad();
//	}

	public void run() {
	
		deleteTeilhaberschaftButton.setStylePrimaryName("mainButton");
		addAuspraegung.setStylePrimaryName("addButton");
		zurueckZuAllKontaktView.setStylePrimaryName("mainButton");
		wertEigenschaftColumn.setFieldUpdater(new WertEigenschaftFieldUpdater());

		kontaktNameBox.addKeyPressHandler(new KontaktTextBoxKeyPressHandler());
		addAuspraegung.addClickHandler(new CreateEigenschaftAuspraegungClickHandler());

		hPanel.add(zurueckZuAllKontaktView);
		
		
//		celltable.setSelectionModel(celltable.getSelectionModel());

		kontaktNameLabel.setStylePrimaryName("kontaktFormText");
		kontaktLabelkontaktName.add(kontaktNameLabel);
		kontaktLabelkontaktName.add(kontaktNameBox);
		tableButtonPanel.add(deleteContact);
		tableButtonPanel.add(addAuspraegung);
		flextable.setWidget(0, 0, kontaktLabelkontaktName);
		flextable.setWidget(2, 0, celltable);


		vPanel.add(flextable);
		vPanel.setStylePrimaryName("kontaktCellTableView");
		vPanel2.setStylePrimaryName("kontaktCellTableView");
		
		this.add(vPanel);
		this.add(vPanel3);
		this.add(vPanel2);
		hPanel.setStylePrimaryName("menuBarLabelContainer");
		RootPanel.get("menubar").add(hPanel);
		RootPanel.get("content").add(vPanel);
		RootPanel.get("content").add(vPanel3);
		RootPanel.get("content").add(vPanel2);

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

	public class WertEigenschaftFieldUpdater implements FieldUpdater<EigenschaftsAuspraegungWrapper, String> {
		@Override
		public void update(int index, EigenschaftsAuspraegungWrapper object, String value) {
			object.setBezeichnungEigenschaftValue(value);
			selection.getLastSelectedObject().setBezeichnungEigenschaftValue(value);

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

	public class DeleteChangesClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteKontaktDialogBox db = new DeleteKontaktDialogBox();
			db.center();

		}

	}

	public class CreateEigenschaftAuspraegungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			CreateEigenschaftsauspraegungDialogBox dialogbox = new CreateEigenschaftsauspraegungDialogBox(k, kontaktliste);
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
			timer.schedule(10000);
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
			timer.schedule(1000);

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
			
			kontaktmanagerVerwaltung.findTeilhaberschaftString(nutzer.getId(), auspraegungVector, new AuspraegungTeilhaberschaftCallback());
		
			celltable.setRowData(0, result);
			celltable.setRowCount(result.size(), true);
			for (EigenschaftsAuspraegungWrapper eigenschaftsAuspraegungWrapper : result) {
				Eigenschaftsauspraegung e = new Eigenschaftsauspraegung();
				e.setId(eigenschaftsAuspraegungWrapper.getEigenschaftIdValue());
				auspraegungVector.add(e);
			}
		}

	}

	public class AuspraegungTeilhaberschaftCallback implements AsyncCallback<String>{
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(String result) {
			if(result.equals("teilhaberschaft")){
				wertEigenschaftColumn = celltable.new WertEigenschaftColumn(clickEigenschaft);
				wertAuspraegungColumn = celltable.new WertAuspraegungColumn(clickEigenschaft);
				celltable.addColumn(wertEigenschaftColumn, "");
				celltable.addColumn(wertAuspraegungColumn, "");
				kontaktNameBox.setEnabled(false);
				celltable.setSelectionModel(ssm);
				deleteTeilhaberschaftButton.addClickHandler(new DeleteTeilhaberschaftAuspraegungClickHandler());
				hPanel.add(deleteTeilhaberschaftButton);
				
			} else {
				celltable.addColumn(wertEigenschaftColumn, "");
				celltable.setColumnWidth(wertEigenschaftColumn, 7, Unit.EM);
				celltable.addColumn(wertAuspraegungColumn, "");
				celltable.setColumnWidth(wertAuspraegungColumn, 14, Unit.EM);
				celltable.addColumn(iconColumn, "");
				flextable.setWidget(15, 0, tableButtonPanel);
				
			}
		}
		
	}
	public class DeleteTeilhaberschaftAuspraegungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			Teilhaberschaft t = new Teilhaberschaft();
			t.setKontaktID(ssm.getSelectedObject().getPersonIdEigenschaftsauspraegungValue());
			t.setEigenschaftsauspraegungID(ssm.getSelectedObject().getAuspraegung().getId());
			t.setTeilhabenderID(nutzer.getId());
			kontaktmanagerVerwaltung.deleteTeilhaberschaftByTeilhaberschaft(t, new DeleteTeilhaberschaftAuspraegungCallback());
		}
		
	}
	public class DeleteTeilhaberschaftAuspraegungCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen der Teilhaberschaft: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			AllKontaktView allkontaktView = new AllKontaktView();
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
			kontaktmanagerVerwaltung.findEigenschaftAndAuspraegungByKontakt(nutzer.getId(), result.getId(), new AllAuspraegungenCallback());
			kontaktNameBox.setValue(result.getName());
			k = result;
		}

	}

	public class DeleteKontaktDialogBox extends DialogBox {
		private VerticalPanel vPanel = new VerticalPanel();
		private HorizontalPanel hPanel = new HorizontalPanel();
		private Label abfrage = new HTML("Soll der Kontakt " + k.getName() + " wirklich gelöscht werden?<br><br>");
		private Button ja = new Button("Ja");
		private Button nein = new Button("Nein");

		public DeleteKontaktDialogBox() {
			ja.addClickHandler(new DeleteKontaktClickHandler());
			nein.addClickHandler(new AbortDeleteClickHandler());
			vPanel.add(abfrage);
			hPanel.add(ja);
			hPanel.add(nein);
			vPanel.add(hPanel);
			this.setTitle("Vorgang abbrechen");
			this.add(vPanel);
		}

		public class AbortDeleteClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}

		}

		public class DeleteKontaktClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				kontaktmanagerVerwaltung.deleteKontaktByOwner(k, new DeleteKontaktCallback());
			}
		}

		public class DeleteKontaktCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Der Vorgang konnte nicht abgebrochen werden: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Der Vorgang wurde erfolgreich abgebrochen.");
				hide();
				AllKontaktView allKontaktView = new AllKontaktView();
			}

		}

	}
}

