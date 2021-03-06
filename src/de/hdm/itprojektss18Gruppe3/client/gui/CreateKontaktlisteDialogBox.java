package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktPopup.KontaktClickHandler;

/**
 * Die Klasse "KontaktlisteDialogBox" ermöglicht das Hinzufügen einer neuen Kontaktliste über eine DialogBox
 * 
 * @version 1.0 23 May 2018
 * @author wahidvanaki
 *
 */
public class CreateKontaktlisteDialogBox extends DialogBox {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	private CustomTreeModel ctm = null;
	private Kontaktliste kontaktliste = null;
	/**
	 * Instanziierung vom VerticalPanel 
	 */
	private VerticalPanel vPanel = new VerticalPanel();

	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel labelPanel = new HorizontalPanel();


	/**
	 * Instanziierung vom Label "kontaktlisteLabel" welches als Hilfe für die TextBox dient, damit erkenntlich ist welche Angabe in die TextBox eingetragen werden soll
	 */
	private Label kontaktlisteLabel = new Label("Bezeichnung: ");

	/**
	 * Instanziierung der TextBox namens "tkontaktliste"
	 */
	private TextBox tkontaktliste = new TextBox();

	/**
	 * Instanziierung der Buttons "speichern" und "abbrechen"
	 * @see bspeichern, babbrechen 
	 */
	private Button bspeichern = new Button("Speichern");
	private Button babbrechen = new Button("Abbrechen");

	/**
	 * Non-Argument-Konstruktor
	 */
	public CreateKontaktlisteDialogBox() {
		bspeichern.addClickHandler(new insertKontaktlisteClickHandler());
		run();
	}

	/*
	 * Argument Konstruktor mit einer Kontaktliste als Parameter, 
	 * durch den eine bestehende Kontaktliste umbenannt werden kann.
	 */
	public CreateKontaktlisteDialogBox(Kontaktliste kl) {
		this.kontaktliste = kl;
		tkontaktliste.setValue(kl.getBezeichnung());
		this.setText("Neue Bezeichnung für die Kontaktliste");
		bspeichern.addClickHandler(new updateKontaktlisteClickHandler());
		run();
	}

	public void run(){
		
		this.setText("Neue Kontaktliste erstellen");
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(true);
		
		hPanel.add(bspeichern);
		hPanel.add(babbrechen);
		labelPanel.add(tkontaktliste);
		tkontaktliste.setReadOnly(true);
		tkontaktliste.setValue("Bezeichnung der Kontaktliste");
		tkontaktliste.addClickHandler(new KontaktClickHandler());
		tkontaktliste.setWidth("15em");

		babbrechen.addClickHandler(new closeKontaktlisteClickHandler());

		vPanel.add(labelPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(hPanel);
		this.add(vPanel);
	}
	
	class KontaktClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			tkontaktliste.setReadOnly(false);
			tkontaktliste.setValue("");
		}
	}

	public class createKontaktlisteCallback implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontaktliste result) {
			if(result == null){
				Window.alert("Diese Kontaktliste existiert bereits");
			}
			ctm = new CustomTreeModel();
			hide();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);		
		}
	}

	public class saveKontaktlisteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Ändern: " + caught.getMessage());	
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Kontaktliste wurde umbenannt");
			ctm = new CustomTreeModel();
			hide();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);	
		}
	}

	public class insertKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.createKontaktliste(tkontaktliste.getValue(), nutzer.getId(), 0, new createKontaktlisteCallback());
			hide();
		}
	}

	public class closeKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

	}

	public class updateKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			kontaktliste.setBezeichnung(tkontaktliste.getValue());
			kontaktmanagerVerwaltung.saveKontaktliste(kontaktliste, new saveKontaktlisteCallback());
		}	
	}
}