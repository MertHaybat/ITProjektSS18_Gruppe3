package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class LeftSideFrame extends VerticalPanel {

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

