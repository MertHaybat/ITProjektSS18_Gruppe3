package de.hdm.itprojektss18Gruppe3.client.gui;


import java.util.Vector;

import com.gargoylesoftware.htmlunit.javascript.host.KeyboardEvent;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktCellTable.AllAuspraegungenCallback;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;


/**
 * Die Klasse "Kontaktformular" beinhaltet einige festgelegten Textboxen, die mit Eigenschaften bezeichnet sind und durch deren Eintrag
 * erhalten die Eigenschaften ihre jeweiligen Eigenschaftsausprägungen. Des Weiteren lässt die TextArea einen Hinweis-Text zu für einen neuen Kontakt.
 * Damit ermöglicht die Klasse "Kontaktformular" das erstellen von einem Objekt "Kontakt" mit den dazugehörigen Eigenschaften und Eigenschaftsausprägungen.
 * 
 * @version 1.0 18 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktForm extends VerticalPanel {
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	private CellTable<EigenschaftsAuspraegungHybrid> celltable = new CellTable<EigenschaftsAuspraegungHybrid>();
	
	private Button addAuspraegung = new Button("+");
	private Button saveChanges = new Button("Speichern");
	
	private EigenschaftsAuspraegungHybrid eigenschaftObject = new EigenschaftsAuspraegungHybrid();
	private EigenschaftsAuspraegungHybrid auspraegungObject = new EigenschaftsAuspraegungHybrid();
		
	private Eigenschaftsauspraegung auspraegung = new Eigenschaftsauspraegung();
	
	private final SingleSelectionModel<EigenschaftsAuspraegungHybrid> ssmAuspraegung = new SingleSelectionModel<EigenschaftsAuspraegungHybrid>();
	private Kontakt k = new Kontakt();
	public KontaktForm(Kontakt kontakt){
		k.setId(3);
		
		EditTextCell editEigenschaft = new EditTextCell();
		EditTextCell editAuspraegung = new EditTextCell();
		
		Column<EigenschaftsAuspraegungHybrid, String> wertEigenschaft = new Column<EigenschaftsAuspraegungHybrid, String>(
				editEigenschaft) {
			@Override
			public String getValue(EigenschaftsAuspraegungHybrid object) {
				return object.getEigenschaft();
			}
		};

		
		Column<EigenschaftsAuspraegungHybrid, String> wertAuspraegung = new Column<EigenschaftsAuspraegungHybrid, String>(
				editAuspraegung) {
			@Override
			public String getValue(EigenschaftsAuspraegungHybrid object) {
				return object.getAuspraegung();
			}
		};
		wertEigenschaft.setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungHybrid, String>() {
			
			@Override
			public void update(int index, EigenschaftsAuspraegungHybrid object, String value) {
				object.setEigenschaft(value);				
				ssmAuspraegung.getSelectedObject().setEigenschaft(value);

			}
		});
		wertAuspraegung.setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungHybrid, String>() {
			
			@Override
			public void update(int index, EigenschaftsAuspraegungHybrid object, String value) {
				object.setEigenschaft(value);	
				ssmAuspraegung.getSelectedObject().setAuspraegung(value);
				ssmAuspraegung.getSelectedObject().setAuspraegungid(object.getAuspraegungid());
				auspraegung.setWert(object.getAuspraegung());
				auspraegung.setId(object.getAuspraegungid());
			}
		});
		
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontakt, new AllAuspraegungenCallback());
		addAuspraegung.addClickHandler(new CreateEigenschaftAuspraegungClickHandler());
		saveChanges.addClickHandler(new UpdateAuspraegungClickHandler());
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
//			}
//		});
		
		celltable.addColumn(wertEigenschaft, "");
		celltable.addColumn(wertAuspraegung, "");
		celltable.setSelectionModel(ssmAuspraegung);
		this.add(celltable);
		this.add(addAuspraegung);
		this.add(saveChanges);

	}
	class UpdateAuspraegungClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
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

	class CreateEigenschaftAuspraegungClickHandler implements ClickHandler{

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
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungHybrid> result) {
			// TODO
			celltable.setRowData(0, result);
			celltable.setRowCount(result.size(), true);
			

		}
				
	}
	
}