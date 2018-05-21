package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;


import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

/**
 * DialogBox zum Teilen und Betrachten der Teilhaberschaften
 * @author ersinbarut
 *
 */
public class TeilhaberschaftDialogBox extends DialogBox{

	private Label lb1 = new Label ("Vorname: ");
	private Label lb2 = new Label ("Nachname: ");
	private Label lb3 = new Label ("Geburtsdatum: ");
	private Label lb4 = new Label ("Telefonnummer: ");
	private Label lb5 = new Label ("Email: ");
	
//	private Label lb6 = new Label ()
			
	private Button b1 = new Button ("teilen");
	private Button b2 = new Button ("abbrechen");
	
	private static KontaktmanagerAdministrationAsync kverwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	public TeilhaberschaftDialogBox(){
		/**
		 * Die Anordnung der Textboxen festlegen und dazu deren Bezeichnungen.
		 */
		Grid kontaktWidget = new Grid(11,10);
		Grid hinweisWidget = new Grid(4,4);
		
		
	}
	
	
	
	
}
