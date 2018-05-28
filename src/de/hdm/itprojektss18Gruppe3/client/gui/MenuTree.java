package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

public class MenuTree {
	private VerticalPanel vPanel = new VerticalPanel();
	private Label welcomeMessage = new Label("Willkommen beim Kontaktmanager");
	private HTML instructionMessage = new HTML("<br>Hier kannst du deine Kontakte verwalten.");

	private static KontaktmanagerAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	private Kontakt k = new Kontakt();

	public MenuTree() {

		welcomeMessage.addStyleName("headline");
		vPanel.setStylePrimaryName("headlinePanel");
		vPanel.add(welcomeMessage);
		instructionMessage.setStylePrimaryName("landingpageText");
		vPanel.add(instructionMessage);

		/*
		 * Navigationsbaum auf der linken Seite erzeugen
		 */

		final TreeItem kontakte = new TreeItem();
		final TreeItem kontaktlisten = new TreeItem();
		final TreeItem teilhaberschaften = new TreeItem();
		Tree navigationTree = new Tree();
		Label navigationHeadline = new Label("Navigation");
		VerticalPanel navigationTreePanel = new VerticalPanel();

		
		/*
		 * Baummenü definieren
		 */
		kontakte.setText("Kontakte");
		kontaktlisten.setText("Kontaktlisten");
		teilhaberschaften.setText("Teilhaberschaften");
//		kontakte.addTextItem("Neuer Kontakt");
		kontakte.addTextItem("Alle Kontakte");

		kontaktlisten.addTextItem("Neue Kontaktliste");
		kontaktlisten.addTextItem("Alle Kontaktlisten");

//		teilhaberschaften.addTextItem("Neue Teilhaberschaft");
		teilhaberschaften.addTextItem("Alle Teilhaberschaften");

		navigationTree.addItem(kontakte);
		navigationTree.addItem(kontaktlisten);
		navigationTree.addItem(teilhaberschaften);

		navigationHeadline.setStylePrimaryName("navigationPanelHeadline");

		navigationTreePanel.add(navigationHeadline);
		navigationTreePanel.add(navigationTree);

		/*
		 * TreeListener erzeugen und über eine Switch Case Anweisung das jeweils
		 * angeklickte Menü Item herausfinden, um den User dann auf die
		 * gewünschte Seite zu navigieren.
		 */
		navigationTree.setAnimationEnabled(true);
		navigationTree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = event.getSelectedItem();
				
				switch (item.getText()) {
				
//								case "Neuer Kontakt":
//									KontaktForm kontaktForm = new KontaktForm();
//									return;
				
								case "Alle Kontakte":
									AllKontaktView allKontaktView = new AllKontaktView();
									return;
				
								case "Neue Kontaktliste":
									Window.alert("To do");
									return;
				
								case "Alle Kontaktlisten":
									KontaktlistView kontaktlistView = new KontaktlistView();
									RootPanel.get("content").clear();
									RootPanel.get("content").add(kontaktlistView);
									return;
				
								case "Neue Teilhaberschaft":
//									TeilhaberschaftDialogBox teilhaberschaftDb = new TeilhaberschaftDialogBox(k);
//									teilhaberschaftDb.center();
									return;
				
								case "Alle Teilhaberschaften":
									TeilhaberschaftenAlle teilhaberschaftenAlle = new TeilhaberschaftenAlle();
									RootPanel.get("content").clear();
									RootPanel.get("content").add(teilhaberschaftenAlle);
									return;
			}
			}
		});
	

		// Add it to the root panel.
		RootPanel.get("leftmenutree").add(navigationTreePanel);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(vPanel);
	}
}

