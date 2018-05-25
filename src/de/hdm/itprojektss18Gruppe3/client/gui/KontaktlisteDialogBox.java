package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;

/**
 * Die Klasse "KontaktlisteDialogBox" ermöglicht das Hinzufügen einer neuen Kontaktliste über eine DialogBox
 * 
 * @version 1.0 23 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktlisteDialogBox extends DialogBox {
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	/**
	 * Instanziierung vom VerticalPanel 
	 */
	private VerticalPanel vPanel = new VerticalPanel();

	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel hPanel2 = new HorizontalPanel();
	
	/**
	 * Instanziierung vom Label "kontaktlisteLabel" welches als Hilfe für die TextBox dient, damit erkenntlich ist welche Angabe in die TextBox eingetragen werden soll
	 */
	private Label kontaktlisteLabel = new Label("Neue Kontaktliste: ");
	
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
	public KontaktlisteDialogBox() {
		
		hPanel.add(kontaktlisteLabel);
		hPanel.add(tkontaktliste);
		hPanel2.add(bspeichern);
		hPanel2.add(babbrechen);
		
		vPanel.add(hPanel);
		vPanel.add(hPanel2);
		this.add(vPanel);
		
		bspeichern.addClickHandler(new insertKontaktlisteClickHandler());
		babbrechen.addClickHandler(new closeKontaktlisteClickHandler());
		
	}
	
	public class createKontaktlisteCallback implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontaktliste result) {
			// TODO Auto-generated method stub
			hide();
			KontaktlistView kontaktlisteBox = new KontaktlistView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktlisteBox);
			
		}
	}
	
	public class insertKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kontaktmanagerVerwaltung.createKontaktliste(tkontaktliste.getValue(), 1, new createKontaktlisteCallback());
		}
	}
	
	public class closeKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			hide();
			KontaktlistView kontaktlisteBox = new KontaktlistView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktlisteBox);
		}
		
	}
}