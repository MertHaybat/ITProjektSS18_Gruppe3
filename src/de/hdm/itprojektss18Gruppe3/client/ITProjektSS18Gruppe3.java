package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS18Gruppe3 implements EntryPoint {

	private VerticalPanel vpanel = new VerticalPanel();
	private TextBox tb = new TextBox();
	private Button bt = new Button("Abschicken");
	private static KontaktmanagerAdministrationAsync verwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	@Override
	public void onModuleLoad() {
		RootPanel.get("nameFieldContainer").clear();
		vpanel.add(tb);
		vpanel.add(bt);
		RootPanel.get("nameFieldContainer").add(vpanel);
		bt.addClickHandler(new ClickHandler(){

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
	
}