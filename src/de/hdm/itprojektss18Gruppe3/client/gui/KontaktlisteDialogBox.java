package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;

/**
 * DialogBox zum Hinzufügen einer neuen Kontaktliste
 * 
 * @version 1.0 23 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktlisteDialogBox extends DialogBox {
	
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	
	private Label kontaktlisteLabel = new Label("Neue Kontaktliste: ");
	
	private TextBox tkontaktliste = new TextBox();
	
	private Button bspeichern = new Button("speichern");
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	public KontaktlisteDialogBox() {
		
		vPanel.add(kontaktlisteLabel);
		vPanel.add(tkontaktliste);
		vPanel.add(bspeichern);
		
		vPanel.add(hPanel);
		this.add(hPanel);
		
		bspeichern.addClickHandler(new insertKontaktlisteClickHandler());
		
	}
	
	public class createKontaktlisteCallback implements AsyncCallback<Kontaktliste> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Kontaktliste result) {
			// TODO Auto-generated method stub
			hide();
		}
	}
	
	public class insertKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			kontaktmanagerVerwaltung.createKontaktliste(tkontaktliste.getValue(), 1, new createKontaktlisteCallback());
		}
	}
}