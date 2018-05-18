package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.gui.AllKontaktView;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktlistView;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktmanagerCellTree;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS18Gruppe3 implements EntryPoint {

	private HorizontalPanel vpanel = new HorizontalPanel();
	private Label welcomeMessage = new Label("Willkommen beim Kontaktmanager");
	private Label instructionMessage = new Label("Bitte melde dich mit deinem Google Konto an");
	private TextBox tb = new TextBox();
	private Button testButtonKontaktlistView = new Button("KontaktlistView");
	private Button testButtonAllKontaktView = new Button("Alle Kontakte");
	private Button nix = new Button("LEER");
	private static KontaktmanagerAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();

	@Override
	public void onModuleLoad() {

		welcomeMessage.addStyleName("headline");
		vpanel.setStylePrimaryName("headlinePanel");
		vpanel.add(welcomeMessage);

		vpanel.add(testButtonKontaktlistView);
		vpanel.add(testButtonAllKontaktView);
		testButtonKontaktlistView.addClickHandler(new KontaktlistViewClickHandler());
		testButtonAllKontaktView.addClickHandler(new AllKontaktViewClickHandler());

		RootPanel.get("content").clear();
		RootPanel.get("content").add(vpanel);
		nix.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				verwaltung.createNutzer(tb.getValue(), new AsyncCallback<Nutzer>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Nein");
					}

					@Override
					public void onSuccess(Nutzer result) {
						Window.alert("Ja");
					}

				});

			}
		});

	}

	public class KontaktlistViewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktlistView kontaktlistView = new KontaktlistView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktlistView);
		}
	}
	
	public class AllKontaktViewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			AllKontaktView allKontaktView = new AllKontaktView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(allKontaktView);
		}
	}
}
