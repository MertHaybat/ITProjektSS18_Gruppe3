package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;

public abstract class Menubar extends HorizontalPanel {
	
	Grid gridForMenuButtons = new Grid(1,2);
	HorizontalPanel panelForMenuButtons = new HorizontalPanel();


	public void onLoad() {
	    /*
	     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der
	     * Superklasse eine Initialisierung vorzunehmen.
	     */
	    super.onLoad();
	     
	    RootPanel.get("menubar").clear();

	    run();
	}

	protected abstract void run();
	
}

