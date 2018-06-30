package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Abstrakte Basisklasse für das LeftSideFrame, also für Baumstruktur.
 * 
 * @version 1.0 30 June 2018
 * @author Kevin
 *
 */
public abstract class LeftSideFrame extends HorizontalPanel {

	/**
	 * Automatisch geladene Methode onLoad(). Löscht den Div-Container leftmenutree und ruft die Methode run() auf.
	 */
	public void onLoad() {
		/*
		 * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der
		 * Superklasse eine Initialisierung vorzunehmen.
		 */
		super.onLoad();

		RootPanel.get("leftmenutree").clear();

		run();
	}

	protected abstract void run();

}

