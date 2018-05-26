package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;

/**
 * Die Klasse "KontaktlisteLoeschenDialogBox" soll das Löschen von einem Kontakt aus einer Kontaktliste ermöglichen.
 * Dazu wird nachdem auswählen von einem Objekt "Konktat" aus einer ausgewählten "Kontaktliste" eine DialogBox geöffnet. 
 * Des Weiteren sind Panels "VerticalPanel" und "HorizontalPanel", zwei Buttons
 * "Bestätigen" oder "Abbrechen" und AsyncCallback und ClickHandler in der Klasse implementiert
 * 
 * @version 1.0 26 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktlisteLoeschenDialogBox extends DialogBox {
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	/**
	 * Panels für die Möglichkeit zum Anordnen der Buttons
	 */
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel1 = new HorizontalPanel();
	private HorizontalPanel hPanel2 = new HorizontalPanel();
	
	/**
	 * Buttons für die DialogBox
	 * Zum einen für das Bestätigen und Abbrechen
	 */
	private Button bBestaetigen = new Button("Bestätigen");
	private Button bAbbrechen = new Button("Abbrechen");

	/**
	 * Non-Argument Konstruktor
	 */
	public KontaktlisteLoeschenDialogBox() {
		
		/**
		 * Anordnung der Buttons in der DialogBox durch die Panels
		 */
		hPanel2.add(bBestaetigen);
		hPanel2.add(bAbbrechen);
		
		vPanel.add(hPanel1);
		vPanel.add(hPanel2);
		this.add(vPanel);
		
		/**
		 * ClickHandler
		 */
		bBestaetigen.addClickHandler(new deleteKontaktKontaktlisteClickHandler());
		bAbbrechen.addClickHandler(new closeKontaktKontaktlisteClickHandler());	
	}
	
	public class deleteKontaktKontaktlisteCallback implements AsyncCallback<KontaktKontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(KontaktKontaktliste result) {
			// TODO Auto-generated method stub
			hide();
			KontaktlistView kontaktlisteBox = new KontaktlistView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktlisteBox);
		}

	}
	
	class deleteKontaktKontaktlisteClickHandler implements ClickHandler {

		@Override	
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			KontaktKontaktliste kontaktKontaktliste = new KontaktKontaktliste();
//			kontaktmanagerVerwaltung.deleteKontaktKontaktliste(kontaktKontaktliste, new deleteKontaktKontaktlisteCallback());
		}
	}
	
	public class closeKontaktKontaktlisteClickHandler implements ClickHandler {

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