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

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

/**
 * DialogBox zum Hinzufügen von Eigenschaften und Eigenschaftsausprägungen
 * 
 * @author giuseppegalati
 * @version 1.0 23 May 2018
 *
 */
public class NewEigenschaftsauspraegungDialogBox extends DialogBox{

	private VerticalPanel vPanel = new VerticalPanel();
	private VerticalPanel vPanel2 = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private Label eigenschaftLabel = new Label("Eigenschaft: ");
	private Button speichern = new Button("speichern");
	private TextBox tbEigenschaft = new TextBox();
	private TextBox tbEigenschaftsauspraegung = new TextBox();
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	Kontakt kontakt = new Kontakt();

	
	public NewEigenschaftsauspraegungDialogBox (final Kontakt kontakt) {
	
	/**
	 * Struktur des linken VerticalPanels
	 */
	vPanel.add(eigenschaftLabel);
	vPanel.add(tbEigenschaft);
	vPanel.add(speichern);
	
	/**
	 * Struktur des rechten VerticalPanels 
	 */
	vPanel2.add(tbEigenschaftsauspraegung);
		
	/**
	 * Struktur der gesamten Seite. 
	 * Die beiden VerticalPanels werden dem HorizontalPanel hinzugefügt.
	 */
	vPanel.add(hPanel);
	vPanel2.add(hPanel);
	this.add(hPanel);
	

	/**
	 * Funktionalität des Buttons anlegen. 
	 */
	speichern.addClickHandler(new insertEigenschaftClickHandler());

	
	}
	
	/**
	 * Callback um Eigenschaft und Eigenschaftsausprägung zu erstellen
	 *
	 */
	class createEigenschaftsauspraegungCallback implements AsyncCallback<Eigenschaftsauspraegung> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Eigenschaftsauspraegung result) {
		// TODO Auto-generated method stub
		hide();
	}
		
	}

	/*
	 * ClickHandler, der den Callback ansteuert und die eingegebenen Werte übergibt.
	 */
	 class insertEigenschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			kontaktmanagerVerwaltung.createEigenschaftsauspraegung
			(tbEigenschaftsauspraegung.getValue(), 0, kontakt.getId(), tbEigenschaft.getValue(), 
					new createEigenschaftsauspraegungCallback());
	
		}
	 
	 
	 }
	 
}
	
