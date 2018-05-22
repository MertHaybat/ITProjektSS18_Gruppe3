package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class Menubar extends HorizontalPanel {
	
	Grid gridForMenuButtons = new Grid(1,2);
	HorizontalPanel panelForMenuButtons = new HorizontalPanel();
	Label menuBarHeadlineLabel = new Label("Kontaktlisten");
	VerticalPanel menuBarContainerPanel = new VerticalPanel();
	FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	Button addKontaktlisteButton = new Button("+ Kontaktliste");
	Button deleteKontaktlisteButton = new Button("Loeschen");
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();


	public void onLoad() {
	    /*
	     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der
	     * Superklasse eine Initialisierung vorzunehmen.
	     */
	    super.onLoad();
	     
	    RootPanel.get("menubar").clear();

		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		
	    run();
	    
	}

	protected abstract void run();
	
	
}

