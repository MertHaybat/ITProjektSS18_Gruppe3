package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Date;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.AllAuspraegungenCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.CreateKontaktCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.UpdateAuspraegungCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.UpdateKontaktCallback;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class KontaktPopup extends DialogBox {
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private VerticalPanel vPanel = new VerticalPanel();
	private FlexTable flextable1 = new FlexTable();

	private TextBox ktNameTa = new TextBox();
	private Button speichern = new Button("Hinzufügen");
	private Button abbrechen = new Button("Abbrechen");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Kontaktliste kontaktliste = new Kontaktliste();
	
	
	public KontaktPopup(){
		super(true);
		speichern.addClickHandler(new SpeichernOhneKontaktlisteClickHandler());
		abbrechen.addClickHandler(new AbbrechenClickHandler());
		run();
	}
	
	
	public KontaktPopup(Kontaktliste kontaktliste){
		super(true);
		this.kontaktliste = kontaktliste;
		speichern.addClickHandler(new SpeichernMitKontaktlisteClickHandler());
		abbrechen.addClickHandler(new AbbrechenClickHandler());
		run();
	}
	
	
	public void run(){

		this.setText("Neuen Kontakt erstellen");
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(true);

		ktNameTa.setReadOnly(true);
		ktNameTa.setValue("Name des neuen Kontaktes");
		ktNameTa.addClickHandler(new KontaktClickHandler());
		ktNameTa.setWidth("15em");

		buttonPanel.add(speichern);
		buttonPanel.add(abbrechen);
		flextable1.setWidget(0, 0, ktNameTa);	
		flextable1.setWidget(1, 0, new HTML("<br>"));
		flextable1.setWidget(2, 0, buttonPanel);
		
		vPanel.add(flextable1);
		this.add(vPanel);
		this.setGlassEnabled(true);
	}
	
	
	class SpeichernMitKontaktlisteClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			kontaktmanagerVerwaltung.createKontakt(ktNameTa.getValue(), 0, nutzer.getId(),
					new CreateKontaktMitKontaktlisteCallback());
		}
		
	}
	
	
	class SpeichernOhneKontaktlisteClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			kontaktmanagerVerwaltung.createKontakt(ktNameTa.getValue(), 0, nutzer.getId(),
					new CreateKontaktOhneKontaktlisteCallback());
		}
		
	}
	
	
	class KontaktClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			ktNameTa.setReadOnly(false);
			ktNameTa.setValue("");
		}
		
	}
	
	class AbbrechenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
			
		}
		
	}
	
	
	class CreateKontaktMitKontaktlisteCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontakt result) {
			hide();
			kontaktmanagerVerwaltung.createKontaktKontaktliste(result.getId(), kontaktliste.getId(), new CreateKontaktKontaktlisteCallback());
			KontaktForm kontaktForm = new KontaktForm(result);
			CustomTreeModel ctm = new CustomTreeModel();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);
		}
		
		
		class CreateKontaktKontaktlisteCallback implements AsyncCallback<KontaktKontaktliste>{

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
			}

			@Override
			public void onSuccess(KontaktKontaktliste result) {
				hide();
			}
			
		}

	}
	class CreateKontaktOhneKontaktlisteCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Erstellen des Kontakts: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontakt result) {
			hide();
			Window.alert("Kontakt wurde erfolgreich erstellt.");
			KontaktForm kontaktForm = new KontaktForm(result);
			CustomTreeModel ctm = new CustomTreeModel();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);
		}

	}
	

}