package de.hdm.itprojektss18Gruppe3.client;

import java.awt.Window;
import java.util.HashMap;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView;

public class Menubar extends MenuBar {
	

	VerticalPanel hp = new VerticalPanel();
	Command cmd = null;
	
	private MenuBar menubar = new MenuBar();
    private MenuBar kontaktMenu = new MenuBar(true);
    private MenuBar kontaktlisteMenu = new MenuBar(true);
    private MenuBar teilhaberschaftMenu = new MenuBar(true);
   
	private MenuItem addKontakt = new MenuItem("Neuen Kontakt erstellen", new AllKontaktView.CreateKontaktCommand());
	private MenuItem deleteKontakt = new MenuItem("Kontakt löschen", new AllKontaktView.KontaktDeleteCommand());
	private MenuItem shareKontakt = new MenuItem("Kontakt teilen", new AllKontaktView.AddTeilhaberschaftKontaktCommand());
	private MenuItem addKontaktToKontaktliste = new MenuItem("Kontakt zur Kontaktliste hinzufügen", new AllKontaktView.AddKontaktToKontaktlisteCommand());
	private MenuItem addKontaktliste = new MenuItem("Neue Kontaktliste erstellen", cmd);
	private MenuItem deleteKontaktliste = new MenuItem("Kontaktliste löschen", cmd);
	private MenuItem shareKontaktliste = new MenuItem("Kontaktliste teilen", cmd);
	private MenuItem manageTeilhaberschaften = new MenuItem("Teilhaberschaften verwalten", cmd);
	
	
	public Menubar() {
		RootPanel.get("menubar").clear();
		run();
	}

	public void run() {

		
		kontaktMenu.addItem(addKontakt);
		kontaktMenu.addItem(deleteKontakt);
		kontaktMenu.addItem(shareKontakt);
		kontaktMenu.addItem(addKontaktToKontaktliste);
		
		kontaktlisteMenu.addItem(addKontaktliste);
		kontaktlisteMenu.addItem(deleteKontaktliste);
		kontaktlisteMenu.addItem(shareKontaktliste);
		
		teilhaberschaftMenu.addItem(manageTeilhaberschaften);
		
		menubar.setAutoOpen(true);
	    menubar.setAnimationEnabled(true);
	    menubar.setWidth("auto");
	    
	    menubar.addItem("Kontakte", kontaktMenu);
	    menubar.addItem("Kontaktlisten", kontaktlisteMenu);
	    menubar.addItem("Teilhaberschaft", teilhaberschaftMenu);
	    

		
	    /*
	     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der
	     * Superklasse eine Initialisierung vorzunehmen.
	     */
	     
	    RootPanel.get("menubar").clear();
	    hp.add(menubar);
	    RootPanel.get("menubar").add(hp);

	}
	
	public MenuBar getMenuBar() {
		return menubar;
	}

	}

