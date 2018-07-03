package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
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
	private FlexTable flextable1 = new FlexTable();

	/**
	 * Buttons für die DialogBox
	 * Zum einen für das Bestätigen und Abbrechen
	 */
	private Button bBestaetigen = new Button("Bestätigen");
	private Button bAbbrechen = new Button("Abbrechen");
	private Kontaktliste kontaktliste = new Kontaktliste();
	private Kontakt kontaktToRemoveFromKontaktliste;
	private KontaktKontaktliste kontaktKontaktlisteObject = new KontaktKontaktliste();
	private ArrayList<Kontakt> kontakteToRemoveFromKontaktliste;
		
	
	public KontaktFromKontaktlisteLoeschenDialogBox(ArrayList<Kontakt> kontakteToRemoveFromKontaktliste, Kontaktliste kontaktliste) {
		
		bBestaetigen.setStylePrimaryName("mainButton");
		bAbbrechen.setStylePrimaryName("mainButton");

		this.kontakteToRemoveFromKontaktliste = kontakteToRemoveFromKontaktliste;
		this.kontaktliste = kontaktliste;
		StringBuilder listOfNames = new StringBuilder();
		for(Kontakt k:kontakteToRemoveFromKontaktliste) {
			listOfNames.append(k.getName() + ", ");
			
		}
		
		/**
		 * Anordnung der Buttons in der DialogBox durch die Panels
		 */
		setText("Möchtest du " + listOfNames.toString().substring(0, listOfNames.length() - 2) + " aus der Kontaktliste entfernen?");
		flextable1.setWidget(0, 0, bBestaetigen);
		flextable1.setWidget(0, 1, bAbbrechen);
		vPanel.add(flextable1);
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(true);
		
		vPanel.add(hPanel1);
		vPanel.add(hPanel2);
		this.add(vPanel);
		
		/**
		 * ClickHandler
		 */
		bBestaetigen.addClickHandler(new deleteKontakteFromKontaktlisteClickHandler());
		bAbbrechen.addClickHandler(new closeKontaktKontaktlisteClickHandler());	
	}
	
	public KontaktFromKontaktlisteLoeschenDialogBox(Kontakt kontaktToRemoveFromKontaktliste, Kontaktliste kontaktliste) {
		
		this.kontaktToRemoveFromKontaktliste = kontaktToRemoveFromKontaktliste;
		this.kontaktliste = kontaktliste;
		
		/**
		 * Anordnung der Buttons in der DialogBox durch die Panels
		 */
		setText("Möchtest du " + kontaktToRemoveFromKontaktliste.getName() + " aus der Kontaktliste entfernen?");
		flextable1.setWidget(0, 0, bBestaetigen);
		flextable1.setWidget(0, 1, bAbbrechen);
		vPanel.add(flextable1);
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(true);
		
		vPanel.add(hPanel1);
		vPanel.add(hPanel2);
		this.add(vPanel);
		
		/**
		 * ClickHandler
		 */
		bBestaetigen.addClickHandler(new deleteKontaktKontaktlisteClickHandler());
		bAbbrechen.addClickHandler(new closeKontaktKontaktlisteClickHandler());	
	}

	
	class deleteKontakteFromKontaktlisteClickHandler implements ClickHandler {
		KontaktKontaktliste k = new KontaktKontaktliste();

		@Override	
		public void onClick(ClickEvent event) {
				for (Kontakt kontakt : kontakteToRemoveFromKontaktliste) {
					k.setKontaktID(kontakt.getId());
					k.setKontaktlisteID(kontaktliste.getId());
					kontaktmanagerVerwaltung.deleteKontaktKontaktliste(k, new deleteKontaktKontaktlisteCallback());
				
			}
			
		}
	}
	
	class deleteKontaktKontaktlisteClickHandler implements ClickHandler {
		
		KontaktKontaktliste kkl = new KontaktKontaktliste();
		
		@Override
		public void onClick(ClickEvent event) {
			kkl.setKontaktID(kontaktToRemoveFromKontaktliste.getId());
			kkl.setKontaktlisteID(kontaktliste.getId());
			kontaktmanagerVerwaltung.deleteKontaktKontaktliste(kkl, new deleteKontaktKontaktlisteCallback());
			
		}
	}
	
	public class closeKontaktKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}
		
	}
	

	public class deleteKontaktKontaktlisteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			hide();
			CustomTreeModel ctm = new CustomTreeModel();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);
			AllKontaktView allkontaktView = new AllKontaktView(kontaktliste);
		}

	}
}