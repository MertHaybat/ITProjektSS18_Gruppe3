package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.NewKontaktlisteDialogBox.closeKontaktlisteClickHandler;
import de.hdm.itprojektss18Gruppe3.client.gui.NewKontaktlisteDialogBox.createKontaktlisteCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.NewKontaktlisteDialogBox.insertKontaktlisteClickHandler;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class DeleteKontaktlisteDialogBox extends DialogBox {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	/**
	 * Instanziierung vom VerticalPanel 
	 */
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel hPanel2 = new HorizontalPanel();

	/**
	 * Instanziierung der Buttons "Löschen" und "Abbrechen"
	 * @see bspeichern, babbrechen 
	 */
	private Button deleteButton = new Button("Löschen");
	private Button abortButton = new Button("Abbrechen");
	private Button okButton = new Button("OK");
	private Kontaktliste kontaktlisteToDelete;
	/**
	 * Non-Argument-Konstruktor
	 * @param kontaktlisteToDelete 
	 */
	public DeleteKontaktlisteDialogBox(Kontaktliste kontaktlisteToDelete) {
		this.kontaktlisteToDelete = kontaktlisteToDelete;
		
		setText("Soll die Kontaktliste " + kontaktlisteToDelete.getBezeichnung() + " wirklich gelöscht werden?");
		hPanel2.add(deleteButton);
		hPanel2.add(abortButton);
		
		vPanel.add(hPanel);
		vPanel.add(hPanel2);
		this.add(vPanel);
		
		deleteButton.addClickHandler(new deleteKontaktlisteClickHandler());
		abortButton.addClickHandler(new closeClickHandler());
		
	}
	
	public class deleteKontaktlisteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			setText("Fehler beim Löschen");
			
		}

		@Override
		public void onSuccess(Void result) {
			setText("Kontaktliste gelöscht");
			vPanel.remove(deleteButton);
			vPanel.remove(abortButton);

			vPanel.clear();
			vPanel.add(okButton);
			okButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			KontaktlistView kontaktlisteBox = new KontaktlistView();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktlisteBox);	
		}
	}
	
	public class deleteKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.deleteKontaktlisteByID(kontaktlisteToDelete, new deleteKontaktlisteCallback());
		}
	}
	
	public class closeClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}
		
	}
}
