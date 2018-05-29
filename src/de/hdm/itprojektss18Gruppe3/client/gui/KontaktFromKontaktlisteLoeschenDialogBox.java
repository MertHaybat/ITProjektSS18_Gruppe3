package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;

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
public class KontaktFromKontaktlisteLoeschenDialogBox extends DialogBox {
	
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
	private Kontaktliste kliste = new Kontaktliste();
	ArrayList<Kontakt> kontakteToRemoveFromKontaktliste;
	
	public KontaktFromKontaktlisteLoeschenDialogBox(ArrayList<Kontakt> kontakteToRemoveFromKontaktliste, Kontaktliste kontaktliste) {
		
		
		
		this.kontakteToRemoveFromKontaktliste = kontakteToRemoveFromKontaktliste;
		this.kliste = kontaktliste;
		StringBuilder listOfNames = new StringBuilder();
		for(Kontakt k:kontakteToRemoveFromKontaktliste) {
			listOfNames.append(k.getName() + ", ");
		}
		
		/**
		 * Anordnung der Buttons in der DialogBox durch die Panels
		 */
		setText("Möchtest du " + listOfNames.toString().substring(0, listOfNames.length() - 2) + " aus der Kontaktliste entfernen?");
		hPanel2.add(bBestaetigen);
		hPanel2.add(bAbbrechen);
		setGlassEnabled(true);
		
		vPanel.add(hPanel1);
		vPanel.add(hPanel2);
		this.add(vPanel);
		
		/**
		 * ClickHandler
		 */
		bBestaetigen.addClickHandler(new deleteKontaktKontaktlisteClickHandler());
		bAbbrechen.addClickHandler(new closeKontaktKontaktlisteClickHandler());	
	}


	public class deleteKontaktKontaktlisteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			hide();
			KontaktlistView kontaktlisteBox = new KontaktlistView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktlisteBox);
		}

	}
	
	class deleteKontaktKontaktlisteClickHandler implements ClickHandler {
		KontaktKontaktliste k = new KontaktKontaktliste();

		@Override	
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
				for (Kontakt kontakt : kontakteToRemoveFromKontaktliste) {
					k.setKontaktID(kontakt.getId());
					k.setKontaktlisteID(kliste.getId());
					kontaktmanagerVerwaltung.deleteKontaktKontaktliste(k, new deleteKontaktKontaktlisteCallback());
				
			}
			
		}
	}
	
	public class closeKontaktKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			hide();
		}
		
	}
}