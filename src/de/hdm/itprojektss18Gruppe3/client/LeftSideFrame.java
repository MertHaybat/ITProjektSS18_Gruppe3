package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class LeftSideFrame extends HorizontalPanel {

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

