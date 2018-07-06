package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/*
 * Klasse, welche in das Footer Div implementiert wird und die FUßzeile unseres Kontaktmanagers bilder
 * Hier findet sich auch der Button um den aktuellen Nutzer zu löschen sowie das Impressum
 * 
 * @autor: kevinhofmann
 */

public class Footer extends HorizontalPanel {

	private Label footerLabel = new HTML("IT Projekt SS18 | Hochschule der Medien Stuttgart |");
	private Button impressum = new Button("Impressum");
	private Button deleteNutzer = new Button("Nutzer löschen");
	private HorizontalPanel footerPanel = new HorizontalPanel();
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();


	public Footer() {
		impressum.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Impressum ip = new Impressum();

			}
		});
		
		deleteNutzer.addClickHandler(new DeleteNutzerClickHandler());
		deleteNutzer.setStylePrimaryName("impressumButton");
		impressum.setStylePrimaryName("impressumButton");
		footerPanel.add(footerLabel);
		footerPanel.add(deleteNutzer);
		footerPanel.add(new HTML("| "));
		footerPanel.add(impressum);
		footerPanel.setStylePrimaryName("footerPanel");
		RootPanel.get("footer").clear();
		RootPanel.get("footer").add(footerPanel);
	}

	public void run() {

	}

	public class DeleteNutzerClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			boolean deleteNutzer = Window.confirm("Möchten Sie den Nutzer löschen?");
			if(deleteNutzer == true){
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				nutzer.setMail(Cookies.getCookie("email"));
				kontaktmanagerVerwaltung.deleteNutzer(nutzer, new DeleteNutzerCallback());	
			} 
		}

	}
	public class DeleteNutzerCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen des Nutzers: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			Anchor signOutLink = new Anchor();
			Window.alert("Nutzer wurde gelöscht");
			signOutLink.setHref(Cookies.getCookie("signout"));
			Window.open(signOutLink.getHref(), "_self", "");
		}

	}
}
