package de.hdm.itprojektss18Gruppe3.client.gui.report;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.AllKontaktTeilhaberschaften;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class TeilnahmenReportForm extends HorizontalPanel {

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	private Button btAllKontaktTeilhaberschaften = new Button("Report Starten");

	private ListBox lbNutzerEigentuemer = new ListBox();
	private ListBox lbNutzerTeilhabender = new ListBox();

	private Label lbEigentuemer = new Label("Eigentümer: ");
	private Label lbTeilhabender = new Label("Teilhabender: ");

	private VerticalPanel vpanel = new VerticalPanel();

	public TeilnahmenReportForm() {

		reportverwaltung.findNutzer(new NutzerForTeilhaberschaftCallback());

		lbNutzerEigentuemer.setVisibleItemCount(1);
		lbNutzerTeilhabender.setVisibleItemCount(1);

		lbNutzerTeilhabender.setStylePrimaryName("listbox-report");
		lbNutzerEigentuemer.setStylePrimaryName("listbox-report");

		this.add(lbEigentuemer);
		this.add(lbNutzerEigentuemer);
		this.add(lbTeilhabender);
		this.add(lbNutzerTeilhabender);
		this.add(btAllKontaktTeilhaberschaften);

		RootPanel.get("content").add(this);

		btAllKontaktTeilhaberschaften.addClickHandler(new AllKontaktTeilhaberschaftenClickHandler());
	
	}

	class NutzerForTeilhaberschaftCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden aus der Datenbank" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO
			for (Nutzer nutzer : result) {
				lbNutzerEigentuemer.addItem(nutzer.getMail());
				lbNutzerTeilhabender.addItem(nutzer.getMail());
			}
		}

	}
	
	class AllKontaktTeilhaberschaftenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			vpanel.clear();
			vpanel.add(new AllKontaktTeilhaberschaften(lbNutzerEigentuemer.getSelectedValue(),
					lbNutzerTeilhabender.getSelectedValue()));
			RootPanel.get("content").add(vpanel);
		}
		
	}
}
