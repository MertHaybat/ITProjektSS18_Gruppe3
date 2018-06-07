package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.NoSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox.AbortDeleteClickHandler;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox.DeleteKontaktCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView.KontaktDeleteClickHandler.DeleteKontaktDialogBox.DeleteKontaktClickHandler;
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
	private CellTable<EigenschaftsAuspraegungWrapper> celltable = new CellTable<EigenschaftsAuspraegungWrapper>();

	private Button addAuspraegung = new Button("+");
	private Button saveChanges = new Button("Speichern");
	private Button deleteContact = null;
	private Button zurueckZuAllKontaktView = new Button("Zurück");

	private VerticalPanel vPanel = new VerticalPanel();
	private VerticalPanel vPanel2 = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();

	private Label modifikationsdatum = new Label("Modifikationsdatum: ");
	private Label erstellungsdatum = new Label("Erstellungsdatum: ");
	private Label kontaktNameLabel = new Label("Kontaktname: ");
	private TextBox kontaktNameBox = new TextBox();
	private Eigenschaftsauspraegung auspraegung = new Eigenschaftsauspraegung();
	
	private Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
	private final NoSelectionModel<EigenschaftsAuspraegungWrapper> ssmAuspraegung = new NoSelectionModel<EigenschaftsAuspraegungWrapper>();
	private Kontakt k = new Kontakt();
	private Kontaktliste kontaktliste = new Kontaktliste();
	private Vector<Eigenschaftsauspraegung> auspraegungVector = new Vector<Eigenschaftsauspraegung>();
	EditTextCell editEigenschaft = new EditTextCell();
	EditTextCell editAuspraegung = new EditTextCell();

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
		zurueckZuAllKontaktView.addClickHandler(new ZurueckZuKontaktClickHandler());
		kontaktNameBox.setValue(kontakt.getName());
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontakt, new AllAuspraegungenCallback());
		modifikationsdatum.setText("Zuletzt geändert am: " + kontakt.getModifikationsdatum());
		erstellungsdatum.setText("Erstellt am: " + kontakt.getErzeugungsdatum());
		deleteContact.addClickHandler(new DeleteChangesClickHandler());
		vPanel2.add(modifikationsdatum);
		vPanel2.add(erstellungsdatum);
		super.onLoad();

	}
	
	public KontaktForm(Kontakt kontakt, Kontaktliste kontaktliste){
		this.k = kontakt;
		this.kontaktliste = kontaktliste;
		deleteContact = new Button("Löschen");
		zurueckZuAllKontaktView.addClickHandler(new ZurueckZuKontaktlisteClickHandler());
		kontaktNameBox.setValue(kontakt.getName());
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontakt, new AllAuspraegungenCallback());
		modifikationsdatum.setText("Zuletzt geändert am: " + kontakt.getModifikationsdatum());
		erstellungsdatum.setText("Erstellt am: " + kontakt.getErzeugungsdatum());
		deleteContact.addClickHandler(new DeleteChangesClickHandler());
		vPanel2.add(modifikationsdatum);
		vPanel2.add(erstellungsdatum);
		super.onLoad();		
	}
	
	public KontaktForm(Kontakt kontakt, Teilhaberschaft teilhaberschaft){
		this.k = kontakt;
		this.teilhaberschaft = teilhaberschaft;
		deleteContact = new Button("Löschen");
		zurueckZuAllKontaktView.addClickHandler(new ZurueckZuKontaktClickHandler());
		kontaktNameBox.setValue(kontakt.getName());
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontakt, new AllAuspraegungenCallback());
		modifikationsdatum.setText("Zuletzt geändert am: " + kontakt.getModifikationsdatum());
		erstellungsdatum.setText("Erstellt am: " + kontakt.getErzeugungsdatum());
		deleteContact.addClickHandler(new DeleteTeilhaberschaftClickHandler());
		vPanel2.add(modifikationsdatum);
		vPanel2.add(erstellungsdatum);
		super.onLoad();
	}

	public CellTable<EigenschaftsAuspraegungWrapper> getCelltable() {
		return celltable;
	}

	public void setCelltable(CellTable<EigenschaftsAuspraegungWrapper> celltable) {
		this.celltable = celltable;
	}

	public void run() {
		addAuspraegung.setStylePrimaryName("addButton");
		zurueckZuAllKontaktView.setStylePrimaryName("mainButton");
		// TODO Auto-generated method stub
		
		Column<EigenschaftsAuspraegungWrapper, String> wertEigenschaft = new Column<EigenschaftsAuspraegungWrapper, String>(
				editEigenschaft) {
			@Override
			public String getValue(EigenschaftsAuspraegungWrapper object) {
				
				object.setEigenschaftIdValue(object.getEigenschaftIdValue());
				return object.getBezeichnungEigenschaftValue();
			}

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
			}
		};

		Column<EigenschaftsAuspraegungWrapper, String> wertAuspraegung = new Column<EigenschaftsAuspraegungWrapper, String>(
				editAuspraegung) {
			@Override
			public String getValue(EigenschaftsAuspraegungWrapper object) {
				object.setIDEigenschaftsauspraegungValue(object.getIDEigenschaftsauspraegungValue());
				return object.getWertEigenschaftsauspraegungValue();

			}

			@Override
			public void onBrowserEvent(Context context, Element elem, EigenschaftsAuspraegungWrapper object,
					NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);

				if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
					k.setName(kontaktNameBox.getValue());
					kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
					kontaktmanagerVerwaltung.saveKontakt(k, new UpdateKontaktCallback());
				}
				if (object.getWertEigenschaftsauspraegungValue() == "") {
					kontaktmanagerVerwaltung.deleteEigenschaftsauspraegungById(
							object.getEigenschaftsauspraegungObject(object.getIDEigenschaftsauspraegungValue()),
							new DeleteEigenschaftsauspraegung());
				}

			}
		};

		wertEigenschaft.setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungWrapper, String>() {

			@Override
			public void update(int index, EigenschaftsAuspraegungWrapper object, String value) {
				object.setBezeichnungEigenschaftValue(value);
				ssmAuspraegung.getLastSelectedObject().setBezeichnungEigenschaftValue(value);// getSelectedObject().setEigenschaft(value);

			}
		});

		wertAuspraegung.setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungWrapper, String>() {

			@Override
			public void update(int index, EigenschaftsAuspraegungWrapper object, String value) {

				object.setBezeichnungEigenschaftValue(value);
				ssmAuspraegung.getLastSelectedObject().setWertEigenschaftsauspraegungValue(value);
				ssmAuspraegung.getLastSelectedObject()
						.setIDEigenschaftsauspraegungValue(object.getIDEigenschaftsauspraegungValue());
				auspraegung.setWert(object.getWertEigenschaftsauspraegungValue());
				auspraegung.setId(object.getIDEigenschaftsauspraegungValue());
			}
		});

		kontaktNameBox.addKeyPressHandler(new KontaktTextBoxKeyPressHandler());

		addAuspraegung.addClickHandler(new CreateEigenschaftAuspraegungClickHandler());
		saveChanges.addClickHandler(new UpdateAuspraegungClickHandler());
		

		

		celltable.addColumn(wertEigenschaft, "");
		celltable.addColumn(wertAuspraegung, "");
		celltable.setSelectionModel(ssmAuspraegung);

		hPanel.add(zurueckZuAllKontaktView);

		vPanel.add(kontaktNameLabel);
		vPanel.add(kontaktNameBox);
		vPanel.add(celltable);

		vPanel.add(addAuspraegung);
		vPanel.add(deleteContact);

		this.add(vPanel);
		this.add(vPanel2);
		RootPanel.get("menubar").add(hPanel);
		RootPanel.get("content").add(vPanel);
		RootPanel.get("content").add(vPanel2);

	}
	private class DeleteEigenschaftsauspraegung implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			kontaktmanagerVerwaltung.findEigenschaftHybrid(k, new AllAuspraegungenCallback());
		}
		
	}
	private class KontaktTextBoxKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				k.setName(kontaktNameBox.getValue());
				kontaktmanagerVerwaltung.saveKontakt(k, new UpdateKontaktCallback());
			}
		}

	}
private class ZurueckZuKontaktlisteClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			KontaktlistView allKontaktlistView = new KontaktlistView();
		}		
	}
	private class ZurueckZuKontaktClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("content").clear();
			AllKontaktView allKontaktView = new AllKontaktView();
		}		
	}

//	class DeleteKontakt implements AsyncCallback<Void> {
//
//		@Override
//		public void onFailure(Throwable caught) {
//			Window.alert("Server Fehler: " + caught.getMessage());
//		}
//
//		@Override
//		public void onSuccess(Void result) {
//			Window.alert("Kontakt wurde erfolgreich gelöscht");
//			AllKontaktView allKontaktView = new AllKontaktView();
//		}
//
//	}
	class DeleteTeilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			kontaktmanagerVerwaltung.deleteTeilhaberschaftByID(teilhaberschaft, new DeleteTeilhaberschaftCallBack());
		}
		class DeleteTeilhaberschaftCallBack implements AsyncCallback<Void>{

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
	class DeleteChangesClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		
			
			DeleteKontaktDialogBox db = new DeleteKontaktDialogBox();
			db.center();

		
		}

	}

	public class DeleteKontaktDialogBox extends DialogBox {
		private VerticalPanel vPanel = new VerticalPanel();
		private HorizontalPanel hPanel = new HorizontalPanel();
		private Label abfrage = new Label(
				"Sind Sie sicher, dass Sie diesen Vorgang abbrechen " + "und Ihre Änderungen verwerfen möchten?");
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
	class UpdateAuspraegungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			k.setName(kontaktNameBox.getValue());
			kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
			kontaktmanagerVerwaltung.saveKontakt(k, new UpdateKontaktCallback());
		}

	}

	class UpdateKontaktCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Kontakt konnte nicht abgespeichert werden" + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Window.alert("Kontakt wurde erfolgreich abgespeichert");
			// RootPanel.get("content").clear();
			// KontaktForm kontaktForm = new KontaktForm(k);
			// RootPanel.get("content").add(kontaktForm);
		}

	}

	class UpdateAuspraegungCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Speichern der Eigenschaftsauspraegung" + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO

		}

	}

	class CreateEigenschaftAuspraegungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NewEigenschaftsauspraegungDialogBox dialogbox = new NewEigenschaftsauspraegungDialogBox(k);
			dialogbox.center();
		}

	}

	class CreateEigenschaftAuspraegungCallback implements AsyncCallback<Eigenschaftsauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Speichern der Eigenschaftsauspraegung" + caught.getMessage());
		}

		@Override
		public void onSuccess(Eigenschaftsauspraegung result) {
			// TODO

		}

	}

	class AllAuspraegungenCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden des Kontaktes");
		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungWrapper> result) {
			// TODO
			celltable.setRowData(0, result);
			celltable.setRowCount(result.size(), true);
			for (EigenschaftsAuspraegungWrapper eigenschaftsAuspraegungWrapper : result) {
				Eigenschaftsauspraegung e = new Eigenschaftsauspraegung();
				e.setId(eigenschaftsAuspraegungWrapper.getEigenschaftIdValue());
				auspraegungVector.add(e);

			}

		}

	}

	class CreateKontaktCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Erstellen des Kontakts" + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontakt result) {
			kontaktmanagerVerwaltung.findEigenschaftHybrid(result, new AllAuspraegungenCallback());
			kontaktNameBox.setValue(result.getName());
			k = result;
		}

	}

}