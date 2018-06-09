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

import de.hdm.itprojektss18Gruppe3.client.AllKontakte;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;

public class KontakteReportForm extends HorizontalPanel{

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	private Button btAllNutzer = new Button("Report Starten");

	private ListBox listboxNutzer = new ListBox();
	private Label labelbNutzer = new Label("Kontakt: ");
	
	private VerticalPanel vpanel = new VerticalPanel();

	public KontakteReportForm() {
		
		reportverwaltung.findNutzer(new AllKontakteReport());
		
		listboxNutzer.setVisibleItemCount(1);
		listboxNutzer.setStylePrimaryName("listbox-report");
		
		this.add(labelbNutzer);
		this.add(listboxNutzer);
		this.add(btAllNutzer);
		
		RootPanel.get("content").add(this);

		btAllNutzer.addClickHandler(new AllKontakteClickHandler());



	}
	
	class AllKontakteReport implements AsyncCallback <Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden aus der Datenbank" + caught.getMessage());

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO Auto-generated method stub
			for (Nutzer nutzer : result) {
				listboxNutzer.addItem("Alle");
				listboxNutzer.addItem(nutzer.getMail());
			}
		}


		
	}
	
	class AllKontakteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			if (listboxNutzer.getSelectedValue() =="Alle") {
				
			}
			else{
				
				vpanel.clear();
				vpanel.add(new AllKontakte(listboxNutzer.getSelectedValue()));
				RootPanel.get("content").add(vpanel);

			}
		}
	
	}

		



	
}
