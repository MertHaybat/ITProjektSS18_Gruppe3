package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.CreateKontaktlisteDialogBox.closeKontaktlisteClickHandler;
import de.hdm.itprojektss18Gruppe3.client.gui.CreateKontaktlisteDialogBox.createKontaktlisteCallback;
import de.hdm.itprojektss18Gruppe3.client.gui.CreateKontaktlisteDialogBox.insertKontaktlisteClickHandler;
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
		vPanel.setWidth("300px");
		this.add(vPanel);
		
		deleteButton.addClickHandler(new deleteKontaktlisteClickHandler());
		abortButton.addClickHandler(new closeClickHandler());
		
	}
	
	public class deleteKontaktlisteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen: "+caught.getMessage());
			vPanel.remove(deleteButton);
			vPanel.clear();
		}

		@Override
		public void onSuccess(Void result) {
			setText("Kontaktliste gelöscht");
			vPanel.remove(deleteButton);
			vPanel.remove(abortButton);
			vPanel.clear();
			vPanel.add(okButton);
			hide();
			CustomTreeModel ctm = new CustomTreeModel();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);
			AllKontaktView allkontaktView = new AllKontaktView();

//			RootPanel.get("content").clear();
//			RootPanel.get("content").add(kontaktlisteBox);	
		}
	}
	
	public class deleteKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
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
