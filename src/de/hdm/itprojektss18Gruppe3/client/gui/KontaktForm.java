package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.Cell.Context;
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
	private VerticalPanel vPanel3 = new VerticalPanel();
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
	private EditTextCell editEigenschaft = new EditTextCell();
	private EditTextCell editAuspraegung = new EditTextCell();
	private DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MMMM.yyyy");

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
		modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(kontakt.getModifikationsdatum()));
		erstellungsdatum.setText("Erstellt am: " + dtf.format(kontakt.getErzeugungsdatum()));
		deleteContact.addClickHandler(new DeleteChangesClickHandler());
		vPanel2.add(modifikationsdatum);
		vPanel2.add(erstellungsdatum);
		super.onLoad();

	}
	
	public KontaktForm(Kontakt kontakt, Kontaktliste kontaktliste){
		this.k = kontakt;
		this.kontaktliste = kontaktliste;
		deleteContact = new Button("Löschen");
		zurueckZuAllKontaktView.addClickHandler(new ZurueckZuKontaktClickHandler());
		kontaktNameBox.setValue(kontakt.getName());
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontakt, new AllAuspraegungenCallback());
		modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(kontakt.getModifikationsdatum()));
		erstellungsdatum.setText("Erstellt am: " + dtf.format(kontakt.getErzeugungsdatum()));
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
		modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(kontakt.getModifikationsdatum()));
		erstellungsdatum.setText("Erstellt am: " + dtf.format(kontakt.getErzeugungsdatum()));
		
		Button kontaktHinzufuegen = new Button("Eigenen Kontakten Hinzufügen");
		kontaktHinzufuegen.addClickHandler(new KontaktHinzufuegenClickHandler());
		vPanel3.add(kontaktHinzufuegen);
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
		celltable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		celltable.setStylePrimaryName("kontaktformCelltable");
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
//					k.setName(kontaktNameBox.getValue());
					setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungWrapper, String>() {
						
						@Override
						public void update(int index, EigenschaftsAuspraegungWrapper object, String value) {
							
							object.setBezeichnungEigenschaftValue(value);
							ssmAuspraegung.getLastSelectedObject().setWertEigenschaftsauspraegungValue(value);
							ssmAuspraegung.getLastSelectedObject()
							.setIDEigenschaftsauspraegungValue(object.getIDEigenschaftsauspraegungValue());
							auspraegung.setWert(object.getWertEigenschaftsauspraegungValue());
							auspraegung.setId(object.getIDEigenschaftsauspraegungValue());
							auspraegung.setPersonID(object.getPersonIdEigenschaftsauspraegungValue());
							kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
						}
					});
//					kontaktmanagerVerwaltung.saveKontakt(k, new UpdateKontaktCallback());
				}
				if (object.getWertEigenschaftsauspraegungValue() == "") {
					kontaktmanagerVerwaltung.deleteEigenschaftsauspraegungById(
							object.getEigenschaftsauspraegungObject(object.getIDEigenschaftsauspraegungValue()),
							new DeleteEigenschaftsauspraegung());
				}

			}
		};
		
		TextColumn<EigenschaftsAuspraegungWrapper> iconColumn = new TextColumn<EigenschaftsAuspraegungWrapper>() {
	        @Override
	        public String getValue(EigenschaftsAuspraegungWrapper object) {
	            return "";                          
	        }
	        @Override
	        public void render(Context context, EigenschaftsAuspraegungWrapper object, SafeHtmlBuilder sb) {
	        	// TODO Auto-generated method stub
	        	if(object.getStatusValue() == 0){
	        		sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">"); 
	        		
	        	} else {
	        		sb.appendHtmlConstant("<img width=\"20\" src=\"images/group.svg\">"); 
	        	}

	        	super.render(context, object, sb);
	        }
	    };  

		wertEigenschaft.setFieldUpdater(new FieldUpdater<EigenschaftsAuspraegungWrapper, String>() {

			@Override
			public void update(int index, EigenschaftsAuspraegungWrapper object, String value) {
				object.setBezeichnungEigenschaftValue(value);
				ssmAuspraegung.getLastSelectedObject().setBezeichnungEigenschaftValue(value);// getSelectedObject().setEigenschaft(value);

			}
		});


		kontaktNameBox.addKeyPressHandler(new KontaktTextBoxKeyPressHandler());

		addAuspraegung.addClickHandler(new CreateEigenschaftAuspraegungClickHandler());
//		saveChanges.addClickHandler(new UpdateAuspraegungClickHandler());
		

		
		celltable.addColumn(wertEigenschaft, "");
		celltable.setColumnWidth(wertEigenschaft, 5, Unit.EM);
		celltable.addColumn(wertAuspraegung, "");
		celltable.setColumnWidth(wertAuspraegung, 12, Unit.EM);
		celltable.addColumn(iconColumn, "");
		celltable.setSelectionModel(ssmAuspraegung);

		hPanel.add(zurueckZuAllKontaktView);

		vPanel.add(kontaktNameLabel);
		vPanel.add(kontaktNameBox);
		vPanel.add(celltable);

		vPanel.add(addAuspraegung);
		vPanel.add(deleteContact);

		this.add(vPanel);
		this.add(vPanel3);
		this.add(vPanel2);
		RootPanel.get("menubar").add(hPanel);
		RootPanel.get("content").add(vPanel);
		RootPanel.get("content").add(vPanel3);
		RootPanel.get("content").add(vPanel2);

	}
	public class KontaktHinzufuegenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			kontaktmanagerVerwaltung.createKontakt(k.getName(), 0, nutzer.getId(), new KontaktHinzufuegenCallback());
		}
		
	}
	public class KontaktHinzufuegenCallback implements AsyncCallback<Kontakt>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontakt result) {
			Window.alert("Der geteilte Kontakte wurde zu Ihren Kontakten hinzugefügt.");
		}
		
	}
	public class DeleteEigenschaftsauspraegung implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			kontaktmanagerVerwaltung.findEigenschaftHybrid(k, new AllAuspraegungenCallback());
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
	public class DeleteTeilhaberschaftClickHandler implements ClickHandler {

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
	public class DeleteChangesClickHandler implements ClickHandler {

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
				"Sind Sie sicher, dass Sie diesen Vorgang abbrechen " + "und Ihre Änderungen verwerfen/den Kontakt löschen möchten?");
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
//	class UpdateAuspraegungClickHandler implements ClickHandler{
//	
//		@Override
//		public void onClick(ClickEvent event) {
//			k.setName(kontaktNameBox.getValue());
//			kontaktmanagerVerwaltung.saveEigenschaftsauspraegung(auspraegung, new UpdateAuspraegungCallback());
//			kontaktmanagerVerwaltung.saveKontakt(k, new UpdateKontaktCallback());
//		}
//
//	}

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
			// TODO Auto-generated method stub
//			Window.alert("Kontakt wurde erfolgreich abgespeichert");
			// RootPanel.get("content").clear();
			// KontaktForm kontaktForm = new KontaktForm(k);
			// RootPanel.get("content").add(kontaktForm);
		}

	}

	public class UpdateAuspraegungCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Speichern der Eigenschaftsauspraegung" + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			kontaktmanagerVerwaltung.findKontaktByID(auspraegung.getPersonID(), new FindKontaktCallback());
			
		
			// TODO
//			Window.alert("hallo");
			vPanel3.add(new Label("Änderungen gespeichert"));
			Timer timer = new Timer() {
			     @Override
			     public void run() {
			    	 vPanel3.clear();
			     }
			 }; 
			 timer.schedule(1000);

		}
		public class FindKontaktCallback implements AsyncCallback<Kontakt>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Kontakt result) {
				

				modifikationsdatum.setText("Zuletzt geändert am: " + dtf.format(result.getModifikationsdatum()));
			}
			
		}

	}

	public class CreateEigenschaftAuspraegungClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NewEigenschaftsauspraegungDialogBox dialogbox = new NewEigenschaftsauspraegungDialogBox(k);
			dialogbox.center();
		}

	}

	public class CreateEigenschaftAuspraegungCallback implements AsyncCallback<Eigenschaftsauspraegung> {

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

	public class AllAuspraegungenCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> {

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

	public class CreateKontaktCallback implements AsyncCallback<Kontakt> {

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