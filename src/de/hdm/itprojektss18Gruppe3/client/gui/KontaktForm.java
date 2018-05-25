package de.hdm.itprojektss18Gruppe3.client.gui;

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
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

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
public class KontaktForm extends VerticalPanel {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private CellTable<EigenschaftsAuspraegungHybrid> celltable = new CellTable<EigenschaftsAuspraegungHybrid>();

	private Button addAuspraegung = new Button("+");
	private Button saveChanges = new Button("Speichern");
	private Button cancelChanges = new Button("Abbrechen");

	private Label modifikationsdatum = new Label("Modifikationsdatum: ");
	private Label erstellungsdatum = new Label("Erstellungsdatum: ");
	private Label kontaktNameLabel = new Label("Kontaktname: ");
	private TextBox kontaktNameBox = new TextBox();

	private Button zurueckZuAllKontaktView = new Button("Alle Kontakte");
	private Eigenschaftsauspraegung auspraegung = new Eigenschaftsauspraegung();

	private final NoSelectionModel<EigenschaftsAuspraegungHybrid> ssmAuspraegung = new NoSelectionModel<EigenschaftsAuspraegungHybrid>();
	private Kontakt k = new Kontakt();

	private Vector<Eigenschaftsauspraegung> auspraegungVector = new Vector<Eigenschaftsauspraegung>();

	public KontaktForm() {
		// TODO Statt der 1 muss die NutzerID rein.. Cookies kommen aber noch!!!
		kontaktmanagerVerwaltung.createKontakt("Neuer Kontakt", new Date(), new Date(), 0, 1,
				new CreateKontaktCallback());
		this.add(kontaktNameLabel);
		this.add(kontaktNameBox);
		this.add(celltable);
		
		this.add(addAuspraegung);
		this.add(saveChanges);
		this.add(cancelChanges);
		run();	
	}

	public KontaktForm(Kontakt kontakt) {
		k = kontakt;
		kontaktNameBox.setValue(kontakt.getName());
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontakt, new AllAuspraegungenCallback());
		modifikationsdatum.setText("Zuletzt geändert am: " + kontakt.getModifikationsdatum());
		erstellungsdatum.setText("Erstellt am: " + kontakt.getErzeugungsdatum());
		this.add(kontaktNameLabel);
		this.add(kontaktNameBox);
		this.add(celltable);
		
		this.add(addAuspraegung);
		this.add(saveChanges);
		this.add(cancelChanges);

		this.add(modifikationsdatum);
		this.add(erstellungsdatum);
		run();
	}

	public void run() {
		
		
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(zurueckZuAllKontaktView);
		// TODO Auto-generated method stub
		EditTextCell editEigenschaft = new EditTextCell(); 
		EditTextCell editAuspraegung = new EditTextCell();

		Column<EigenschaftsAuspraegungHybrid, String> wertEigenschaft = new Column<EigenschaftsAuspraegungHybrid, String>(
				editEigenschaft) {
			@Override
			public String getValue(EigenschaftsAuspraegungHybrid object) {
				object.setEigenschaftid(object.getEigenschaftid());
				return object.getEigenschaft();
			}
		};

		Column<EigenschaftsAuspraegungHybrid, String> wertAuspraegung = new Column<EigenschaftsAuspraegungHybrid, String>(
				editAuspraegung) {
			@Override
			public String getValue(EigenschaftsAuspraegungHybrid object) {
				object.setAuspraegungid(object.getAuspraegungid());
				return object.getAuspraegung();
				
			}
			
			@Override
	        public void onBrowserEvent(Context context, Element elem,
	        		EigenschaftsAuspraegungHybrid object, NativeEvent event) {
	            super.onBrowserEvent(context, elem, object, event); 
	         
	            	if(event.getKeyCode() == KeyCodes.KEY_ENTER) {
	            		k.setName(kontaktNameBox.getValue());
	        			kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
	        			kontaktmanagerVerwaltung.saveKontakt(k, new UpdateKontaktCallback());
	            	}
	            
			}
		};
		
		

		wertEigenschaft.setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungHybrid, String>() {

			@Override
			public void update(int index, EigenschaftsAuspraegungHybrid object, String value) {
				object.setEigenschaft(value);
				ssmAuspraegung.getLastSelectedObject().setEigenschaft(value);// getSelectedObject().setEigenschaft(value);

			}
		});

		wertAuspraegung.setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungHybrid, String>() {

			@Override
			public void update(int index, EigenschaftsAuspraegungHybrid object, String value) {

				object.setEigenschaft(value);
				ssmAuspraegung.getLastSelectedObject().setAuspraegung(value);
				ssmAuspraegung.getLastSelectedObject().setAuspraegungid(object.getAuspraegungid());
				auspraegung.setWert(object.getAuspraegung());
				auspraegung.setId(object.getAuspraegungid());
			}
		});

		addAuspraegung.addClickHandler(new CreateEigenschaftAuspraegungClickHandler());
		saveChanges.addClickHandler(new UpdateAuspraegungClickHandler());
		cancelChanges.addClickHandler(new CancelChangesClickHandler());

		zurueckZuAllKontaktView.addClickHandler(new ZurueckClickHandler());

		
		celltable.addColumn(wertEigenschaft, "");
		celltable.addColumn(wertAuspraegung, "");
		celltable.setSelectionModel(ssmAuspraegung);

	}
	private class ZurueckClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			AllKontaktView allKontaktView = new AllKontaktView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(allKontaktView);
		}
		
	}

	class DeleteKontakt implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Server Fehler: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {

		}

	}

	class CancelChangesClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			kontaktmanagerVerwaltung.deleteKontaktByOwner(k, new DeleteKontakt());

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
			RootPanel.get("content").clear();
			KontaktForm kontaktForm = new KontaktForm(k);
			RootPanel.get("content").add(kontaktForm);
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

	class AllAuspraegungenCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungHybrid>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden des Kontaktes");
		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungHybrid> result) {
			// TODO
			celltable.setRowData(0, result);
			celltable.setRowCount(result.size(), true);
			for (EigenschaftsAuspraegungHybrid eigenschaftsAuspraegungHybrid : result) {
				Eigenschaftsauspraegung e = new Eigenschaftsauspraegung();
				e.setId(eigenschaftsAuspraegungHybrid.getEigenschaftid());
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