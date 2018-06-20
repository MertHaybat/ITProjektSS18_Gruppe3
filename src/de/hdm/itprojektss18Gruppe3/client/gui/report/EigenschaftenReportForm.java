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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.AllKontaktEigenschaftenAndAuspraegungen;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;

public class EigenschaftenReportForm extends HorizontalPanel {

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	private Button btAllAuspraegungen = new Button("Report Starten");

	private ListBox listboxEigenschaften = new ListBox();
	private TextBox tbAuspraegung = new TextBox();

	private Label labelbEigenschaft = new Label("Eigenschaften: ");
	private Label labelAuspraegung = new Label("Eigenschaftsausprägung: ");

	private VerticalPanel vpanel = new VerticalPanel();

	public EigenschaftenReportForm() {

		reportverwaltung.findAllEigenschaften(new EigenschaftenCallback());

		listboxEigenschaften.setVisibleItemCount(1);

		listboxEigenschaften.setStylePrimaryName("listbox-report");
		btAllAuspraegungen.setStylePrimaryName("reportButton");

		this.add(labelbEigenschaft);
		this.add(listboxEigenschaften);
		this.add(labelAuspraegung);
		this.add(tbAuspraegung);
		this.add(btAllAuspraegungen);

		RootPanel.get("content").add(this);

		btAllAuspraegungen.addClickHandler(new AllAuspraegungenClickHandler());
	}
	
	class AllAuspraegungenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
//			if (tbAuspraegung.getValue()==""){
//				Window.alert("Bitte geben Sie eine Eigenschaftsausprägung ein.");
//			}
//			else {
			vpanel.clear();
			vpanel.add(new AllKontaktEigenschaftenAndAuspraegungen(listboxEigenschaften.getSelectedValue(),
					tbAuspraegung.getValue()));
			RootPanel.get("content").add(vpanel);
//			}
		}
		
	}

	class EigenschaftenCallback implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden aus der Datenbank" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			// TODO Auto-generated method stub
			for (Eigenschaft eigenschaft : result) {
				listboxEigenschaften.addItem(eigenschaft.getBezeichnung());
			}
		}

	}
}