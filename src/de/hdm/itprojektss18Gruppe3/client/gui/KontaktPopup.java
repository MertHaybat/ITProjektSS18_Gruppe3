package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Date;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.AllAuspraegungenCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.CreateKontaktCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.UpdateAuspraegungCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.UpdateKontaktCallback;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class KontaktPopup extends PopupPanel{
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private VerticalPanel vPanel = new VerticalPanel();
	private TextArea ktNameTa = new TextArea();
	private Button speichern = new Button("Hinzufügen");
	
	public KontaktPopup(){
		super(true);
		setAnimationEnabled(true);
		ktNameTa.setReadOnly(true);
		ktNameTa.setValue("Geben Sie einen Kontaktnamen an.");
		ktNameTa.addClickHandler(new KontaktClickHandler());
		speichern.addClickHandler(new SpeichernClickHandler());
		vPanel.add(ktNameTa);
		vPanel.add(speichern);
		this.add(vPanel);
		
	}
	class SpeichernClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			kontaktmanagerVerwaltung.createKontakt(ktNameTa.getValue(), 0, nutzer.getId(),
					new CreateKontaktCallback());
		}
		
	}
	class KontaktClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			ktNameTa.setReadOnly(false);
			ktNameTa.setValue("");
		}
		
	}
	class CreateKontaktCallback implements AsyncCallback<Kontakt> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Erstellen des Kontakts: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kontakt result) {
			hide();
			Window.alert("Kontakt wurde erfolgreich erstellt.");
			KontaktForm kontaktForm = new KontaktForm(result);
		}

	}
}