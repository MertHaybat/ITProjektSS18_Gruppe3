package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;

/**
 * DialogBox zum Hinzufügen von Eigenschaften und Eigenschaftsausprägungen
 * 
 * @author giuseppegalati
 * @version 1.0 23 May 2018
 *
 */
public class CreateEigenschaftsauspraegungDialogBox extends DialogBox {

	private VerticalPanel vPanel = new VerticalPanel();
	private VerticalPanel vPanel2 = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private Label eigenschaftLabel = new Label("Eigenschaft: ");
	private Label auspraegungLabel = new Label("Eigenschaftsausprägung: ");
	private Button erstellen = new Button("Erstellen");
	private Button abbrechen = new Button("Abbrechen");
	private TextBox tbEigenschaft = new TextBox();
	private TextBox tbEigenschaftsauspraegung = new TextBox();

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private Kontakt kontakt = new Kontakt();
	private Kontaktliste kontaktliste = new Kontaktliste();

	public CreateEigenschaftsauspraegungDialogBox(Kontakt k, Kontaktliste kontaktliste) {
		this.kontakt = k;
		this.kontaktliste = kontaktliste;
		/**
		 * Struktur des linken VerticalPanels
		 */
		vPanel.add(eigenschaftLabel);
		vPanel.add(tbEigenschaft);
		vPanel.add(erstellen);

		/**
		 * Struktur des rechten VerticalPanels
		 */
		vPanel2.add(auspraegungLabel);
		vPanel2.add(tbEigenschaftsauspraegung);
		vPanel2.add(abbrechen);

		/**
		 * Struktur der gesamten Seite. Die beiden VerticalPanels werden dem
		 * HorizontalPanel hinzugefügt.
		 */
		hPanel.add(vPanel);
		hPanel.add(vPanel2);
		this.add(hPanel);

		/**
		 * Funktionalität des Buttons anlegen.
		 */
		erstellen.addClickHandler(new insertEigenschaftClickHandler());
		abbrechen.addClickHandler(new AbbruchClickHandler());

	}

	/**
	 * ClickHandler um Vorgang abzubrechen und DialogBox zu schließen.
	 * 
	 */
	private class AbbruchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

	}

	/**
	 * Callback um Eigenschaft und Eigenschaftsausprägung zu erstellen
	 *
	 */
	class createEigenschaftsauspraegungCallback implements AsyncCallback<Eigenschaftsauspraegung> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Eigenschaftsauspraegung result) {
			hide();
			KontaktForm kForm = new KontaktForm(kontakt);
			KontaktlistView klisteView = new KontaktlistView(kontaktliste);

		}

	}

	/*
	 * ClickHandler, der den Callback ansteuert und die eingegebenen Werte
	 * übergibt.
	 */
	class insertEigenschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String input = tbEigenschaft.getValue();
			String inputAuspraegung = tbEigenschaftsauspraegung.getValue();
			if (input.equals("PLZ") || input.equals("Telefonnummer")) {
				if (!inputAuspraegung.matches("[0-9]*")) {
					Window.alert("Für diese Eigenschaft müssen Sie Zahlen als Ausprägung eingeben!");
				} else {
					kontaktmanagerVerwaltung.createEigenschaftsauspraegung(tbEigenschaftsauspraegung.getValue(),
							kontakt.getId(), 0, tbEigenschaft.getValue(), new createEigenschaftsauspraegungCallback());
				}
			} else if (input.equals("Geburtsdatum")) {
				if (!inputAuspraegung.matches("[0-9][0-9].[0-9][0-9].[0-9][0-9][0-9][0-9]")) {
					Window.alert("Bitte beachten Sie das Format: TT.MM.YYYY");
				} else {
					kontaktmanagerVerwaltung.createEigenschaftsauspraegung(tbEigenschaftsauspraegung.getValue(),
							kontakt.getId(), 0, tbEigenschaft.getValue(), new createEigenschaftsauspraegungCallback());
				}
			} else {
				kontaktmanagerVerwaltung.createEigenschaftsauspraegung(tbEigenschaftsauspraegung.getValue(),
						kontakt.getId(), 0, tbEigenschaft.getValue(), new createEigenschaftsauspraegungCallback());
			}
		}

	}

}
