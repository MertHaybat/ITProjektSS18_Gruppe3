package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;

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
	/**
	 * Instanziierung vom VerticalPanel 
	 */
	private VerticalPanel vPanel = new VerticalPanel();

	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel hPanel2 = new HorizontalPanel();
	
	private FlexTable flextable1 = new FlexTable();

	
	private HorizontalPanel treeContainer = new HorizontalPanel();

	private Kontaktliste kontaktliste = new Kontaktliste();
	
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
		run();
	}
	
	public void run(){
		
		this.setText("Gib eine Bezeichnung für die neue Kontaktliste an");
		hPanel.add(bspeichern);
		hPanel.add(babbrechen);
		flextable1.setWidget(0, 0, kontaktlisteLabel);
		flextable1.setWidget(1, 0, tkontaktliste);
		flextable1.setWidget(2, 0, hPanel);

		bspeichern.addClickHandler(new insertKontaktlisteClickHandler());
		babbrechen.addClickHandler(new closeKontaktlisteClickHandler());
		
		bspeichern.setStylePrimaryName("mainButton");
		babbrechen.setStylePrimaryName("mainButton");

		vPanel.add(flextable1);
		vPanel.setWidth("300px");
		this.add(vPanel);
		
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
}